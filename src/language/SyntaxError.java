package language;

/**/
public class SyntaxError extends Exception {
    public SyntaxError( String msg, int pos )
    {
        super(String.format("Տող %d: %s", pos, msg));
    }
}
