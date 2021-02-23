package ennemies;

import towser.Game;

public class StrongEnnemie extends Ennemie{
    
    public StrongEnnemie(){
        super();
        speedRatio = 2;
        reward = 6;
        power = 3;
        shootRate = 1;
        moveSpeed = 0.7f;
        range = 30;
        life = 130;
        id = 22;
        width = Game.unite/2-Game.unite/8;
        r = 0.4f;
        g = 0.9f;
        b = 0.1f;
    }
}
