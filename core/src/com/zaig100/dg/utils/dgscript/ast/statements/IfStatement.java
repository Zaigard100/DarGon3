package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;

public class IfStatement implements Statement {

    private final Expression exp;
    private final Statement ifSt, elseSt;

    public IfStatement(Expression exp, Statement ifSt, Statement elseSt) {
        this.exp = exp;
        this.ifSt = ifSt;
        this.elseSt = elseSt;
    }

    @Override
    public void execute() {
        final double result = exp.eval().asNum();
        if (result != 0) {
            ifSt.execute();
        } else if (elseSt != null) {
            elseSt.execute();
        }

    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("if ").append(exp).append(ifSt);
        if (elseSt != null) {
            result.append("\nelse ").append(elseSt);
        }
        return result.toString();
    }
}
