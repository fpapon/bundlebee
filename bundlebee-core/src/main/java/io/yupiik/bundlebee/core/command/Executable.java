/*
 * Copyright (c) 2021 - Yupiik SAS - https://www.yupiik.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.yupiik.bundlebee.core.command;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface Executable {
    String UNSET = "<unset>";

    String name();

    String description();

    CompletionStage<?> execute();

    default Stream<String> complete(final Map<String, String> config, final String optionName) {
        return Stream.empty();
    }
}
