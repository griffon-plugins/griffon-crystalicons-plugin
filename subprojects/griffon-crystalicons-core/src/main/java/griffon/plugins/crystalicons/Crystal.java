/*
 * Copyright 2014-2017 the original author or authors.
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
import static griffon.util.GriffonNameUtils.requireNonBlank;

/**
 * @author Andres Almiray
 */
public final class Crystal {
    private static final String ERROR_DESCRIPTION_BLANK = "Argument 'description' must not be blank";
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
        requireValidSize(size);
        return "com/everaldo/crystal/" + size + "x" + size + "/" + category + "/" + description + ".png";
    }

    @Nonnull
    public static String asResource(@Nonnull String description) {
        int size = 16;
        checkDescription(description);

        String[] parts = description.split(":");
        if (parts.length == 3) {
            try {
                size = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw invalidDescription(description, e);
            }
        }

        Crystal crystal = findByDescription(description, size);
        return crystal.asResource(size);
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
        checkDescription(description);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String[] parts = description.split(":");
        Crystal icon = new Crystal(parts[0].toLowerCase(), parts[1].toLowerCase());
        if (parts.length == 3) {
            int size = 16;
            try {
                size = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw invalidDescription(description, e);
            }
            if (classLoader.getResource(icon.asResource(size)) != null) {
                return icon;
            }
        }

        if (classLoader.getResource(icon.asResource(16)) != null) {
            return icon;
        } else if (classLoader.getResource(icon.asResource(24)) != null) {
            return icon;
        } else if (classLoader.getResource(icon.asResource(32)) != null) {
            return icon;
        }

        throw invalidDescription(description);
    }

    @Nonnull
    public static Crystal findByDescription(@Nonnull String description, int size) {
        checkDescription(description);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String[] parts = description.split(":");
        Crystal icon = new Crystal(parts[0].toLowerCase(), parts[1].toLowerCase());
        if (classLoader.getResource(icon.asResource(size)) != null) {
            return icon;
        }

        throw invalidDescription(description);
    }

    public static int requireValidSize(int size) {
        requireState(size == 16 || size == 24 || size == 32, "Argument 'size' must be one of [16, 24, 32].");
        return size;
    }

    private static void checkDescription(String description) {
        if (isBlank(description)) {
            throw new IllegalArgumentException("Description is blank");
        }
        if (!description.contains(":")) {
            throw invalidDescription(description);
        }
    }

    @Nonnull
    public static IllegalArgumentException invalidDescription(@Nonnull String description) {
        requireNonBlank(description, ERROR_DESCRIPTION_BLANK);
        throw new IllegalArgumentException("Description " + description + " is not a valid Crystal icon description");
    }

    @Nonnull
    public static IllegalArgumentException invalidDescription(@Nonnull String description, Exception e) {
        requireNonBlank(description, ERROR_DESCRIPTION_BLANK);
        throw new IllegalArgumentException("Description " + description + " is not a valid Crystal icon description", e);
    }
}
