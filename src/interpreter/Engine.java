package interpreter;

import java.util.ArrayList;
import java.util.List;

/**/
public class Engine {
    private List<Automata> automatas = null;
    private List<Recognize> recogizes = null;

    //
    public Engine()
    {
        automatas = new ArrayList<>();
        recogizes = new ArrayList<>();
    }

    public void addAutomata( Automata am )
    {
        automatas.add(am);
    }

    public void addRecognize( Recognize re )
    {
        recogizes.add(re);
    }

    public void run()
    {
        for( Recognize rec : recogizes ) {
            Automata autom = null;
            for( Automata ai : automatas )
                if( ai.name.equals(rec.automata) )
                    autom = ai;
            if( autom == null )
                continue; // TODO սխալ

            boolean resok = autom.recognize(rec.pattern);
            if( resok )
                System.out.println("OK: " + rec);
        }
    }
}
