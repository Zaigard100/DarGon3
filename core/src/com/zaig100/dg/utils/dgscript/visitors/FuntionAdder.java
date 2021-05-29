package com.zaig100.dg.utils.dgscript.visitors;

import com.zaig100.dg.utils.dgscript.ast.statements.FunctionDefineStatement;

public class FuntionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        s.body.accept(this);
        s.execute();
    }

}
