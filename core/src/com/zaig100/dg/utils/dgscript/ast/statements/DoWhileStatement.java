package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;

public class DoWhileStatement implements Statement {
    private final Expression exp;
    private final Statement statement;

    public DoWhileStatement(Expression exp, Statement statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public void execute() {
        do {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                //continue;
            }
        } while (exp.eval().asNum() != 0);
    }

    @Override
    public String toString() {
        return "do" + statement + " while" + exp;
    }
}
