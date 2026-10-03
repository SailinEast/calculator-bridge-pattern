package calculator.bridge.abstraction;

import calculator.bridge.implementation.MathEngine;
import calculator.frontend.TokenType;

public class StandardCalculator extends Calculator {

    public StandardCalculator(MathEngine engine) {
        super(engine);
    }

    @Override
    protected void onLiteral(String literal, Number val) {
        // Silent evaluation
    }

    @Override
    protected void onUnary(TokenType op, Number operand, Number result) {
        // Silent evaluation
    }

    @Override
    protected void onBinary(TokenType op, Number left, Number right, Number result) {
        // Silent evaluation
    }
}