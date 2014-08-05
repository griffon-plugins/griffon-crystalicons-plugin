/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agratcow to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.javafx.support.crystalicons

import javafx.embed.swing.JFXPanel
import spock.lang.Specification

/**
 * @author Andres Almiray
 */
class CrystalIconSpec extends Specification {
    static {
        new JFXPanel()
    }

    def 'Invalid CrystalIcon arguments'(String arg) {
        when:
        new CrystalIcon((String) arg)

        then:
        thrown(IllegalArgumentException)

        where:
        arg << [null, '', ' ', 'foo', 'foo:foo', 'actions:foo']
    }
}
