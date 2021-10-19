package ennemies;

import java.util.ArrayList;
import towser.Game;


public class BasicEnemy extends Enemy{
    
    public BasicEnemy(){
        super();
        speedRatio = 0.8;
        reward = 2;
        power = 1;
        shootRate = 1;
        moveSpeed = 2.7;
        range = 30;
        life = 10;
        weight = 1;
        width = Game.unite-Game.unite/3;
        rgb = new ArrayList<Float>();
        rgb.add(1f);
        rgb.add(0.7f);
        rgb.add(0f);
    }
}
