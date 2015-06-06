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

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiPackage;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class VaadinFramework extends TwinKeyMapImpl<TagBase> {

    private static final String COMPONENT_CLASS_NAME = "com.vaadin.ui.Component";
    private static final String CONTAINER_CLASS_NAME = "com.vaadin.ui.ComponentContainer";

    private TwinKeyMap<TagBase> containers = new TwinKeyMapImpl<>();
    private TwinKeyMap<TagBase> components = new TwinKeyMapImpl<>();

    private VaadinFramework(PsiClass[] classes, PsiClass componentClass, PsiClass containerClass) {
        StaticTag meta = reg(new StaticTag("meta", "charset", "name", "content"));
        StaticTag head = reg(new StaticTag("head",new TwinKeyMapImpl<TagBase>(meta)));

        head.getAllowedChildren().addMember(meta);
        StaticTag body = reg(new StaticTag("body", containers));
        rootComponent = reg(new StaticTag("html",new TwinKeyMapImpl<TagBase>(head,body)));


        for (PsiClass psiClass : classes) {
            if (!psiClass.hasModifierProperty(PsiModifier.ABSTRACT) &&
                    psiClass.hasModifierProperty(PsiModifier.PUBLIC)) {
                TagBase.Type classType;
                if (psiClass.isInheritor(containerClass, true)) {
                    classType = TagBase.Type.CONTAINER;
                } else if (psiClass.isInheritor(componentClass, true)) {
                    classType = TagBase.Type.COMPONENT;
                } else continue;
                ComponentTag componentTag = reg(new ComponentTag(psiClass, classType));
                switch (classType) {
                    case COMPONENT:
                        components.addMember(componentTag);
                        break;
                    case CONTAINER:
                        components.addMember(componentTag);
                        containers.addMember(componentTag);
                        componentTag.setAllowedChildren(components);
                        break;
                }
            }
        }
    }

    private final StaticTag rootComponent;

    public TagBase getRootTag() {
        return rootComponent;
    }

    private <T extends TagBase> T reg(T tag) {
        addMember(tag);
        return tag;
    }

    public static VaadinFramework newInstance(CompletionParameters parameters) {
        PsiFile originalFile = parameters.getOriginalFile();
        Project p = originalFile.getProject();
        PsiPackage aPackage = JavaPsiFacade.getInstance(p).findPackage("com.vaadin.ui");
        if (aPackage == null) {
            return null;
        }
        PsiClass componentClass = null;
        PsiClass containerClass = null;
        PsiClass[] classes = aPackage.getClasses();
        for (PsiClass aClass : classes) {
            if (COMPONENT_CLASS_NAME.equals(aClass.getQualifiedName())) {
                componentClass = aClass;
                if (containerClass != null) break;
            }
            if (CONTAINER_CLASS_NAME.equals(aClass.getQualifiedName())) {
                containerClass = aClass;
                if (componentClass != null) break;
            }
        }
        if (componentClass == null || containerClass == null) return null;

        return new VaadinFramework(classes, componentClass, containerClass);
    }
}
