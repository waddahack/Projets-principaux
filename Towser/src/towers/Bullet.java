package towers;

import ennemies.Ennemie;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import towser.Game;
import towser.Towser;
import towser.Shootable;

public class Bullet{
    
    private int speed, radius;
    private Shootable aim, shooter;
    private boolean follow;
    private double x, y, xDest, yDest, rdyToMove = 0;
    private float r, g, b;
    
    public Bullet(Shootable shooter, Shootable aim, int radius, float r, float g, float b){
        this.x = shooter.getX();
        this.y = shooter.getY();
        this.radius = radius;
        speed = shooter.getBulletSpeed();
        follow = shooter.getFollow();
        this.aim = aim;
        xDest = aim.getX();
        yDest = aim.getY();
        this.r = r;
        this.g = g;
        this.b = b;
        this.shooter = shooter;
    }
    
    public Bullet(Shootable shooter, int xDest, int yDest, int radius, float r, float g, float b){
        this.x = shooter.getX();
        this.y = shooter.getY();
        this.radius = radius;
        speed = shooter.getBulletSpeed();
        follow = false;
        this.aim = null;
        this.xDest = xDest;
        this.yDest = yDest;
        this.r = r;
        this.g = g;
        this.b = b;
        this.shooter = shooter;
    }
    
    public void move(){
        double xDiffConst = xDest-shooter.getX(), yDiffConst = yDest-shooter.getY(), xDiff = xDiffConst, yDiff = yDiffConst;
        double hyp = Math.sqrt(xDiffConst*xDiffConst + yDiffConst*yDiffConst), prop = speed/hyp, angle = Math.atan2(yDiff, xDiff);
        rdyToMove = System.currentTimeMillis();
        if(!(hasTouched(angle)) && isInRange()){
            if(follow){
                xDiff = aim.getX()-x;
                yDiff = aim.getY()-y;
                angle = Math.atan2(yDiff, xDiff);
                hyp = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
                prop = speed/hyp;
                x += xDiff*prop;
                y += yDiff*prop;
            }
            else{
                x += xDiffConst*prop;
                y += yDiffConst*prop; 
            }
        }
        if(!isInRange())
            shooter.getBulletsToRemove().add(this);
        if(hasTouched(angle)){
            aim.attacked(shooter.getPower());
            shooter.getBulletsToRemove().add(this);
        }
    }
    
    public void render(){
        if(System.currentTimeMillis()-rdyToMove >= 10){
            move();
            Towser.drawFilledCircle(x, y, radius, r, g, b);
        }
    }
    
    public double getRdyToMove(){
        return rdyToMove;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public int getRadius(){
        return radius;
    }
    
    private boolean isInRange(){
        double xDiff = xDest-shooter.getX(), yDiff = yDest-shooter.getY();
        double angle = Math.atan2(yDiff, xDiff), cosinus = Math.abs(Math.cos(angle)), sinus = Math.abs(Math.sin(angle)), coef = 1.5;
        if(shooter.isMultipleShot()) coef = 1;
        if(follow)
            return (x <= Towser.windWidth && x >= 0 && y <= Towser.windHeight && y >= 0 && !aim.isDead());
        return (Math.abs(x-shooter.getX()) <= shooter.getRange()*cosinus*coef && Math.abs(y-shooter.getY()) <= shooter.getRange()*sinus*coef);
    }
    
    private boolean hasTouched(double angle){
        double cosinus = Math.abs(Math.cos(angle)), sinus = Math.abs(Math.sin(angle));
        if(!follow){
            ArrayList<Ennemie> ennemies = Game.getEnnemies();
            Ennemie e;
            int i;
            double xDiff, yDiff;
            for(i = 0 ; i < ennemies.size() ; i++){
                e = ennemies.get(i);
                xDiff = e.getX()-x;
                yDiff = e.getY()-y;
                angle = Math.atan2(yDiff, xDiff);
                cosinus = Math.abs(Math.cos(angle));
                sinus = Math.abs(Math.sin(angle));
                if(aimTouched(e, cosinus, sinus)){
                    aim = e;
                    return true;
                }
            }       
            return false;
        }
        else
            return aimTouched(aim, cosinus, sinus);
    }
    
    private boolean aimTouched(Shootable aim, double cosinus, double sinus){
        int xHitBoxPoint = (int) ((aim.getWidth()+speed)*cosinus), yHitBoxPoint = (int) ((aim.getWidth()+speed)*sinus);
        if(x-radius <= aim.getX()+xHitBoxPoint && x-radius >= aim.getX()-xHitBoxPoint && y <= aim.getY()+yHitBoxPoint && y >= aim.getY()-yHitBoxPoint)
            return true;
        if(x+radius <= aim.getX()+xHitBoxPoint && x+radius >= aim.getX()-xHitBoxPoint && y <= aim.getY()+yHitBoxPoint && y >= aim.getY()-yHitBoxPoint)
            return true;
        if(x <= aim.getX()+xHitBoxPoint && x >= aim.getX()-xHitBoxPoint && y-radius <= aim.getY()+yHitBoxPoint && y-radius >= aim.getY()-yHitBoxPoint)
            return true;
        if(x <= aim.getX()+xHitBoxPoint && x >= aim.getX()-xHitBoxPoint && y+radius <= aim.getY()+yHitBoxPoint && y+radius >= aim.getY()-yHitBoxPoint)
            return true;
        return false;
    }
}
