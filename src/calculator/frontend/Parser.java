package calculator.frontend;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr parse() {
        Expr root = expression();
        if (!isAtEnd()) {
            throw new IllegalArgumentException(
                    "Unexpected token after expression: " + peek().getLexeme()
            );
        }
        return root;
    }

    // expression -> term ( ( "+" | "-" ) term )*
    private Expr expression() {
        Expr expr = term();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().getType();
            Expr right = term();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    // term -> power ( ( "*" | "/" ) power )*
    private Expr term() {
        Expr expr = power();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType op = previous().getType();
            Expr right = power();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    // power -> unary ( "^" power )?
    private Expr power() {
        Expr expr = unary();

        if (match(TokenType.CARET)) {
            TokenType op = previous().getType();
            Expr right = power();
            expr = new Expr.Binary(expr, op, right);
        }

        return expr;
    }

    // unary -> "-" unary | primary
    private Expr unary() {
        if (match(TokenType.MINUS)) {
            TokenType op = previous().getType();
            Expr right = unary();
            return new Expr.Unary(op, right);
        }

        return primary();
    }

    // primary -> NUMBER | "(" expression ")"
    private Expr primary() {
        if (match(TokenType.NUMBER)) {
            return new Expr.Number(previous().getLexeme());
        }

        if (match(TokenType.LPAREN)) {
            Expr expr = expression();
            consume(TokenType.RPAREN, "Expected ')' after expression.");
            return expr;
        }

        throw new IllegalArgumentException(
                "Expected number or '(' but found: '" + peek().getLexeme() + "' at index " + current
        );
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new IllegalArgumentException(message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}