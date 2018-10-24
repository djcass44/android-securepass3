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

package com.django.securepass3.algorithm

import java.security.SecureRandom

class Golf: BaseAlgorithm() {
    override fun getResult(): String {
        val random = SecureRandom()
        var num = random.nextInt(length.max - length.min) + length.min
        val builder = StringBuilder()
        while (--num > 0) {
            val t = if (num.rem(2) == 0) "aeiouy" else "bcdfghjklmnpqrstvwxz"
            val pos = random.nextInt(6 + (num.rem(2) * 14))
            builder.append(t[pos])
        }
        return builder.toString()
    }
}