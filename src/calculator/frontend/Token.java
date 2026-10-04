package calculator.frontend;

public record Token(TokenType type, String lexeme, int position) {

    @Override
    public String toString() {
        return String.format("Token(%s, '%s', %d)", type, lexeme, position);
    }
}
