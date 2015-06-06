package com.vaadin.idea.declarator7;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class VaadinDeclaratorFileTypeFactory extends FileTypeFactory {
        public void createFileTypes(@NotNull FileTypeConsumer consumer) {
            consumer.consume(VaadinDeclaratorFileType.INSTANCE, VaadinDeclaratorFileType.FILE_EXTENSION);
    }
}
