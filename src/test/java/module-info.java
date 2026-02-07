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
@SuppressWarnings("requires-automatic")
open module io.github.scordio.tests.junit.converters {

	requires io.github.scordio.junit.converters;
	requires org.assertj.core;
	requires org.junit.jupiter.params;
	requires org.junit.platform.testkit;
	requires spring.core;

}
