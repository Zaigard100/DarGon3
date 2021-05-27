package com.zaig100.dg.utils.dgscript;

import com.zaig100.dg.utils.dgscript.ast.statements.Statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class ScriptTest {


    @SuppressWarnings("NewApi")
    public static void main(String[] args) throws IOException {
        final String input = new String(Files.readAllBytes(Paths.get("testscript.dgs")));
        long time = System.currentTimeMillis();
        final List<Token> tokens = (new Lexer(input)).tokenize();
        System.out.println(System.currentTimeMillis() - time);

        //token_show(tokens);

        time = System.currentTimeMillis();
        final List<Statement> statements = new Parser(tokens).parse();
        System.out.println(System.currentTimeMillis() - time);

        //statement_show(statements);

        System.out.println("Script run:");
        System.out.println();
        for (Statement statement : statements) {
            statement.execute();
        }

    }

    static void token_show(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }

    static void statement_show(List<Statement> statements) {
        for (Statement statement : statements) {
            System.out.println(statement);
        }
    }

}
