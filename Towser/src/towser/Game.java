package towser;

import ennemies.Ennemie;
import ennemies.Wave;
import java.awt.Color;
import towers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.newdawn.slick.opengl.Texture;
import static towser.Towser.windHeight;
import static towser.Towser.windWidth;
import ui.*;


public class Game {
    
    private static ArrayList<ArrayList<Tile>> map;
    private static ArrayList<Integer> spawn, base;
    public static int unite = 50, money,/* stone, coal, gold, wood, */life, waveNumber, waveReward;
    private int mapW = windWidth/unite, mapH = windHeight/unite;
    private static ArrayList<Tower> towers, towersDestroyed;
    private static ArrayList<Ennemie> ennemies, ennemiesDead;
    private boolean towerSelected = false, inWave = false, renderOverlay = false, dontPlace = false;
    private ArrayList<Wave> waves;
    private Texture currentText = null;
    private ArrayList<Overlay> overlays;
    
    public Game(int lvl){
        readFile("levels/level_"+lvl+".txt");
        towers = new ArrayList<Tower>();
        towersDestroyed = new ArrayList<Tower>();
        ennemies = new ArrayList<Ennemie>();
        ennemiesDead = new ArrayList<Ennemie>();
        life = 100;
        money = 200;
        /*stone = 50;
        wood = 50;
        gold = 0;
        coal = 0;*/
        waveNumber = 1;
        waveReward = 150;
        initOverlays();
    }
    
    public void checkInput(){
        clearArrays();
        // Towers placement
        for(Tower t : towers){
            if(overlays.get(0).getButtons().get(0).isClicked(0)) dontPlace = true;
            if(dontPlace && !Mouse.isButtonDown(0)) dontPlace = false;
            if(!t.isPlaced() && Mouse.isButtonDown(0) && t.canBePlaced() && !overlays.get(0).isClicked(0) && !dontPlace){
                towerSelected = false;
                t.place(map);
            }
            else if(!t.isPlaced() && Mouse.isButtonDown(1)){
                towerSelected = false;
                t.destroy();
            }
            if(t.isClicked(0))
                t.setSelected(true);
        }
        // Click check
        while(Mouse.next()){
            // Reinitializing
            if((Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) && !overlays.get(1).getButtons().get(0).isClicked(0)){
                for(Tower t : towers){
                    if(t.isPlaced() && t.isSelected() && !t.getOverlay().isClicked(0) && !overlays.get(0).getButtons().get(0).isClicked(0))
                        t.setSelected(false);
                }
            }
            // Overlays inputs
            for(Tower t : towers)
                    if(t.isSelected() && t.isPlaced()) t.checkOverlayInput();
            checkOverlaysInput();
        }
        // Wave check
        if(waves != null){
            int i = 0;
            while(i < waves.size() && waves.get(i).isDone())
                i++;
            if(i == waves.size()){
                inWave = false;
                waves = null;
                money += waveReward;
                waveNumber++;
            }
        }
    }
    
    public void render(){
        int i, j;
        Ennemie e;
        currentText = null;
        for(i = 0 ; i < mapH ; i++){
            for(j = 0 ; j < mapW ; j++){
                createBlock(j*unite, i*unite, map.get(i).get(j));
            }
        }
        for(i = 0 ; i < ennemies.size() ; i++){
            e = ennemies.get(i);
            if(e.isSpawned())
                Towser.drawFilledCircle(e.getX(), e.getY(), e.getWidth(), e.getR(), e.getG(), e.getB());
        }
        for(Tower t : towers){
            if(!t.isPlaced()){
                t.canBePlaced();
                t.render();
            }
            else
                t.searchAndShoot(ennemies);
            t.renderBullets();
        }
        for(Tower t : towers)
            if(t.isSelected()){
                t.renderDetails();
                break;
            }
        renderOverlays();
    }
    
    private void readFile(String path){
        map = new ArrayList<ArrayList<Tile>>();
        spawn = new ArrayList<Integer>();
        base = new ArrayList<Integer>();
        try{
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            ArrayList<Tile> row = new ArrayList<Tile>();
            int x = 0, y = 0;
            while(myReader.hasNextInt()){
              int data = myReader.nextInt();
              switch(data){
                  case 0:
                      row.add(new Tile(Towser.wall, "wall"));
                      break;
                  case 1:
                      row.add(new Tile(Towser.grass, "grass"));
                      break;
                  case 2:
                      row.add(new Tile(Towser.road, "road"));
                      break;
                  case 3:
                      row.add(new Tile(0.9f, 0.1f, 0.1f, "spawn"));
                      spawn.add(x);
                      spawn.add(y);
                      break;
                  case 4:
                      row.add(new Tile(0.1f, 0.1f, 0.9f, "base"));
                      base.add(x);
                      base.add(y);
                      break;
                  default:
                      row.add(new Tile(0.0f, 0.0f, 0.0f, "void"));
                      break;
              }
              if(row.size() == mapW){
                  map.add(row);
                  row = new ArrayList<Tile>();
                  x = 0;
                  y++;
              }
              else
                  x++;
              if(map.size() == mapH)
                  break;
            }
            myReader.close();
        }catch (FileNotFoundException e){
            System.out.println("File : "+path+" doesn't exist.");
            e.printStackTrace();
        }
    }
    
    private void clearArrays(){
        int i;
        for(i = 0 ; i < ennemiesDead.size() ; i++)
            ennemies.remove(ennemiesDead.get(i));
        ennemiesDead.clear();
        for(i = 0 ; i < towersDestroyed.size() ; i++)
            towers.remove(towersDestroyed.get(i));
        towersDestroyed.clear();
    }
    
    private void createBlock(int x, int y, Tile tile){
        float r = 100, g = r, b = r;
        float xRot = 0, yRot = 0;
        Texture t = tile.getTexture();
        if(t == null){ // Si pas de text
            r = tile.getR();
            g = tile.getG();
            b = tile.getB();
        }
        else if(tile.getTower() == null){ // si c'est pas une tower
            currentText = t;
            t.bind();
        }
        
        else{ // Si c'est une tower
            createBlock(x, y, tile.getBackgroundTile());
            xRot = (float)(tile.getCos() * unite);
            yRot = (float)(tile.getSin() * unite);
            t.bind();
            currentText = t;
        }

        if(t != null){
            glEnable(GL_TEXTURE_2D);
            glColor3f(1, 1, 1);
        }
        else
            glColor3f(r, g, b);
        
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(x + xRot, y + yRot);
            glTexCoord2f(1, 0);
            glVertex2f(x+unite + xRot, y + yRot);
            glTexCoord2f(1, 1);
            glVertex2f(x+unite + xRot, y+unite + yRot);
            glTexCoord2f(0, 1);
            glVertex2f(x + xRot, y+unite + yRot);
        glEnd();

        if(t != null)
            glDisable(GL_TEXTURE_2D);
    }
    
    private void initOverlays(){
        overlays = new ArrayList<Overlay>();
        Overlay o;
        
        o = new Overlay(Towser.windWidth-5*unite+unite/2, Towser.windHeight/6, 5*unite, 2*Towser.windHeight/3);
        o.addButton(0, 25, 50, 30, "brown", ">>");
        o.setMargin(Game.unite);
        o.addButton(o.getMargin(), o.getMargin()*2, Game.unite, Game.unite, "blue", null);
        o.getButtons().get(1).setBG(Towser.basicTower);
        o.addButton(o.getMargin()*3, o.getMargin()*2, Game.unite, Game.unite, "blue", null);
        o.getButtons().get(2).setBG(Towser.circleTower);
        overlays.add(o);
        
        o = new Overlay(0, 0, Towser.windWidth, unite);
        o.setBG(null);
        o.addButton(o.getW()-100, 25, 200, 50, "brown", "Lancer la vague");
        overlays.add(o);
    }
    
    public void checkOverlaysInput(){
        Overlay o;
        Button but;
        // Overlay 1
        o = overlays.get(0);
        but = o.getButtons().get(0);
        if(but.isClicked(0)){
            if(but.getText() == ">>"){
                o.updateCoords(+(o.getW()-unite), 0);
                o.setButton(0, 0, 25, 50, 30, "brown", "<<");
            }
            else{
                o.updateCoords(-(o.getW()-unite), 0);
                o.setButton(0, 0, 25, 50, 30, "brown", ">>");
            }
            
        }
        for(Button b : o.getButtons()){
            if(b.isClicked(0) && !towerSelected){
                Tower tower = null;
                switch(o.getButtons().indexOf(b)){
                    case 1 :
                        tower = new BasicTower();
                        break;
                    case 2 :
                        tower = new CircleTower();
                        break;
                }
                if(tower.getPrice() <= money){
                    towers.add(tower);
                    towerSelected = true;
                }
            }
        }
        // Overlay 2
        o = overlays.get(1);
        if(o.getButtons().get(0).isClicked(0) && !inWave && !towerSelected && Mouse.getEventButtonState())
            startWave();
    }
    
    public void renderOverlays(){
        for(Overlay o : overlays)
            o.render(); 
        overlays.get(0).render();
        String t;
        Overlay o;
        
        // Overlay 1
        o = overlays.get(0);
        t = "TOURS";
        o.drawText(o.getW()/2-Towser.normalL.getWidth(t)/2, o.getMargin()-Towser.normalL.getHeight(t), t, Towser.normalL);
        
        t = BasicTower.priceP+"*";
        o.drawText(o.getMargin()-Towser.price.getWidth(t)/2, o.getMargin()*2+Game.unite/2, t, Towser.price);
        
        t = CircleTower.priceP+"*";
        o.drawText(o.getMargin()*3-Towser.price.getWidth(t)/2, o.getMargin()*2+Game.unite/2, t, Towser.price);
        //
        // Overlay 2
        o = overlays.get(1);
        t = money+"*";
        o.drawText(o.getW()/5-Towser.astres.getWidth(t)/2, o.getH()/2-Towser.astres.getHeight(t)/2, t, Towser.astres);
        
        t = life+"";
        o.drawText(2*o.getW()/5-Towser.life.getWidth(t)/2, o.getH()/2-Towser.life.getHeight(t)/2, t, Towser.life);
        
        t = "Vague n°"+waveNumber;
        o.drawText(3*o.getW()/5-Towser.normalL.getWidth(t)/2, o.getH()/2-Towser.normalL.getHeight(t)/2, t, Towser.normalL);
        //
    }
    
    @SuppressWarnings("unchecked")
    private void startWave(){
        int i, poidEnnemie = (int) Math.pow(waveNumber, 2), nbEnnemie = poidEnnemie/Math.floorDiv(5+waveNumber, 5);
        waves = new ArrayList<Wave>();
        if(waveNumber >= 10) nbEnnemie = poidEnnemie/3;
        Wave wave = new Wave(nbEnnemie, 0);
        ennemies = (ArrayList<Ennemie>) wave.getEnnemies().clone();
        waves.add(wave);
        if(waveNumber >= 5){
            wave = new Wave((int) Math.floor(nbEnnemie/1.5), 1);
            for(Ennemie e : wave.getEnnemies()){
                i = 0;
                while(i < ennemies.size() && e.getMoveSpeed() <= ennemies.get(i).getMoveSpeed()) i++;
                ennemies.add(i, e);
            }
            waves.add(wave);
        }
        if(waveNumber >= 10){
            wave = new Wave(nbEnnemie/5, 2);
            for(Ennemie e : wave.getEnnemies()){
                i = 0;
                while(i < ennemies.size() && e.getMoveSpeed() <= ennemies.get(i).getMoveSpeed()) i++;
                ennemies.add(i, e);
            }
            waves.add(wave);
        }
        if(waveNumber >= 14){
            wave = new Wave(nbEnnemie/6, 3);
            for(Ennemie e : wave.getEnnemies()){
                i = 0;
                while(i < ennemies.size() && e.getMoveSpeed() <= ennemies.get(i).getMoveSpeed()) i++;
                ennemies.add(i, e);
            }
            waves.add(wave);
        }
        for(Wave w : waves)
            w.start();
        inWave = true;
    }
    
    public static void getAttackedBy(int p){
        life -= p;
        if(life <= 0)
            gameOver();
    }

    private static void gameOver(){
        Towser.state = Towser.State.MENU;
        int i;
        for(i = 0 ; i < ennemies.size() ; i++)
            ennemies.get(i).die();
        Towser.game = null;
    }
    
    public static ArrayList<Tower> getTowers(){
        return towers;
    }
    
    public static ArrayList<Tower> getTowersDestroyed(){
        return towersDestroyed;
    }
    
    public static ArrayList<Ennemie> getEnnemies(){
        return ennemies;
    }
    
    public static ArrayList<Ennemie> getEnnemiesDead(){
        return ennemiesDead;
    }
    
    public static ArrayList<ArrayList<Tile>> getMap(){
        return map;
    }
    
    public static ArrayList<Integer> getSpawn(){
        return spawn;
    }
    
    public static ArrayList<Integer> getBase(){
        return base;
    }
    
    public int getLife(){
        return life;
    }
}
