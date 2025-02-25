/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2022 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.maven;

import java.io.IOException;
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test for {@link FileHash}.
 *
 * @since 0.26
 */
final class FileHashTest {

    @Test
    void readsFromExistingFile(@TempDir final Path temp) throws IOException {
        final Path path = temp.resolve("1.txt");
        new Home(temp).save("hey, you", temp.relativize(path));
        MatcherAssert.assertThat(
            new FileHash(path).toString(),
            Matchers.startsWith("[-26, 1, -29, 113, ")
        );
    }

    @Test
    void readsFromAbsentFile(@TempDir final Path temp) {
        final Path path = temp.resolve("2.txt");
        MatcherAssert.assertThat(
            new FileHash(path).toString(),
            Matchers.equalTo("")
        );
    }

}
