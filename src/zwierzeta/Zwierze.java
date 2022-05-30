package zwierzeta;

import inne.EventSubscriber;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Zwierze {
    ReentrantLock lock = new ReentrantLock();
    Thread thread = new Thread();
    public Point pozycja;
    float zapotrzebowanieEnergetyczne;
    float glod;

    float pragnienie;

    public Zwierze(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja) {
        zapotrzebowanieEnergetyczne = _zapotrzebowanie;
        glod = _glod;
        pragnienie = _pragnienie;
        pozycja = _pozycja;
    }

    void jedz(float kalorie) {
        glod -= kalorie;
    }

    void pragnienie(float litry) {
        pragnienie -= litry;
    }

    public void poruszajSie(int x, int y) {
        if (!thread.isAlive()) {
            thread = new Thread(() -> {
                lock.lock();
                while (pozycja.x != x || pozycja.y != y) {
                    if(pozycja.x != x)pozycja.x += (pozycja.x - x) < 0 ? 1 : -1;
                    if(pozycja.y != y)pozycja.y += (pozycja.y - y) < 0 ? 1 : -1;
                    EventSubscriber.publishEvent();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                lock.unlock();
            });
            thread.start();
        }
    }
}
