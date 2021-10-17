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
        rgb = Towser.colors.get("blue");
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
        priceMultipliers.add(1.6f);
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
        priceP *= 1.2;
        price = priceP;
    }
    
    @Override
    public void initOverlay(){
        overlay = new Overlay(0, Towser.windHeight-2*Game.unite, Towser.windWidth, 2*Game.unite);
        int upW = 100, upH = 25;
        String t;
        Text text;
        
        text = new Text(overlay.getW()/2, 0, name, Towser.normalL);
        text.setX(text.getX()-text.getWidth());
        overlay.addText(text);
        
        t = "Range : "+range;
        text = new Text(0, Game.unite/2, t, Towser.normal);
        overlay.addText(text);
        overlay.addButton(new Button(text.getWidth(), Game.unite/2, upW, upH, "Upgrade", "blue", 3));
        
        /*t = "Power : "+power;
        text = new Text(0, Game.unite, t, Towser.normal);
        overlay.addText(text);
        overlay.addButton(new Button(text.length(), Game.unite, upW, upH, "Upgrade", "blue", 3));
        
        t = "Shoot rate : "+shootRate;
        text = new Text(0, Game.unite, t, Towser.normal);
        overlay.addText(text);
        overlay.addButton(new Button(text.length(), Game.unite, upW, upH, "Upgrade", "blue", 3));
        
        t = "Bullet speed : "+bulletSpeed;
        text = new Text(0, Game.unite, t, Towser.normal);
        overlay.addText(text);
        overlay.addButton(new Button(text.length(), Game.unite, upW, upH, "Upgrade", "blue", 3));*/
    }
}
