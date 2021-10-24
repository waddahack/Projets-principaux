package towser;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;
import org.newdawn.slick.opengl.Texture;
import towers.*;
import static towser.Game.unite;
import towser.Towser;

public class Tile {
    
    protected Tower tower;
    protected double x, y;
    protected float r, g, b;
    protected double angle = 0, newAngle = 0;
    protected String type;
    protected ArrayList<Texture> textures;
    private double renderX, renderY;
    
    public Tile(String t){
        textures = new ArrayList<Texture>();
        tower = null;
        type = t;
        this.x = Mouse.getX();
        this.y = Towser.windHeight-Mouse.getY();
    }
    
    public Tile(Texture text, String t){
        textures = new ArrayList<Texture>();
        textures.add(text);
        tower = null;
        type = t;
        this.x = Mouse.getX();
        this.y = Towser.windHeight-Mouse.getY();
    }
    
    public Tile(float r, float g, float b, String t){
        textures = new ArrayList<Texture>();
        tower = null;
        this.r = r;
        this.g = g;
        this.b = b;
        type = t;
        this.x = Mouse.getX();
        this.y = Towser.windHeight-Mouse.getY();
    }
    
    public void render(){
        int n = 1;
        if(!textures.isEmpty())
            n = textures.size();
        for(int i = 0 ; i < n ; i++){
            
            glPushMatrix(); //Save the current matrix.
            glTranslated(renderX, renderY, 0);
            if(i == n-1 && angle != 0)
                glRotated(angle, 0, 0, 1);

            if(!textures.isEmpty()){
                textures.get(i).bind();
                glEnable(GL_TEXTURE_2D);
                glColor3f(1, 1, 1);
            }
            else
                glColor3f(r, g, b);

            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2d(-unite/2, -unite/2);
                glTexCoord2f(1, 0);
                glVertex2d(unite/2, -unite/2);
                glTexCoord2f(1, 1);
                glVertex2d(unite/2, unite/2);
                glTexCoord2f(0, 1);
                glVertex2d(-unite/2, unite/2);
            glEnd();

            if(!textures.isEmpty())
                glDisable(GL_TEXTURE_2D);  

            glPopMatrix(); // Reset the current matrix to the one that was saved.
        }
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
        renderX = Math.floorDiv((int)x, Game.unite)*Game.unite+Game.unite/2;
    }
    
    public void setY(double y){
        this.y = y;
        renderY = Math.floorDiv((int)y, Game.unite)*Game.unite+Game.unite/2;
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
