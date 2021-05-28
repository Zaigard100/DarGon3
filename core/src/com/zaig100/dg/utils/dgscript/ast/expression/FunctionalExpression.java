package com.zaig100.dg.utils.dgscript.ast.expression;

import com.zaig100.dg.utils.dgscript.lib.Functions;
import com.zaig100.dg.utils.dgscript.lib.Value;

import java.util.ArrayList;
import java.util.List;

public class FunctionalExpression implements Expression {

    private final String name;
    private final List<Expression> args;

    public FunctionalExpression(String name) {
        this.name = name;
        args = new ArrayList<>();
    }

    public FunctionalExpression(String name, List<Expression> args) {
        this.name = name;
        this.args = args;
    }

    public void addArg(Expression exp) {
        args.add(exp);
    }

    @Override
    public Value eval() {
        final int size = args.size();
        Value[] vals = new Value[size];
        for (int i = 0; i < size; i++) {
            vals[i] = args.get(i).eval();
        }
        return Functions.get(name).execute(vals);
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(name).append("(");
        for (int i = 0; i < args.size(); i++) {
            buffer.append(args.get(i).toString());
            if (!(i < args.size() + 1)) buffer.append(",");
        }
        buffer.append(")");
        return buffer.toString();
    }
}
