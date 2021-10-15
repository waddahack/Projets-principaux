package towers;

import ennemies.Ennemie;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import towser.Game;
import towser.Towser;
import towser.Shootable;
import ui.*;

public abstract class Tower extends Tile implements Shootable{
    
    protected int price, power, bulletSpeed, range, life, width;
    protected double lastShoot = 0, shootRate;
    protected float r = 0.7f, g = 0.7f, b = 0.7f;
    protected String name;
    protected boolean isPlaced = false, renderIt = true, follow, selected = true, isMultipleShot, canRotate;
    protected Ennemie enemyAimed;
    protected ArrayList<Bullet> bullets = new ArrayList<Bullet>(), bulletsToRemove = new ArrayList<Bullet>();
    protected Overlay overlay;
    protected ArrayList<Integer> upgradePrices; // order : range, power, shootRate
    
    public Tower(Texture text, String type){
        super(text, type);
        this.setTower(this);
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<Integer> getUpgradePrices(){
        return upgradePrices;
    }
    
    public int getPrice(){
        return price;
    }
    
    public int getPower(){
        return power;
    }
    
    public boolean isMultipleShot(){
        return isMultipleShot;
    }
    
    public double getShootRate(){
        return shootRate;
    }
    
    public int getLife(){
        return life;
    }
    
    public int getRange(){
        return range;
    }
    
    public void attacked(int power){
        this.life -= power;
        if(life <= 0)
            die();
    }
    
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    
    public ArrayList<Bullet> getBulletsToRemove(){
        return bulletsToRemove;
    }
    
    public void searchAndShoot(ArrayList<Ennemie> enemies){
        int i;
        ArrayList<Ennemie> enemiesInRange = new ArrayList<Ennemie>();
        Ennemie first;
        if(enemies != null && !enemies.isEmpty()){
            i = 0;
            while(i < enemies.size()){
                if(enemies.get(i).isSpawned() && enemies.get(i).isInRangeOf(this))
                    enemiesInRange.add(enemies.get(i));
                i++;
            }
            if(!enemiesInRange.isEmpty()){
                first = enemiesInRange.get(0);
                for(i = 0 ; i < enemiesInRange.size() ; i++)
                    if(enemiesInRange.get(i).getIndiceTuile() > first.getIndiceTuile())
                        first = enemiesInRange.get(i);
                aim(first);
            }
            else
                aim(null);
        }
        else
            aim(null);
        if(enemyAimed != null && !enemyAimed.isDead() && enemyAimed.isInRangeOf(this) && canShoot())
            shoot();
    }
    
    public void aim(Ennemie e){
        if(e == null && enemyAimed != null)
            enemyAimed.setIsAimed(false);
        enemyAimed = e;
        if(e != null){
            e.setIsAimed(true);
            if(canRotate){
                double t = 0.3;
                newAngle = Math.toDegrees(Math.atan2(enemyAimed.getY()-y, enemyAimed.getX()-x));
                
                if(newAngle-angle > 180)
                    newAngle -= 360;
                else if(angle-newAngle > 180)
                    newAngle += 360;
                
                angle = (1-t)*angle + t*newAngle;
                
                if(angle >= 360)
                    angle -= 360;
                else if(angle <= -360)
                    angle += 360;
                
                angle = Math.round(angle);
                newAngle = Math.round(newAngle);
            }
        }   
    }
    
    public void setFollow(boolean b){
        follow = b;
    }
    
    @Override
    public boolean getFollow(){
        return follow;
    }
    
    public boolean canShoot(){
        return (System.currentTimeMillis()-lastShoot >= 1000/shootRate && angle >= newAngle-4 && angle <= newAngle+4);
    }
    
    public void shoot(){
        lastShoot = System.currentTimeMillis();
        Bullet b = new Bullet(this, enemyAimed, 5, 0.9f, 0.1f, 0.1f);
        bullets.add(b);
    }
    
    public void renderBullets(){
        int i;
        Bullet b;
        for(i = 0 ; i < bulletsToRemove.size() ; i++){
            bullets.remove(bulletsToRemove.get(i));
        }
        bulletsToRemove.clear();
        
        for(i = 0 ; i < bullets.size() ; i++)
            bullets.get(i).render();
    }
    
    public Ennemie getEnemyAimed(){
        return enemyAimed;
    }
    
    public boolean isDead(){
        return (life <= 0);
    }
    
    public int getBulletSpeed(){
        return bulletSpeed;
    }
    
    public void render(){
        if(renderIt){
            glColor3f(r, g, b);
            glRectf(Math.floorDiv((int) x, Game.unite)*Game.unite, Math.floorDiv((int) y, Game.unite)*Game.unite, Math.floorDiv((int) x, Game.unite)*Game.unite+Game.unite, Math.floorDiv((int) y, Game.unite)*Game.unite+Game.unite);
        }
        x += Mouse.getDX();
        y -= Mouse.getDY();
    }
    
    public void place(ArrayList<ArrayList<Tile>> map){
        x = Math.floorDiv((int)x, Game.unite);
        y = Math.floorDiv((int)y, Game.unite);
        map.get((int) y).set((int) x, this);
        x = x*Game.unite+Game.unite/2;
        y = y*Game.unite+Game.unite/2;
        isPlaced = true;
        initOverlay();
        Game.money -= price;
        raisePrice();
    }
    
    protected void raisePrice(){
        
    }
    
    public boolean canBePlaced(){
        boolean bool = false;
        String tileType = Game.getMap().get(Math.floorDiv((int)y, Game.unite)).get(Math.floorDiv((int) x, Game.unite)).getType(); // middle point
        if(isInWindow() && tileType == "grass")
            bool = true;
        renderIt = bool;
        return bool;
    }
    
    public boolean isPlaced(){
        return isPlaced;
    }

    private boolean isInWindow() {
        return (x < Towser.windWidth && x > 0 && y < Towser.windHeight && y > 0);
    }
    
    public void destroy(){
        Game.getTowersDestroyed().add(this);
    }
   
    private boolean isMouseIn(){
        int MX = Mouse.getX(), MY = Towser.windHeight-Mouse.getY();
        return (MX >= x-Game.unite/2 && MX <= x+Game.unite/2 && MY >= y-Game.unite/2 && MY <= y+Game.unite/2);
    }
    
    public boolean isClicked(int but){
        return (isMouseIn() && Mouse.isButtonDown(but));
    }
    
    public void die(){
        destroy();
    }
    
    public void setSelected(boolean b){
        selected = b;
    }
    
    public void renderDetails(){
        if(selected && renderIt){
            Towser.drawCircle(Math.floorDiv((int) x, Game.unite)*Game.unite+Game.unite/2, Math.floorDiv((int) y, Game.unite)*Game.unite+Game.unite/2, range, 1, 1, 1);
            if(isPlaced)
                renderOverlay();
        }
    }
    
    public void initOverlay(){
    }
    
    public void renderOverlay(){
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public int getWidth(){
        return width;
    }
    
    public Overlay getOverlay(){
        return overlay;
    }
    
    public void checkOverlayInput(){
    }
}
