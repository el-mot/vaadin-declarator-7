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

import com.intellij.psi.PsiClass;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class StaticTag implements TagBase {

    public final String declName;
    public final TwinKeyMap<TagBase> allowedChildren;
    public final TwinKeyMap<PropertyInfo> properties;

    public StaticTag(String declName, String... properties) {
        this(declName, (TwinKeyMap<TagBase>) TwinKeyMapImpl.EMPTY,properties);
    }
    public StaticTag(String declName,TwinKeyMap<TagBase> allowedChildren, String... properties) {
        this.declName = declName;
        this.allowedChildren = allowedChildren;
        if (properties == null || properties.length == 0) {
            this.properties = (TwinKeyMap<PropertyInfo>) TwinKeyMapImpl.EMPTY;
        } else {
            this.properties = new TwinKeyMapImpl<>();
            for (String property : properties) {
                this.properties.addMember(new StaticAttr(property));
            }
        }
    }

    @Override
    public PsiClass getPsiClass() {
        return null;
    }

    @Override
    public TwinKeyMap<PropertyInfo> getProperties() {
        return properties;
    }

    @Override
    public Type getType() {
        return Type.STATIC;
    }

    @Override
    public TwinKeyMap<TagBase> getAllowedChildren() {
        return allowedChildren;
    }

    @Override
    public String getJavaName() {
        return null;
    }

    @Override
    public String getDeclName() {
        return declName;
    }

}
