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
import towser.Towser;

public abstract class Ennemie implements Shootable{
    
    protected int reward, power, shootRate, range, life, id, width, indiceTuile = -1;
    protected Texture sprite = null;
    protected ArrayList<Float> rgb;
    protected long stopFor = 0;
    protected String name;
    protected double x, y, xBase, yBase, speedRatio, moveSpeed, stopForStartTime, moveStartTime, checkDirStartTime;
    protected Tile spawn, base;
    protected String dir;
    protected ArrayList<ArrayList<Tile>> map;
    protected boolean isAimed = false, isSpawned, isMultipleShot, started;
    
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
        started = false;
        moveStartTime = System.currentTimeMillis();
        checkDirStartTime = System.currentTimeMillis();
    }
    
    public void update(){
        if(started){
            isSpawned = true;
            if(stopFor > 0 && System.currentTimeMillis() - stopForStartTime >= stopFor){
                move();
                stopFor = 0;
            }
            move();
        }
    }
    
    private void move(){
        if(isOnCenterOfTile() && !isOnSameTile()){
            indiceTuile += 1;
            if(isInBase())
                attack();
            setPositionInCenterOfTile();
            chooseDirection();
            checkDirStartTime = System.currentTimeMillis();
        }
        switch(dir){
            case "down" : 
                y += moveSpeed*Towser.deltaTime/50;
                break;
            case "left" : 
                x -= moveSpeed*Towser.deltaTime/50;
                break;
            case "up" : 
                y -= moveSpeed*Towser.deltaTime/50;
                break;
            case "right" : 
                x += moveSpeed*Towser.deltaTime/50;
                break;
        }
        moveStartTime = System.currentTimeMillis();
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
    
    private boolean isOnCenterOfTile(){
        return(Math.floor(x)%Game.unite <= Game.unite/2+1 && Math.floor(x)%Game.unite >= Game.unite/2-1 && Math.floor(y)%Game.unite <= Game.unite/2+1 && Math.floor(y)%Game.unite >= Game.unite/2-1);
    }
    
    private boolean isOnSameTile(){
        if(indiceTuile == -1)
            return false;
        if(isDead())
            return true;
        int x = (int) Math.floor(this.x/Game.unite)*Game.unite, y = (int) Math.floor(this.y/Game.unite)*Game.unite;
        Tile t = Game.getPath().get(indiceTuile);
        return (x == t.getX() && y == t.getY());
    }
    
    private void setPositionInCenterOfTile(){
        Tile t = Game.getPath().get(indiceTuile);
        x = t.getX()+Game.unite/2;
        y = t.getY()+Game.unite/2;
    }
    
    private boolean canMoveThrough(int dx, int dy){
        int x = (int) Math.floor(this.x/Game.unite), y = (int) Math.floor(this.y/Game.unite);
        if(x+dx < 0 || x+dx >= map.get(0).size() || y+dy >= map.size() || y+dy < 0)
            return false;
        String tileType = map.get(y+dy).get(x+dx).getType();
        return (tileType == "road" || tileType == "base");
    }
    
    public boolean isInRangeOf(Tower t){
        double xDiff = t.getX()-x, yDiff = t.getY()-y;
        double angle, cosinus, sinus;
        angle = Math.atan2(yDiff, xDiff);
        cosinus = Math.floor(Math.cos(angle)*1000)/1000;
        sinus = Math.floor(Math.sin(angle)*1000)/1000;
        return (x <= t.getX()+((t.getRange())*Math.abs(cosinus))+moveSpeed && x >= t.getX()-((t.getRange())*Math.abs(cosinus))-moveSpeed && y <= t.getY()+((t.getRange())*Math.abs(sinus))+moveSpeed && y >= t.getY()-((t.getRange())*Math.abs(sinus))-moveSpeed);
    }
    
    public void attack(){
        Game.getAttackedBy(power);
        die();
    }
    
    public boolean isInBase(){
        return (x >= xBase-Game.unite/2 && x <= xBase+Game.unite/2 && y >= yBase-Game.unite/2 && y <= yBase+Game.unite/2);
    }
    
    public void attacked(int power){
        life -= power;
        if(life <= 0){
            die();
            Game.money += reward;
        }
    }
    
    public void die(){
        life = 0;
        Game.getEnnemiesDead().add(this);
    }
    
    public boolean isDead(){
        return (life <= 0);
    }
    
    public void setStarted(boolean b){
        started = b;
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
    
    public double getMoveSpeed(){
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
    
    public int getPower(){
        return power;
    }
    
    public boolean isSpawned(){
        return isSpawned;
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
        stopForStartTime = System.currentTimeMillis();
    }
    
    public boolean isAimed(){
        return isAimed;
    }
    
    public void setIsAimed(boolean b){
        isAimed = b;
    }
}
