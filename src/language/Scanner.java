package language;

import java.util.HashMap;
import java.util.Map;

/**/
public class Scanner {
    // ծառայողական բառեր
    private Map<String,Token> keywords = null;

    // ընթերցվելիք ծրագիրը
    private char[] source = null;
    // հերթական նիշի դիրքը
    private int position = 0;

    // լեքսեմ
    public String lexeme = null;
    // տողի համարը
    public int line = 1;

    //
    public Scanner( String text )
    {
        keywords = new HashMap<>();
        keywords.put("automata", Token.xAutomata);
        keywords.put("alphabet", Token.xAlphabet);
        keywords.put("states", Token.xStates);
        keywords.put("initial", Token.xInitial);
        keywords.put("finals", Token.xFinals);
        keywords.put("commands", Token.xCommands);
        keywords.put("recognize", Token.xRecognize);
        keywords.put("with", Token.xWith);

        source = text.toCharArray();
    }

    //
    public Token nextToken()
    {
        char ch = source[position++];

        while( Character.isWhitespace(ch) ) {
            if( ch == '\n' )
                ++line;
            ch = source[position++];
        }

        if( position == source.length )
            return Token.xEos;

        lexeme = "";

        // իդենտիֆիկատոր կամ ծառայթողկան բառեր
        if( Character.isLetter(ch) ) {
            int begin = position - 1;
            while( Character.isLetterOrDigit(ch) )
                ch = source[position++];
            --position;
            lexeme = String.copyValueOf(source, begin, position - begin);
            return keywords.getOrDefault(lexeme, Token.xIdentifier);
        }

        // սիմվոլներ
        if( ch == '\'' ) {
            ch = source[position++];
            if( Character.isLetterOrDigit(ch) )
                lexeme = String.valueOf(ch);
            return Token.xSymbol;
        }

        // տողեր
        if( ch == '"' ) {
            int begin = position;
            do
                ch = source[position++];
            while( ch != '"' );
            lexeme = String.copyValueOf(source, begin, position - begin - 1);
            return Token.xString;
        }

        // սլաք (անցում)
        if( ch == '-' ) {
            ch = source[position++];
            if( ch == '>' )
                return Token.xArrow;
            return Token.xNothing;
        }

        // այլ մետասիմվոլներ
        if( ch == '=' )
            return Token.xEqual;

        if( ch == '{' )
            return Token.xLeftBrace;

        if( ch == '}' )
            return Token.xRightBrace;

        if( ch == ',' )
            return Token.xComma;

        return Token.xNothing;
    }
}
