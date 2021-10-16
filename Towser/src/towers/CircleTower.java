package towers;

import java.util.ArrayList;
import towser.Game;
import towser.Towser;
import ui.Overlay;

public class CircleTower extends Tower{

    public static int priceP = 350;
    
    public CircleTower(){
        super("circleTower");
        textureStatic = Towser.getTexture(("circleTower"));
        textures.add(Towser.getTexture("grass")); 
        textures.add(textureStatic);
        canRotate = false;
        price = priceP;
        totalPrice = price;
        power = 10;
        shootRate = 0.3f;
        range = (int) (1.2*Game.unite);
        life = 100;
        width = Game.unite;
        name = "Tour circulaire";
        bulletSpeed = 8;
        follow = false;
        isMultipleShot = true;
        rgb = Towser.colors.get("grey");
        ArrayList<Float> prices = new ArrayList<Float>();
        ArrayList<Float> priceMultipliers = new ArrayList<Float>();
        ArrayList<Float> multipliers = new ArrayList<Float>();
        prices.add(150f); // range
        prices.add(150f); // power
        prices.add(170f); // shoot rate
        prices.add(0f); // bullet speed
        priceMultipliers.add(1.5f);
        priceMultipliers.add(1.5f);
        priceMultipliers.add(1.3f);
        priceMultipliers.add(0f);
        multipliers.add(1.2f);
        multipliers.add(1.5f);
        multipliers.add(1.5f);
        multipliers.add(0f);
        upgradesParam.put("prices", prices);
        upgradesParam.put("priceMultipliers", priceMultipliers);
        upgradesParam.put("multipliers", multipliers);
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
        overlay.addButton(upX, upY, upW, upH, "blue", "Upgrade", 2);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()+overlay.getMargin(), upW, upH, "blue", "Upgrade", 3);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*2+overlay.getMargin()*2, upW, upH, "blue", "Upgrade", 4);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*3+overlay.getMargin()*3, upW, upH, "blue", "Upgrade");
        overlay.getButtons().get(3).setHidden(true);
    }
    
    @Override
    public void shoot(){
        float r = 0, g = 0, b = 0;
        lastShoot = System.currentTimeMillis();

        bullets.add(new Bullet(this, x-100, y, 3, rgb));
        bullets.add(new Bullet(this, x+100, y, 3, rgb));
        bullets.add(new Bullet(this, x, y-100, 3, rgb));
        bullets.add(new Bullet(this, x, y+100, 3, rgb));
        bullets.add(new Bullet(this, x-100, y-100, 3, rgb));
        bullets.add(new Bullet(this, x+100, y+100, 3, rgb));
        bullets.add(new Bullet(this, x+100, y-100, 3, rgb));
        bullets.add(new Bullet(this, x-100, y+100, 3, rgb));
    }
}
