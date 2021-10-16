package ennemies;

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
        r = 1;
        g = 0.7f;
        b = 0;
    }
}
