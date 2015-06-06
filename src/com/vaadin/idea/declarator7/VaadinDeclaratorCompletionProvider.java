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
package com.vaadin.idea.declarator7;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import com.vaadin.idea.declarator7.elements.PropertyInfo;
import com.vaadin.idea.declarator7.elements.TagBase;
import com.vaadin.idea.declarator7.elements.VaadinFramework;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
class VaadinDeclaratorCompletionProvider extends CompletionProvider<CompletionParameters> {
    public void addCompletions(@NotNull CompletionParameters parameters,
                               ProcessingContext context,
                               @NotNull CompletionResultSet resultSet) {
        PsiElement psiContext = parameters.getPosition().getContext();
        if (psiContext instanceof XmlAttribute) {
            VaadinFramework vaadinFramework = VaadinFramework.newInstance(parameters);
            if (vaadinFramework == null) return;

            XmlTag psiTag = (XmlTag) psiContext.getParent();
            if (psiTag != null) {
                TagBase tag = vaadinFramework.getByDeclName(psiTag.getName());
                if (tag != null) {
                    Collection<String> declNames = tag.getProperties().getDeclNames();
                    for (String declName : declNames) {
                        //todo Insert as xml attribute
                        resultSet.addElement(LookupElementBuilder.create(declName + "=\"\""));
                    }
                    //TODO focusable elements and other custom attrs
                    resultSet.addElement(LookupElementBuilder.create(":expand=\"\""));
                    resultSet.addElement(LookupElementBuilder.create("tabindex=\"\""));
                    resultSet.addElement(LookupElementBuilder.create("_id=\"\""));
                }
            }

        } else if (psiContext instanceof XmlAttributeValue) {
            PsiElement attribute = psiContext.getParent();
            if (attribute instanceof XmlAttribute) {
                PsiElement psiElement = attribute.getParent();
                if (psiElement instanceof XmlTag) {
                    VaadinFramework vaadinFramework = VaadinFramework.newInstance(parameters);
                    if (vaadinFramework == null) return;
                    TagBase tag = vaadinFramework.getByDeclName(((XmlTag) psiElement).getName());
                    if (tag != null) {
                        PropertyInfo property = tag.getProperties().getByDeclName(((XmlAttribute) attribute).getName());
                        if (property != null) {
                            for (String s : property.getProposedValues()) {
                                resultSet.addElement(LookupElementBuilder.create(s));
                            }
                        }
                    }
                }
            }
        } else {
            PsiElement current = psiContext;
            Collection<String> allowedNames = null;
            while (current != null) {
                current = current.getParent();
                if (current instanceof XmlTag) {
                    VaadinFramework vaadinFramework = VaadinFramework.newInstance(parameters);
                    if (vaadinFramework == null) return;
                    TagBase closestParentTag = vaadinFramework.getByDeclName(((XmlTag) current).getName());
                    if (closestParentTag != null) {
                        allowedNames = closestParentTag.getAllowedChildren().getDeclNames();
                    } else {
                        allowedNames = vaadinFramework.getDeclNames();
                    }
                    break;
                } else if (current instanceof XmlDocument) {
                    VaadinFramework vaadinFramework = VaadinFramework.newInstance(parameters);
                    if (vaadinFramework == null) return;
                    allowedNames = Collections.singleton(vaadinFramework.getRootTag().getDeclName());
                    break;
                } else if (current instanceof XmlFile) return;

            }
            if(allowedNames!=null) {
                for (String s : allowedNames) {
                    resultSet.addElement(LookupElementBuilder.create(s));
                }
            }
            //todo take context in account
        }
    }

    /*
    private String dashesToCamelCase(String clsName, int startIndex, String prefix, String postfix) {
        StringBuilder dashName = new StringBuilder(prefix);
        for (int i = startIndex; i < clsName.length(); i++) {
            char c = clsName.charAt(i);
            if (Character.isUpperCase(c)) {
                if (dashName.length() > 0) dashName.append('-');
                dashName.append(Character.toLowerCase(c));
            } else {
                dashName.append(c);
            }
        }
        dashName.append(postfix);
        return dashName.toString();
    }
*/
}
