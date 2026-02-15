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
/**
 * Collection of {@link org.junit.jupiter.params.converter.ArgumentConverter argument
 * converters} for JUnit parameterized testing.
 *
 * @see org.junit.jupiter.params.ParameterizedClass
 * @see org.junit.jupiter.params.ParameterizedTest
 */
@SuppressWarnings("requires-automatic")
module io.github.scordio.junit.converters {

	requires static transitive org.jspecify;

	requires static spring.context;
	requires static spring.core;

	requires org.junit.jupiter.params;

	exports io.github.scordio.junit.converters;

	opens io.github.scordio.junit.converters to org.junit.platform.commons;

}
