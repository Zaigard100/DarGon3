package com.zaig100.dg.utils.dgscript.ast.statements;

public class BreakStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "break";
    }
}
