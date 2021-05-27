package com.zaig100.dg.utils.dgscript.ast.expression;

import com.zaig100.dg.utils.dgscript.lib.NumberVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public final class NumExpression implements Expression {

    private final Value value;

    public NumExpression(double value) {
        this.value = new NumberVal(value);
    }

    @Override
    public Value eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
