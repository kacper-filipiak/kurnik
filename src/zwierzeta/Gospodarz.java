package zwierzeta;

import inne.ACTIONS;
import inne.EventSubscriber;
import inne.GlobalRandom;
import urzadzenia.Gniazdo;
import urzadzenia.Pasnik;
import urzadzenia.Pasza;
import urzadzenia.Poidlo;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class Gospodarz {
    int iloscZbieranychJajek;
    static int zebraneJajka;
    public Point pozycja;
    ReentrantLock lock = new ReentrantLock();
    Thread thread = new Thread();
    public Gospodarz(int _iloscZbieranychJajek, Point _pozycja) {
        iloscZbieranychJajek = _iloscZbieranychJajek;
        pozycja = _pozycja;
    }

    void uzupelnijPasze(Pasnik pasnik) {
        pasnik.uzupelnijPasze(new Pasza(10.f));
    }

    void uzupelnijWode(Poidlo poidlo) {
        poidlo.uzupelnijWode();
    }

    void zbierzJajka(Gniazdo gniazdo) {
        for(int i = 0; i < zebraneJajka; i++) {
            Jajko jajko = gniazdo.zwrocWolneJajko();
            if (jajko != null) {
                gniazdo.usunJajko(jajko);
                zebraneJajka++;
            }else{
                break;
            }
        }
    }

    void poruszajSie(Point point) {
        if (!thread.isAlive()) {
            thread = new Thread(() -> {
                lock.lock();
                while (pozycja.x != point.x || pozycja.y != point.y) {
                    if(pozycja.x != point.x)pozycja.x += (pozycja.x - point.x) < 0 ? 1 : -1;
                    if(pozycja.y != point.y)pozycja.y += (pozycja.y - point.y) < 0 ? 1 : -1;
                    EventSubscriber.publishEvent();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                lock.unlock();
            });
            thread.start();
        }
    }

    ACTIONS decyduj(){
        switch (GlobalRandom.rand.nextInt(3)){
            case 0 -> {
                return ACTIONS.NAPELNIJ_POIDLO;
            }
            case 1 -> {
                return ACTIONS.NAPELNIJ_PASNIK;
            }
            case 2 -> {
                return ACTIONS.ZBIERAJ_JAJKA;
            }
            case 3 -> {
                return ACTIONS.BIEGAJ;
            }
            default -> {
                return ACTIONS.NIC;
            }
        }
    }

}