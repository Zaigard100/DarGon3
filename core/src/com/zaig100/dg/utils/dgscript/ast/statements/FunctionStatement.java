package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;

public class FunctionStatement implements Statement {

    private final Expression func;

    public FunctionStatement(Expression func) {
        this.func = func;
    }

    @Override
    public void execute() {
        func.eval();
    }

    @Override
    public String toString() {
        return func.toString();
    }
}
