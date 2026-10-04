package calculator.bridge.abstraction;

import calculator.bridge.implementation.MathEngine;
import calculator.frontend.*;

import java.util.Objects;

public abstract class Calculator {
    protected MathEngine engine;

    public Calculator(MathEngine engine) {
        this.engine = Objects.requireNonNull(engine, "MathEngine must not be null");
    }

    public void setEngine(MathEngine engine) {
        this.engine = Objects.requireNonNull(engine, "MathEngine must not be null");
    }

    public MathEngine getEngine() {
        return engine;
    }

    public String calculate(String expression) {
        Lexer lexer = new Lexer(expression);
        Parser parser = new Parser(lexer.tokenize());
        Expr ast = parser.parse();

        beforeCalculation(expression);
        Number result = evaluate(ast);
        afterCalculation(result);

        return engine.format(result);
    }

    protected Number evaluate(Expr node) {
        if (node instanceof Expr.Number num) {
            Number val = engine.parseLiteral(num.getValue());
            onLiteral(num.getValue(), val);
            return val;
        }

        if (node instanceof Expr.Unary u) {
            Number operand = evaluate(u.getRight());
            Number result = engine.negate(operand);
            onUnary(u.getOperator(), operand, result);
            return result;
        }

        if (node instanceof Expr.Binary b) {
            Number left = evaluate(b.getLeft());
            Number right = evaluate(b.getRight());

            Number result = switch (b.getOperator()) {
                case PLUS  -> engine.add(left, right);
                case MINUS -> engine.subtract(left, right);
                case STAR  -> engine.multiply(left, right);
                case SLASH -> engine.divide(left, right);
                case CARET -> engine.power(left, right);
                default -> throw new UnsupportedOperationException("Unknown operator: " + b.getOperator());
            };

            onBinary(b.getOperator(), left, right, result);
            return result;
        }

        throw new IllegalArgumentException("Unknown AST node type: " + node.getClass().getSimpleName());
    }

    protected void beforeCalculation(String expression) {}
    protected void afterCalculation(Number result) {}
    protected abstract void onLiteral(String literal, Number val);
    protected abstract void onUnary(TokenType op, Number operand, Number result);
    protected abstract void onBinary(TokenType op, Number left, Number right, Number result);
}