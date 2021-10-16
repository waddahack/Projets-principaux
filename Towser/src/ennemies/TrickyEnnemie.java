package ennemies;

import java.util.ArrayList;
import towser.Game;

public class TrickyEnnemie extends Ennemie{
    
    public TrickyEnnemie(){
        super();
        speedRatio = 2.5f;
        reward = 10;
        power = 8;
        shootRate = 1;
        moveSpeed = 1.2f;
        range = 30;
        life = 35;
        id = 23;
        width = Game.unite/2-Game.unite/4;
        rgb = new ArrayList<Float>();
        rgb.add(0.2f);
        rgb.add(0.2f);
        rgb.add(0.8f);
    }
    
    public void die(){
        life = 0;
        Game.getEnnemiesDead().add(this);
        if(!isInBase()){
            int i;
            for(i = 0 ; i < 3 ; i++){
                Ennemie e = new FastEnnemie();
                e.setX(x);
                e.setY(y);
                e.setIndiceTuile(indiceTuile);
                e.setDir(dir);
                Game.getEnnemies().add(e);
                e.start();
                if(i > 0) e.stopFor(i*250);
            }
        }
    }
}
