package language;

import engine.AutomFarm;
import engine.Automata;
import engine.Command;
import engine.Recognize;

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
    public AutomFarm parse() throws SyntaxError
    {
        return parseProgram();
    }

    // Program   = {(Automata|Recognize)}.
    private AutomFarm parseProgram() throws SyntaxError
    {
        AutomFarm afarm = new AutomFarm();

        while( lookahead == Token.xAutomata || lookahead == Token.xRecognize ) {
            if (lookahead == Token.xAutomata) {
                Automata autom = parseAutomata();
                afarm.addAutomata(autom);
            } else if (lookahead == Token.xRecognize) {
                Recognize reco = parseRecognize();
                afarm.addRecognize(reco);
            }
        }

        return afarm;
    }

    // Automata  = 'automata' Ident '{' Alphabet States Initial Finals Commands '}'.
    private Automata parseAutomata() throws SyntaxError
    {
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

    // Alphabet  = 'alphabet' '=' '{' Symbol {',' Symbol} '}'.
    private Set<Character> parseAlphabet() throws SyntaxError
    {
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

    // States    = 'states' '=' '{' Ident {',' Ident} '}'.
    private Set<String> parseStates() throws SyntaxError
    {
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

    // Initial   = 'initial' '=' Ident.
    private String parseInitial() throws SyntaxError
    {
        match(Token.xInitial);
        match(Token.xEqual);
        String state = scan.lexeme;
        match(Token.xIdentifier);

        return state;
    }

    // Finals    = 'finals' '=' '{' Ident {',' Ident} '}'.
    private Set<String> parseFinals() throws SyntaxError
    {
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

    // Commands  = 'commands' '=' '{' Command {',' Command} '}'.
    private Set<Command> parseCommands() throws SyntaxError
    {
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

    // Command   = Ident ',' Symbol '->' Ident.
    private Command parseOneCommand() throws SyntaxError
    {
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

    // Recognize = 'recognize' String 'with' Ident.
    private Recognize parseRecognize() throws SyntaxError
    {
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
            throw new SyntaxError("Շարահյուսական սխալ։");
    }
}
