package com.zaig100.dg.utils.dgscript.ast.statements;

import com.zaig100.dg.utils.dgscript.lib.Functions;
import com.zaig100.dg.utils.dgscript.lib.UserDefFunc;

import java.util.List;

public class FunctionDefineStatement implements Statement {

    private final String name;
    private final List<String> argNames;
    private final Statement body;

    public FunctionDefineStatement(String name, List<String> argNames, Statement body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public void execute() {
        Functions.set(name, new UserDefFunc(argNames, body));
    }

    @Override
    public String toString() {
        return "def " + name + "(" + argNames.toString() + ") " + "{\n" + body.toString() + "}";
    }
}
