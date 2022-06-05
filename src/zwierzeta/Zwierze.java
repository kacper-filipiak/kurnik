package zwierzeta;

import inne.ACTIONS;
import inne.EventSubscriber;

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

    public void dispose(){
        thread.interrupt();
    }

    public void jedz(float kalorie) {
        glod -= kalorie;
        chce = ACTIONS.NIC;
    }

    public void pij(float litry) {
        pragnienie -= litry;
        chce = ACTIONS.NIC;
    }

    public void poruszajSie(Point point) {
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

    abstract public ACTIONS decyduj();
}
