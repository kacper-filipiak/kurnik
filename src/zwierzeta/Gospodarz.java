package zwierzeta;

import inne.*;
import urzadzenia.Gniazdo;
import urzadzenia.Pasnik;
import urzadzenia.Pasza;
import urzadzenia.Poidlo;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class Gospodarz {
    public ACTIONS chce = ACTIONS.NIC;
    int iloscZbieranychJajek;
    static int zebraneJajka = 0;

    public static int getZebraneJajka() {
        return zebraneJajka;
    }

    public Point pozycja;
    ReentrantLock lock = new ReentrantLock();
    Thread thread = new Thread();

    public Gospodarz(int _iloscZbieranychJajek, Point _pozycja) {
        iloscZbieranychJajek = _iloscZbieranychJajek;
        pozycja = _pozycja;
    }
    public void uzupelnijPasze(Pasnik pasnik) {
        pasnik.uzupelnijPasze(new Pasza(100.f));
    }

    public void uzupelnijWode(Poidlo poidlo) {
        poidlo.uzupelnijWode();
    }

    //Jesli to mozliwe zbiera tyle jajek ile jest w iloscZbieranychJajek
    public void zbierzJajka(Gniazdo gniazdo) {
        for (int i = 0; i < iloscZbieranychJajek; i++) {
            Jajko jajko = gniazdo.zwrocWolneJajko();
            if (jajko != null) {
                gniazdo.usunJajko(jajko);
                zebraneJajka++;
                Logger.log("ZEBRANO_JAJKO", "1");
            } else {
                break;
            }
        }
    }

    //Realizacja ruchu do punktu
    public void poruszajSie(Point point) {
        if (!thread.isAlive()) {
            thread = new Thread(() -> {
                lock.lock();
                while (pozycja.x != point.x || pozycja.y != point.y) {
                    if (pozycja.x != point.x) pozycja.x += (pozycja.x - point.x) < 0 ? 1 : -1;
                    if (pozycja.y != point.y) pozycja.y += (pozycja.y - point.y) < 0 ? 1 : -1;
                    EventSubscriber.publishEvent();
                    try {
                        Thread.sleep(Speed.getTimeBase());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                lock.unlock();
            });
            thread.start();
        }
    }

    //Losuje nast??pny ruch
    public ACTIONS decyduj() {
        if (chce == ACTIONS.NIC || chce == null) {
            switch (GlobalRandom.rand.nextInt(12)) {
                case 0 -> chce = ACTIONS.NAPELNIJ_POIDLO;
                case 1 -> chce = ACTIONS.NAPELNIJ_PASNIK;
                case 2 -> chce = ACTIONS.ZBIERAJ_JAJKA;
                case 3, 4, 5, 6 -> chce = ACTIONS.BIEGAJ;
                default -> chce = ACTIONS.NIC;
            }
        }
        return chce;
    }

}