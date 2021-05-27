package com.zaig100.dg.utils.dgscript.ast;

public final class BinExpression implements Expression {
    private final Expression ex1, ex2;
    private final char opr;

    public BinExpression(char opr, Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.opr = opr;
    }

    @Override
    public double evol() {
        switch (opr) {
            case '-':
                return ex1.evol() - ex2.evol();
            case '*':
                return ex1.evol() * ex2.evol();
            case '/':
                return ex1.evol() / ex2.evol();
            default:
            case '+':
                return ex1.evol() + ex2.evol();
        }
    }

    @Override
    public String toString() {
        return String.format("%s %c %s", ex1, opr, ex2);
    }
}
