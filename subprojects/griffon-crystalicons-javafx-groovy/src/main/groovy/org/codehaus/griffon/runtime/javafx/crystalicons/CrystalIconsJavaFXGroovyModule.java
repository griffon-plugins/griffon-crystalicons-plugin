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
package org.codehaus.griffon.runtime.javafx.crystalicons;

import griffon.builder.javafx.CrystalIconsJavaFXBuilderCustomizer;
import griffon.core.injection.Module;
import griffon.inject.DependsOn;
import griffon.util.BuilderCustomizer;
import org.codehaus.griffon.runtime.core.injection.AbstractModule;
import org.kordamp.jipsy.ServiceProviderFor;

import javax.inject.Named;

/**
 * @author Andres Almiray
 */
@DependsOn("javafx-groovy")
@Named("crystalicons-javafx-groovy")
@ServiceProviderFor(Module.class)
public class CrystalIconsJavaFXGroovyModule extends AbstractModule {
    @Override
    protected void doConfigure() {
        // tag::bindings[]
        bind(BuilderCustomizer.class)
            .to(CrystalIconsJavaFXBuilderCustomizer.class)
            .asSingleton();
        // end::bindings[]
    }
}