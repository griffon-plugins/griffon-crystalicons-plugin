/*
 * Copyright 2014 the original author or authors.
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
package griffon.javafx.support.crystalicons;

import griffon.plugins.crystalicons.Crystal;
import javafx.scene.image.Image;

import javax.annotation.Nonnull;
import java.net.URL;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public class CrystalIcon extends Image {
    private final Crystal crystal;
    private final int size;

    public CrystalIcon(@Nonnull Crystal crystal) {
        this(crystal, 16);
    }

    public CrystalIcon(@Nonnull Crystal crystal, int size) {
        super(toURL(crystal, size), true);
        this.crystal = crystal;
        this.size = size;
    }

    public CrystalIcon(@Nonnull String description) {
        this(Crystal.findByDescription(description));
    }

    @Nonnull
    private static String toURL(@Nonnull Crystal crystal, int size) {
        requireNonNull(crystal, "Argument 'crystal' must not be null.");
        String resource = crystal.asResource(size);
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalArgumentException("Icon " + crystal.formatted() + ":" + size + " does not exist");
        }
        return url.toExternalForm();
    }

    @Nonnull
    public Crystal getCrystal() {
        return crystal;
    }

    public int getSize() {
        return size;
    }
}