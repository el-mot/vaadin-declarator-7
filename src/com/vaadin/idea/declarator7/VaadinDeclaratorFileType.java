package com.vaadin.idea.declarator7;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class VaadinDeclaratorFileType extends LanguageFileType{

    public static final VaadinDeclaratorFileType INSTANCE = new VaadinDeclaratorFileType();
    public static final String FILE_EXTENSION = "dvml";

    public VaadinDeclaratorFileType() {
        super(VaadinDeclaratorLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "VaadinDeclarative";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Vaadin Declarative";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return FILE_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return VaadinDeclaratorIcons.ICON;
    }

}
