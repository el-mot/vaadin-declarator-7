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
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeVisitor;
import com.vaadin.idea.declarator7.parse.NamingUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class ComponentTag implements TagBase {

    private final PsiClass psiClass;

    private final Type type;

    private String declName = null;
    private String javaName = null;

    private TwinKeyMap<PropertyInfo> properties = null;

    private TwinKeyMap<TagBase> allowedChildren = (TwinKeyMap<TagBase>) TwinKeyMapImpl.EMPTY;

    public ComponentTag(PsiClass psiClass, Type type) {
        this.psiClass = psiClass;
        this.type = type;
    }

    @Override
    public PsiClass getPsiClass() {
        return null;
    }

    @Override
    public TwinKeyMap<PropertyInfo> getProperties() {
        if (properties == null) initProperties();
        return properties;
    }

    private void initProperties() {
        properties = new TwinKeyMapImpl<>();
        PsiMethod[] allMethods = psiClass.getAllMethods();
        for (PsiMethod method : allMethods) {
            @NotNull String methodName = method.getName();
            if (method.hasModifierProperty(PsiModifier.PUBLIC) &&
                    methodName.length() > 4 && methodName.startsWith("set")) {
                PsiParameterList parameterList = method.getParameterList();
                if (parameterList.getParametersCount() == 1) {
                    PsiType type = parameterList.getParameters()[0].getType();
                    PropertyInfo propertyInfo = type.accept(new SetterVisitor(method));
                    //todo recognize types
                    if (propertyInfo != null)
                        properties.addMember(new StringPropertyInfo(method));
                }
            }
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getJavaName() {
        if (javaName == null) {
            javaName = psiClass.getName();
        }
        return javaName;
    }

    @Override
    public String getDeclName() {
        if (declName == null) {
            declName = NamingUtils.camelCaseToDashes(getJavaName(), 0, "v");
        }
        return declName;
    }

    @Override
    public TwinKeyMap<TagBase> getAllowedChildren() {
        return allowedChildren;
    }


    public void setAllowedChildren(TwinKeyMap<TagBase> allowedChildren) {
        this.allowedChildren = allowedChildren;
    }

    private class SetterVisitor extends PsiTypeVisitor<PropertyInfo> {
        private final PsiMethod method;

        public SetterVisitor(PsiMethod method) {
            this.method = method;
        }

        @Nullable
        @Override
        public PropertyInfo visitPrimitiveType(PsiPrimitiveType primitiveType) {
            switch (primitiveType.getPresentableText()) {
                case "boolean":
                    return new BooleanPropertyInfo(method);
                case "int":
                    return new IntPropertyInfo(method);
                case "double":
                    return new DoublePropertyInfo(method);
            }
            return new StringPropertyInfo(method);
        }

        @Nullable
        @Override
        public PropertyInfo visitClassType(PsiClassType classType) {
            if("String".equals(classType.getClassName())) {
                return new StringPropertyInfo(method);
            }
            //TODO boxed
            //TODO enums

            return null;
        }

        @Nullable
        @Override
        public PropertyInfo visitType(PsiType type) {
            return super.visitType(type);
        }
    }
}
