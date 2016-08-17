package language;

import interpreter.Engine;
import interpreter.Automata;
import interpreter.Command;
import interpreter.Recognize;

import java.util.HashSet;
import java.util.Set;

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
    public Engine parse() throws SyntaxError
    {
        return parseProgram();
    }

    private Engine parseProgram() throws SyntaxError
    {
        // Program   = {(Automata|Recognize)}.
        Engine aeng = new Engine();

        while( lookahead != Token.xEos ) {
            if( lookahead == Token.xAutomata ) {
                Automata autom = parseAutomata();
                aeng.addAutomata(autom);
            }
            else if( lookahead == Token.xRecognize ) {
                Recognize reco = parseRecognize();
                aeng.addRecognize(reco);
            }
            else
                throw new SyntaxError("Շարահյուսական սխալ։", scan.line);
        }

        return aeng;
    }

    private Automata parseAutomata() throws SyntaxError
    {
        // Automata  = 'automata' Ident '{' Alphabet States Initial Finals Commands '}'.
        match(Token.xAutomata);
        String aunam = scan.lexeme;
        match(Token.xIdentifier);

        Automata autom = new Automata(aunam);

        match(Token.xLeftBrace);

        Set<Character> alph = parseAlphabet();
        autom.setAlphabet(alph);

        Set<String> stas = parseStates();
        autom.setStates(stas);

        String inis = parseInitial();
        autom.setInitial(inis);

        Set<String> fins = parseFinals();
        autom.setFinals(fins);

        Set<Command> coms = parseCommands();
        autom.addCommands(coms);

        match(Token.xRightBrace);

        return autom;
    }

    private Set<Character> parseAlphabet() throws SyntaxError
    {
        // Alphabet  = 'alphabet' '=' '{' Symbol {',' Symbol} '}'.
        match(Token.xAlphabet);
        match(Token.xEqual);
        match(Token.xLeftBrace);

        Set<Character> symset = new HashSet<>();

        char sym = scan.lexeme.charAt(0);
        match(Token.xSymbol);
        symset.add(sym);
        while( lookahead == Token.xComma ) {
            match(Token.xComma);
            sym = scan.lexeme.charAt(0);
            match(Token.xSymbol);
            symset.add(sym);
        }
        match(Token.xRightBrace);

        return symset;
    }

    private Set<String> parseStates() throws SyntaxError
    {
        // States    = 'states' '=' '{' Ident {',' Ident} '}'.
        match(Token.xStates);
        match(Token.xEqual);
        match(Token.xLeftBrace);

        Set<String> states = new HashSet<>();

        String snm = scan.lexeme;
        match(Token.xIdentifier);
        states.add(snm);
        while( lookahead == Token.xComma ) {
            match(Token.xComma);
            snm = scan.lexeme;
            match(Token.xIdentifier);
            states.add(snm);
        }
        match(Token.xRightBrace);

        return states;
    }

    private String parseInitial() throws SyntaxError
    {
        // Initial   = 'initial' '=' Ident.
        match(Token.xInitial);
        match(Token.xEqual);
        String state = scan.lexeme;
        match(Token.xIdentifier);

        return state;
    }

    private Set<String> parseFinals() throws SyntaxError
    {
        // Finals    = 'finals' '=' '{' Ident {',' Ident} '}'.
        match(Token.xFinals);
        match(Token.xEqual);
        match(Token.xLeftBrace);

        Set<String> states = new HashSet<>();

        String snm = scan.lexeme;
        match(Token.xIdentifier);
        states.add(snm);
        while( lookahead == Token.xComma ) {
            match(Token.xComma);
            snm = scan.lexeme;
            match(Token.xIdentifier);
            states.add(snm);
        }
        match(Token.xRightBrace);

        return states;
    }

    private Set<Command> parseCommands() throws SyntaxError
    {
        // Commands  = 'commands' '=' '{' Command {',' Command} '}'.
        match(Token.xCommands);
        match(Token.xLeftBrace);

        Set<Command> comset = new HashSet<>();

        Command com = parseOneCommand();
        comset.add(com);
        while( lookahead == Token.xComma ) {
            match(Token.xComma);
            com = parseOneCommand();
            comset.add(com);
        }
        match(Token.xRightBrace);

        return comset;
    }

    private Command parseOneCommand() throws SyntaxError
    {
        // Command   = Ident ',' Symbol '->' Ident.
        String from = scan.lexeme;
        match(Token.xIdentifier);
        match(Token.xComma);
        char with = scan.lexeme.charAt(0);
        match(Token.xSymbol);
        match(Token.xArrow);
        String goes = scan.lexeme;
        match(Token.xIdentifier);

        return new Command(from, with, goes);
    }

    private Recognize parseRecognize() throws SyntaxError
    {
        // Recognize = 'recognize' String 'with' Ident.
        match(Token.xRecognize);
        String pattern = scan.lexeme;
        match(Token.xString);
        match(Token.xWith);
        String autom = scan.lexeme;
        match(Token.xIdentifier);

        return new Recognize(pattern, autom);
    }

    //
    private void match( Token exp ) throws SyntaxError
    {
        if( exp == lookahead )
            lookahead = scan.nextToken();
        else
            throw new SyntaxError("Շարահյուսական սխալ։", scan.line);
    }
}
