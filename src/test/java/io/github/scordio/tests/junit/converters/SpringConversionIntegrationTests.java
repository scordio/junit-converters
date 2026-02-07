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
package io.github.scordio.tests.junit.converters;

import io.github.scordio.junit.converters.SpringConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;
import java.util.Map;

import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SpringConversionIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(13).succeeded(13));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@FieldSource
		void array_to_array(@SpringConversion int[] array, int[] expected) {
			assertThat(array).isEqualTo(expected);
		}

		static List<?> array_to_array = List.of(arguments(new String[] { "123", "456" }, new int[] { 123, 456 }));

		@ParameterizedTest
		@FieldSource
		void array_to_list(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> array_to_list = List.of( //
				arguments(new int[] { 123, 456 }, List.of(123, 456)),
				arguments(new Integer[] { 123, 456 }, List.of(123, 456)),
				arguments(new String[] { "123", "456" }, List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void array_to_object(@SpringConversion int value, int expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> array_to_object = List.of( //
				arguments(new int[] { 123, 456 }, 123), //
				arguments(new Integer[] { 123, 456 }, 123), //
				arguments(new String[] { "123", "456" }, 123));

		@ParameterizedTest
		@FieldSource
		void array_to_string(@SpringConversion String string, String expected) {
			assertThat(string).isEqualTo(expected);
		}

		static List<?> array_to_string = List.of( //
				arguments(new int[] { 123, 456 }, "123,456"), //
				arguments(new Integer[] { 123, 456 }, "123,456"), //
				arguments(new String[] { "123", "456" }, "123,456"));

		@ParameterizedTest
		@FieldSource
		void list_to_list(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> list_to_list = List.of(arguments(List.of("123", "456"), List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void map_to_map(@SpringConversion Map<Integer, Double> map, Map<Integer, Double> expected) {
			assertThat(map).isEqualTo(expected);
		}

		static List<?> map_to_map = List.of(arguments(Map.of("1", "123.4", "2", "567.8"), Map.of(1, 123.4, 2, 567.8)));

		@ParameterizedTest
		@FieldSource
		void string_to_list(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> string_to_list = List.of(arguments("123, 456", List.of(123, 456)));

	}

}
