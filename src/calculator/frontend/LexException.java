package calculator.frontend;

public class LexException extends SyntaxException {
    public LexException(int position, String message) {
        super(position, message);
    }

    @Override
    String getError() {
        return "Lexical Error";
    }
}
