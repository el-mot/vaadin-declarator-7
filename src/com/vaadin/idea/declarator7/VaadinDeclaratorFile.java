package com.vaadin.idea.declarator7;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class VaadinDeclaratorFile extends PsiFileBase {
    public VaadinDeclaratorFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, VaadinDeclaratorLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return VaadinDeclaratorFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Vaadin Declarative File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
