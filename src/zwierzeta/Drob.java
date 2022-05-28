package zwierzeta;

import java.awt.*;

public abstract class Drob extends Zwierze {
    long wiek;
    long wiekSmierci;
    float smiertelnyDeficytKalorii;
    float smiertelnyDeficytWody;

    public Drob(float _zapotrzebowanie, float _glod, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pozycja);
        wiek = _wiek;
        wiekSmierci = _wiekSmierci;
        smiertelnyDeficytKalorii = _smiertelnyDeficytKalorii;
        smiertelnyDeficytWody = _smiertelnyDeficytWody;
    }
    public ACTIONS starzej(){
       wiek += 100;
       if(wiekSmierci < wiek)
       return ACTIONS.ZABIJ_SIE;
       else return ACTIONS.NIC;
    }
}

