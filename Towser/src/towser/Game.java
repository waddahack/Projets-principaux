package towser;

import ennemies.Enemy;
import ennemies.Wave;
import towers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import static towser.Towser.windHeight;
import static towser.Towser.windWidth;
import ui.*;


public class Game {
    
    private static ArrayList<ArrayList<Tile>> map;
    private static Tile spawn, base;
    public static int unite = 50, money, life, waveNumber, waveReward, nbTower = 2;
    private int mapW = windWidth/unite, mapH = windHeight/unite;
    private static ArrayList<Tower> towers, towersDestroyed;
    private static ArrayList<Enemy> enemies, ennemiesDead;
    private static ArrayList<Tile> path;
    private static boolean gameOver;
    private boolean inWave, renderOverlay, dontPlace;
    private Tower towerSelected;
    private ArrayList<Wave> waves;
    private ArrayList<Overlay> overlays;
    
    public Game(int lvl){
        readFile("levels/level_"+lvl+".txt");
        searchPath();
        fixRoadSprites();
        towers = new ArrayList<Tower>();
        towersDestroyed = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();
        ennemiesDead = new ArrayList<Enemy>();
        life = 100;
        money = 20000;
        waveNumber = 30;
        waveReward = 200;
        gameOver = false;
        inWave = false;
        renderOverlay = false;
        dontPlace = false;
        towerSelected = null;
        initOverlays();
    }
    
    private void readFile(String filePath){
        map = new ArrayList<ArrayList<Tile>>();
        try{
            File file = new File(filePath);
            Scanner myReader = new Scanner(file);
            ArrayList<Tile> row = new ArrayList<Tile>();
            int i = 0, j = 0, n;
            Texture t;
            Random rand = new Random();
            String data;
            Tile tile;
            while(myReader.hasNext()){
              data = myReader.next();
              switch(data){
                  case "0":
                      n = rand.nextInt(100)+1;
                      if(n > 93)
                          t = Towser.getTexture("bigPlant1");
                      else if(n > 86)
                          t = Towser.getTexture("bigPlant2");
                      else
                          t = Towser.getTexture("grass");
                      n = 0;
                      if(t != Towser.getTexture("grass"))
                          n = Math.round(rand.nextInt(361)/90)*90;
                      tile = new Tile(t, "grass");
                      tile.setAngle(n);
                      break;
                  case "o":
                      tile = new Tile(Towser.getTexture("roadStraight"), "road");
                      break;
                  case "S":
                      tile = new Tile(0.9f, 0.1f, 0.1f, "spawn");
                      spawn = tile;
                      break;
                  case "B":
                      tile = new Tile(0.1f, 0.1f, 0.9f, "base");
                      base = tile;
                      break;
                  default:
                      tile = new Tile(0.0f, 0.0f, 0.0f, "void");
                      break;
              }
              tile.setX(j*unite);
              tile.setY(i*unite);
              row.add(tile);
              
              if(row.size() == mapW){
                  map.add(row);
                  row = new ArrayList<Tile>();
                  j = 0;
                  i++;
              }
              else
                  j++;
              if(map.size() == mapH)
                  break;
            }
            myReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File : "+path+" doesn't exist.");
            e.printStackTrace();
        }
    }
    
    private void searchPath(){
        path = new ArrayList<Tile>();
        Tile tile = spawn;
        path.add(tile);
        int i = Math.floorDiv((int)spawn.getY(), unite), j = Math.floorDiv((int)spawn.getX(), unite);
        while(tile != base){
            if(i < map.size()-1 && map.get(i+1).get(j).getType() == "road" && !path.contains(map.get(i+1).get(j))) // check en bas
                tile = map.get(i+1).get(j);
            else if(j < map.get(i).size()-1 && map.get(i).get(j+1).getType() == "road" && !path.contains(map.get(i).get(j+1))) // check à droite
                tile = map.get(i).get(j+1);
            else if(j > 0 && map.get(i).get(j-1).getType() == "road" && !path.contains(map.get(i).get(j-1))) // check à gauche
                tile = map.get(i).get(j-1);
            else if(i > 0 && map.get(i-1).get(j).getType() == "road" && !path.contains(map.get(i-1).get(j))) // check en haut
                tile = map.get(i-1).get(j);
            else // sinon c'est la base
                tile = base;
            path.add(tile);
            i = Math.floorDiv((int)tile.getY(), unite);
            j = Math.floorDiv((int)tile.getX(), unite);
        }
    }
    
    private void fixRoadSprites(){
        // Fix road sprites connections
        Tile road, nextRoad = null, previousRoad = null;
        for(int i = 0 ; i < path.size() ; i++){
            if(i > 0)
                previousRoad = path.get(i-1);
            else
                previousRoad = spawn;
            road = path.get(i);
            if(i < path.size()-1)
                nextRoad = path.get(i+1);
            else
                nextRoad = base;

            // Si previousRoad est à GAUCHE et nextRoad est en BAS ou l'inverse
            if((previousRoad.getX()+unite/2 < road.getX() && nextRoad.getY()+unite/2 > road.getY()+unite) || (nextRoad.getX()+unite/2 < road.getX() && previousRoad.getY()+unite/2 > road.getY()+unite)){
                    road.setTexture(Towser.getTexture("roadTurn"));
                    road.setAngle(180);
            }
            // Si previousRoad est à GAUCHE et nextRoad est en HAUT ou l'inverse
            else if((previousRoad.getX()+unite/2 < road.getX() && nextRoad.getY()+unite/2 < road.getY()) || (nextRoad.getX()+unite/2 < road.getX() && previousRoad.getY()+unite/2 < road.getY())){
                road.setTexture(Towser.getTexture("roadTurn"));
                    road.setAngle(270);
            }
            // Si previousRoad est à DROITE et nextRoad est en BAS ou l'inverse
            else if((previousRoad.getX()+unite/2 > road.getX()+unite && nextRoad.getY()+unite/2 > road.getY()+unite) || (nextRoad.getX()+unite/2 > road.getX()+unite && previousRoad.getY()+unite/2 > road.getY()+unite)){
                road.setTexture(Towser.getTexture("roadTurn"));
                road.setAngle(90);
            }
            // Si previousRoad est à DROITE et nextRoad est en HAUT ou l'inverse
            else if((previousRoad.getX()+unite/2 > road.getX()+unite && nextRoad.getY()+unite/2 < road.getY()) || (nextRoad.getX()+unite/2 > road.getX()+unite && previousRoad.getY()+unite/2 < road.getY()))
                road.setTexture(Towser.getTexture("roadTurn"));
            // Si previousRoad est à DROITE et nextRoad est à GAUCHE ou l'inverse
            else if((previousRoad.getX()+unite/2 > road.getX()+unite && nextRoad.getX()+unite/2 < road.getX()) || (nextRoad.getX()+unite/2 > road.getX()+unite && previousRoad.getX()+unite/2 < road.getX()))
                    road.setAngle(90);
        }
    }
    
    public void update(){
        checkInput();
        render();
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
        if(inWave)
            for(Wave w : waves)
                w.update();
        for(Enemy e : enemies)
            e.update();
        for(Tower t : towers)
            t.update();
        
        renderOverlays();
        
        if(gameOver)
            gameOver();
    }
    
    private void checkInput(){
        clearArrays();
        // Towers placement
        for(Tower t : towers){
            if(overlays.get(0).getButtons().get(0).isClicked(0))
                dontPlace = true;
            if(dontPlace && !Mouse.isButtonDown(0))
                dontPlace = false;
            if(!t.isPlaced() && Mouse.isButtonDown(0) && t.canBePlaced() && !overlays.get(0).isClicked(0) && !dontPlace)
                t.place(map);
            else if(!t.isPlaced() && Mouse.isButtonDown(1)){
                selectTower(null);
                t.destroy();
            }
            if(t.isClicked(0) && towerSelected == null)
                selectTower(t);
            else if(t.isClicked(0) && towerSelected != null && towerSelected.isPlaced() && !towerSelected.getOverlay().isClicked(0))
                selectTower(t);
        }
        // Click check
        while(Mouse.next() || Keyboard.next()){
            // Reinitializing if clicking nowhere
            if(towerSelected != null && !towerSelected.isClicked(0) && (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) && !towerSelected.getOverlay().isClicked(0) && !overlays.get(1).getButtons().get(0).isClicked(0)){
                selectTower(null);
            }
            // Overlays inputs
            checkOverlaysInput();
        }
    }
    
    private void render(){
        for(int i = 0 ; i < mapH ; i++){
            for(int j = 0 ; j < mapW ; j++)
                map.get(i).get(j).render();
        }
    }
    
    public void renderOverlays(){ 
        String t;
        Overlay o;
        
        // Overlay selection tours
        if(towerSelected == null || !towerSelected.isPlaced()){
            o = overlays.get(0);
            o.render();
            t = "TOURS";
            o.drawText(o.getW()/2-Towser.normalL.getWidth(t)/2, 0, t, Towser.normalL);

            UnicodeFont font = Towser.canBuy;
            if(Game.money < BasicTower.priceP)
                font = Towser.cantBuy;
            t = BasicTower.priceP+"*";
            o.drawText(o.getMargin()-font.getWidth(t)/2, 10, t, font);
            
            font = Towser.canBuy;
            if(Game.money < CircleTower.priceP)
                font = Towser.cantBuy;
            t = CircleTower.priceP+"*";
            o.drawText(o.getMargin()*3-font.getWidth(t)/2, 10, t, font);
        }
        //
        // Overlay principal
        o = overlays.get(1);
        o.render();
        t = money+"*";
        o.drawText(o.getW()/5-Towser.astres.getWidth(t)/2, o.getH()/2-Towser.astres.getHeight(t)/2, t, Towser.astres);
        
        t = life+"";
        o.drawText(2*o.getW()/5-Towser.life.getWidth(t)/2, o.getH()/2-Towser.life.getHeight(t)/2, t, Towser.life);
        
        t = "Vague n°"+waveNumber;
        o.drawText(3*o.getW()/5-Towser.normalL.getWidth(t)/2, o.getH()/2-Towser.normalL.getHeight(t)/2, t, Towser.normalL);
        //
    }
    
    public void checkOverlaysInput(){
        Overlay o;
        Button but;
        // Overlay selections tours
        o = overlays.get(0);
        for(Button b : o.getButtons()) // Check tower clicked
            if(b.isClicked(0) && towerSelected == null)
                createTower(o.getButtons().indexOf(b)+2);

        for(int i = 0 ; i < nbTower ; i++){ // Check tower pressed by keyboard
            if(Keyboard.isKeyDown(i+2) || Keyboard.isKeyDown(79+i))
                createTower(Keyboard.getEventKey());  
        }
        // Overlay principal
        o = overlays.get(1);
        if(o.getButtons().get(0).isClicked(0) && !inWave && Mouse.getEventButtonState())
            startWave();
    }
    
    private void clearArrays(){
        int i;
        for(i = 0 ; i < ennemiesDead.size() ; i++)
            enemies.remove(ennemiesDead.get(i));
        ennemiesDead.clear();
        for(i = 0 ; i < towersDestroyed.size() ; i++)
            towers.remove(towersDestroyed.get(i));
        towersDestroyed.clear();
    }

    private void initOverlays(){
        overlays = new ArrayList<Overlay>();
        Overlay o;
        
        o = new Overlay(0, Towser.windHeight-2*Game.unite, Towser.windWidth, 2*Game.unite);
        o.setMargin(Game.unite);
        o.addButton(o.getMargin(), o.getMargin(), Game.unite, Game.unite, "blue", null);
        o.getButtons().get(0).setBG(Towser.getTexture("basicTower"));
        o.addButton(o.getMargin()*3, o.getMargin(), Game.unite, Game.unite, "blue", null);
        o.getButtons().get(1).setBG(Towser.getTexture("circleTower"));
        overlays.add(o);
        
        o = new Overlay(0, 0, Towser.windWidth, unite);
        o.setBG(null);
        o.addButton(o.getW()-100, 25, 200, 50, "brown", "Lancer la vague");
        overlays.add(o);
    }
    
    
    @SuppressWarnings("unchecked")
    private void startWave(){
        int poidEnnemie = (int) Math.pow(waveNumber, 2), nbEnnemie = poidEnnemie/Math.floorDiv(5+waveNumber, 5);
        waves = new ArrayList<Wave>();
        if(waveNumber >= 10)
            nbEnnemie = poidEnnemie/3;
        Wave wave = new Wave(nbEnnemie, 0);
        enemies = (ArrayList<Enemy>) wave.getEnnemies().clone();
        waves.add(wave);
        if(waveNumber >= 5){
            wave = new Wave((int) Math.floor(nbEnnemie/1.5), 1);
            for(Enemy e : wave.getEnnemies())
                enemies.add(e);
            waves.add(wave);
        }
        if(waveNumber >= 10){
            wave = new Wave(nbEnnemie/5, 2);
            for(Enemy e : wave.getEnnemies())
                enemies.add(e);
            waves.add(wave);
        }
        if(waveNumber >= 14){
            wave = new Wave(nbEnnemie/6, 3);
            for(Enemy e : wave.getEnnemies())
                enemies.add(e);
            waves.add(wave);
        }

        inWave = true;
    }
    
    public void createTower(int id){
        if(towerSelected != null){
            if(!towerSelected.isPlaced())
                towerSelected.destroy();
            towerSelected.setSelected(false);
            towerSelected = null;
        }
        Tower tower = null;
        if(id >= 79)
            id -= 77;
        id -= 2;
        switch(id){
            case 0 :
                tower = new BasicTower();
                break;
            case 1 :
                tower = new CircleTower();
                break;
        }
        if(tower != null && tower.getPrice() <= money){
            towers.add(tower);
            towerSelected = tower;
        }
    }
    
    public static void getAttackedBy(int p){
        life -= p;
        if(life <= 0)
            gameOver = true;
    }

    private static void gameOver(){
        Towser.state = Towser.State.MENU;
        int i;
        for(i = 0 ; i < enemies.size() ; i++)
            enemies.get(i).die();
        Towser.game = new Game(1);
    }
    
    public void selectTower(Tower t){
        if(towerSelected != null)
            towerSelected.setSelected(false);
        towerSelected = t;
        if(t != null)
            t.setSelected(true);
    }
    
    public static void addEnemie(Enemy e){
        enemies.add(e);
    }
    
    public static ArrayList<Tower> getTowers(){
        return towers;
    }
    
    public static ArrayList<Tower> getTowersDestroyed(){
        return towersDestroyed;
    }
    
    public static ArrayList<Enemy> getEnnemies(){
        return enemies;
    }
    
    public static ArrayList<Enemy> getEnnemiesDead(){
        return ennemiesDead;
    }
    
    public static ArrayList<ArrayList<Tile>> getMap(){
        return map;
    }
    
    public static ArrayList<Tile> getPath(){
        return path;
    }
    
    public static Tile getSpawn(){
        return spawn;
    }
    
    public static Tile getBase(){
        return base;
    }
    
    public int getLife(){
        return life;
    }
}
