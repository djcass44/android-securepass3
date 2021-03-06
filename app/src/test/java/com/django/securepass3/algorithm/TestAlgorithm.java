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

package com.django.securepass3.algorithm;

import org.junit.Test;

public class TestAlgorithm<E extends BaseAlgorithm> {
    E getObject() {
        return null;
    }
    @Test
    public void validateOutputNotEmpty() {
        E object = getObject();
        String result = object.getResult();
        System.out.println(result);
        assert !result.isEmpty();
    }
    @Test
    public void validateAlgorithmLength() {
        E object = getObject();
        assert object.getLength() == Length.Companion.getDEFAULT();

        object.setLength(Length.Companion.getWINDOWS());
        assert object.getLength() == Length.Companion.getWINDOWS();
        String result = object.getResult();
        System.out.println(result);
        System.out.println(result.length());
        assert result.length() >= Length.Companion.getWINDOWS().getMin();
        assert result.length() <= Length.Companion.getWINDOWS().getMax();
    }
}
