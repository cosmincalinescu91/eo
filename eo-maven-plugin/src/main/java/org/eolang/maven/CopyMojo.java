/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2021 Yegor Bugayenko
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;

/**
 * Make a file with a listing of all EO objects in this module.
 *
 * @since 0.11
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@Mojo(
    name = "copy-sources",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    threadSafe = true
)
@SuppressWarnings("PMD.ImmutableField")
public final class CopyMojo extends SafeMojo {

    /**
     * Dir with sources.
     */
    public static final String DIR = "EO-SOURCES";

    /**
     * Replacer or version.
     */
    private static final Pattern REPLACE = Pattern.compile(
        "^(\\+rt .+):0\\.0\\.0(.*)$",
        Pattern.MULTILINE
    );

    /**
     * Directory in which .eo files are located.
     * @checkstyle MemberNameCheck (7 lines)
     */
    @Parameter(
        required = true,
        defaultValue = "${project.basedir}/src/main/eo"
    )
    private File sourcesDir;

    /**
     * Target directory with resources to be packaged in JAR.
     * @checkstyle MemberNameCheck (7 lines)
     */
    @Parameter(
        required = true,
        defaultValue = "${project.build.directory}/classes"
    )
    private File classesDir;

    /**
     * The version to use for 0.0.0 replacements.
     * @checkstyle MemberNameCheck (7 lines)
     */
    @Parameter(required = true, defaultValue = "${project.version}")
    private String version;

    @Override
    public void exec() throws IOException {
        final Path target = this.classesDir.toPath().resolve(CopyMojo.DIR);
        for (final Path src : new Walk(this.sourcesDir.toPath())) {
            new Save(
                CopyMojo.REPLACE
                    .matcher(new TextOf(new InputOf(src)).asString())
                    .replaceAll(String.format("\1:%s\2", this.version)),
                target.resolve(
                    src.toAbsolutePath().toString().substring(
                        this.sourcesDir.toPath().toAbsolutePath().toString().length() + 1
                    )
                )
            ).save();
        }
    }

}