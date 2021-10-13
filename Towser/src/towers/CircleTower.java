package towers;

import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import towser.Game;
import towser.Towser;
import ui.Button;
import ui.Overlay;

public class CircleTower extends Tower{

    public static int priceP = 350;
    
    public CircleTower(){
        super(Towser.circleTower, "circleTower");
        canRotate = false;
        price = priceP;
        power = 10;
        shootRate = 0.3;
        range = (int) (1.2*Game.unite);
        life = 100;
        name = "Tour circulaire";
        id = 11;
        bulletSpeed = 8;
        width = Game.unite;
        follow = false;
        isMultipleShot = true;
        upgradePrices = new ArrayList<Integer>();
        upgradePrices.add(150); // range
        upgradePrices.add(150); // power 
        upgradePrices.add(170); // shoot rate
        upgradePrices.add(0); // bullet speed
    }
    
    @Override
    protected void raisePrice(){
        priceP *= 1.1;
        price = priceP;
    }
    
    @Override
    public void checkOverlayInput(){
        ArrayList<Button> buts = overlay.getButtons();
        Button b;
        int n;
        n = 0;
        b = buts.get(n);
        if(b.isClicked(0) && Game.money >= upgradePrices.get(n)){
            Game.money -= upgradePrices.get(n);
            range *= 1.2;
            upgradePrices.set(n, (int)(1.5*upgradePrices.get(n)));
            b.click();
            if(b.getNbClicks() == 2)
                b.setHidden(true);
        }
        n = 1;
        b = buts.get(n);
        if(b.isClicked(0) && Game.money >= upgradePrices.get(n)){
            Game.money -= upgradePrices.get(n);
            power *= 1.5;
            upgradePrices.set(n, (int)(1.5*upgradePrices.get(n)));
            b.click();
            if(b.getNbClicks() == 3)
                b.setHidden(true);
        }
        n = 2;
        b = buts.get(n);
        if(b.isClicked(0) && Game.money >= upgradePrices.get(n)){
            b.click();
            Game.money -= upgradePrices.get(n);
            shootRate *= 1.5;
            upgradePrices.set(n, (int)(1.3*upgradePrices.get(n)*b.getNbClicks()));
            if(b.getNbClicks() == 3)
                upgradePrices.set(n, 850);
            else if(b.getNbClicks() == 4)
                b.setHidden(true);
        }
    }
    
    @Override
    public void initOverlay(){
        if(x-Game.unite/2 > Game.unite/2+6*Game.unite || y+Game.unite/2 < Towser.windHeight-Game.unite/2-5*Game.unite)
            overlay = new Overlay(Game.unite/2, Towser.windHeight-Game.unite/2-5*Game.unite, 6*Game.unite, 5*Game.unite);
        else
            overlay = new Overlay(Game.unite/2, 3*Game.unite/2, 6*Game.unite, 5*Game.unite);
        int upX = overlay.getW()-overlay.getMargin()-50, upY = overlay.getH()/3, upW = 100, upH = 25;
        overlay.addButton(upX, upY, upW, upH, "blue", "Upgrade");
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()+overlay.getMargin(), upW, upH, "blue", "Upgrade");
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*2+overlay.getMargin()*2, upW, upH, "blue", "Upgrade");
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*3+overlay.getMargin()*3, upW, upH, "blue", "Upgrade");
        overlay.getButtons().get(3).setHidden(true);
    }
    
    @Override
    public void renderOverlay(){
        int tX = overlay.getMargin(), tY = overlay.getH()/3, k = 0;
        String t;
        Button b;
        overlay.render();
        
        overlay.drawText(overlay.getW()/2-Towser.normalL.getWidth(name)/2, overlay.getMargin()-Towser.normalL.getHeight(name)/2, name, Towser.normalL);
        
        t = "Range : "+range;
        overlay.drawText(tX, tY-Towser.normal.getHeight(t)/2, t, Towser.normal);
        if(!overlay.getButtons().get(0).isHidden()){
            t = upgradePrices.get(0)+"*";
            b = overlay.getButtons().get(0);
            overlay.drawText(b.getX()-overlay.getX()-Towser.price.getWidth(t)/2, b.getY()-overlay.getY()-b.getH()/2-Towser.price.getHeight(t), t, Towser.price);
        }
        
        k++;
        t = "Power : "+power;
        overlay.drawText(tX, tY-Towser.normal.getHeight(t)/2+overlay.getButtons().get(0).getH()+overlay.getMargin(), t, Towser.normal);
        if(!overlay.getButtons().get(1).isHidden()){
            t = upgradePrices.get(1)+"*";
            b = overlay.getButtons().get(1);
            overlay.drawText(b.getX()-overlay.getX()-Towser.price.getWidth(t)/2, b.getY()-overlay.getY()-b.getH()/2-Towser.price.getHeight(t), t, Towser.price);
        }
        
        k++;
        t = "Shoot rate : "+Math.floor(shootRate*100)/100;
        overlay.drawText(tX, tY-Towser.normal.getHeight(t)/2+overlay.getButtons().get(0).getH()*k+overlay.getMargin()*k, t, Towser.normal);
        if(!overlay.getButtons().get(2).isHidden()){
            t = upgradePrices.get(2)+"*";
            b = overlay.getButtons().get(2);
            overlay.drawText(b.getX()-overlay.getX()-Towser.price.getWidth(t)/2, b.getY()-overlay.getY()-b.getH()/2-Towser.price.getHeight(t), t, Towser.price);
        }
        
        k++;
        t = "Bullet speed : "+bulletSpeed;
        overlay.drawText(tX, tY-Towser.normal.getHeight(t)/2+overlay.getButtons().get(0).getH()*k+overlay.getMargin()*k, t, Towser.normal);
    }
    
    @Override
    public void shoot(){
        float r = 0, g = 0, b = 0;
        lastShoot = System.currentTimeMillis();
        Bullet bul = new Bullet(this, x-100, y, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x+100, y, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x, y-100, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x, y+100, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x-100, y-100, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x+100, y+100, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x+100, y-100, 3, r, g, b);
        bullets.add(bul);
        
        bul = new Bullet(this, x-100, y+100, 3, r, g, b);
        bullets.add(bul);
    }
}
