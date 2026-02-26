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

import io.github.scordio.junit.converters.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ElementsIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(16).succeeded(16));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_booleans(@Elements boolean[] elements, boolean[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_booleans = List.of(arguments("true, false", new boolean[] { true, false }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_chars(@Elements char[] elements, char[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_chars = List.of(arguments("a, b, c", new char[] { 'a', 'b', 'c' }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_bytes(@Elements byte[] elements, byte[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_bytes = List.of(arguments("1, 2, 3", new byte[] { 1, 2, 3 }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_shorts(@Elements short[] elements, short[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_shorts = List.of(arguments("1, 2, 3", new short[] { 1, 2, 3 }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_integers(@Elements int[] elements, int[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_integers = List.of(arguments("1, 2, 3", new int[] { 1, 2, 3 }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_longs(@Elements long[] elements, long[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_longs = List.of(arguments("1, 2, 3", new long[] { 1L, 2L, 3L }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_floats(@Elements float[] elements, float[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_floats = List.of(arguments("1, 2, 3", new float[] { 1.0f, 2.0f, 3.0f }));

		@ParameterizedTest
		@FieldSource
		void array_of_primitive_doubles(@Elements double[] elements, double[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_primitive_doubles = List.of(arguments("1, 2, 3", new double[] { 1.0, 2.0, 3.0 }));

		@ParameterizedTest
		@FieldSource
		void array_of_integers(@Elements Integer[] elements, Integer[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_integers = List.of(arguments("1, 2, 3", new Integer[] { 1, 2, 3 }));

		@ParameterizedTest
		@FieldSource
		void array_of_strings(@Elements String[] elements, String[] expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> array_of_strings = List.of(arguments("a, b, c", new String[] { "a", "b", "c" }));

		@ParameterizedTest
		@FieldSource
		void iterable_of_integers(@Elements Iterable<Integer> elements, Iterable<Integer> expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> iterable_of_integers = List.of(arguments("1, 2, 3", List.of(1, 2, 3)));

		@ParameterizedTest
		@FieldSource
		void collection_of_integers(@Elements Collection<Integer> elements, Collection<Integer> expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> collection_of_integers = List.of(arguments("1, 2, 3", List.of(1, 2, 3)));

		@ParameterizedTest
		@FieldSource
		void list_of_integers(@Elements List<Integer> elements, List<Integer> expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> list_of_integers = List.of(arguments("1, 2, 3", List.of(1, 2, 3)));

		@ParameterizedTest
		@FieldSource
		void set_of_integers(@Elements Set<Integer> elements, Set<Integer> expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> set_of_integers = List.of(arguments("1, 2, 3", Set.of(1, 2, 3)));

		@ParameterizedTest
		@FieldSource
		void list_of_strings(@Elements List<String> elements, List<String> expected) {
			assertThat(elements).isEqualTo(expected);
		}

		static List<?> list_of_strings = List.of( //
				arguments("a, b, c", List.of("a", "b", "c")), //
				arguments(" a, b ,c ", List.of("a", "b", "c")));

	}

}
