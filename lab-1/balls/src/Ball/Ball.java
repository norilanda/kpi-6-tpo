package Ball;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Ball {
    private BallCanvas canvas;
    private final Color color;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private static final int epsilonIntersect = 7;
    private int x = 0;
    private int y= 0;
    private int dx = 2;
    private int dy = 2;

    public Ball(BallCanvas c) {
        this(c, Color.darkGray);
    }
    public Ball(BallCanvas c, Color color){
        this.canvas = c;
        this.color = color;
        if(Math.random()<0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }else{
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public Ball(BallCanvas c, int x, int y) {
        this(c, x,  y, Color.darkGray);
    }
    public Ball(BallCanvas c, int x, int y, Color color) {
        this.canvas = c;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public static void f(){
        int a = 0;
    }
    public void draw (Graphics2D g2){
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public boolean move(){
        x+=dx;
        y+=dy;

        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
        if (checkIfInPocket())
            return false;

        return true;
    }

    private boolean checkIfInPocket() {
        var pockets = canvas.getPockets();
        for (Pocket pocket : pockets) {
            if (intersects(pocket)) {
                canvas.onBallInPocket(this);

                return true;
            }
        }
        return false;
    }
    public boolean intersects(Pocket pocket) {
        double distanceX = this.x - pocket.getX();
        double distanceY = this.y - pocket.getY();
        double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

        return distance < epsilonIntersect;
    }
}
