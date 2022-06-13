package zwierzeta;

import inne.ACTIONS;
import inne.EventSubscriber;
import inne.Speed;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Zwierze {
    ReentrantLock lock = new ReentrantLock();
    Thread thread = new Thread();

    public ACTIONS chce = ACTIONS.NIC;
    public Point pozycja;
    float zapotrzebowanieEnergetyczne;
    float zapotrzebowanieWody;

    float glod;

    float pragnienie;

    public Zwierze(float _zapotrzebowanieKalorii, float _zapotrzebowanieWody, float _glod, float _pragnienie, Point _pozycja) {
        zapotrzebowanieEnergetyczne = _zapotrzebowanieKalorii;
        zapotrzebowanieWody = _zapotrzebowanieWody;
        glod = _glod;
        pragnienie = _pragnienie;
        pozycja = _pozycja;
    }

    //Zmniejsza glod i resetuje obecnie wykonywana akcje
    public void jedz(float kalorie) {
        if(thread.isAlive()) {
            thread = new Thread(() -> {
                try {
                    Thread.sleep(Speed.getTimeBase());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.lock();
                glod -= kalorie;
                if (glod < 0) glod = 0;
                chce = ACTIONS.NIC;
                lock.unlock();
            });
            thread.start();
        }
    }

    //Zmniejsza pragnienie i resetuje obecnie wykonywana akcje
    public void pij(float litry) {
        if(!thread.isAlive()) {
            thread = new Thread(() -> {
                try {
                    Thread.sleep(Speed.getTimeBase());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.lock();
                pragnienie -= litry;
                if (pragnienie < 0) pragnienie = 0;
                chce = ACTIONS.NIC;
                lock.unlock();
            });
            thread.start();
        }
    }

    //Realizuje ruch do punktu
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

    abstract public ACTIONS decyduj();
}
