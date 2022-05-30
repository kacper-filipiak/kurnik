package zwierzeta;

import java.awt.*;

public abstract class Gospodarz {
    int iloscZbieranychJajek;
    Point pozycja;

    public Gospodarz(int _iloscZbieranychJajek, Point _pozycja) {
        iloscZbieranychJajek = _iloscZbieranychJajek;
        pozycja = _pozycja;
    }

}