package urzadzenia;

import java.awt.*;

public abstract class Urzadzenie {
    public Point pozycja;
    int liczbaStanowisk;

    Urzadzenie(Point _pozycja, int _liczbaStanowisk) {
        pozycja = _pozycja;
        liczbaStanowisk = _liczbaStanowisk;
    }

    abstract boolean zajmijStanowisko();
}
