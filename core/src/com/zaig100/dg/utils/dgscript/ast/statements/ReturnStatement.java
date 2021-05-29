package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.Statement;
import com.zaig100.dg.utils.dgscript.ast.Visitor;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class ReturnStatement extends RuntimeException implements Statement {

    public final Expression exp;
    public Value val;

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
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (exp != null) return "return" + exp.toString();
        return "return";
    }
}
