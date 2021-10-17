package ennemies;

import java.util.ArrayList;
import towser.Game;

public class FastEnnemie extends Ennemie{
    
    public FastEnnemie(){
        super();
        speedRatio = 1f;
        reward = 4;
        power = 2;
        shootRate = 1;
        moveSpeed = 1.36f;
        range = 30;
        life = 15;
        id = 21;
        width = Game.unite/2-Game.unite/6;
        rgb = new ArrayList<Float>();
        rgb.add(1f);
        rgb.add(1f);
        rgb.add(0f);
    }
}
