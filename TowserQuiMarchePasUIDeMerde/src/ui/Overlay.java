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
    private ArrayList<Text> texts = new ArrayList<Text>();
    
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
        for(Text t : texts)
            t.render();
    }
    
    public void addText(Text t){
        t.setX(x+margin+t.getX());
        t.setY(y+margin+t.getY());
        texts.add(t);
    }
    
    public void addButton(Button b){
        b.setX(x+margin+b.getX());
        b.setY(y+margin+b.getY());
        buttons.add(b);
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
