package Ball;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Pocket {
    private Component canvas;
    public static final int RADIUS = 20;
    private int x = 0;
    private int y= 0;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Pocket(Component c){
        this.canvas = c;
        if(Math.random()<0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = this.canvas.getHeight()/2;
        }else{
            x = this.canvas.getWidth()/2;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public Pocket(Component c, int x, int y) {
        this.canvas = c;
        this.x = x;
        this.y = y;
    }

    public void draw (Graphics2D g2){
        g2.setColor(Color.black);
        g2.fill(new Ellipse2D.Double(x,y, RADIUS,RADIUS));
    }
}
