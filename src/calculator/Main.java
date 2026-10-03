package calculator;

import calculator.bridge.abstraction.Calculator;
import calculator.bridge.abstraction.StandardCalculator;
import calculator.bridge.abstraction.TracedCalculator;
import calculator.bridge.implementation.BigDecimalMathEngine;
import calculator.bridge.implementation.DoubleMathEngine;
import calculator.bridge.implementation.MathEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SOFTWARE DESIGN PATTERNS - BRIDGE PATTERN DEMO ");
        System.out.println("==================================================\n");

        // 1. Create Concrete Implementations
        MathEngine doubleEngine = new DoubleMathEngine();
        MathEngine bigDecimalEngine = new BigDecimalMathEngine(40);

        // ================================================================
        // DEMO 1: Runtime Implementor Switching
        // (Demonstrates IEEE-754 binary float vs. Arbitrary-Precision decimal)
        // ================================================================
        System.out.println("--- DEMO 1: Runtime Implementor Switching ---");
        String precisionExpr = "0.1 + 0.2";
        System.out.println("Evaluating: " + precisionExpr);

        // Compose StandardCalculator (Abstraction) with DoubleMathEngine (Implementor)
        Calculator calculator = new StandardCalculator(doubleEngine);
        System.out.println("Using: " + calculator.getEngine().getName());
        System.out.println("Result -> " + calculator.calculate(precisionExpr));

        System.out.println("\n>> [RUNTIME SWAP] Switching engine to BigDecimalMathEngine <<\n");

        // Switch the implementor at runtime without re-creating the calculator or changing the abstraction
        calculator.setEngine(bigDecimalEngine);
        System.out.println("Using: " + calculator.getEngine().getName());
        System.out.println("Result -> " + calculator.calculate(precisionExpr));
        System.out.println();

        // ================================================================
        // DEMO 2: Switching Refined Abstraction (TracedCalculator)
        // (Demonstrates independent variation on the Abstraction side)
        // ================================================================
        System.out.println("--- DEMO 2: Refined Abstraction (Step-by-Step Trace) ---");
        String complexExpr = "3 + 4 * (2 - 1) ^ 3";

        // Compose TracedCalculator with BigDecimalMathEngine
        Calculator tracedCalculator = new TracedCalculator(bigDecimalEngine);
        tracedCalculator.calculate(complexExpr);

        // Swap TracedCalculator implementor back to DoubleMathEngine
        tracedCalculator.setEngine(doubleEngine);
        tracedCalculator.calculate("10 / 3");
    }
}