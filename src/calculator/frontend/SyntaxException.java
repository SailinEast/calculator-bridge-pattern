package calculator.frontend;

public abstract class SyntaxException extends RuntimeException {
    private final int position;

    public SyntaxException(String source, int position, String errorType, String message) {
        super(buildMessage(source, position, errorType, message));
        this.position = position;
    }

    // Do not record stack frames
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public int getPosition() { return position; }

    private static String buildMessage(String source, int position, String errorType, String message) {
        int pos = Math.clamp(position, 0, source == null ? 0 : source.length());

        return String.format(
                "\n\n  %s\n  %s^\n%s: %s\n",
                source,
                " ".repeat(Math.max(0, pos - 1)),
                errorType,
                message
        );
    }
}
