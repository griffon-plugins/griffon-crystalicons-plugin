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
package griffon.swing.support.crystalicons;

import griffon.plugins.crystalicons.Crystal;

import javax.annotation.Nonnull;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.net.URL;

import static griffon.plugins.crystalicons.Crystal.invalidDescription;
import static griffon.plugins.crystalicons.Crystal.requireValidSize;
import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public class CrystalIcon extends ImageIcon {
    private static final String ERROR_CRYSTAL_NULL = "Argument 'crystal' must not be null";
    private Crystal crystal;
    private int size;

    public CrystalIcon() {
        this(Crystal.findByDescription("actions:bookmark"));
    }

    public CrystalIcon(@Nonnull Crystal crystal) {
        this(crystal, 16);
    }

    public CrystalIcon(@Nonnull Crystal crystal, int size) {
        super(toURL(crystal, size));
        this.crystal = requireNonNull(crystal, ERROR_CRYSTAL_NULL);
        this.size = size;
    }

    public CrystalIcon(@Nonnull String description) {
        this(Crystal.findByDescription(description));
        setCrystal(description);
    }

    @Nonnull
    private static URL toURL(@Nonnull Crystal crystal, int size) {
        requireNonNull(crystal, ERROR_CRYSTAL_NULL);
        String resource = crystal.asResource(size);
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalArgumentException("Icon " + crystal.formatted() + " does not exist");
        }
        return url;
    }

    @Nonnull
    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(@Nonnull Crystal crystal) {
        this.crystal = requireNonNull(crystal, ERROR_CRYSTAL_NULL);
        setImage(Toolkit.getDefaultToolkit().getImage(toURL(crystal, size)));
    }

    public void setCrystal(@Nonnull String description) {
        requireNonBlank(description, "Argument 'description' must not be blank");

        String[] parts = description.split(":");
        if (parts.length == 3) {
            try {
                int s = Integer.parseInt(parts[2]);
                size = requireValidSize(s);
            } catch (NumberFormatException e) {
                throw invalidDescription(description, e);
            }
        } else if (size == 0) {
            size = 16;
        }

        crystal = Crystal.findByDescription(description);
        setImage(Toolkit.getDefaultToolkit().getImage(toURL(crystal, size)));
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = requireValidSize(size);
        setImage(Toolkit.getDefaultToolkit().getImage(toURL(crystal, size)));
    }
}
