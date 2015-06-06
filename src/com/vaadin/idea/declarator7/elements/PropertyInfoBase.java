/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.idea.declarator7.elements;

import com.intellij.psi.PsiMethod;
import com.vaadin.idea.declarator7.parse.NamingUtils;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public abstract class PropertyInfoBase implements PropertyInfo {
    private final PsiMethod method;
    private String declName = null;
    private String javaName = null;

    public PropertyInfoBase(PsiMethod method) {
        this.method = method;
    }

    @Override
    public String getJavaName() {
        if (javaName == null) {
            String methodName = method.getName();
            javaName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        }
        return javaName;
    }

    @Override
    public String getDeclName() {
        if (declName == null) {
            declName = NamingUtils.camelCaseToDashes(getJavaName(), 0, "");
        }
        return declName;
    }
}
