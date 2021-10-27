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
        bulletSpeed = 6;
        follow = false;
        isMultipleShot = true;
        rgb = Towser.colors.get("grey");
        ArrayList<Float> prices = new ArrayList<Float>();
        ArrayList<Float> priceMultipliers = new ArrayList<Float>();
        ArrayList<Float> multipliers = new ArrayList<Float>();
        ArrayList<Float> maxUpgradeClicks = new ArrayList<Float>();
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
        maxUpgradeClicks.add(2f);
        maxUpgradeClicks.add(3f);
        maxUpgradeClicks.add(4f);
        maxUpgradeClicks.add(0f);
        upgradesParam.put("prices", prices);
        upgradesParam.put("priceMultipliers", priceMultipliers);
        upgradesParam.put("multipliers", multipliers);
        upgradesParam.put("maxUpgradeClicks", maxUpgradeClicks);
    }
    
    @Override
    protected void raisePrice(){
        priceP *= 1.1;
        price = priceP;
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
