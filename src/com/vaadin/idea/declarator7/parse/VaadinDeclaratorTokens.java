package com.vaadin.idea.declarator7.parse;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface VaadinDeclaratorTokens {
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    IElementType COMMENT = new VaadinDeclaratorElementType("COMMENT");

    IElementType TAG_CHARACTERS = new VaadinDeclaratorElementType("TAG_CHARACTERS");
    IElementType ATTR_CHARACTERS = new VaadinDeclaratorElementType("ATTR_CHARACTERS");
    IElementType ATTR_VALUE_CHARACTERS = new VaadinDeclaratorElementType("ATTR_VALUE_CHARACTERS");

    TokenSet COMMENTS = TokenSet.create(COMMENT);
    TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);

}
