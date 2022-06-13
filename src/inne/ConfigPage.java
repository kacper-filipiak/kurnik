package inne;

import urzadzenia.Pasza;
import zwierzeta.*;

import javax.swing.*;

public class ConfigPage extends JFrame {

    static int currentPanel = 0;
    JPanel panel = new JPanel();

    static JTextField drobWiekSmierci = new JTextField(1300 + " ", 40);
    static JTextField drobSmiertelnyDeficytKalorii = new JTextField(1000.f + " ", 40);
    static JTextField drobSmiertelnyDeficytWody = new JTextField(500.f + " ", 40);
    static JTextField kuraZapotrzebowanieWody = new JTextField(1.f + " ", 40);
    static JTextField kuraZapotrzebowanieKalorii = new JTextField(2.f + " ", 40);
    static JTextField kogutZapotrzebowanieKalorii = new JTextField(1.f + " ", 40);
    static JTextField kogutZapotrzebowanieWody = new JTextField(2.f + " ", 40);
    static JTextField kurczakZapotrzebowanieKalorii = new JTextField(1.f + " ", 40);
    static JTextField kurczakZapotrzebowanieWody = new JTextField(1.f + " ", 40);
    static JTextField paszaKalorycznosc = new JTextField(10.f + " ", 40);

    static JTextField kalorycznoscDrobiu = new JTextField(1000.f + "", 40);
    static JTextField lisWspolczynnikAtaku = new JTextField(0.2f + "", 40);
    static JTextField lisZapotrzebownieKaloryczne = new JTextField(100.f + "", 40);
    static JTextField podstawaCzasu = new JTextField(10 + "", 40);
    static JTextField maksymalnyCzas = new JTextField(1000000+"", 40);

    static JTextField maksymalnaLiczbaDrobiu = new JTextField(40+"", 40);
    static JTextField liczbaKur = new JTextField(10 + "", 40);
    static JTextField liczbaKogutow = new JTextField(1 + "", 40);
    static JTextField liczbaLisow = new JTextField(1 + "", 40);
    static JTextField liczbaGospodarzy = new JTextField(1 + "", 40);
    static JTextField liczbaGniazd = new JTextField(1 + "", 40);
    static JTextField liczbaPasnikow = new JTextField(1 + "", 40);
    static JTextField liczbaPoidel = new JTextField(1 + "", 40);

    JButton submitBtn = new JButton("Rozpoczni symulacje");
    JButton nextBtn = new JButton("Nastepne");
    JButton prevBtn = new JButton("Poprzednie");

    public static void main(String[] args) {
        System.out.println("Starting");
        new ConfigPage();
    }

    void drawFirstPage() {

        panel.add(new JLabel("Drob Wiek Smierci"));
        panel.add(drobWiekSmierci);
        panel.add(new JLabel("Drob Smiertelny Deficyt Kalorii"));
        panel.add(drobSmiertelnyDeficytKalorii);
        panel.add(new JLabel("Drob Smiertelny Deficyt Wody"));
        panel.add(drobSmiertelnyDeficytWody);
        panel.add(new JLabel("Kura Zapotrzebowanie Wody"));
        panel.add(kuraZapotrzebowanieWody);
        panel.add(new JLabel("Kura Zapotrzebowanie Kalorii"));
        panel.add(kuraZapotrzebowanieKalorii);
        panel.add(new JLabel("Kogut Zapotrzebowanie Kalorii"));
        panel.add(kogutZapotrzebowanieKalorii);
        panel.add(new JLabel("Kogut Zapotrzebowanie Wody"));
        panel.add(kogutZapotrzebowanieWody);
        panel.add(new JLabel("Kurczak Zapotrzebowanie Kalorii"));
        panel.add(kurczakZapotrzebowanieKalorii);
        panel.add(new JLabel("Kurczak Zapotrzebowanie Wody"));
        panel.add(kurczakZapotrzebowanieWody);
    }

    void drawSecondPage() {
        panel.add(new JLabel("Pasza Kalorycznosc"));
        panel.add(paszaKalorycznosc);
        panel.add(new JLabel("Kalorycznosc Drobiu"));
        panel.add(kalorycznoscDrobiu);
        panel.add(new JLabel("Lis Wspolczynnik Ataku"));
        panel.add(lisWspolczynnikAtaku);
        panel.add(new JLabel("Lis Zapotrzebowanie Kaloryczne"));
        panel.add(lisZapotrzebownieKaloryczne);
        panel.add(new JLabel("Maksymalny czas symulacji"));
        panel.add(maksymalnyCzas);
        panel.add(new JLabel("Podstawa czasu"));
        panel.add(podstawaCzasu);
    }

    void drawThirdPage() {
        panel.add(new JLabel("Maksymalna liczba drobiu w kurniku"));
        panel.add(maksymalnaLiczbaDrobiu);
        panel.add(new JLabel("Liczba Kur"));
        panel.add(liczbaKur);
        panel.add(new JLabel("Liczba Kogutow"));
        panel.add(liczbaKogutow);
        panel.add(new JLabel("Liczba Lisow"));
        panel.add(liczbaLisow);
        panel.add(new JLabel("Liczba Gospodarzy"));
        panel.add(liczbaGospodarzy);
        panel.add(new JLabel("Liczba Gniazd"));
        panel.add(liczbaGniazd);
        panel.add(new JLabel("Liczba Pasnikow"));
        panel.add(liczbaPasnikow);
        panel.add(new JLabel("Liczba Poidel"));
        panel.add(liczbaPoidel);
    }

    public ConfigPage() {
        super("Konfiguracja kurnika");
        setSize(500, 1000);

        if (currentPanel == 0) {
            drawFirstPage();
        } else if (currentPanel == 1) {
            drawSecondPage();
        } else if (currentPanel == 2) {
            drawThirdPage();
        }


        submitBtn.addActionListener((action) -> {
            Drob.setWiekSmierci(Long.parseLong(drobWiekSmierci.getText().trim()));
            Drob.setSmiertelnyDeficytKalorii(Float.parseFloat(drobSmiertelnyDeficytKalorii.getText().trim()));
            Drob.setSmiertelnyDeficytWody(Float.parseFloat(drobSmiertelnyDeficytWody.getText().trim()));
            Kura.setZapotrzebowanieWody(Float.parseFloat(kuraZapotrzebowanieWody.getText().trim()));
            Kura.setZapotrzebowanieKalorii(Float.parseFloat(kuraZapotrzebowanieKalorii.getText().trim()));
            Kogut.setZapotrzebowanieKalorii(Float.parseFloat(kogutZapotrzebowanieKalorii.getText().trim()));
            Kogut.setZapotrzebowanieWody(Float.parseFloat(kogutZapotrzebowanieWody.getText().trim()));
            Kurczak.setZapotrzebowanieKalorii(Float.parseFloat(kuraZapotrzebowanieKalorii.getText().trim()));
            Kurczak.setZapotrzebowanieWody(Float.parseFloat(kurczakZapotrzebowanieWody.getText().trim()));
            Pasza.setKalorycznosc(Float.parseFloat(paszaKalorycznosc.getText().trim()));
            Drob.setKalorycznoscDrobiu(Float.parseFloat(kalorycznoscDrobiu.getText().trim()));
            Lis.setWspolczynnikSzansAtaku(Float.parseFloat(lisWspolczynnikAtaku.getText().trim()));
            Lis.setZapotrzebowanieEnergetyczne(Float.parseFloat(lisZapotrzebownieKaloryczne.getText().trim()));
            Speed.setTimeBase(Long.parseLong(podstawaCzasu.getText().trim()));
            new Thread(() -> {
                new Kurnik(
                        Integer.parseInt(maksymalnyCzas.getText().trim()),
                        Integer.parseInt(maksymalnaLiczbaDrobiu.getText().trim()),
                        Integer.parseInt(liczbaKur.getText().trim()),
                        Integer.parseInt(liczbaKogutow.getText().trim()),
                        Integer.parseInt(liczbaLisow.getText().trim()),
                        Integer.parseInt(liczbaGospodarzy.getText().trim()),
                        Integer.parseInt(liczbaPasnikow.getText().trim()),
                        Integer.parseInt(liczbaPoidel.getText().trim()),
                        Integer.parseInt(liczbaGniazd.getText().trim())
                );
            }).start();
            this.setVisible(false);
        });

        nextBtn.addActionListener((action) -> {
            if (currentPanel < 2) {
                currentPanel++;
                new Thread(ConfigPage::new).start();
                setVisible(false);
            }
        });

        prevBtn.addActionListener((action) -> {
            if (currentPanel > 0) {
                currentPanel--;
                new Thread(ConfigPage::new).start();
                setVisible(false);
            }
        });

        panel.add(prevBtn);
        panel.add(nextBtn);
        panel.add(submitBtn);
        add(panel);

        setVisible(true);
    }

}