package towers;

import java.util.ArrayList;
import towser.Game;
import towser.Towser;
import ui.Button;
import ui.Overlay;
import ui.Text;

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
        priceMultipliers.add(1.5f);
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
        /*overlay = new Overlay(0, Towser.windHeight-2*Game.unite, Towser.windWidth, 2*Game.unite);
        int upW = 100, upH = 25;
        String t;
        
        overlay.addText(new Text(overlay.getW()/2-name.length()/2, 0, name, Towser.normalL));
        
        t = "Range : "+range;
        overlay.addText(new Text(t.length()/2, Game.unite, t, Towser.normal));
        overlay.addButton(new Button(t.length(), Game.unite, upW, upH, new Text("Upgrade"), "blue", 2));
        
        t = "Power : "+power;
        overlay.addText(new Text(t.length()/2, Game.unite, t, Towser.normal));
        overlay.addButton(new Button(t.length(), Game.unite, upW, upH, new Text("Upgrade"), "blue", 3));
        
        t = "Shoot rate : "+shootRate;
        overlay.addText(new Text(t.length()/2, Game.unite, t, Towser.normal));
        overlay.addButton(new Button(t.length(), Game.unite, upW, upH, new Text("Upgrade"), "blue", 4));
        
        t = "Bullet speed : "+bulletSpeed;
        overlay.addText(new Text(t.length()/2, Game.unite, t, Towser.normal));*/
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
