package com.zaig100.dg.utils.dgscript.ast;

public final class NumExpression implements Expression {

    private final double value;

    public NumExpression(double value) {
        this.value = value;
    }

    @Override
    public double evol() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
