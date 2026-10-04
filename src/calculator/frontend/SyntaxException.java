package calculator.frontend;

public abstract class SyntaxException extends RuntimeException {
    private final int position;

    public SyntaxException(int position, String message) {
        super(message);
        this.position = position;
    }

    public int getPosition() { return position; }

    abstract String getError();

    public String formatWithSource(String expression) {
        int pos = Math.clamp(position, 0, expression.length());

        StringBuilder sb = new StringBuilder();
        sb.append("\n  ").append(expression).append("\n");
        sb.append("  ").repeat(" ", pos - 1).append("^\n");
        sb.append(getError()).append(": ").append(getMessage());
        return sb.toString();
    }
}
