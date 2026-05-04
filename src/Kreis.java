import basis.AnimLeinwand;
import basis.Hilfe;

public class Kreis extends GrafikObjekt {
    public Kreis(AnimLeinwand lw) {
        super(lw);
    }

    @Override
    public void job() {
        stift.zeichneKreis(size);
    }

    @Override
    public void anim() {
        for (int i = 0; i < 200; i++) {
            this.loesche(size, rotation);
            size = i;
            this.zeichne();
            Hilfe.warte(10);
        }
    }
}
