package zwierzeta;

import java.awt.*;

public abstract class Zwierze {
    float zapotrzebowanieEnergetyczne;
    float glod;
    public Point pozycja;
    public Zwierze(float _zapotrzebowanie, float _glod, Point _pozycja){
        zapotrzebowanieEnergetyczne = _zapotrzebowanie;
        glod = _glod;
        pozycja = _pozycja;
    }
}
