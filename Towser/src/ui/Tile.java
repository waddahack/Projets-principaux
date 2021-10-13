package ui;

import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import towers.*;
import towser.Towser;

public class Tile {
    
    protected Tower tower;
    protected float r, g, b;
    protected double angle = 0, newAngle = 0;
    protected String type;
    protected ArrayList<Texture> textures;
    
    public Tile(ArrayList<Texture> textures, String t){
        this.textures = textures;
        tower = null;
        type = t;
    }
    
    public Tile(Texture text, String t){
        textures = new ArrayList<Texture>();
        textures.add(text);
        tower = null;
        type = t;
    }
    
    public Tile(float r, float g, float b, String t){
        tower = null;
        this.r = r;
        this.g = g;
        this.b = b;
        type = t;
    }

    public ArrayList<Texture> getTextures(){
        return textures;
    }
    
    public String getType(){
        return type;
    }
    
    public Tower getTower(){
        return tower;
    }
    
    public double getAngle(){
        return angle;
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
    }
}
