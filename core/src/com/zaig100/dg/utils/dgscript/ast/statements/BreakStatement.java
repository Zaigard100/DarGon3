package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.Statement;
import com.zaig100.dg.utils.dgscript.ast.Visitor;

public class BreakStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "break";
    }
}
