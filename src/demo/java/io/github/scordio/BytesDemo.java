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
import io.github.scordio.junit.converters.Bytes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
// --8<-- [end:import]

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BytesDemo {

	static class WithStrings {

// @formatter:off
// --8<-- [start:test-strings]

@ParameterizedTest
@ValueSource(strings = "value")
void test(@Bytes byte[] bytes) {
	assertArrayEquals(new byte[] { 118, 97, 108, 117, 101 }, bytes);
}
// --8<-- [end:test-strings]
// @formatter:on

	}

	static class WithNumbers {

// @formatter:off
// --8<-- [start:test-numbers]

@ParameterizedTest
@ValueSource(ints = 0x12345678)
void test(@Bytes byte[] bytes) {
	assertArrayEquals(new byte[] { 0x12, 0x34, 0x56, 0x78 }, bytes);
}
// --8<-- [end:test-numbers]
// @formatter:on

	}

}
