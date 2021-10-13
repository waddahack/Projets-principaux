package towers;

import towers.Tower;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import org.newdawn.slick.opengl.TextureLoader;
import towser.*;
import ui.*;

public class BasicTower extends Tower{
    
    public static int priceP = 200;
    
    public BasicTower() {
        super(Towser.basicTower, "basicTower");
        canRotate = true;
        price = priceP;
        power = 15;
        shootRate = 0.5f;
        range = 3*Game.unite;
        life = 100;
        name = "Tour simple";
        id = 10;
        bulletSpeed = 6;
        width = Game.unite;
        follow = false;
        isMultipleShot = false;
        upgradePrices = new ArrayList<Integer>();
        upgradePrices.add(100); // range
        upgradePrices.add(180); // power 
        upgradePrices.add(150); // shoot rate
        upgradePrices.add(60); // bullet speed
    }
    
    @Override
    protected void raisePrice(){
        priceP *= 1.1;
        price = priceP;
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
        if(!overlay.getButtons().get(3).isHidden()){
            t = upgradePrices.get(3)+"*";
            b = overlay.getButtons().get(3);
            overlay.drawText(b.getX()-overlay.getX()-Towser.price.getWidth(t)/2, b.getY()-overlay.getY()-b.getH()/2-Towser.price.getHeight(t), t, Towser.price);
        }
    }
    
    @Override
    public void checkOverlayInput(){
        ArrayList<Button> buts = overlay.getButtons();
        int n = 0;
        if(buts.get(n).isClicked(0) && Game.money >= upgradePrices.get(n)){
            Game.money -= upgradePrices.get(n);
            range *= 1.2;
            upgradePrices.set(n, (int)(1.5*upgradePrices.get(n)));
            buts.get(n).click();
            if(buts.get(n).getNbClicks() == 3)
                buts.get(n).setHidden(true);
        }
        n = 1;
        if(buts.get(n).isClicked(0) && Game.money >= upgradePrices.get(n)){
            Game.money -= upgradePrices.get(n);
            power += 10;
            upgradePrices.set(n, (int)(1.25*upgradePrices.get(n)));
            buts.get(n).click();
            if(buts.get(n).getNbClicks() == 3)
                buts.get(n).setHidden(true);
        }
        n = 2;
        if(buts.get(n).isClicked(0) && Game.money >= upgradePrices.get(n)){
            Game.money -= upgradePrices.get(n);
            shootRate *= 1.4;
            upgradePrices.set(n, (int)(1.7*upgradePrices.get(n)));
            buts.get(n).click();
            if(buts.get(n).getNbClicks() == 5)
                buts.get(n).setHidden(true);
        }
        n = 3;
        if(buts.get(n).isClicked(0) && Game.money >= upgradePrices.get(n)){
            Game.money -= upgradePrices.get(n);
            bulletSpeed *= 1.4;
            upgradePrices.set(n, (int)(1.8*upgradePrices.get(n)));
            buts.get(n).click();
            if(buts.get(n).getNbClicks() == 3)
                buts.get(n).setHidden(true);
        }
    }
}
