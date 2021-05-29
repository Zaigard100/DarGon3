package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.Statement;
import com.zaig100.dg.utils.dgscript.ast.Visitor;
import com.zaig100.dg.utils.dgscript.ast.expression.ArrayAssignExpression;

public class ArrayAssignStatement implements Statement {

    public final ArrayAssignExpression array;
    public final Expression exp;

    public ArrayAssignStatement(ArrayAssignExpression array, Expression exp) {
        this.array = array;
        this.exp = exp;
    }

    @Override
    public void execute() {
        array.getArr().set(array.lastIndex(), exp.eval());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return array.toString() + " = " + exp.toString();
    }
}
