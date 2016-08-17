package interpreter;

// ավտոմատի հրամանը
public class Command {
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
