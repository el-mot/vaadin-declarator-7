package com.vaadin.idea.declarator7;

import com.intellij.ide.highlighter.custom.SyntaxTable;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lang.html.HTMLParser;
import com.intellij.lang.xml.XMLParserDefinition;
import com.intellij.lexer.HtmlHighlightingLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.psi.xml.XmlTokenType;
import org.jetbrains.annotations.NotNull;


public class VaadinDeclaratorParserDefinition implements ParserDefinition {


    private static final SyntaxTable TABLE = new SyntaxTable();
    static {
        TABLE.addKeyword1("v-button");
        TABLE.addKeyword1("v-vertical-layout");
        TABLE.addKeyword2("html");
        TABLE.setStartComment("<!--");
        TABLE.setEndComment("-->");
    }
    public static IFileElementType DVML_FILE = new IFileElementType(VaadinDeclaratorLanguage.INSTANCE);

    @NotNull
    public Lexer createLexer(Project project) {
        return new HtmlHighlightingLexer();
    }

    public IFileElementType getFileNodeType() {
        return DVML_FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return XmlTokenType.WHITESPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return XmlTokenType.COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new HTMLParser();
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return PsiUtilCore.NULL_PSI_ELEMENT;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new VaadinDeclaratorFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        final Lexer lexer = createLexer(left.getPsi().getProject());
        return XMLParserDefinition.canStickTokensTogetherByLexerInXml(left, right, lexer, 0);
    }

}
