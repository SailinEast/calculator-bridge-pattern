package calculator.bridge.abstraction;

import calculator.bridge.implementation.MathEngine;
import calculator.frontend.TokenType;

public class TracedCalculator extends Calculator {
    private int stepCount = 0;

    public TracedCalculator(MathEngine engine) {
        super(engine);
    }

    @Override
    protected void beforeCalculation(String expression) {
        stepCount = 0;
        System.out.println("==================================================");
        System.out.println("Tracing Expression: " + expression);
        System.out.println("Active Backend:     " + engine.getName());
        System.out.println("--------------------------------------------------");
    }

    @Override
    protected void onLiteral(String literal, Number val) {
        stepCount++;
        System.out.printf("  Step %02d: [LITERAL] Read '%s' -> %s%n",
                stepCount, literal, engine.format(val));
    }

    @Override
    protected void onUnary(TokenType op, Number operand, Number result) {
        stepCount++;
        System.out.printf("  Step %02d: [UNARY]   %s(%s) -> %s%n",
                stepCount, op, engine.format(operand), engine.format(result));
    }

    @Override
    protected void onBinary(TokenType op, Number left, Number right, Number result) {
        stepCount++;
        System.out.printf("  Step %02d: [BINARY]  %s %s %s = %s%n",
                stepCount, engine.format(left), op, engine.format(right), engine.format(result));
    }

    @Override
    protected void afterCalculation(Number result) {
        System.out.println("--------------------------------------------------");
        System.out.println("Final Result: " + engine.format(result));
        System.out.println("==================================================\n");
    }
}