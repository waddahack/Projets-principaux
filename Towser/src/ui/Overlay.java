package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Function;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import towser.Towser;

public class Overlay {
    
    private int x, y, width, height, margin = 20;
    private Texture bg = Towser.getTexture("woodDisplay");
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private ArrayList<Integer> texturesX = new ArrayList<Integer>(), texturesY = new ArrayList<Integer>(), texturesW = new ArrayList<Integer>(), texturesH = new ArrayList<Integer>();
    private ArrayList<Texture> textures = new ArrayList<Texture>();
    
    public Overlay(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setBG(Texture bg){
        this.bg = bg;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void render(){
        if(bg != null){
            bg.bind();
            glEnable(GL_TEXTURE_2D);
            glColor3f(1, 1, 1);
        }
        else 
            glColor3f(0, 0, 0);
        
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2i(x, y);
            glTexCoord2f(1, 0);
            glVertex2i(x+width, y);
            glTexCoord2f(1, 1);
            glVertex2i(x+width, y+height);
            glTexCoord2f(0, 1);
            glVertex2i(x, y+height);
        glEnd();
        
        if(bg != null)
            glDisable(GL_TEXTURE_2D);
        
        for(Button b : buttons)
            b.render();
        
        int x, y, w, h;
        for(Texture t : textures){
            x = texturesX.get(textures.indexOf(t));
            y = texturesY.get(textures.indexOf(t));
            w = texturesW.get(textures.indexOf(t));
            h = texturesH.get(textures.indexOf(t));
            t.bind();
            glEnable(GL_TEXTURE_2D);
            glColor3f(1, 1, 1);
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2i(x-w/2, y-h/2);
                glTexCoord2f(1, 0);
                glVertex2i(x+w/2, y-h/2);
                glTexCoord2f(1, 1);
                glVertex2i(x+w/2, y+h/2);
                glTexCoord2f(0, 1);
                glVertex2i(x-w/2, y+h/2);
            glEnd();
            glDisable(GL_TEXTURE_2D);
        }
    }
    
    public void addButton(int x, int y, int w, int h, String type, String text){
        Button b = new Button(this.x+x, this.y+y, w, h, text, type);
        buttons.add(b);
    }
    
    public void addButton(int x, int y, int w, int h, String type, String text, int nbClicksMax){
        Button b = new Button(this.x+x, this.y+y, w, h, text, type, nbClicksMax);
        buttons.add(b);
    }
    
    public void setButton(int i, int x, int y, int w, int h, String type, String text){
        Button b = new Button(this.x+x, this.y+y, w, h, text, type);
        buttons.set(i, b);
    }
    
    public void addImage(int x, int y, int w, int h, Texture texture){
        textures.add(texture);
        texturesX.add(this.x+x);
        texturesY.add(this.y+y);
        texturesW.add(w);
        texturesH.add(h);
    }
    
    private boolean isMouseIn(){
        int MX = Mouse.getX(), MY = Towser.windHeight-Mouse.getY();
        return (MX >= x && MX <= x+width && MY >= y && MY <= y+height);
    }
    
    public boolean isClicked(int but){
        return (isMouseIn() && Mouse.isButtonDown(but));
    }
    
    public void updateCoords(int xChange, int yChange){
        x += xChange;
        y += yChange;
        for(Button b : buttons){
            b.setX(b.getX()+xChange);
            b.setY(b.getY()+yChange);
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getW(){
        return width;
    }
    
    public int getH(){
        return height;
    }
    
    public int getMargin(){
        return margin;
    }
    
    public void setMargin(int m){
        margin = m;
    }
    
    public ArrayList<Button> getButtons(){
        return buttons;
    } 

    public void drawText(int x, int y, String text, UnicodeFont font) {
        font.drawString(this.x+x, this.y+y, text);
    }
}
