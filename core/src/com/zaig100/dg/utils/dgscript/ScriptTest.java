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
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
        time = System.currentTimeMillis();
        final List<Statement> expressions = new Parser(tokens).parse();
        System.out.println(System.currentTimeMillis() - time);
        for (Statement statement : expressions) {
            System.out.println(statement);
        }

        System.out.println("Script run:");

        for (Statement statement : expressions) {
            statement.execute();
        }

    }

}
