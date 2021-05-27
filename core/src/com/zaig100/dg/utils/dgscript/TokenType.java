package com.zaig100.dg.utils.dgscript;

public enum TokenType {
    NUMBER,
    WORD,
    TEXT,

    //kes words
    PRINT,
    IF,
    ELSE,
    WHILE,
    FOR,

    PLUS,
    MINUS,
    STAR,
    SLASH,

    LPAR, // (
    RPAR, // )
    LBRACE, // {
    RBRACE, // }
    COMMA, // ,
    DO,
    BRAKE,
    CONTINUE,

    EQ, // =
    EQEQ, // ==
    EXCL, //!
    EXCLEQ, //!=
    LT, // <
    LTEQ, // <=
    GT, // >
    GTEQ, // >=

    BAR, // &
    BARBAR, // &&
    AMP, // |
    AMPAMP, // ||

    EOF,

}
