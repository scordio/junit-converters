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

import io.github.scordio.junit.converters.SpringConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;

import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SpringConversionIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(12).succeeded(12));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@FieldSource
		void arrayToArray(@SpringConversion int[] ints, int[] expected) {
			assertThat(ints).isEqualTo(expected);
		}

		static List<?> arrayToArray = List.of(arguments(new String[] { "123", "456" }, new int[] { 123, 456 }));

		@ParameterizedTest
		@FieldSource
		void arrayToList(@SpringConversion List<Integer> ints, List<Integer> expected) {
			assertThat(ints).isEqualTo(expected);
		}

		static List<?> arrayToList = List.of( //
				arguments(new int[] { 123, 456 }, List.of(123, 456)),
				arguments(new Integer[] { 123, 456 }, List.of(123, 456)),
				arguments(new String[] { "123", "456" }, List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void arrayToObject(@SpringConversion int value, int expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> arrayToObject = List.of( //
				arguments(new int[] { 123 }, 123), //
				arguments(new Integer[] { 123 }, 123), //
				arguments(new String[] { "123" }, 123));

		@ParameterizedTest
		@FieldSource
		void arrayToString(@SpringConversion String string, String expected) {
			assertThat(string).isEqualTo(expected);
		}

		static List<?> arrayToString = List.of( //
				arguments(new int[] { 123, 456 }, "123,456"), //
				arguments(new Integer[] { 123, 456 }, "123,456"), arguments(new String[] { "123", "456" }, "123,456"));

		@ParameterizedTest
		@FieldSource("stringToList")
		void stringToList(@SpringConversion List<Integer> ints, List<Integer> expected) {
			assertThat(ints).isEqualTo(expected);
		}

		static List<?> stringToList = List.of( //
				arguments("123, 456", List.of(123, 456)), //
				arguments(List.of("123", "456"), List.of(123, 456)));

	}

}
