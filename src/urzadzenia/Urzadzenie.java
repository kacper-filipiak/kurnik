package urzadzenia;

import java.awt.*;

public abstract class Urzadzenie {
    Point pozycja;
    int liczbaStanowisk;

    abstract boolean zajmijStanowisko();

    Urzadzenie(Point _pozycja, int _liczbaStanowisk){
        pozycja = _pozycja;
        liczbaStanowisk = _liczbaStanowisk;
    }
}
