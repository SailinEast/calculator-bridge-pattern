package calculator.frontend;

public class LexException extends SyntaxException {
    public LexException(String source, int position, String message) {
        super(source, position, "Lex Error", message);
    }
}
