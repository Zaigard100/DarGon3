package com.zaig100.dg.utils.dgscript;

import com.zaig100.dg.utils.dgscript.ast.Statement;
import com.zaig100.dg.utils.dgscript.visitors.AsignValid;
import com.zaig100.dg.utils.dgscript.visitors.FuntionAdder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class ScriptTest {


    @SuppressWarnings("NewApi")
    public static void main(String[] args) throws IOException {
        final String input = new String(Files.readAllBytes(Paths.get("testscript.dgs")));
        final List<Token> tokens = (new Lexer(input)).tokenize();

        token_show(tokens);

        long time = System.currentTimeMillis();
        final Statement program = new Parser(tokens).parse();
        System.out.println(System.currentTimeMillis() - time);

        statement_show(program);
        program.accept(new FuntionAdder());
        //program.accept(new ValPrinter());
        program.accept(new AsignValid());
        System.out.println("Script run:");
        program.execute();


    }

    static void token_show(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.print(tokens.indexOf(token) + ": ");
            System.out.println(token.toString());
        }
    }

    static void statement_show(Statement statements) {
        System.out.println(statements.toString());

    }

}
