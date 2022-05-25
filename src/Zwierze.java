public abstract class Zwierze {
    float zapotrzebowanieEnergetyczne;
    float glod;
    Point pozycja;
    public Zwierze(float _zapotrzebowanie, float _glod, Point _pozycja){
        zapotrzebowanieEnergetyczne = _zapotrzebowanie;
        glod = _glod;
        pozycja = _pozycja;
    }
}
