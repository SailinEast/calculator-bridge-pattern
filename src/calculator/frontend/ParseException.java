package calculator.frontend;

public class ParseException extends SyntaxException {
    public ParseException(int position, String message) {
        super(position, message);
    }

    @Override
    String getError() {
        return "Parse Error";
    }
}
