package drive;

import interpreter.Engine;
import language.Parser;
import language.Scanner;
import language.SyntaxError;
import language.Token;

/**/
public class Main {
    private static void test_a()
    {
        Scanner scan = new Scanner("automata \"myau123\" '3 { ef } -> ");
        Token e = scan.nextToken();
        e = scan.nextToken();
        e = scan.nextToken();
        e = scan.nextToken();
    }

    private static void test_b()
    {
        final String autotext =
                "automata A0 {\n"
              + "  alphabet = {'a, 'b, 'c}\n"
              + "  states = {s0, s1, s2, s3}\n"
              + "  initial = s0\n"
              + "  finals = {s2, s3}\n"
              + "  commands {\n"
              + "    s0, 'a -> s1,\n"
              + "    s1, 'b -> s1,\n"
              + "    s0, 'c -> s2,\n"
              + "    s2, 'c -> s2\n"
              + "  }\n"
              + "}\n"
              + "\n"
              + "recognize \"aaabbb\" with A0\n"
              + "\n";
        try {
            Parser pr0 = new Parser(autotext);
            Engine f0 = pr0.parse();
            //System.out.println(f0);
        }
        catch( SyntaxError se ) {
            System.err.println(se.getMessage());
        }
    }

    public static void main( String[] args )
    {
        //test_a();
        test_b();
    }
}
