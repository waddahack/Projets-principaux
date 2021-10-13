package ui;

import org.newdawn.slick.opengl.Texture;
import towers.*;
import towser.Towser;

public class Tile {
    
    protected Texture texture;
    protected Tower tower;
    protected float r, g, b;
    protected double cos = 0, sin = 0;
    protected String type;
    protected Tile backgroundTile;
    
    public Tile(Texture text, String t){
        texture = text;
        tower = null;
        type = t;
    }
    
    public Tile(float r, float g, float b, String t){
        texture = null;
        tower = null;
        this.r = r;
        this.g = g;
        this.b = b;
        type = t;
    }
    
    public double getCos(){
        return cos;
    }
    
    public double getSin(){
        return sin;
    }
    
    public Texture getTexture(){
        return texture;
    }
    
    public String getType(){
        return type;
    }
    
    public Tower getTower(){
        return tower;
    }
    
    public float getR(){
        return r;
    }
    
    public float getG(){
        return g;
    }
    
    public float getB(){
        return b;
    }
    
    protected void setTower(Tower t){
        tower = t;
        backgroundTile = new Tile(Towser.grass, "grass");
    }
    
    public Tile getBackgroundTile(){
        return backgroundTile;
    }
}
