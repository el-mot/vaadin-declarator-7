package com.vaadin.idea.declarator7;

import com.intellij.lang.Language;

public class VaadinDeclaratorLanguage extends Language {
    public static final Language INSTANCE = new VaadinDeclaratorLanguage();

    private VaadinDeclaratorLanguage() {
        super("DVML");
    }
}
