package ennemies;

import java.util.ArrayList;
import towser.Game;

public class FastEnemy extends Enemy{
    
    public FastEnemy(){
        super();
        speedRatio = 1;
        reward = 4;
        power = 2;
        shootRate = 1;
        moveSpeed = 4;
        range = 30;
        life = 15;
        weight = 1.5;
        width = Game.unite-Game.unite/3;
        rgb = new ArrayList<Float>();
        rgb.add(1f);
        rgb.add(1f);
        rgb.add(0f);
    }
}
