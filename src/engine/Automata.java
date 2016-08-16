package engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**/
public class Automata {
    // ավտոմատի անունը
    private String name = null;

    // ժապավենի թույլատրելի նիշերի բազմություն
    private Set<Character> alphabet = null;
    // վիճակների բազմություն
    private Set<String> states = null;
    // սկզբնական վիճակ
    private String initial = null;
    // վերջնական վիճակների բազմություն
    private Set<String> finals = null;
    // ավտոմատի հրամանը
    private class Command {
        // ընթացիկ վիճակ
        public String from = null;
        // դիտարկվող սիմվոլ
        public char with = '\0';
        //՛ նոր վիճակ
        public String goes = null;

        //
        public Command( String se, char sy, String ge )
        {
            from = se;
            with = sy;
            goes = ge;
        }

        @Override
        public String toString()
        {
            return String.format("%s, %c -> %s", from, with, goes);
        }
    }
    // հրամանների բազմություն
    private Set<Command> commands = null;


    //
    public Automata(String nm )
    {
        name = nm;

        alphabet = new HashSet<>();
        states = new HashSet<>();
        finals = new HashSet<>();
        commands = new HashSet<>();
    }

    //
    public void setAlphabet( Set<Character> alph )
    {
        alphabet.addAll(alph);
    }

    //
    public void setStates( Set<String> sas )
    {
        states.addAll(sas);
    }

    //
    public void setInitial( String ins )
    {
        initial = ins;
    }

    //
    public void setFinals( Set<String> saf )
    {
        finals.addAll(saf);
    }

    //
    public void addCommand( String fr, char sy, String gs )
    {
        commands.add(new Command(fr, sy, gs));
    }

    // TODO ստուգումները տեղափոխել parser
    public void checkSemantics() throws SemanticError
    {
        if( alphabet.isEmpty() )
            throw new SemanticError("Այբուբենը դատարկ է։");

        if( initial == null )
            throw new SemanticError("Սկզբնական վիճակն ընտրված չէ։");

        if( !states.contains(initial) )
            throw new SemanticError("'" + initial + "' սկզբնական վիճակը վիճակների բազմությունից չէ։");

        Set<String> unks = new HashSet<>();
        ///finals.stream().filter(e -> !states.contains(e))
        for( String se : finals )
            if( !states.contains(se) )
                unks.add(se);
        if( !unks.isEmpty() )
            throw new SemanticError(String.join(", ", unks) + " վերջնական վիճակները վիճակների բազմությունից չեն։");

//        // TODO ստուգել հրամանների կոռեկտությունը
//        for( Command co : commands ) {
//            if( !states.contains(co.from) )
//                ;
//            if( !alphabet.contains(co.with) )
//                ;
//            if( !states.contains(co.goes) )
//                ;
//        }
    }

    //
    public boolean recognize( String seq )
    {
        // ժապավեն
        final char[] stape = seq.toCharArray();
        // ժապավենի երկարությունը
        final int slength = stape.length;

        // ընթացիկ վիճակ
        String state = initial;
        // ժապավենից կարդալու դիրքը
        int position = 0;

        while( !finals.contains(state) || position != slength ) {
            char sy = stape[position];
            ++position;
            state = delta(state, sy);
        }

        return finals.contains(state) && position == slength;
    }

    //
    private String delta( String so, char sy )
    {
        for( Command ci : commands )
            if( ci.from == so && ci.with == sy )
                return ci.goes;
        return null;
    }
}
