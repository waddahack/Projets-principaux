package ennemies;

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
        r = 1;
        g = 1;
        b = 0;
    }
}
