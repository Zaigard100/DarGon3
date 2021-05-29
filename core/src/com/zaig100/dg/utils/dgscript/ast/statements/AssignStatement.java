package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.Statement;
import com.zaig100.dg.utils.dgscript.ast.Visitor;
import com.zaig100.dg.utils.dgscript.lib.Value;
import com.zaig100.dg.utils.dgscript.lib.Variables;

public class AssignStatement implements Statement {

    public final String variable;
    public final Expression exp;

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
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, exp);
    }
}
