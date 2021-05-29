package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.Statement;
import com.zaig100.dg.utils.dgscript.ast.Visitor;

public class FunctionStatement implements Statement {

    public final Expression func;

    public FunctionStatement(Expression func) {
        this.func = func;
    }

    @Override
    public void execute() {
        func.eval();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return func.toString();
    }
}
