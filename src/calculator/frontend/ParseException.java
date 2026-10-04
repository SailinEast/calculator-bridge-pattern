package calculator.frontend;

public class ParseException extends SyntaxException {
    public ParseException(String source, int position, String message) {
        super(source, position, "Parse Error", message);
    }
}
