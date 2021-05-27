package com.zaig100.dg.utils.dgscript;

import com.zaig100.dg.utils.dgscript.ast.expression.BinExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.Expression;
import com.zaig100.dg.utils.dgscript.ast.expression.NumExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.StringExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.UnaryExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.ValueExpression;
import com.zaig100.dg.utils.dgscript.ast.statements.AssignStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.PrintStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.Statement;

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

    public List<Statement> parse() {
        final List<Statement> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }

    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        return assignmentStatement();
    }

    private Statement assignmentStatement() {
        final Token current = get(0);
        if (match(TokenType.WORD) && consume(TokenType.EQ)) {
            final String var = current.getText();
            return new AssignStatement(var, expression());
        }
        throw new RuntimeException("Unknown statment in token " + pos + ": " + current.toString());
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
        if (match(TokenType.WORD)) {
            return new ValueExpression(current.getText());
        }
        if (match(TokenType.TEXT)) {
            return new StringExpression(current.getText());
        }
        if (match(TokenType.LPAR)) {
            Expression result = expression();
            match(TokenType.RPAR);
            return result;
        }
        throw new RuntimeException("Unknown expression in token " + pos + ": " + current.toString());

    }

    private boolean consume(TokenType type) {
        if (type != get(0).getType())
            throw new RuntimeException("Token " + pos + get(0).getType() + "dosen't match" + type);
        pos++;
        return true;
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
