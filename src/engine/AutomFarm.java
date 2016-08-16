package engine;

import java.util.ArrayList;
import java.util.List;

/**/
public class AutomFarm {
    private List<Automata> automatas = null;
    private List<Recognize> recogizes = null;

    //
    public AutomFarm()
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
}
