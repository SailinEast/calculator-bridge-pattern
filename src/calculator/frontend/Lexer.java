package calculator.frontend;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int cursor = 0;

    public Lexer(String source) {
        this.source = source != null ? source : "";
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            char c = advance();

            switch (c) {
                case '+': tokens.add(new Token(TokenType.PLUS, "+", cursor)); break;
                case '-': tokens.add(new Token(TokenType.MINUS, "-", cursor)); break;
                case '*': tokens.add(new Token(TokenType.STAR, "*", cursor)); break;
                case '/': tokens.add(new Token(TokenType.SLASH, "/", cursor)); break;
                case '^': tokens.add(new Token(TokenType.CARET, "^", cursor)); break;
                case '(': tokens.add(new Token(TokenType.LPAREN, "(", cursor)); break;
                case ')': tokens.add(new Token(TokenType.RPAREN, ")", cursor)); break;

                // Ignore whitespace
                case ' ':
                case '\t':
                case '\r':
                case '\n':
                    break;

                default:
                    if (Character.isDigit(c)) {
                        readNumber(c);
                    } else {
                        throw new LexException(
                                cursor,
                                "Unexpected character '" + c + "'"
                        );
                    }
                    break;
            }
        }

        tokens.add(new Token(TokenType.EOF, "", cursor));
        return tokens;
    }

    private void readNumber(char firstDigit) {
        int startPos = cursor;
        StringBuilder sb = new StringBuilder();
        sb.append(firstDigit);

        // Continue reading following digits
        while (!isAtEnd() && (Character.isDigit(peek()) || peek() == '.')) {
            // Prevent double decimals
            if (peek() == '.' && sb.indexOf(".") != -1) {
                break;
            }
            sb.append(advance());
        }

        tokens.add(new Token(TokenType.NUMBER, sb.toString(), startPos));
    }

    private char advance() {
        return source.charAt(cursor++);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(cursor);
    }

    private boolean isAtEnd() {
        return cursor >= source.length();
    }
}