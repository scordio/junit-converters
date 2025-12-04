/*
 * Copyright © 2025 Stefano Cordio
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
import io.github.scordio.junit.converters.Base64;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
// --8<-- [end:import]

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Base64Demo {

// @formatter:off
// --8<-- [start:test]

@ParameterizedTest
@FieldSource("encoded")
void test(@Base64 byte[] bytes) {
	assertArrayEquals(new byte[] { 63, 63, 63 }, bytes);
}

static List<?> encoded = List.of("Pz8/", new byte[] { 80, 122, 56, 47 });
// --8<-- [end:test]
// @formatter:on

}
