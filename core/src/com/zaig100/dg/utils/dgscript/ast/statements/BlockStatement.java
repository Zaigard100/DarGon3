package com.zaig100.dg.utils.dgscript.ast.statements;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {

    List<Statement> statemens;

    public BlockStatement() {
        this.statemens = new ArrayList<>();
    }

    public void add(Statement statement) {
        statemens.add(statement);
    }

    @Override
    public void execute() {
        for (Statement statement : statemens) {
            statement.execute();
        }
    }

    @SuppressWarnings("NewApi")
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        for (Statement statement : statemens) {
            buffer.append(statement.toString()).append(System.lineSeparator());
        }
        return buffer.toString();
    }
}
