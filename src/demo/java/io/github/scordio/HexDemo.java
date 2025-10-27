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
package io.github.scordio;

// --8<-- [start:import]
import io.github.scordio.junit.converters.Hex;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
// --8<-- [end:import]

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HexDemo {

// @formatter:off
// --8<-- [start:test]

@ParameterizedTest
@ValueSource(strings = { "0x0A1B2C", "0A1B2C" })
void test(@Hex byte[] bytes) {
	assertArrayEquals(new byte[] { 0x0A, 0x1B, 0x2C }, bytes);
}
// --8<-- [end:test]
// @formatter:on

}
