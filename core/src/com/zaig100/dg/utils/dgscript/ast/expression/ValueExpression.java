package com.zaig100.dg.utils.dgscript.ast.expression;

import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.Visitor;
import com.zaig100.dg.utils.dgscript.lib.Value;
import com.zaig100.dg.utils.dgscript.lib.Variables;

public class ValueExpression implements Expression {

    public final String name;

    public ValueExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variables.isExist(name))
            throw new RuntimeException("Noe-existent variable or constant " + name);
        return Variables.get(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        //return  String.format("%s[%f]",name,Constants.get(name));
        return name;
    }
}
