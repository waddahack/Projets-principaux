package towers;

import towser.Tile;
import ennemies.Enemy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;
import org.newdawn.slick.opengl.Texture;
import towser.Game;
import towser.Towser;
import towser.Shootable;
import ui.*;

public abstract class Tower extends Tile implements Shootable{
    
    protected int price, range, power, bulletSpeed, life, width, totalPrice, nbUpgrades = 4;
    protected double lastShoot = 0;
    protected float shootRate;
    protected String name;
    protected boolean isPlaced = false, follow, selected = true, isMultipleShot, canRotate;
    protected Enemy enemyAimed;
    protected ArrayList<Bullet> bullets = new ArrayList<Bullet>(), bulletsToRemove = new ArrayList<Bullet>();
    protected Overlay overlay;
    protected Texture textureStatic;
    protected ArrayList<Float> rgb;
    // Upgrades order : range, power, shootRate, bulletSpeed
    protected Map<String, ArrayList<Float>> upgradesParam;
    
    public Tower(String type){
        super(type);
        this.setTower(this);
        upgradesParam = new HashMap<String, ArrayList<Float>>();
    }
    
    public void update(){
        if(isPlaced){
            if(selected)
                checkOverlayInput();
            searchAndShoot();
            updateBullets();
        }
        renderOther();
    }
    
    private void checkOverlayInput(){
        ArrayList<Button> buts = overlay.getButtons();
        Button b;
        String name = "";
        float up = 0f, upPrice, upPriceIncrease, upMultiplier;
        for(int i = 0 ; i < buts.size() ; i++){
            switch(i){
                case 0:
                    name = "range";
                    up = range;
                    break;
                case 1:
                    name = "power";
                    up = power;
                    break;
                case 2:
                    name = "shootRate";
                    up = shootRate;
                    break;
                case 3:
                    name = "bulletSpeed";
                    up = bulletSpeed;
                    break;
            }
            b = buts.get(i);
            upPrice = upgradesParam.get("prices").get(i);
            upPriceIncrease = upgradesParam.get("priceMultipliers").get(i);
            upMultiplier = upgradesParam.get("multipliers").get(i);
            if(b.isClicked(0) && Game.money >= upPrice){
                Game.money -= upPrice;
                if(upMultiplier > 2)
                    up += upMultiplier;
                else
                    up *= upMultiplier;
                totalPrice += upPrice;
                upgradesParam.get("prices").set(i, upPrice*upPriceIncrease);
                b.click();
                switch(i){
                    case 0:
                        range = (int)up;
                        break;
                    case 1:
                        power = (int)up;
                        break;
                    case 2:
                        shootRate = (float) Math.ceil(up*10)/10;
                        break;
                    case 3:
                        bulletSpeed = (int)up;
                        break;
                }
            }
        }
    }
    
    public void searchAndShoot(){
        int i;
        ArrayList<Enemy> enemies = Game.getEnnemies();
        ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
        Enemy first;
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
    
    public void aim(Enemy e){
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
    
    public void renderOther(){
        if(!isPlaced)
            renderPrevisu();
        
        if(selected && (isPlaced || canBePlaced()))
            renderDetails();
    }
    
    
    
    private void renderPrevisu(){
        if(canBePlaced()){
            int xPos = Math.floorDiv((int) x, Game.unite)*Game.unite, yPos = Math.floorDiv((int) y, Game.unite)*Game.unite;
            textureStatic.bind();
            glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            glEnable(GL_TEXTURE_2D);
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2d(xPos, yPos);
                glTexCoord2f(1, 0);
                glVertex2d(xPos+Game.unite, yPos);
                glTexCoord2f(1, 1);
                glVertex2d(xPos+Game.unite, yPos+Game.unite);
                glTexCoord2f(0, 1);
                glVertex2d(xPos, yPos+Game.unite);
            glEnd();
            glDisable(GL_TEXTURE_2D);
        }
        x += Mouse.getDX();
        y -= Mouse.getDY();
    }
    
    public void renderDetails(){
        int xPos = (int)x, yPos = (int)y;
        if(!isPlaced){
            xPos = Math.floorDiv((int) x, Game.unite)*Game.unite+Game.unite/2;
            yPos = Math.floorDiv((int) y, Game.unite)*Game.unite+Game.unite/2;
        }
        Towser.drawCircle(xPos, yPos, range, Towser.colors.get("blue"));
        Towser.drawCircle(xPos, yPos, range-1, Towser.colors.get("grey"));
        Towser.drawCircle(xPos, yPos, range-2, Towser.colors.get("grey_light"));
        Towser.drawFilledCircle(xPos, yPos, range-2, Towser.colors.get("grey_light"), 0.1f);
        if(isPlaced)
            renderOverlay();
    }
    
    public void renderOverlay(){
        int tX = overlay.getMargin(), tY = overlay.getH()/3;
        String t = "";
        float upPrice;
        Button b;
        
        overlay.render();
        
        overlay.drawText(overlay.getW()/2-Towser.normalL.getWidth(name)/2, overlay.getMargin()-Towser.normalL.getHeight(name)/2, name, Towser.normalL);
        
        for(int i = 0 ; i < nbUpgrades ; i++){
            switch(i){
                case 0:
                    t = "Range : "+range;
                    break;
                case 1:
                    t = "Power : "+power;
                    break;
                case 2:
                    t = "Shoot rate : "+shootRate;
                    break;
                case 3:
                    t = "Bullet speed : "+bulletSpeed;
                    break;
            }
            upPrice = upgradesParam.get("prices").get(i);
            overlay.drawText(tX, tY-Towser.normal.getHeight(t)/2+overlay.getButtons().get(0).getH()*i+overlay.getMargin()*i, t, Towser.normal);
            b = overlay.getButtons().get(i);
            if(upPrice != 0 && !b.isHidden()){
                t = (int)Math.floor(upPrice)+"*";
                overlay.drawText(b.getX()-overlay.getX()-Towser.price.getWidth(t)/2, b.getY()-overlay.getY()-b.getH()/2-Towser.price.getHeight(t), t, Towser.price);
            }
        }
    }
    
    public void updateBullets(){
        for(Bullet b : bulletsToRemove)
            bullets.remove(b);

        bulletsToRemove.clear();
        
        for(Bullet b : bullets)
            b.update();
    }
    
    public void place(ArrayList<ArrayList<Tile>> map){
        x = Math.floorDiv((int)x, Game.unite);
        y = Math.floorDiv((int)y, Game.unite);
        map.get((int) y).set((int) x, this);
        setX(x*Game.unite+Game.unite/2);
        setY(y*Game.unite+Game.unite/2);
        isPlaced = true;
        initOverlay();
        Game.money -= price;
        raisePrice();
    }
    
    public boolean canBePlaced(){
        boolean bool = false;
        String tileType = Game.getMap().get(Math.floorDiv((int)y, Game.unite)).get(Math.floorDiv((int) x, Game.unite)).getType(); // middle point
        if(isInWindow() && tileType == "grass")
            bool = true;
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
    
    public void initOverlay(){
        int upX = overlay.getW()-overlay.getMargin()-50, upY = overlay.getH()/3, upW = 100, upH = 25;
        overlay = new Overlay(Game.unite/2, Towser.windHeight-Game.unite/2-5*Game.unite, 6*Game.unite, 5*Game.unite);
        for(int i = 0 ; i < upgradesParam.size() ; i++){
            overlay.addButton(upX, upY, upW, upH, "blue", "Upgrade", (int)Math.floor(upgradesParam.get("maxUpgradeClicks").get((i))));
        }
        overlay.addButton(upX, upY, upW, upH, "blue", "Upgrade", 3);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()+overlay.getMargin(), upW, upH, "blue", "Upgrade", 3);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*2+overlay.getMargin()*2, upW, upH, "blue", "Upgrade", 5);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*3+overlay.getMargin()*3, upW, upH, "blue", "Upgrade", 3);
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    @Override
    public int getWidth(){
        return width;
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
        Bullet bullet = new Bullet(this, enemyAimed, 5, rgb);
        bullets.add(bullet);
    }
    
    public Enemy getEnemyAimed(){
        return enemyAimed;
    }
    
    @Override
    public boolean isDead(){
        return (life <= 0);
    }
    
    @Override
    public int getBulletSpeed(){
        return bulletSpeed;
    }
    
    public Overlay getOverlay(){
        return overlay;
    }
    
    public String getName(){
        return name;
    }
    
    public int getPrice(){
        return price;
    }
    
    @Override
    public int getPower(){
        return power;
    }
    
    @Override
    public boolean isMultipleShot(){
        return isMultipleShot;
    }
    
    public float getShootRate(){
        return shootRate;
    }
    
    public int getLife(){
        return life;
    }
    
    @Override
    public int getRange(){
        return range;
    }
    
    @Override
    public void attacked(int power){
        this.life -= power;
        if(life <= 0)
            die();
    }
    
    @Override
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    
    @Override
    public ArrayList<Bullet> getBulletsToRemove(){
        return bulletsToRemove;
    }
    
    protected void raisePrice(){
        
    }
}
