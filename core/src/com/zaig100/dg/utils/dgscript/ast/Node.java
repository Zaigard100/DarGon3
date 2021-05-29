package com.zaig100.dg.utils.dgscript.ast;

public interface Node {

    void accept(Visitor visitor);

}
