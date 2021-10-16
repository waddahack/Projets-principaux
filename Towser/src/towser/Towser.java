package towser;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Towser{
    
    public static enum State{
        MENU, GAME, EXIT
    }
    
    public static State state = State.MENU;
    public static int windWidth = 1000, windHeight = 800; // Width and Height divisible by unite in Game.java
    public static Menu menu;
    public static Game game;
    private static Map<String, Texture> textures;
    public static UnicodeFont normal, astres, life, normalL, price;
    public static DecimalFormat formatter = new DecimalFormat("#.##");
    
    public static void main(String[] args){
        try{
            Display.setDisplayMode(new DisplayMode(windWidth, windHeight));
            Display.setResizable(false);
            Display.setTitle("Towser");
            Display.create();
        }catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        initTextures();
        init();
        setUpFont();

        while(!Display.isCloseRequested()){
            checkInput();
            render();
            
            Display.update();
            Display.sync(60);
        }
        releaseTextures();
        exit();
    }
    
    private static void init(){
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glMatrixMode(GL_PROJECTION);
        glOrtho(0, windWidth, windHeight, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        menu = new Menu();
        game = new Game(1);
    }

    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        switch(state){
            case MENU:
                menu.render();
                break;
            case GAME:
                game.render();
                break;
            case EXIT:
                releaseTextures();
                exit();
                break;
        }
    }  
    
    private static void checkInput() {
        // STATES
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            state = State.MENU;
        // CLICKS
        if(state == State.MENU){
            if(menu.getStart().isClicked(0))
                state = State.GAME;
            if(menu.getExit().isClicked(0))
                state = State.EXIT;
        }
        // KEYS
        else if(state == State.GAME){
            if(game == null) game = new Game(1);
            game.checkInput();
        }
    }
    
    public static void drawCircle(double x, double y, float radius, float r, float g, float b){
        float DEG2RAD = (float) (3.15149/180), degInRad;
        int i;
        glBegin(GL_LINE_LOOP);
        glColor3f(r, g, b);
        for(i = 0 ; i < 360; i++){
            degInRad = i*DEG2RAD;
            glVertex2d(x+cos(degInRad)*radius, y+sin(degInRad)*radius);
       }
       glEnd();
    }
    
    public static void drawFilledCircle(double x, double y, float radius, float r, float g, float b){
        float DEG2RAD = (float) (3.15149/180), degInRad, degInRad2;
        int i;
        glBegin(GL_TRIANGLES);
        glColor3f(r, g, b);
        for(i = 0 ; i < 360; i++){
            degInRad = i*DEG2RAD;
            degInRad2 = (i+1)*DEG2RAD;
            glVertex2d(x, y);
            glVertex2d(x+cos(degInRad)*radius, y+sin(degInRad)*radius);
            glVertex2d(x+cos(degInRad2)*radius, y+sin(degInRad2)*radius);
       }
       glEnd();
    }
    
    private static void exit(){
        Display.destroy();
        System.exit(0);
    }
    
    private static void initTextures() {
        try {
            textures = new HashMap<String, Texture>();
            textures.put("woodBG", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/wood.png"))));
            textures.put("woodDisplay", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/wood_display.png"))));
            textures.put("rocks", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/rocks.png"))));
            textures.put("roadStraight", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/road_straight.png"))));
            textures.put("roadTurn", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/road_turn.png"))));
            textures.put("grass", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/grass.png"))));
            textures.put("bigPlant1", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/big_plant1.png"))));
            textures.put("bigPlant2", TextureLoader.getTexture("PNG", new FileInputStream(new File("images/big_plant2.png"))));
            // Towers
            textures.put("basicTower", TextureLoader.getTexture("PNG", new FileInputStream(new File("towers/basic_tower.png"))));
            textures.put("basicTowerBase", TextureLoader.getTexture("PNG", new FileInputStream(new File("towers/basic_tower_base.png"))));
            textures.put("basicTowerTurret", TextureLoader.getTexture("PNG", new FileInputStream(new File("towers/basic_tower_turret.png"))));
            textures.put("circleTower", TextureLoader.getTexture("PNG", new FileInputStream(new File("towers/circle_tower.png"))));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Towser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Towser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Texture getTexture(String key){
        return textures.get(key);
    }
    
    private static void releaseTextures(){
        for(Map.Entry<String, Texture> entry : textures.entrySet())
            entry.getValue().release();
    }
    
    @SuppressWarnings("unchecked")
    private static void setUpFont() {
        java.awt.Font awtFont = new java.awt.Font("Imprint MT Shadow", java.awt.Font.BOLD, 16);
        normal = new UnicodeFont(awtFont);
        normal.getEffects().add(new ColorEffect(Color.white));
        normal.addAsciiGlyphs();
        
        awtFont = new java.awt.Font("Imprint MT Shadow", java.awt.Font.BOLD, 20);
        astres = new UnicodeFont(awtFont);
        astres.getEffects().add(new ColorEffect(Color.yellow));
        astres.addAsciiGlyphs();
        
        awtFont = new java.awt.Font("Imprint MT Shadow", java.awt.Font.BOLD, 20);
        life = new UnicodeFont(awtFont);
        life.getEffects().add(new ColorEffect(Color.red));
        life.addAsciiGlyphs();
        
        awtFont = new java.awt.Font("Imprint MT Shadow", java.awt.Font.BOLD, 20);
        normalL = new UnicodeFont(awtFont);
        normalL.getEffects().add(new ColorEffect(Color.white));
        normalL.addAsciiGlyphs();
        
        awtFont = new java.awt.Font("Imprint MT Shadow", java.awt.Font.BOLD, 12);
        price = new UnicodeFont(awtFont);
        price.getEffects().add(new ColorEffect(Color.yellow));
        price.addAsciiGlyphs();
        try {
            normal.loadGlyphs();
            astres.loadGlyphs();
            life.loadGlyphs();
            normalL.loadGlyphs();
            price.loadGlyphs();
        } catch (SlickException ex) {
            Logger.getLogger(Towser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
