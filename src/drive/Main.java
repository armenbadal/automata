package drive;

import engine.AutomFarm;
import engine.Automata;
import language.Parser;
import language.Scanner;
import language.SyntaxError;
import language.Token;

/**
 * Created by abadalian on 8/16/16.
 */
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
                "automata A0 {\n" +
                "  alphabet = {'a, 'b, 'c}\n" +
                "  states = {s0, s1, s2, s3}\n" +
                "  initial = s0\n" +
                "  finals = {s2, s3}\n" +
                "  commands {\n" +
                "    s0, 'a -> s1,\n" +
                "    s1, 'b -> s1,\n" +
                "    s0, 'c -> s2,\n" +
                "    s2, 'c -> s2\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "rtecognize \"aaabbb\" with A0\n" +
                "\n";
        try {
            Parser pr0 = new Parser(autotext);
            AutomFarm f0 = pr0.parse();
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
