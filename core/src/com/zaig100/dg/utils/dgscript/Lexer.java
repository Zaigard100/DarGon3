package com.zaig100.dg.utils.dgscript;

import java.util.ArrayList;
import java.util.List;

public final class Lexer {

    private static final String OPERATOR_CHARS = "+-*/()=";
    private static final TokenType[] OPREATORS_TOKNS = {
            TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH, TokenType.LPAR, TokenType.RPAR, TokenType.EQ
    };

    private final String input;
    private final int lenght;

    private final List<Token> tokens;

    private int pos;

    public Lexer(String input) {
        this.input = input;
        lenght = input.length();

        tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (pos < lenght) {
            final char curent = peek(0);
            if (Character.isDigit(curent)) tokinizeNum();
            else if (Character.isLetter(curent)) tokinizeWord();
            else if (curent == '\"') {
                tokinizeText();
            } else if (OPERATOR_CHARS.indexOf(curent) != -1) {
                tokinizeOpe();
            } else {
                next();
            }
        }
        return tokens;
    }

    private void tokinizeOpe() {
        addtoken(OPREATORS_TOKNS[OPERATOR_CHARS.indexOf(peek(0))]);
        next();
    }

    private void tokinizeText() {
        next(); //skip "
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"':
                        current = next();
                        buffer.append('"');
                        continue;
                    case 'n':
                        current = next();
                        buffer.append('\n');
                        continue;
                    case 't':
                        current = next();
                        buffer.append('\t');
                        continue;
                }
                buffer.append('\\');
                continue;
            }
            if (current == '\"') break;
            buffer.append(current);
            current = next();
        }
        next();
        addtoken(TokenType.TEXT, buffer.toString());
    }


    private void tokinizeWord() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (Character.isLetterOrDigit(current) || (current == '_')) {
            buffer.append(current);
            current = next();
        }
        final String toString = buffer.toString();
        if (toString.equals("print")) {
            addtoken(TokenType.PRINT);
        } else {
            addtoken(TokenType.WORD, toString);
        }
    }

    private void tokinizeNum() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1)
                    throw new RuntimeException("Invalid number in " + pos + " : " + current);
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        addtoken(TokenType.NUMBER, buffer.toString());
    }

    private char next() {
        pos++;
        return peek(0);

    }

    private char peek(int revPos) {
        final int position = pos + revPos;
        if (position >= lenght) return '\0';
        return input.charAt(position);
    }

    private void addtoken(TokenType type) {
        addtoken(type, "");
    }

    private void addtoken(TokenType type, String text) {
        tokens.add(new Token(type, text));
    }

}
