package ennemies;

import java.util.ArrayList;
import towser.Game;

public class StrongEnemy extends Enemy{
    
    public StrongEnemy(){
        super();
        speedRatio = 2;
        reward = 6;
        power = 3;
        shootRate = 1;
        moveSpeed = 2;
        range = 30;
        life = 130;
        weight = 2.5;
        width = Game.unite-Game.unite/4;
        rgb = new ArrayList<Float>();
        rgb.add(0.4f);
        rgb.add(0.9f);
        rgb.add(0.1f);
    }
}
