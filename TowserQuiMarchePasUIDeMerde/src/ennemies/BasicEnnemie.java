package ennemies;

import java.util.ArrayList;
import towser.Game;


public class BasicEnnemie extends Ennemie{
    
    public BasicEnnemie(){
        super();
        speedRatio = 0.8f;
        reward = 2;
        power = 1;
        shootRate = 1;
        moveSpeed = 1;
        range = 30;
        life = 10;
        id = 20;
        width = Game.unite/2-Game.unite/6;
        rgb = new ArrayList<Float>();
        rgb.add(1f);
        rgb.add(0.7f);
        rgb.add(0f);
    }
}
