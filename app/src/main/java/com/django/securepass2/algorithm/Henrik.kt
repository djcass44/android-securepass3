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

package com.django.securepass2.algorithm

import java.lang.StringBuilder
import java.security.SecureRandom

/**
 * Based off https://stackoverflow.com/a/636170
 */
class Henrik: Algorithm {
    private val vowels: CharArray = "aeiou".toCharArray()
    private val consonants: CharArray = "bcdfghjklmnpqrstvwxyz".toCharArray()
    private val pairs = arrayListOf<String>()

    init {
        for (v in vowels) {
            for (c in consonants) {
                pairs.add("" + v + c)
            }
        }
    }

    override fun getResult(): String {
        val random = SecureRandom()
        val num = random.nextInt(3) + 3
        val builder = StringBuilder()
        for (i in 0 until num) {
            builder.append(pairs[random.nextInt(pairs.size - 1)])
        }
        val s5 = random.nextBoolean()
        if(s5)
            builder.deleteCharAt(builder.lastIndex)
        return builder.toString()
    }

}