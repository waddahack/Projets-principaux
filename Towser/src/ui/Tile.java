package ui;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import towers.*;
import towser.Towser;

public class Tile {
    
    protected Tower tower;
    protected double x, y;
    protected float r, g, b;
    protected double angle = 0, newAngle = 0;
    protected String type;
    protected ArrayList<Texture> textures;
    
    public Tile(Texture text, String t){
        textures = new ArrayList<Texture>();
        textures.add(text);
        tower = null;
        type = t;
        this.x = Mouse.getX();
        this.y = Towser.windHeight-Mouse.getY();
    }
    
    public Tile(Texture text, double x, double y, String t){
        textures = new ArrayList<Texture>();
        textures.add(text);
        tower = null;
        type = t;
        this.x = x;
        this.y = y;
    }
    
    public Tile(float r, float g, float b, String t){
        tower = null;
        this.r = r;
        this.g = g;
        this.b = b;
        type = t;
        this.x = Mouse.getX();
        this.y = Towser.windHeight-Mouse.getY();
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public ArrayList<Texture> getTextures(){
        return textures;
    }
    
    public void setTexture(Texture t){
        if(textures.size() == 1){
            textures.clear();
            textures.add(t);
        }
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
    
    public void setAngle(double a){
        angle = a;
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
