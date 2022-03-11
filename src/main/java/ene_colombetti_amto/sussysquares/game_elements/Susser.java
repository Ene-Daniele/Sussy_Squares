package ene_colombetti_amto.sussysquares.game_elements;

import ene_colombetti_amto.sussysquares.PlayField;
import ene_colombetti_amto.sussysquares.game_elements.attacks.Bullet;
import ene_colombetti_amto.sussysquares.game_elements.attacks.Rocket;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Susser extends Rectangle {

    private double hsp;
    private double vsp;
    private final int maxSpeed;

    private boolean left;
    private boolean down;
    private boolean right;
    private boolean up;
    private boolean shoot;

    private int shootDelay;
    private int rocketCharge;

    private final Arc rocketTelegraph = new Arc();

    private final ArrayList <Bullet> activeBullets = new ArrayList<>();

    public Susser(int x, int y, int maxSpeed, Paint color){

        super(x, y, 50, 50);

        hsp = 0;
        vsp = 0;
        this.maxSpeed = maxSpeed;

        left = false;
        down = false;
        right = false;
        up = false;
        shoot = false;

        this.shootDelay = 60;
        this.rocketCharge = 0;

        PlayField.root.getChildren().add(rocketTelegraph);

        this.setFill(color);
    }

    public void shoot(Bullet bullet){
        this.activeBullets.add(bullet);
        PlayField.root.getChildren().add(this.activeBullets.get(this.activeBullets.size() -1)); //Get the last added bullet
    }

    public void update(Susser opponent){

        for (Bullet i : activeBullets){
            i.update(opponent);
            if (i.getCenterX() > PlayField.scene.getWidth() + 100
                    || i.getCenterX() < PlayField.scene.getX() - 100
                    || i.getCenterY() > PlayField.scene.getHeight()
                    || i.getCenterY() < PlayField.scene.getY()
            ){
                PlayField.root.getChildren().remove(i);
            }
            for (Bullet activeBullet : activeBullets) {
                if (!i.equals(activeBullet) && !i.isRocket()) {

                    //TODO Collision between 2 bullets
                    //* If 2 bullets collide, delete them, if a bullet collides with a rocker, delete the bullet only.
                }
            }
        }

        movement();
        shooting();
        chargeTelegraphy();
        borderCollision();

        this.setX(this.getX() + this.hsp);
        this.setY(this.getY() + this.vsp);
    }

    private void borderCollision(){

        if (this.getX() + this.getWidth() + this.hsp > PlayField.half.getEndX() - PlayField.half.getStrokeWidth() / 2
                && this.getX() < PlayField.half.getEndX()
                || this.getX() + this.hsp < PlayField.half.getEndX() + PlayField.half.getStrokeWidth() / 2
                && this.getX() + this.getWidth() > PlayField.half.getEndX()

                || this.getX() + hsp < PlayField.scene.getX() - 8 || this.getX() + this.getWidth() + hsp > PlayField.scene.getWidth()
        ){
            this.hsp = 0;
        }
        if (this.getY() + this.getHeight() + vsp > PlayField.scene.getHeight() || this.getY() + vsp < PlayField.scene.getY() - 32){
            this.vsp = 0;
        }

    }

    private void chargeTelegraphy(){

        rocketTelegraph.setCenterX(this.getX() + this.getWidth() / 2);
        rocketTelegraph.setCenterY(this.getY() + this.getHeight() / 2);
        rocketTelegraph.setStartAngle(90);
        rocketTelegraph.setLength(rocketCharge);
        rocketTelegraph.setRadiusY(75);
        rocketTelegraph.setRadiusX(75);
        rocketTelegraph.setType(ArcType.OPEN);
        rocketTelegraph.setStrokeWidth(5);
        rocketTelegraph.setStroke(Color.GRAY);
        rocketTelegraph.setFill(Color.rgb(0,0,0,0.5));
    }

    private void shooting(){

        if (shoot && shootDelay == 0 && Math.abs(hsp) + Math.abs(vsp) > 5 && rocketCharge == 0){
            this.shoot(new Bullet(
                    this.getX() + (this.getWidth() / 2),
                    this.getY() + (this.getHeight() / 2),
                    this.getFill(), this.hsp, this.vsp
            ));

            shootDelay = 60;
        } else if (shootDelay > 0) shootDelay--;

        if (shoot){
            rocketCharge += 2;
        } else {
            if (rocketCharge > 360){
                this.shoot(new Rocket(
                        this.getX() + (this.getWidth() / 2),
                        this.getY() + (this.getHeight() / 2),
                        this.getFill(), this.hsp, this.vsp
                ));
            }
            rocketCharge = 0;
        }
    }

    private void movement(){

        if (hsp < maxSpeed && hsp > -maxSpeed) {
            if (!right && left){
                hsp--;
            } else if (right && !left){
                hsp++;
            }
        }
        if (vsp < maxSpeed && vsp > -maxSpeed) {
            if (!down && up){
                vsp--;
            } else if (down && !up){
                vsp++;
            }
        }

        hsp -= 0.5 * Math.signum(hsp);
        vsp -= 0.5 * Math.signum(vsp);
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
}
