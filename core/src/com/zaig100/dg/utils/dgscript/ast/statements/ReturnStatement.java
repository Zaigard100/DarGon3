package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;
import com.zaig100.dg.utils.dgscript.lib.NumberVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class ReturnStatement extends RuntimeException implements Statement {

    private final Expression exp;
    private Value val;

    public ReturnStatement() {
        exp = null;
        val = NumberVal.ZERO;
    }

    public ReturnStatement(Expression exp) {
        this.exp = exp;
    }

    public Value getVal() {
        return val;
    }

    @Override
    public void execute() {
        if (exp != null) val = exp.eval();
        throw this;
    }

    @Override
    public String toString() {
        if (exp != null) return "return" + exp.toString();
        return "return";
    }
}
