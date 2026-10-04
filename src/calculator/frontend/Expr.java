package calculator.frontend;

public abstract sealed class Expr permits Expr.Number, Expr.Unary, Expr.Binary {
    public String toTreeString() {
        return toTreeString("", "");
    }

    public String toTreeString(String prefix) {
        return toTreeString(prefix, prefix);
    }

    protected abstract String toTreeString(String prefix, String childPrefix);

    public static final class Number extends Expr {
        private final String value;

        public Number(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        protected String toTreeString(String prefix, String childPrefix) {
            return prefix + "Number(" + value + ")\n";
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Binary extends Expr {
        private final Expr left;
        private final TokenType operator;
        private final Expr right;

        public Binary(Expr left, TokenType operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public Expr getLeft() { return left; }
        public TokenType getOperator() { return operator; }
        public Expr getRight() { return right; }

        @Override
        protected String toTreeString(String prefix, String childPrefix) {
            return prefix + "BinaryOp(" + operator + ")\n" +
                    left.toTreeString(childPrefix + "├── ", childPrefix + "│   ") +
                    right.toTreeString(childPrefix + "└── ", childPrefix + "    ");
        }

        @Override
        public String toString() {
            return "(" + left + " " + operator + " " + right + ")";
        }
    }

    public static final class Unary extends Expr {
        private final TokenType operator;
        private final Expr right;

        public Unary(TokenType operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        public TokenType getOperator() { return operator; }
        public Expr getRight() { return right; }

        @Override
        protected String toTreeString(String prefix, String childPrefix) {
            return prefix + "UnaryOp(" + operator + ")\n" +
                    right.toTreeString(childPrefix + "└── ", childPrefix + "    ");
        }

        @Override
        public String toString() {
            return "(" + operator + right + ")";
        }
    }
}