package towers;

import java.util.ArrayList;
import towser.*;
import ui.*;

public class BasicTower extends Tower{
    
    public static int priceP = 200;
    
    public BasicTower() {
        super("basicTower");
        textures.add(Towser.getTexture("grass"));
        textures.add(Towser.getTexture("basicTowerBase"));
        textures.add(Towser.getTexture("basicTowerTurret"));
        textureStatic = Towser.getTexture(("basicTower"));
        canRotate = true;
        price = priceP;
        totalPrice = price;
        power = 15;
        shootRate = 0.5f;
        range = 3*Game.unite;
        life = 100;
        width = Game.unite;
        name = "Tour simple";
        bulletSpeed = 6;
        follow = false;
        isMultipleShot = false;
        ArrayList<Float> prices = new ArrayList<Float>();
        ArrayList<Float> priceMultipliers = new ArrayList<Float>();
        ArrayList<Float> multipliers = new ArrayList<Float>();
        prices.add(100f); // range
        prices.add(180f); // power
        prices.add(150f); // shoot rate
        prices.add(60f); // bullet speed
        priceMultipliers.add(1.5f);
        priceMultipliers.add(1.25f);
        priceMultipliers.add(1.7f);
        priceMultipliers.add(1.f);
        multipliers.add(1.2f);
        multipliers.add(10f);
        multipliers.add(1.4f);
        multipliers.add(1.4f);
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
        overlay.addButton(upX, upY, upW, upH, "blue", "Upgrade", 3);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()+overlay.getMargin(), upW, upH, "blue", "Upgrade", 3);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*2+overlay.getMargin()*2, upW, upH, "blue", "Upgrade", 5);
        overlay.addButton(upX, upY+overlay.getButtons().get(0).getH()*3+overlay.getMargin()*3, upW, upH, "blue", "Upgrade", 3);
    }
}
