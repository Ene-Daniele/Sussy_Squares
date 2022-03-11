package ene_colombetti_amto.sussysquares.game_elements.attacks;

import ene_colombetti_amto.sussysquares.game_elements.Susser;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Bullet extends Circle {
    private double hsp;
    private double vsp;

    public Bullet(double x, double y, Paint color, double originHsp, double originVsp){
        super();
        this.setRadius(10);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setFill(color);
        this.hsp = originHsp * 0.8;
        this.vsp = originVsp * 0.5;
    }

    public boolean isRocket(){
        return false;
    }

    public void update(Susser opponent){

        this.setCenterX(this.getCenterX() + this.hsp);
        this.setCenterY(this.getCenterY() + this.vsp);
    }

    public double getHsp() {
        return hsp;
    }

    public void setHsp(double hsp) {
        this.hsp = hsp;
    }

    public double getVsp() {
        return vsp;
    }

    public void setVsp(double vsp) {
        this.vsp = vsp;
    }
}
