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
package io.yupiik.bundlebee.junit5;

import io.yupiik.bundlebee.junit5.impl.Injectable;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.logging.LogRecord;

/**
 * If injected in method parameters, it enables to capture the command output.
 * <p>
 * IMPORTANT: this logic uses JUL so ensure to log on JUL and not another backend and that {@code io.yupiik.bundlebee} loggers have the needed level.
 */
@Injectable
public interface LogCapturer {
    /**
     * @param filter when set before using {@link CommandExecutor#run()} it filters the captured logs.
     *               If not set, only {@code io.yupiik.bundlebee.core} logs are retained.
     * @return this.
     */
    LogCapturer useFilter(Predicate<LogRecord> filter);

    /**
     * @return an accessor to the filter to use to keep/drop the logs.
     */
    Predicate<LogRecord> filter();

    /**
     * @return the list of currently captured logs.
     */
    Collection<LogRecord> all();
}
