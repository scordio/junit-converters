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

import static io.github.scordio.junit.converters.Base64.Encoding.BASIC;
import static io.github.scordio.junit.converters.Base64.Encoding.MIME;
import static io.github.scordio.junit.converters.Base64.Encoding.URL;
import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.platform.testkit.engine.EventConditions.finishedWithFailure;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.cause;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.instanceOf;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.message;

import io.github.scordio.junit.converters.Base64;
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

class Base64IntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(65).succeeded(65));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@EmptySource
		void with_empty_source(@Base64 byte[] bytes) {
			assertThat(bytes).isEqualTo(new byte[0]);
		}

		@ParameterizedTest
		@MethodSource({ "basicArguments", "commonArguments" })
		void with_default_encoding(@Base64 byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		@ParameterizedTest
		@MethodSource({ "basicArguments", "commonArguments" })
		void with_basic_encoding(@Base64(encoding = BASIC) byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> basicArguments() {
			return Stream.of(//
					new TestCase("Pz8/", new byte[] { 63, 63, 63 }),
					new TestCase("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==",
							new byte[58]))
				.flatMap(TestCase::toArguments);
		}

		@ParameterizedTest
		@MethodSource({ "urlArguments", "commonArguments" })
		void with_url_encoding(@Base64(encoding = URL) byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> urlArguments() {
			return Stream.of( //
					new TestCase("Pz8_", new byte[] { 63, 63, 63 }),
					new TestCase("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==",
							new byte[58]))
				.flatMap(TestCase::toArguments);
		}

		@ParameterizedTest
		@MethodSource({ "mimeArguments", "commonArguments" })
		void with_mime_encoding(@Base64(encoding = MIME) byte[] bytes, byte[] expected) {
			assertThat(bytes).isEqualTo(expected);
		}

		static Stream<Arguments> mimeArguments() {
			return Stream.of( //
					new TestCase("Pz\r\n8/", new byte[] { 63, 63, 63 }),
					new TestCase("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\nAA==",
							new byte[58]))
				.flatMap(TestCase::toArguments);
		}

		static Stream<Arguments> commonArguments() {
			return Stream.of( //
					new TestCase("", new byte[0]), //
					new TestCase("AA", new byte[1]), //
					new TestCase("AA==", new byte[1]), //
					new TestCase("AAA", new byte[2]), //
					new TestCase("AAA=", new byte[2]), //
					new TestCase("AAAA", new byte[3]))
				.flatMap(TestCase::toArguments);
		}

		@SuppressWarnings({ "ArrayRecordComponent" })
		private record TestCase(String actual, byte[] expected) {

			private Stream<Arguments> toArguments() {
				return Stream.of(arguments(actual, expected), arguments(actual.getBytes(UTF_8), expected));
			}

		}

	}

	@Test
	void should_fail_with_unsupported_values() {
		executeTestsForClass(UnsupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(6).succeeded(0).failed(6))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(NullPointerException.class), message("'null' is not supported"))))
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(ArgumentConversionException.class),
							message("Source type java.lang.Integer is not supported"))))
			.haveExactly(2, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(IllegalArgumentException.class),
							message("Input byte[] should at least have 2 bytes for base64 bytes"))))
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(IllegalArgumentException.class), message("Illegal base64 character 20"))))
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), cause(instanceOf(IllegalArgumentException.class),
							message("Last unit does not have enough valid bits"))));
	}

	static class UnsupportedValuesTestCase {

		@ParameterizedTest
		@NullSource
		@ValueSource(ints = 42)
		@ValueSource(strings = { " ", "  ", "A", "A=" })
		void test(@SuppressWarnings("unused") @Base64 byte[] bytes) {
			// never called
		}

	}

}
