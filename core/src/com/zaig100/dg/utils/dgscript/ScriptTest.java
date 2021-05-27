package com.zaig100.dg.utils.dgscript;

import com.zaig100.dg.utils.dgscript.ast.Expression;

import java.util.List;

public final class ScriptTest {

    public static void main(String[] args) {
        final String input = "(2 + 2) * 2";
        final List<Token> tokens = (new Lexer(input)).tokenize();

        for (Token token : tokens) {
            System.out.println(token.toString());
        }

        final List<Expression> expressions = new Parser(tokens).parse();

        for (Expression expression : expressions) {
            System.out.println(expression + " = " + expression.evol());
        }
    }

}
