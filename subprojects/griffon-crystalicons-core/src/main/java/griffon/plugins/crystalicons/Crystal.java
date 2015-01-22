/*
 * Copyright 2014-2015 the original author or authors.
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
package griffon.plugins.crystalicons;

import javax.annotation.Nonnull;

import static griffon.util.GriffonClassUtils.requireState;
import static griffon.util.GriffonNameUtils.isBlank;

/**
 * @author Andres Almiray
 */
public final class Crystal {
    private final String category;
    private final String description;

    public Crystal(@Nonnull String category, @Nonnull String description) {
        this.category = category;
        this.description = description;
    }

    @Nonnull
    public String getCategory() {
        return category;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Nonnull
    public String formatted() {
        return category + ":" + description;
    }

    @Nonnull
    public String asResource(int size) {
        requireState(size == 16 || size == 24 || size == 32, "Argument 'size' must be one of [16, 22, 32].");
        return "com/everaldo/crystal/" + size + "x" + size + "/" + category + "/" + description + ".png";
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Crystal)) return false;
        Crystal c = (Crystal) o;
        return category.equals(c.category) && description.equals(c.description);
    }

    public int hashCode() {
        return (category.hashCode() * 37) + (description.hashCode() * 31);
    }

    @Nonnull
    public static Crystal findByDescription(@Nonnull String description) {
        if (isBlank(description)) {
            throw new IllegalArgumentException("Description " + description + " is not a valid Crystal icon description");
        }
        if (!description.contains(":")) {
            throw new IllegalArgumentException("Description " + description + " is not a valid Crystal icon description");
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String[] parts = description.split(":");
        Crystal icon = new Crystal(parts[0].toLowerCase(), parts[1].toLowerCase());
        if (classLoader.getResource(icon.asResource(16)) != null) {
            return icon;
        } else if (classLoader.getResource(icon.asResource(24)) != null) {
            return icon;
        } else if (classLoader.getResource(icon.asResource(32)) != null) {
            return icon;
        }

        throw new IllegalArgumentException("Description " + description + " is not a a valid Crystal icon description");
    }
}
