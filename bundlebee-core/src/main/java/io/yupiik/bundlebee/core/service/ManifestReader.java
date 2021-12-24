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
package io.yupiik.bundlebee.core.service;

import io.yupiik.bundlebee.core.descriptor.Manifest;
import io.yupiik.bundlebee.core.lang.Substitutor;
import io.yupiik.bundlebee.core.qualifier.BundleBee;
import org.apache.johnzon.jsonb.extension.JsonValueReader;
import org.eclipse.microprofile.config.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.stream.Collectors.joining;

@ApplicationScoped
public class ManifestReader {
    @Inject
    @BundleBee
    private Jsonb jsonb;

    @Inject
    private Config config;

    @Inject
    private Substitutor substitutor;

    public Manifest readManifest(final String location, final Supplier<InputStream> manifest) {
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(manifest.get(), StandardCharsets.UTF_8))) {
            final var content = substitutor.replace(reader.lines().collect(joining("\n")));
            final var json = jsonb.fromJson(content, JsonObject.class);
            final Manifest mf;
            if (json.containsKey("bundlebee")) { // it is a wrapped manifest, we enable that to easily enrich manifest.json with custom attributes without breaking jsonschema
                final var subJson = json.getJsonObject("bundlebee");
                mf = jsonb.fromJson(new JsonValueReader<>(subJson), Manifest.class);
            } else {
                mf = jsonb.fromJson(content, Manifest.class);
            }
            if (location != null && !location.isBlank() && mf.getAlveoli() != null) {
                mf.getAlveoli().stream()
                        .map(Manifest.Alveolus::getDescriptors)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .filter(it -> it.getLocation() == null)
                        .forEach(desc -> desc.setLocation(location));
            }
            return mf;
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
