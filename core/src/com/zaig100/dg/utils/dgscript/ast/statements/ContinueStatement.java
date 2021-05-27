package com.zaig100.dg.utils.dgscript.ast.statements;

public class ContinueStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }

    public String toString() {
        return "continue";
    }
}
