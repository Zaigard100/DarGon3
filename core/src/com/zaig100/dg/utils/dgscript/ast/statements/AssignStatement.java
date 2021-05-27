package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;
import com.zaig100.dg.utils.dgscript.lib.Value;
import com.zaig100.dg.utils.dgscript.lib.Variables;

public class AssignStatement implements Statement {

    final String variable;
    private final Expression exp;

    public AssignStatement(String variable, Expression exp) {
        this.variable = variable;
        this.exp = exp;
    }

    @Override
    public void execute() {
        final Value result = exp.eval();
        Variables.set(variable, result);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, exp);
    }
}
