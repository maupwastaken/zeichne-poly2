import basis.*;

import java.awt.*;

abstract public class GrafikObjekt {
    Stift stift;
    int size;
    protected int rotation;
    AnimLeinwand lw;

    public GrafikObjekt(AnimLeinwand lw) {
        stift = new Stift();
        this.lw = lw;
        stift.maleAuf(lw.stiftEbene(0));
    }

    public void setSize(int newsize) {
        this.size = newsize;
    }

    public void drehe(int winkel) {
        this.rotation = winkel;
    }

    public void zeichne(int groesse, int winkel) {
        this.size = groesse;
        this.rotation = winkel;
        stift.normal();
        stift.hoch();
        stift.bewegeBis(200, 200);
        stift.dreheBis(90 - rotation);
        stift.runter();
        job();
        lw.zeigeHG();
    }

    public void zeichne(int groesse) {
        this.size = groesse;

        stift.normal();
        stift.hoch();
        stift.bewegeBis(200, 200);
        stift.dreheBis(90 - rotation);
        stift.runter();
        job();
        lw.zeigeHG();
    }

    public void zeichne() {
        stift.normal();
        stift.hoch();
        stift.bewegeBis(200, 200);
        stift.dreheBis(90 - rotation);
        stift.runter();
        job();
        lw.zeigeHG();
    }

    public void loesche(int groesse, int winkel) {
        this.size = groesse;
        this.rotation = winkel;
        stift.radiere();
        stift.hoch();
        stift.bewegeBis(200, 200);
        stift.dreheBis(90 - rotation);
        stift.runter();
        job();
        lw.zeigeHG();
    }

    abstract public void job();

    abstract public void anim();

}
