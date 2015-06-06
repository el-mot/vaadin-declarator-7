package com.vaadin.idea.declarator7.parse;

import com.intellij.lang.xml.XmlASTFactory;
import com.intellij.psi.impl.source.tree.HtmlFileElement;
import com.intellij.psi.impl.source.tree.LazyParseableElement;
import com.intellij.psi.tree.ILazyParseableElementType;
import com.vaadin.idea.declarator7.VaadinDeclaratorParserDefinition;

public class VaadinDeclaratorASTFactory extends XmlASTFactory {

    @Override
    public LazyParseableElement createLazy(ILazyParseableElementType type, CharSequence text) {
        if (type == VaadinDeclaratorParserDefinition.DVML_FILE) {
            return new HtmlFileElement(text);
        }
        return super.createLazy(type, text);
    }
}
