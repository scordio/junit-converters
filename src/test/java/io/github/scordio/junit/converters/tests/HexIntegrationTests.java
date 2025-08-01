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
package io.github.scordio.junit.converters.tests;

import static io.github.scordio.junit.converters.tests.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.platform.testkit.engine.EventConditions.finishedWithFailure;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.cause;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.instanceOf;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.message;

import io.github.scordio.junit.converters.Hex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

class HexIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(4).succeeded(4));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@MethodSource("hexArguments")
		void test(@Hex byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> hexArguments() {
			return Stream.of( //
					arguments("", new byte[0]), //
					arguments("00", new byte[1]), //
					arguments("1234567890abcdef",
							new byte[] { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF }),
					arguments("FEDCBA0987654321",
							new byte[] { (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, 0x09, (byte) 0x87, 0x65, 0x43, 0x21 }));
		}

	}

	@Test
	void should_fail_with_unsupported_values() {
		executeTestsForClass(UnsupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(7).succeeded(0).failed(7))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(NullPointerException.class), message("'null' is unsupported"))))
			.haveExactly(3, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(ArgumentConversionException.class), message("Hex string must have even length"))))
			.haveExactly(2, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), //
					cause( //
							instanceOf(ArgumentConversionException.class),
							message("Invalid hex character at position 0"))))
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), //
					cause( //
							instanceOf(ArgumentConversionException.class),
							message("Invalid hex character at position 1"))));
	}

	static class UnsupportedValuesTestCase {

		@ParameterizedTest
		@NullSource
		@ValueSource(strings = { " ", "A", "  ", "AG", "GG", "AAA" })
		void test(@SuppressWarnings("unused") @Hex byte[] bytes) {
			// never called
		}

	}

	// https://github.com/junit-team/junit-framework/issues/4801
	@Test
	void should_fail_with_EmptySource() {
		executeTestsForClass(EmptySourceTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(1).succeeded(0).failed(1))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					message("Error converting parameter at index 0: HexArgumentConverter cannot convert objects of type [[B]. Only source objects of type [java.lang.String] are supported.")));
	}

	static class EmptySourceTestCase {

		@ParameterizedTest
		@EmptySource
		void test(@SuppressWarnings("unused") @Hex byte[] bytes) {
			// never called
		}

	}

}
