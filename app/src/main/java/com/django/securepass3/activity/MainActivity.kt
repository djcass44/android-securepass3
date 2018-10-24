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

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.django.securepass3.R
import com.django.securepass3.extendedcore.ThemedAppCompatActivity
import com.django.securepass3.theme.ThemeChoice
import com.django.securepass3.util.ThemeUtil

class MainActivity : ThemedAppCompatActivity() {
    private lateinit var textNoItems: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PhraseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ThemeChoice.theme = ThemeChoice.Theme.LIGHT

        textNoItems = findViewById(R.id.textEmpty)
        recyclerView = findViewById(R.id.recyclerView)

        val items = arrayListOf("test1", "test2", "test3")
        adapter = PhraseAdapter(this, items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
class PhraseAdapter(val context: Context, val items: ArrayList<String>): RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): PhraseViewHolder {
        return PhraseViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_phrase, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val item = items[position]
        holder.textTitle.text = item

        ThemeUtil.applyTheme(context, holder.itemView)
    }

    class PhraseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textTitle: TextView = itemView.findViewById(R.id.text)
    }
}
