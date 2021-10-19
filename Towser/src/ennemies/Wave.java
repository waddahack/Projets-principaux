package ennemies;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Wave{
    
    private int nbEnnemies, index;
    private double speedRatio;
    private ArrayList<Enemy> enemies;
    private double startTime;
    
    public Wave(int nbEnnemies, int ennemyType){
        super();
        this.nbEnnemies = nbEnnemies;
        enemies = new ArrayList<Enemy>();
        int i;
        switch(ennemyType){
            case 0 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new BasicEnemy());
                break;
            case 1 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new FastEnemy());
                break;
            case 2 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new StrongEnemy());
                break;
            case 3 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new TrickyEnemy());
                break;
            default :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new BasicEnemy());
                break;
        }
        speedRatio = enemies.get(0).getSpeedRatio();
        index = 0;
        startTime = System.currentTimeMillis();
    }
    
    public ArrayList<Enemy> getEnnemies(){
        return enemies;
    }
    
    public void update(){
        if(System.currentTimeMillis() - startTime >= 1000*speedRatio && index < enemies.size() || index == 0){
            enemies.get(index).setStarted(true);
            startTime = System.currentTimeMillis();
            index++;
        }
    }
    
    public boolean isDone(){
        for(Enemy e : enemies)
            if(!e.isDead())
                return false;
        return true;
    }
}
