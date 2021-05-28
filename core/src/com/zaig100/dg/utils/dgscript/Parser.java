package com.zaig100.dg.utils.dgscript;

import com.zaig100.dg.utils.dgscript.ast.expression.BinExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.ConditionalExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.Expression;
import com.zaig100.dg.utils.dgscript.ast.expression.FunctionalExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.NumExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.StringExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.UnaryExpression;
import com.zaig100.dg.utils.dgscript.ast.expression.ValueExpression;
import com.zaig100.dg.utils.dgscript.ast.statements.AssignStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.BlockStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.BreakStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.ContinueStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.DoWhileStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.ForStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.FunctionStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.IfStatement;
import com.zaig100.dg.utils.dgscript.ast.statements.Statement;
import com.zaig100.dg.utils.dgscript.ast.statements.WhileStatement;

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

    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }

    private Statement block() {
        final BlockStatement block = new BlockStatement();
        while (!match(TokenType.RBRACE)) {
            block.add(statement());
        }
        return block;
    }

    private Statement statementOrBlock() {
        if (match(TokenType.LBRACE)) return block();
        else return statement();
    }

    private Statement statement() {
        if (match(TokenType.IF)) {
            return ifElse();
        }
        if (match(TokenType.WHILE)) {
            return whileSt();
        }
        if (match(TokenType.DO)) {
            return doWhileSt();
        }
        if (match(TokenType.BRAKE)) {
            return new BreakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            return new ContinueStatement();
        }
        if (match(TokenType.FOR)) {
            return forSt();
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAR) {
            return new FunctionStatement(function());
        }
        return assignmentStatement();
    }

    private Statement assignmentStatement() {
        final Token current = get(0);
        if (match(TokenType.WORD) && should_match(TokenType.EQ)) {
            final String var = current.getText();
            return new AssignStatement(var, expression());
        }
        throw new RuntimeException("Unknown statment in token " + pos + ": " + current.toString());
    }


    private Statement ifElse() {
        final Expression condition = expression();
        final Statement ifStatement = statementOrBlock();
        final Statement elseStatement;
        if (match(TokenType.ELSE)) {
            elseStatement = statementOrBlock();
        } else {
            elseStatement = null;
        }
        return new IfStatement(condition, ifStatement, elseStatement);
    }

    private Statement whileSt() {
        final Expression condition = expression();
        final Statement statement = statementOrBlock();
        return new WhileStatement(condition, statement);
    }

    private Statement doWhileSt() {
        final Statement statement = statementOrBlock();
        should_match(TokenType.WHILE);
        final Expression condition = expression();
        return new DoWhileStatement(condition, statement);
    }

    private Statement forSt() {
        final Statement init = assignmentStatement();
        should_match(TokenType.COMMA);
        final Expression condition = expression();
        should_match(TokenType.COMMA);
        final Statement increment = assignmentStatement();
        final Statement statement = statementOrBlock();
        return new ForStatement(init, condition, increment, statement);
    }

    private Expression function() {
        final String name = consume(TokenType.WORD).getText();
        System.out.println("name=" + name);
        final FunctionalExpression function = new FunctionalExpression(name);
        should_match(TokenType.LPAR);
        while (!match(TokenType.RPAR)) {
            function.addArg(expression());
            match(TokenType.COMMA);
        }
        return function;
    }

    private Expression expression() {
        return logicalOr();
    }

    private Expression logicalOr() {
        Expression resault = logicalAnd();
        while (true) {
            if (match(TokenType.BARBAR)) {
                resault = new ConditionalExpression(ConditionalExpression.Operator.OR, resault, logicalAnd());
                continue;
            }
            break;
        }
        return resault;
    }

    private Expression logicalAnd() {
        Expression resault = equality();
        while (true) {
            if (match(TokenType.AMPAMP)) {
                resault = new ConditionalExpression(ConditionalExpression.Operator.AND, resault, equality());
                continue;
            }
            break;
        }
        return resault;
    }

    private Expression equality() {
        Expression resault = conditional();
        if (match(TokenType.EQEQ)) {
            resault = new ConditionalExpression(ConditionalExpression.Operator.EQUALS, resault, conditional());
        }
        if (match(TokenType.EXCLEQ)) {
            resault = new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, resault, conditional());
        }
        return resault;
    }

    private Expression conditional() {
        Expression resault = additive();

        while (true) {

            if (match(TokenType.LT)) {
                resault = new ConditionalExpression(ConditionalExpression.Operator.LT, resault, additive());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                resault = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, resault, additive());
                continue;
            }
            if (match(TokenType.GT)) {
                resault = new ConditionalExpression(ConditionalExpression.Operator.GT, resault, additive());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                resault = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, resault, additive());
                continue;
            }
            break;
        }

        return resault;
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
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAR) {
            return function();
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

    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType())
            throw new RuntimeException("Token " + pos + current.getType() + "dosen't match" + type);
        pos++;
        return current;
    }

    private boolean should_match(TokenType type) {
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
