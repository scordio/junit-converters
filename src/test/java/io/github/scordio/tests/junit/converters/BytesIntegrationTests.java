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
package io.github.scordio.tests.junit.converters;

import static io.github.scordio.junit.converters.Bytes.ByteOrder.BIG_ENDIAN;
import static io.github.scordio.junit.converters.Bytes.ByteOrder.LITTLE_ENDIAN;
import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.platform.testkit.engine.EventConditions.finishedWithFailure;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.cause;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.instanceOf;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.message;

import io.github.scordio.junit.converters.Bytes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

class BytesIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(26).succeeded(26));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@MethodSource({ "asciiArguments", "utf8Arguments", "bigEndianArguments" })
		void with_default_attributes(@Bytes byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		@ParameterizedTest
		@MethodSource({ "asciiArguments", "utf8Arguments" })
		void with_string_values_and_utf8_charset(@Bytes(charset = "UTF-8") byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> utf8Arguments() {
			return Stream.of( //
					arguments("ä", new byte[] { -61, -92 }));
		}

		@ParameterizedTest
		@MethodSource("asciiArguments")
		void with_string_values_and_ascii_charset(@Bytes(charset = "US-ASCII") byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> asciiArguments() {
			return Stream.of( //
					arguments("", new byte[0]), //
					arguments("a", new byte[] { 97 }));
		}

		@ParameterizedTest
		@MethodSource("bigEndianArguments")
		void with_numeric_values_and_big_endian_order(@Bytes(order = BIG_ENDIAN) byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> bigEndianArguments() {
			return Stream.of( //
					arguments((byte) 0x12, new byte[] { 0x12 }), //
					arguments((short) 0x1234, new byte[] { 0x12, 0x34 }), //
					arguments(0x12345678, new byte[] { 0x12, 0x34, 0x56, 0x78 }), //
					arguments(0x123456780A1B2C3DL, new byte[] { 0x12, 0x34, 0x56, 0x78, 0x0A, 0x1B, 0x2C, 0x3D }),
					arguments((float) 0x12345678, new byte[] { 0x4D, -0x6F, -0x5E, -0x4C }),
					arguments((double) 0x123456780A1B2C3DL,
							new byte[] { 0x43, -0x4E, 0x34, 0x56, 0x78, 0x0A, 0x1B, 0x2C }));
		}

		@ParameterizedTest
		@MethodSource("littleEndianArguments")
		void with_numeric_values_and_little_endian_order(@Bytes(order = LITTLE_ENDIAN) byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> littleEndianArguments() {
			return Stream.of( //
					arguments((byte) 0x12, new byte[] { 0x12 }), //
					arguments((short) 0x1234, new byte[] { 0x34, 0x12 }), //
					arguments(0x12345678, new byte[] { 0x78, 0x56, 0x34, 0x12 }), //
					arguments(0x123456780A1B2C3DL, new byte[] { 0x3D, 0x2C, 0x1B, 0x0A, 0x78, 0x56, 0x34, 0x12 }),
					arguments((float) 0x12345678, new byte[] { -0x4C, -0x5E, -0x6F, 0x4D }),
					arguments((double) 0x123456780A1B2C3DL,
							new byte[] { 0x2C, 0x1B, 0x0A, 0x78, 0x56, 0x34, -0x4E, 0x43 }));
		}

	}

	@Test
	void should_fail_with_unsupported_values() {
		executeTestsForClass(UnsupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(2).failed(2))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(NullPointerException.class), message("'null' is not supported"))))
			.haveExactly(1, finishedWithFailure( // https://github.com/junit-team/junit-framework/issues/4801
					instanceOf(ParameterResolutionException.class), cause(instanceOf(ArgumentConversionException.class),
							message("Source type byte[] is not supported"))));
	}

	static class UnsupportedValuesTestCase {

		@ParameterizedTest
		@NullSource
		@EmptySource // https://github.com/junit-team/junit-framework/issues/4801
		void test(@SuppressWarnings("unused") @Bytes byte[] bytes) {
			// never called
		}

	}

}
