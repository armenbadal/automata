package drive;

import engine.Automata;
import language.Scanner;
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

    public static void main( String[] args )
    {
        test_a();
    }
}
