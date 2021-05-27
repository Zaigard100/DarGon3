package com.zaig100.dg.utils.dgscript.ast.expression;

import com.zaig100.dg.utils.dgscript.lib.StringVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class StringExpression implements Expression {

    private final Value value;

    public StringExpression(String value) {
        this.value = new StringVal(value);
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
