package ennemies;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Wave{
    
    private int nbEnnemies, index;
    private double speedRatio;
    private ArrayList<Ennemie> enemies;
    private double startTime;
    
    public Wave(int nbEnnemies, int ennemyType){
        super();
        this.nbEnnemies = nbEnnemies;
        enemies = new ArrayList<Ennemie>();
        int i;
        switch(ennemyType){
            case 0 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new BasicEnnemie());
                break;
            case 1 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new FastEnnemie());
                break;
            case 2 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new StrongEnnemie());
                break;
            case 3 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new TrickyEnnemie());
                break;
            default :
                for(i = 0 ; i < nbEnnemies ; i++)
                    enemies.add(new BasicEnnemie());
                break;
        }
        speedRatio = enemies.get(0).getSpeedRatio()/50;
        index = 0;
        startTime = System.currentTimeMillis();
    }
    
    public ArrayList<Ennemie> getEnnemies(){
        return enemies;
    }
    
    public void update(){
        if(System.currentTimeMillis() - startTime >= 1000*speedRatio && index < enemies.size()){
            enemies.get(index).setStarted(true);
            startTime = System.currentTimeMillis();
            index++;
        }
        for(Ennemie e : enemies)
            e.update();
    }
    
    public boolean isDone(){
        for(Ennemie e : enemies)
            if(!e.isDead())
                return false;
        return true;
    }
}
