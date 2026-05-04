
//Import

import basis.*;

import java.awt.*;

public class Demo extends Fenster implements KnopfLauscher, RollbalkenLauscher, ListAuswahlLauscher {

    //Deklaration
    private Knopf ende, knopf1, knopf2, drehKnopf, animKnopf;

    private Rollbalken rollbalken;
    private ZahlenFeld zahlenfeld;
    private ListAuswahl liste;

    private TextFeld text1;
    private AnimLeinwand lw;

    private GrafikObjekt grob;

    private ZahlenFeld drehFeld;

    public Demo() {
        this.initGui();
    }

    public void initGui() {
        this.setzeGroesse(600, 500);
        this.setzeTitel("Demo");
        ende = new Knopf("Ende", 490, 460, 100, 30);
        ende.setzeKnopfLauscher(this);

        text1 = new TextFeld(10, 170, 120, 30);
        text1.setzeText("Liste Klick");
        rollbalken = new Rollbalken(true, 10, 420, 120, 30);
        rollbalken.setzeWerte(10, 200, 100);    //MIN,MAX,Start
        rollbalken.setzeRollbalkenLauscher(this);
        zahlenfeld = new ZahlenFeld(10, 380, 120, 30);
        zahlenfeld.setzeZahl(rollbalken.wert());
        drehFeld = new ZahlenFeld(10, 60, 120, 30);
        drehFeld.setzeZahl(0);
        liste = new ListAuswahl(10, 230, 120, 140);
        liste.setzeListAuswahlLauscher(this);
        liste.fuegeAn("Bild");
        liste.fuegeAn("Kreis");
        liste.waehle(0);

        knopf2 = new Knopf("loesche", 10, 460, 120, 30);
        knopf2.setzeKnopfLauscher(this);
        knopf1 = new Knopf("zeichne", 10, 130, 120, 30);
        knopf1.setzeKnopfLauscher(this);
        drehKnopf = new Knopf("drehe", 10, 90, 120, 30);
        drehKnopf.setzeKnopfLauscher(this);
        animKnopf = new Knopf("anim", 10, 20, 120, 30);
        animKnopf.setzeKnopfLauscher(this);


        lw = new AnimLeinwand(150, 10, 400, 400);
        lw.setzeHintergrundFarbe(Farbe.HELLGRAU);

        if (liste.index() == 0) {
            grob = new Bild(lw);
        } else {
            grob = new Leer(lw);
        }
        grob.setSize(rollbalken.wert());
    }

    @Override
    public void bearbeiteKnopfDruck(Knopf k) {
        if (k == ende) {
            this.gibFrei();
        } else if (k == knopf2) {
            text1.setzeText(liste.gewaehlterText());
            grob.loesche(rollbalken.wert(), drehFeld.ganzZahl());
        } else if (k == knopf1) {
            text1.setzeText(liste.gewaehlterText());
            grob.zeichne(rollbalken.wert(), drehFeld.ganzZahl());
        } else if (k == drehKnopf) {
            text1.setzeText(liste.gewaehlterText());
            grob.drehe(drehFeld.ganzZahl());
        } else if (k == animKnopf) {
            grob.setSize(rollbalken.wert());
            grob.anim();
        }
    }

    @Override
    public void bearbeiteRollbalkenBewegung(Rollbalken k) {
        if (k == rollbalken) {
            zahlenfeld.setzeZahl(rollbalken.wert());
        }
    }

    @Override
    public void bearbeiteAuswahl(ListAuswahl k) {
        if (k == liste) {
            text1.setzeText(liste.gewaehlterText());
            if (liste.index() == 0) {
                grob = new Bild(lw);
            }
            if (liste.index() == 1) {
                grob = new Kreis(lw);
            }
            grob.setSize(rollbalken.wert());
        }
    }

    @Override
    public void bearbeiteDoppelklick(ListAuswahl k) {
    }
}
