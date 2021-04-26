package me.v0idpointer.ld48.gfx;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Art {

    private BufferedImage source;
    private BufferedImage image;

    public Art(BufferedImage source) {
        this.source = source;
        this.image = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.image.getGraphics().drawImage(source, 0, 0, null);
    }

    public void copyDefault() {
        this.image.getGraphics().drawImage(source, 0, 0, null);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public BufferedImage getSource() {
        return this.source;
    }

    public void recolor(int src, int target) {
        for(int xx = 0; xx < this.image.getWidth(); xx ++) {
            for(int yy = 0; yy < this.image.getHeight(); yy++) {
                if(this.image.getRGB(xx, yy) == src) this.image.setRGB(xx, yy, target);
            }
        }
    }

    public BufferedImage getRecolor(int src, int target) {
        BufferedImage i = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        i.getGraphics().drawImage(this.image, 0, 0, null);

        for(int xx = 0; xx < i.getWidth(); xx ++) {
            for(int yy = 0; yy < i.getHeight(); yy++) {
                if(i.getRGB(xx, yy) == src) i.setRGB(xx, yy, target);
            }
        }

        return i;
    }

    public void flipHorizontally() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-this.image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.image = op.filter(this.image, null);
    }

    public BufferedImage getFlippedHorizontally() {
        BufferedImage i = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        i.getGraphics().drawImage(this.image, 0, 0, null);
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-i.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        i = op.filter(i, null);
        return i;
    }

    public void flipVertically() {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -this.image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.image = op.filter(this.image, null);
    }

    public BufferedImage getFlippedVertically() {
        BufferedImage i = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        i.getGraphics().drawImage(this.image, 0, 0, null);
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -i.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        i = op.filter(i, null);
        return i;
    }

    public void multiFlip() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-this.image.getWidth(null), -this.image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.image = op.filter(this.image, null);
    }

    public BufferedImage getMultiFlipped() {
        BufferedImage i = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        i.getGraphics().drawImage(this.image, 0, 0, null);
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-i.getWidth(null), -i.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        i = op.filter(i, null);
        return i;
    }

}
