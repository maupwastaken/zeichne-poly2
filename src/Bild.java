import basis.AnimLeinwand;
import basis.Hilfe;
import basis.Stift;
import basis.util.Vektor2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Bild extends GrafikObjekt {
    URL url;
    BufferedImage img;
    float hueOffset = 0;

    public Bild(AnimLeinwand lw) {
        super(lw);
        try {
            url = new URL("https://rz.gymhaan.de/neue_HP/img/gymhaan.jpg");
            img = ImageIO.read(url);
            if (img == null) {
                System.out.println("ImageIO.read returned null");
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            img = null;
        }
    }

    @Override
    public void job() {
        if (img == null) {
            System.out.println("Bild konnte nicht geladen werden");
            return;
        }

        int width = Math.max(1, size * 2);
        int height = Math.max(1, img.getHeight() * width / img.getWidth());
        double centerX = lw.breite() / 2.0;
        double centerY = lw.hoehe() / 2.0;
        double halfW = width / 2.0;
        double halfH = height / 2.0;

        Vektor2D vektor = new Vektor2D(0, 0);

        double angle = Math.toRadians(rotation);
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);

        int minX = (int) Math.floor(centerX - (Math.abs(cosA) * halfW + Math.abs(sinA) * halfH));
        int maxX = (int) Math.ceil(centerX + (Math.abs(cosA) * halfW + Math.abs(sinA) * halfH));
        int minY = (int) Math.floor(centerY - (Math.abs(sinA) * halfW + Math.abs(cosA) * halfH));
        int maxY = (int) Math.ceil(centerY + (Math.abs(sinA) * halfW + Math.abs(cosA) * halfH));

        stift.dreheBis(0);
        stift.hoch();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                double dx = x - centerX;
                double dy = y - centerY;

                double localX = cosA * dx + sinA * dy;
                double localY = -sinA * dx + cosA * dy;

                int srcX = (int) Math.floor(localX + halfW);
                int srcY = (int) Math.floor(localY + halfH);

                if (srcX < 0 || srcX >= width || srcY < 0 || srcY >= height) {
                    continue;
                }

                int imgX = srcX * img.getWidth() / width;
                int imgY = srcY * img.getHeight() / height;

                if (imgX < 0 || imgX >= img.getWidth() || imgY < 0 || imgY >= img.getHeight()) {
                    continue;
                }

                Color c = new Color(img.getRGB(imgX, imgY), true);
                if (c.getAlpha() == 0) {
                    continue;
                }

                // Apply hue shift
                float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
                float hue = (hsb[0] + hueOffset + (float)srcX / width) % 1.0f;
                Color shiftedColor = Color.getHSBColor(hue, hsb[1], hsb[2]);
                // Preserve alpha if necessary, but getHSBColor returns opaque. 
                // Color has a constructor for rgba but getHSBColor doesn't.
                if (c.getAlpha() < 255) {
                    shiftedColor = new Color(shiftedColor.getRed(), shiftedColor.getGreen(), shiftedColor.getBlue(), c.getAlpha());
                }

                stift.setzeFarbe(shiftedColor);
                stift.bewegeBis(x, y);
                stift.runter();
                stift.bewegeUm(0);
                stift.hoch();
            }
        }

        stift.hoch();
    }

    @Override
    public void anim() {
        for (int i = 0; i < 100; i++) {
            System.out.println(hueOffset);
            this.loesche(size, rotation);
            hueOffset += 0.05f;
            if (hueOffset > 1) hueOffset -= 1;
            this.zeichne();
            Hilfe.warte(33);
        }
    }
}
