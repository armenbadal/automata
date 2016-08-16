package language;

/**/
public class Parser {
    // լեքսիկական վերլուծիչ
    private Scanner scan = null;
    // look a head
    private Token lookahead = null;

    //
    public Parser( String text )
    {
        scan = new Scanner( text + "@" );
        lookahead = scan.nextToken();
    }

    //
    public void parse()
    {
        parseProgram();
    }

    // Program   = {(Automata|Recognize)}.
    private void parseProgram()
    {
        while( lookahead == Token.xAutomata || lookahead == Token.xRecognize )
            if( lookahead == Token.xAutomata )
                parseAutomata();
            else if( lookahead == Token.xRecognize )
                parseRecognize();
    }

    // Automata  = 'automata' Ident '{' Alphabet States Initial Finals Commands '}'.
    private void parseAutomata()
    {
        match(Token.xAutomata);
        match(Token.xIdentifier);
        match(Token.xLeftBrace);
        parseAlphabet();
        parseStates();
        parseInitial();
        parseFinals();
        parseCommands();
        match(Token.xRightBrace);
    }

    // Alphabet  = 'alphabet' '=' '{' Symbol {',' Symbol} '}'.
    private void parseAlphabet()
    {
        match(Token.xAlphabet);
        match(Token.xEqual);
        match(Token.xLeftBrace);
        match(Token.xSymbol);
        while( lookahead == Token.xSymbol ) {
            match(Token.xComma);
            match(Token.xSymbol);
        }
        match(Token.xRightBrace);
    }

    // States    = 'states' '=' '{' Ident {',' Ident} '}'.
    private void parseStates()
    {
        match(Token.xStates);
        match(Token.xEqual);
        match(Token.xLeftBrace);
        match(Token.xIdentifier);
        while( lookahead == Token.xIdentifier ) {
            match(Token.xComma);
            match(Token.xIdentifier);
        }
        match(Token.xRightBrace);
    }

    // Initial   = 'initial' '=' Ident.
    private void parseInitial()
    {
        match(Token.xInitial);
        match(Token.xEqual);
        match(Token.xIdentifier);
    }

    // Finals    = 'finals' '=' '{' Ident {',' Ident} '}'.
    private void parseFinals()
    {
        match(Token.xFinals);
        match(Token.xEqual);
        match(Token.xLeftBrace);
        match(Token.xIdentifier);
        while( lookahead == Token.xIdentifier ) {
            match(Token.xComma);
            match(Token.xIdentifier);
        }
        match(Token.xRightBrace);
    }

    // Commands  = 'commands' '=' '{' Command {',' Command} '}'.
    private void parseCommands()
    {
        match(Token.xCommands);
        match(Token.xEqual);
        match(Token.xLeftBrace);
        parseOneCommand();
        while( lookahead == Token.xIdentifier ) {
            match(Token.xComma);
            parseOneCommand();
        }
        match(Token.xRightBrace);

    }

    // Command   = Ident ',' Symbol '->' Ident.
    private void parseOneCommand()
    {
        match(Token.xIdentifier);
        match(Token.xComma);
        match(Token.xSymbol);
        match(Token.xArrow);
        match(Token.xIdentifier);
    }

    // Recognize = 'recognize' String 'with' Ident.
    private void parseRecognize()
    {
        match(Token.xRecognize);
        match(Token.xString);
        match(Token.xWith);
        match(Token.xIdentifier);
    }

    //
    private boolean match( Token exp )
    {
        if( exp == lookahead ) {
            lookahead = scan.nextToken();
            return true;
        }
        return false;
    }
}
