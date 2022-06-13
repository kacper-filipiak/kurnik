package urzadzenia;

import java.awt.*;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public abstract class Urzadzenie {
    public Point pozycja;
    int liczbaStanowisk;

    Urzadzenie(Point _pozycja, int _liczbaStanowisk) {
        pozycja = _pozycja;
        liczbaStanowisk = _liczbaStanowisk;
    }

    abstract boolean zajmijStanowisko();
}
