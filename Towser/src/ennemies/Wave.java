package ennemies;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Wave extends Thread{
    
    private int nbEnnemies;
    private double speedRatio;
    private ArrayList<Ennemie> ennemies;
    
    public Wave(int nbEnnemies, int ennemyType){
        super();
        this.nbEnnemies = nbEnnemies;
        ennemies = new ArrayList<Ennemie>();
        int i;
        switch(ennemyType){
            case 0 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    ennemies.add(new BasicEnnemie());
                break;
            case 1 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    ennemies.add(new FastEnnemie());
                break;
            case 2 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    ennemies.add(new StrongEnnemie());
                break;
            case 3 :
                for(i = 0 ; i < nbEnnemies ; i++)
                    ennemies.add(new TrickyEnnemie());
                break;
            default :
                for(i = 0 ; i < nbEnnemies ; i++)
                    ennemies.add(new BasicEnnemie());
                break;
        }
        speedRatio = ennemies.get(0).getSpeedRatio()/50;
    }
    
    public ArrayList<Ennemie> getEnnemies(){
        return ennemies;
    }
    
    @Override
    public void run(){
        for(Ennemie e : ennemies){
            e.start();
            if(ennemies.indexOf(e) < ennemies.size()-1){
                try {
                    Thread.sleep((long) (1000*speedRatio));
                } catch (InterruptedException ex) {
                    Logger.getLogger(Wave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public boolean isDone(){
        for(Ennemie e : ennemies)
            if(!e.isDead()) return false;
        return true;
    }
}
