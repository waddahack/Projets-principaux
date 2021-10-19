package ennemies;

import java.util.ArrayList;
import towser.Game;

public class TrickyEnemy extends Enemy{
    
    public TrickyEnemy(){
        super();
        speedRatio = 2.5;
        reward = 10;
        power = 8;
        shootRate = 1;
        moveSpeed = 3.3;
        range = 30;
        life = 35;
        weight = 3;
        width = Game.unite-Game.unite/2;
        rgb = new ArrayList<Float>();
        rgb.add(0.2f);
        rgb.add(0.2f);
        rgb.add(0.8f);
    }
    
    @Override
    public void die(){
        life = 0;
        Game.getEnnemiesDead().add(this);
        if(!isInBase()){
            for(int i = 0 ; i < 3 ; i++){
                Enemy e = new FastEnemy();
                e.setX(x);
                e.setY(y);
                e.setIndiceTuile(indiceTuile);
                e.setDir(dir);
                Game.addEnemie(e);
                e.stopFor(i*200);
                e.setStarted(true);
            }
        }
    }
}
