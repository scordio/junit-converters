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
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

class BytesIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(8).succeeded(8));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@MethodSource({ "asciiArguments", "utf8Arguments" })
		void with_default_charset(@Bytes byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		@ParameterizedTest
		@MethodSource("asciiArguments")
		void with_ascii_charset(@Bytes(charset = "US-ASCII") byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		@ParameterizedTest
		@MethodSource({ "asciiArguments", "utf8Arguments" })
		void with_utf8_charset(@Bytes(charset = "UTF-8") byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		public static Stream<Arguments> utf8Arguments() {
			return Stream.of( //
					arguments("ä", new byte[] { -61, -92 }));
		}

		public static Stream<Arguments> asciiArguments() {
			return Stream.of( //
					arguments("", new byte[0]), //
					arguments("a", new byte[] { 97 }));
		}

	}

	@Test
	void should_fail_with_unsupported_values() {
		executeTestsForClass(UnsupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(3).succeeded(0).failed(3))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(NullPointerException.class), message("'null' is not supported"))))
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(ArgumentConversionException.class),
							message("Source type java.lang.Integer is not supported"))))
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), cause(instanceOf(ArgumentConversionException.class),
							message("Source type byte[] is not supported"))));
	}

	static class UnsupportedValuesTestCase {

		@ParameterizedTest
		@NullSource
		@ValueSource(ints = 42)
		void test(@SuppressWarnings("unused") @Bytes byte[] bytes) {
			// never called
		}

		// https://github.com/junit-team/junit-framework/issues/4801
		@ParameterizedTest
		@EmptySource
		void with_empty_source(@SuppressWarnings("unused") @Bytes byte[] bytes) {
			// never called
		}

	}

}
