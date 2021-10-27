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
        bulletSpeed = 4;
        follow = false;
        isMultipleShot = false;
        rgb = Towser.colors.get("blue");
        ArrayList<Float> prices = new ArrayList<>();
        ArrayList<Float> priceMultipliers = new ArrayList<>();
        ArrayList<Float> multipliers = new ArrayList<>();
        ArrayList<Float> maxUpgradeClicks = new ArrayList<>();
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
        multipliers.add(2.1f);
        maxUpgradeClicks.add(3f);
        maxUpgradeClicks.add(3f);
        maxUpgradeClicks.add(5f);
        maxUpgradeClicks.add(3f);
        upgradesParam.put("prices", prices);
        upgradesParam.put("priceMultipliers", priceMultipliers);
        upgradesParam.put("multipliers", multipliers);
        upgradesParam.put("maxUpgradeClicks", maxUpgradeClicks);
    }
    
    @Override
    protected void raisePrice(){
        priceP *= 1.2;
        price = priceP;
    }
}
