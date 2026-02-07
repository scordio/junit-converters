/*
 * Copyright © 2025-present Stefano Cordio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.scordio.demo;

// --8<-- [start:import]
import io.github.scordio.junit.converters.SpringConversion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
// --8<-- [end:import]

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpringConversionDemo {

// @formatter:off
// --8<-- [start:test]

@ParameterizedTest
@ValueSource(strings = "123, 456")
void test(@SpringConversion List<Integer> ints) {
	assertEquals(List.of(123, 456), ints);
}
// --8<-- [end:test]
// @formatter:on

}
