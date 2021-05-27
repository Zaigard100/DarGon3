package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;

public class PrintStatement implements Statement {

    private final Expression exp;

    public PrintStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public void execute() {
        System.out.print(exp.eval().asString());
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", "print", exp);
    }
}
