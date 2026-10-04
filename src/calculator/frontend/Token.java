package calculator.frontend;

public class Token {
    private final TokenType type;
    private final String lexeme;
    private final int position;

    public Token(TokenType type, String lexeme, int position) {
        this.type = type;
        this.lexeme = lexeme;
        this.position = position;
    }

    public TokenType getType() { return type; }
    public String getLexeme() { return lexeme; }
    public int getPosition() { return position; }

    @Override
    public String toString() {
        return String.format("Token(%s, '%s', %d)", type, lexeme, position);
    }
}
