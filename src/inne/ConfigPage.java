package inne;

import urzadzenia.Pasza;
import zwierzeta.Drob;
import zwierzeta.Kogut;
import zwierzeta.Kura;
import zwierzeta.Kurczak;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;

public class ConfigPage extends JFrame {

    JPanel scaffold = new JPanel();
    JPanel panel = new JPanel();

    JTextField drobWiekSmierci = new JTextField(1300 + " ", 40);
    JTextField drobSmiertelnyDeficytKalorii = new JTextField(1000.f + " ", 40);
    JTextField drobSmiertelnyDeficytWody = new JTextField(500.f + " ", 40);
    JTextField kuraZapotrzebowanieWody = new JTextField(1.f + " ", 40);
    JTextField kuraZapotrzebowanieKalorii = new JTextField(2.f + " ", 40);
    JTextField kogutZapotrzebowanieKalorii = new JTextField(1.f + " ", 40);
    JTextField kogutZapotrzebowanieWody = new JTextField(2.f + " ", 40);
    JTextField kurczakZapotrzebowanieKalorii = new JTextField(1.f + " ", 40);
    JTextField kurczakZapotrzebowanieWody = new JTextField(1.f + " ", 40);
    JTextField paszaKalorycznosc = new JTextField(10.f + " ", 40);

    JButton submitBtn = new JButton("Rozpoczni symulacje");

    public static void main(String[] args) {
        System.out.println("Starting");
        new ConfigPage();
    }

    public ConfigPage() {
        super("Konfiguracja kurnika");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setAutoscrolls(true);
        panel.add(new JLabel("drob Wiek Smierci"));
        panel.add(drobWiekSmierci);
        panel.add(new JLabel("drob Smiertelny Deficyt Kalorii"));
        panel.add(drobSmiertelnyDeficytKalorii);
        panel.add(new JLabel("drob Smiertelny Deficyt Wody"));
        panel.add(drobSmiertelnyDeficytWody);
        panel.add(new JLabel("kura Zapotrzebowanie Wody"));
        panel.add(kuraZapotrzebowanieWody);
        panel.add(new JLabel("kura Zapotrzebowanie Kalorii"));
        panel.add(kuraZapotrzebowanieKalorii);
        panel.add(new JLabel("kogut Zapotrzebowanie Kalorii"));
        panel.add(kogutZapotrzebowanieKalorii);
        panel.add(new JLabel("kogut Zapotrzebowanie Wody"));
        panel.add(kogutZapotrzebowanieWody);
        panel.add(new JLabel("kurczak Zapotrzebowanie Kalorii"));
        panel.add(kurczakZapotrzebowanieKalorii);
        panel.add(new JLabel("kurczak Zapotrzebowanie Wody"));
        panel.add(kurczakZapotrzebowanieWody);
        panel.add(new JLabel("pasza Kalorycznosc"));
        panel.add(paszaKalorycznosc);

        submitBtn.addActionListener((action) ->{
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
            new Thread(Kurnik::new).start();
            this.setVisible(false);
        });
        panel.add(submitBtn);

//        JScrollPane pane = new JScrollPane( panel ) ;
//        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        add(pane);
        pack();

        setVisible(true);
    }
}