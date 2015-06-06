package com.vaadin.idea.declarator7.parse;


import com.intellij.psi.tree.IElementType;
import com.vaadin.idea.declarator7.VaadinDeclaratorLanguage;
import org.jetbrains.annotations.NonNls;

public class VaadinDeclaratorElementType extends IElementType {

    public VaadinDeclaratorElementType(@NonNls String debugName) {
        super(debugName, VaadinDeclaratorLanguage.INSTANCE);
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        return "DVML:" + super.toString();
    }
}
