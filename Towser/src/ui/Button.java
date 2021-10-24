package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import towser.Towser;


public class Button {
    
    private int x, y, width, height, nbClicks = 0, nbClicksMax;
    private String text;
    private Texture bg = null, bgHover = null, currentText = null;
    private boolean hidden = false;
    private boolean mouseButtonDown = false;
    
    public Button(int x, int y, int width, int height, String text, String bgName){
        build(x ,y, width, height, text, bgName, 0);
    }
    
    public Button(int x, int y, int width, int height, String text, String bgName, int nbClicksMax){
        build(x ,y, width, height, text, bgName, nbClicksMax);
    }
    
    private void build(int x, int y, int width, int height, String text, String bgName, int nbClicksMax){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.nbClicksMax = nbClicksMax;
        try {
            bg = TextureLoader.getTexture("PNG", new FileInputStream(new File("images/"+bgName+"_button.png")));
            bgHover = TextureLoader.getTexture("PNG", new FileInputStream(new File("images/"+bgName+"_button_hover.png")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            Keyboard.create();
            Keyboard.enableRepeatEvents(true);
        }
        catch(Exception e){
           
        }
    }
    
    public void click(){
        nbClicks++;
        if(nbClicks == nbClicksMax)
            hidden = true;
    }
    
    public void setHidden(boolean b){
        hidden = b;
    }
    
    public boolean isHidden(){
        return hidden;
    }
    
    public void update(){
        mouseButtonDown = Mouse.isButtonDown(0);
        render();
    }
    
    private void render(){
        if(!hidden){
            if(isMouseIn()){
                if(bgHover != null && currentText != bgHover)
                    currentText = bgHover;
            }
            else if(bg != null && currentText != bg)
                    currentText = bg;
            currentText.bind();
            glEnable(GL_TEXTURE_2D);
            glColor3f(1, 1, 1);
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2i(x-width/2, y-height/2);
                glTexCoord2f(1, 0);
                glVertex2i(x+width/2, y-height/2);
                glTexCoord2f(1, 1);
                glVertex2i(x+width/2, y+height/2);
                glTexCoord2f(0, 1);
                glVertex2i(x-width/2, y+height/2);
            glEnd();
            if(text != null) Towser.normal.drawString(x-Towser.normal.getWidth(text)/2, y-Towser.normal.getHeight(text)/2, text);
            glDisable(GL_TEXTURE_2D);
        }
        
    }

    private boolean isMouseIn(){
        int MX = Mouse.getX(), MY = Towser.windHeight-Mouse.getY();
        return (!hidden && MX >= x-width/2 && MX <= x+width/2 && MY >= y-height/2 && MY <= y+height/2);
    }
    
    public boolean isClicked(int but){
        return (!hidden && isMouseIn() && Mouse.isButtonDown(0) && !mouseButtonDown);
    }
    
    public void setBG(Texture t){
        bg = t;
        bgHover = null;
    }
    
    public int getNbClicks(){
        return nbClicks;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public String getText(){
        return text;
    }
    
    public int getW(){
        return width;
    }
    
    public int getH(){
        return height;
    }
}
