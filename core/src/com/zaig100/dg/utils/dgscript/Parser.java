package com.zaig100.dg.utils.dgscript;

import com.zaig100.dg.utils.dgscript.ast.BinExpression;
import com.zaig100.dg.utils.dgscript.ast.Expression;
import com.zaig100.dg.utils.dgscript.ast.NumExpression;
import com.zaig100.dg.utils.dgscript.ast.UnaryExpression;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
    private static final Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> tokens;
    private final int size;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public List<Expression> parse() {
        final List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(expression());
        }
        return result;
    }

    private Expression expression() {
        return additive();
    }

    private Expression additive() {
        Expression resault = multiplicative();

        while (true) {
            if (match(TokenType.PLUS)) {
                resault = new BinExpression('+', resault, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                resault = new BinExpression('-', resault, multiplicative());
                continue;
            }
            break;
        }

        return resault;
    }

    private Expression multiplicative() {
        Expression resault = unary();

        while (true) {
            if (match(TokenType.STAR)) {
                resault = new BinExpression('*', resault, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                resault = new BinExpression('/', resault, unary());
                continue;
            }
            break;
        }

        return resault;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        if (match(TokenType.PLUS)) {
            return new UnaryExpression('+', primary());
        }
        if (match(TokenType.STAR)) {
            return new UnaryExpression('*', primary());
        }
        if (match(TokenType.SLASH)) {
            return new UnaryExpression('/', primary());
        }
        return primary();
    }

    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new NumExpression(Double.parseDouble(current.getText()));
        }
        if (match(TokenType.LPAR)) {
            Expression result = expression();
            match(TokenType.RPAR);
            return result;
        }
        throw new RuntimeException("Unknown expression");

    }

    private boolean match(TokenType type) {
        if (type != get(0).getType()) return false;
        pos++;
        return true;
    }

    private Token get(int revPos) {
        final int position = pos + revPos;
        if (position >= size) return EOF;
        return tokens.get(position);
    }

}
