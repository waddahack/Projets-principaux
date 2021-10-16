package ennemies;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;
import towers.Bullet;
import towers.Tower;
import towser.Game;
import towser.Shootable;
import towser.Tile;

public abstract class Ennemie extends Thread implements Shootable{
    
    protected int reward, power, shootRate, range, life, id, width, indiceTuile = 0;
    protected Texture sprite = null;
    protected float moveSpeed;
    protected ArrayList<Float> rgb;
    protected long stopFor = 0;
    protected String name;
    protected double x, y, xBase, yBase, speedRatio;
    protected Tile spawn, base;
    protected String dir;
    protected ArrayList<ArrayList<Tile>> map;
    protected boolean isAimed = false, isSpawned, isMultipleShot;
    
    public Ennemie(){
        spawn = Game.getSpawn();
        base = Game.getBase();
        map = Game.getMap();
        if(spawn != null){
            x = spawn.getX()+Game.unite/2;
            y = spawn.getY()+Game.unite/2;
        }
        if(base != null){
            xBase = base.getX()+Game.unite/2;
            yBase = base.getY()+Game.unite/2;
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
    
    public ArrayList<Float> getRGB(){
        return rgb;
    }
    
    public int getIndiceTuile(){
        return indiceTuile;
    }
    
    public float getMoveSpeed(){
        return moveSpeed;
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
        if((x%Game.unite == Game.unite/2 && y%Game.unite == Game.unite/2)){
            if(isInBase())
                attack();
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
        if(canMoveThrough(0, 1) && dir != "up") // down
            dir = "down";
        else if(canMoveThrough(-1, 0) && dir != "right") //  left
            dir = "left";
        else if(canMoveThrough(0, -1) && dir != "down") // up
            dir = "up";
        else if(canMoveThrough(1, 0) && dir != "left") //  right
            dir = "right";
        else
            die();
    }
    
    private boolean canMoveThrough(int dx, int dy){
        int x = (int) Math.floor(this.x/Game.unite), y = (int) Math.floor(this.y/Game.unite);
        if(x+dx < 0 || x+dx >= map.get(0).size() || y+dy >= map.size() || y+dy < 0)
            return false;
        String tileType = map.get(y+dy).get(x+dx).getType();
        return (tileType == "road" || tileType == "base");
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
