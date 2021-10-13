package ennemies;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Drawable;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glVertex2f;
import org.lwjgl.opengl.SharedDrawable;
import org.newdawn.slick.opengl.Texture;
import towers.Bullet;
import towers.Tower;
import towser.Game;
import towser.Towser;
import towser.Shootable;
import ui.Tile;

public abstract class Ennemie extends Thread implements Shootable{
    
    protected int reward, power, shootRate, range, life, xBase, yBase, id, width, indiceTuile = 0;
    protected Texture sprite = null;
    protected float r, g, b, moveSpeed;
    protected long stopFor = 0;
    protected String name;
    protected double x, y, speedRatio;
    protected ArrayList<Integer> spawn, base;
    protected String dir;
    protected ArrayList<ArrayList<Tile>> map;
    protected boolean isAimed = false, isSpawned, isMultipleShot;
    
    public Ennemie(){
        spawn = Game.getSpawn();
        base = Game.getBase();
        map = Game.getMap();
        if(!spawn.isEmpty()){
            x = spawn.get(0)*Game.unite+Game.unite/2;
            y = spawn.get(1)*Game.unite+Game.unite/2;
        }
        if(!base.isEmpty()){
            xBase = base.get(0)*Game.unite+Game.unite/2;
            yBase = base.get(1)*Game.unite+Game.unite/2;
        }
    }
    
    public boolean isInRangeOf(Tower t){
        double xDiff = t.getX()-x, yDiff = t.getY()-y;
        double angle, cosinus, sinus;
        angle = Math.atan2(yDiff, xDiff);
        cosinus = Math.floor(Math.cos(angle)*1000)/1000;
        sinus = Math.floor(Math.sin(angle)*1000)/1000;
        return (x <= t.getX()+((t.getRange())*Math.abs(cosinus))+moveSpeed && x >= t.getX()-((t.getRange())*Math.abs(cosinus))-moveSpeed && y <= t.getY()+((t.getRange())*Math.abs(sinus))+moveSpeed && y >= t.getY()-((t.getRange())*Math.abs(sinus))-moveSpeed);
    }
    
    public double getSpeedRatio(){
        return speedRatio;
    }
    
    public float getR(){
        return r;
    }
    
    public float getG(){
        return g;
    }
    
    public int getIndiceTuile(){
        return indiceTuile;
    }
    
    public float getMoveSpeed(){
        return moveSpeed;
    }
    
    public float getB(){
        return b;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public int getWidth(){
        return width;
    }
    
    public boolean getFollow(){
        return false;
    }
    
    public int getBulletSpeed(){
        return 1;
    }
    
    public int getReward(){
        return reward;
    }
    
    public ArrayList<Bullet> getBullets(){
        return null;
    }
    
    public ArrayList<Bullet> getBulletsToRemove(){
        return null;
    }
    
    public void attacked(int power){
        life -= power;
        if(life <= 0){
            die();
            Game.money += reward;
        }
    }
    
    public int getPower(){
        return power;
    }
    
    public boolean isSpawned(){
        return isSpawned;
    }
    
    @Override
    public void run(){
        isSpawned = true;
        while(!isDead()){
            if(stopFor > 0){
                try {
                    Thread.sleep(stopFor);
                    stopFor = 0;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Ennemie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            move();
            try {
                Thread.sleep((long) (10/moveSpeed));
            } catch (InterruptedException ex) {
                Logger.getLogger(Wave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getRange(){
        return range;
    }
    
    public boolean isMultipleShot(){
        return isMultipleShot;
    }
    
    public void setIndiceTuile(int i){
        indiceTuile = i;
    }
    
    public void setDir(String d){
        dir = d;
    }
    
    public void stopFor(int t){
        stopFor = t;
    }
    
    private void move(){
        if(isInBase()) attack();
        if((x%Game.unite == Game.unite/2 && y%Game.unite == Game.unite/2)){
            chooseDirection();
            indiceTuile += 1;
        }
        switch(dir){
            case "down" : 
                y += Math.ceil((float) (Game.unite*moveSpeed)/100f);
                break;
            case "left" : 
                x -= Math.ceil((float) (Game.unite*moveSpeed)/100f);
                break;
            case "up" : 
                y -= Math.ceil((float) (Game.unite*moveSpeed)/100f);
                break;
            case "right" : 
                x += Math.ceil((float) (Game.unite*moveSpeed)/100f);
                break;
        }
    }
    
    private void chooseDirection(){
        if((getBlockType(0, 1) == "road" || getBlockType(0, 1) == "base") && dir != "up") // down
            dir = "down";
        else if((getBlockType(-1, 0) == "road" || getBlockType(-1, 0) == "base") && dir != "right") //  left
            dir = "left";
        else if((getBlockType(0, -1) == "road" || getBlockType(0, -1) == "base") && dir != "down") // up
            dir = "up";
        else if((getBlockType(1, 0) == "road" || getBlockType(1, 0) == "base") && dir != "left") //  right
            dir = "right";
        else
            die();
    }
    
    private String getBlockType(int dx, int dy){
        int x = (int) Math.floor(this.x/Game.unite), y = (int) Math.floor(this.y/Game.unite);
        if(x+dx < 0 || x+dx >= map.get(0).size() || y+dy >= map.size() || y+dy < 0)
            return "wall";
        return map.get(y+dy).get(x+dx).getType();
    }
    
    public void attack(){
        Game.getAttackedBy(power);
        die();
    }
    
    public boolean isInBase(){
        return (x >= xBase-Game.unite/2 && x <= xBase+Game.unite/2 && y >= yBase-Game.unite/2 && y <= yBase+Game.unite/2);
    }
    
    public boolean isDead(){
        return (life <= 0);
    }
    
    public void die(){
        life = 0;
        Game.getEnnemiesDead().add(this);
    }
    
    public boolean isAimed(){
        return isAimed;
    }
    
    public void setIsAimed(boolean b){
        isAimed = b;
    }
}
