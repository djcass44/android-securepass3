/*
 *    Copyright 2018 Django Cass
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.django.securepass3.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.appyvet.materialrangebar.RangeBar
import com.django.securepass3.R
import com.django.securepass3.algorithm.Golf
import com.django.securepass3.algorithm.Henrik
import com.django.securepass3.algorithm.Length
import com.django.securepass3.extendedcore.RecyclerItemTouchHelper
import com.django.securepass3.extendedcore.ThemedAppCompatActivity
import com.django.securepass3.theme.ThemeChoice
import com.django.securepass3.util.ThemeUtil
import com.konifar.fab_transformation.FabTransformation

class MainActivity : ThemedAppCompatActivity() {
    private lateinit var textNoItems: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PhraseAdapter

    private lateinit var fab: FloatingActionButton
    private lateinit var fabRevealLayout: View
    private lateinit var buttonAddOk: Button
    private lateinit var buttonAddCancel: Button

    private lateinit var rangeBar: RangeBar
    private lateinit var spinnerType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ThemeChoice.theme = ThemeChoice.Theme.LIGHT

        textNoItems = findViewById(R.id.textEmpty)
        recyclerView = findViewById(R.id.recyclerView)

        val items = arrayListOf<String>()
        adapter = PhraseAdapter(this, items, object : PhraseAdapter.OnSizeRequestedListener {
            override fun onSizeRequested(size: Int) {
                textNoItems.visibility = if (size > 0) View.GONE else View.VISIBLE
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
                adapter.removeItem(position)
            }

        })
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            FabTransformation.with(fab).transformTo(fabRevealLayout)
        }
        fabRevealLayout = findViewById(R.id.addLayout)
        buttonAddOk = findViewById(R.id.buttonAddOk)
        buttonAddOk.setOnClickListener {
            generate()
        }
        buttonAddCancel = findViewById(R.id.buttonAddCancel)
        buttonAddCancel.setOnClickListener {
            FabTransformation.with(fab).transformFrom(fabRevealLayout)
        }

        rangeBar = findViewById(R.id.addRangeBar)
        rangeBar.tickStart = Length.DEFAULT2.min.toFloat()
        rangeBar.tickEnd = Length.DEFAULT2.max.toFloat()

        spinnerType = findViewById(R.id.adder_type_spinner)
        spinnerType.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayOf("Henrik", "Golf"))
//        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                when (position) {
//                    0 -> {
//                        rangeBar.tickStart =
//                    }
//                }
//            }
//        }
    }
    private fun generate() {
        val min = rangeBar.tickStart
        val max = rangeBar.tickEnd

        val length = Length(min.toInt(), max.toInt())
        val algorithm = when (spinnerType.selectedItemPosition) {
            1 -> Golf()
            else -> Henrik()
        }
        algorithm.length = length
        adapter.addItem(algorithm.getResult())
        recyclerView.scrollToPosition(0)
    }
}
class PhraseAdapter(private val context: Context, private val items: ArrayList<String>, private val onSizeRequestedListener: OnSizeRequestedListener?): RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): PhraseViewHolder {
        return PhraseViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_phrase, viewGroup, false))
    }

    override fun getItemCount(): Int {
        onSizeRequestedListener?.onSizeRequested(items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val item = items[position]
        holder.textTitle.text = item
        holder.itemView.setOnLongClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(context.getString(R.string.app_name), item)
            clipboard.primaryClip = clip
            Toast.makeText(context, context.getString(R.string.toast_copy), Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        ThemeUtil.applyTheme(context, holder.itemView)
    }

    class PhraseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textTitle: TextView = itemView.findViewById(R.id.text)
        var foregroundView: View = itemView.findViewById(R.id.foregroundView)
        var backgroundView: View = itemView.findViewById(R.id.backgroundView)
    }

    fun addItem(item: String) {
        items.add(0, item)
        notifyItemInserted(0)
    }
    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    interface OnSizeRequestedListener {
        fun onSizeRequested(size: Int)
    }
}
