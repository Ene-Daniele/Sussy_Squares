package ene_colombetti_amto.sussysquares.game_elements.attacks;

import ene_colombetti_amto.sussysquares.game_elements.Susser;
import javafx.scene.paint.Paint;

public class Rocket extends Bullet{

    private int lifeSpan;

    public Rocket(double x, double y, Paint color, double originHsp, double originVsp) {
        super(x, y, color, originHsp, originVsp);
        this.setRadius(20);
        this.lifeSpan = 450;
    }

    @Override
    public boolean isRocket() {
        return true;
    }

    @Override
    public void update(Susser opponent) {

        double orientationX = -Math.signum(this.getCenterX() - opponent.getX() - (opponent.getWidth() / 2));
        double orientationY = -Math.signum(this.getCenterY() - opponent.getY() - (opponent.getHeight() / 2));

        double aB = Math.abs(this.getCenterX() - opponent.getX() - (opponent.getWidth() / 2));
        double bC = Math.abs(this.getCenterY() - opponent.getY() - (opponent.getHeight() / 2));
        double cA = Math.sqrt(aB * aB + bC * bC);

        double verticalAngle = (Math.acos(aB / cA) * 180 / Math.PI) / 90;
        double horizontalAngle = (Math.acos(bC / cA) * 180 / Math.PI) / 90;

        this.setHsp(horizontalAngle * 4 * orientationX);
        this.setVsp(verticalAngle * 4 * orientationY);

        this.setCenterX(this.getCenterX() + this.getHsp());
        this.setCenterY(this.getCenterY() + this.getVsp());

        this.lifeSpan--;

        if (this.lifeSpan < 0){
            this.setRadius(this.getRadius() -1);
        }
    }
}
