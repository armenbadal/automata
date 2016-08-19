package interpreter;

/**/
public class Recognize {
    public String pattern = null;
    public String automata = null;

    public Recognize( String pa, String au )
    {
        pattern = pa;
        automata = au;
    }

    @Override
    public String toString()
    {
        return String.format("recognize \"%s\" with %s", pattern, automata);
    }
}
