package com.zaig100.dg.utils.dgscript.ast;

public class UnaryExpression implements Expression {

    private final Expression ex;
    private final char opr;

    public UnaryExpression(char opr, Expression ex) {
        this.ex = ex;
        this.opr = opr;
    }

    @Override
    public double evol() {
        switch (opr) {
            case '-':
                return -ex.evol();
            case '*':
                return ex.evol() * ex.evol();
            case '/':
                return Math.sqrt(ex.evol());
            default:
            case '+':
                return ex.evol();
        }
    }

    @Override
    public String toString() {
        return opr + "(" + ex.toString() + ")";
    }
}
