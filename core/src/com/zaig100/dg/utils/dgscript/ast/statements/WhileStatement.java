package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;

public class WhileStatement implements Statement {
    private final Expression exp;
    private final Statement statement;

    public WhileStatement(Expression exp, Statement statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public void execute() {
        while (exp.eval().asNum() != 0) {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                //continue;
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("while ").append(exp).append(statement);
        return result.toString();
    }
}
