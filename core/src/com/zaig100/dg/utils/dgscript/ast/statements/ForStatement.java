package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.ast.expression.Expression;

public class ForStatement implements Statement {

    private final Statement init;
    private final Expression exp;
    private final Statement increment;
    private final Statement statement;

    public ForStatement(Statement init, Expression exp, Statement increment, Statement statement) {
        this.init = init;
        this.exp = exp;
        this.increment = increment;
        this.statement = statement;
    }

    @Override
    public void execute() {
        for (init.execute(); exp.eval().asNum() != 0; increment.execute()) {
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
        return "for (" + init + "," + exp + "," + increment + ")" + statement;
    }
}
