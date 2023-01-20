/*
 * Copyright (c) 2021-2023 - Yupiik SAS - https://www.yupiik.com
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
import java.util.stream.Stream;

/**
 * Just more common to implement than having an anonymous completer but strictly equivalent to a plain executable.
 */
public interface CompletingExecutable extends Executable, Executable.Completer {
    @Override
    default Completer completer() {
        return this;
    }

    @Override
    Stream<String> complete(Map<String, String> config, String optionName);
}
