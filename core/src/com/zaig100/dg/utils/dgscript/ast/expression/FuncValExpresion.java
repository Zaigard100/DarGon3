package com.zaig100.dg.utils.dgscript.ast.expression;

import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.Visitor;
import com.zaig100.dg.utils.dgscript.lib.Function;
import com.zaig100.dg.utils.dgscript.lib.FunctionVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public class FuncValExpresion implements Expression {
    public final Value value;

    public FuncValExpresion(Function value) {
        this.value = new FunctionVal(value);
    }

    @Override
    public Value eval() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
