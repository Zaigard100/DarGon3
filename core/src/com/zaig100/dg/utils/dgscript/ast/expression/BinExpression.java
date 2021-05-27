package com.zaig100.dg.utils.dgscript.ast.expression;

import com.zaig100.dg.utils.dgscript.lib.NumberVal;
import com.zaig100.dg.utils.dgscript.lib.StringVal;
import com.zaig100.dg.utils.dgscript.lib.Value;

public final class BinExpression implements Expression {
    private final Expression ex1, ex2;
    private final char opr;

    public BinExpression(char opr, Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.opr = opr;
    }

    @Override
    public Value eval() {
        final Value val1 = ex1.eval();
        final Value val2 = ex2.eval();
        if (val1 instanceof StringVal || val2 instanceof StringVal) {
            final String str1 = val1.asString();
            final String str2 = val2.asString();
            switch (opr) {
                default:
                case '+':
                    return new StringVal(str1 + str2);
                case '*': {
                    final int itrs = (int) val2.asDouble();
                    final StringBuilder buffer = new StringBuilder();
                    for (int i = 0; i < itrs; i++) {
                        buffer.append(str1);
                    }
                    return new StringVal(buffer.toString());
                }
            }
        } else if (val1 instanceof NumberVal && val2 instanceof NumberVal) {
            final double num1 = val1.asDouble();
            final double num2 = val2.asDouble();
            switch (opr) {
                case '-':
                    return new NumberVal(num1 - num2);
                case '*':
                    return new NumberVal(num1 * num2);
                case '/':
                    return new NumberVal(num1 / num2);
                default:
                case '+':
                    return new NumberVal(num1 + num2);
            }
        }
        throw new RuntimeException("Invalid type : " + ex1.toString() + " or " + ex2.toString());
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", ex1, opr, ex2);
    }
}
