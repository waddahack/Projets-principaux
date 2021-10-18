package ennemies;

import java.util.ArrayList;
import towser.Game;

public class StrongEnnemie extends Ennemie{
    
    public StrongEnnemie(){
        super();
        speedRatio = 2;
        reward = 6;
        power = 3;
        shootRate = 1;
        moveSpeed = 2;
        range = 30;
        life = 130;
        id = 22;
        width = Game.unite/2-Game.unite/8;
        rgb = new ArrayList<Float>();
        rgb.add(0.4f);
        rgb.add(0.9f);
        rgb.add(0.1f);
    }
}
