package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Text text;
    private Texture bg = null, bgHover = null, currentText = null;
    private boolean hidden = false;
    
    public Button(int x, int y, int width, int height, String t, String bgName){
        build(x ,y, width, height, t, bgName, 0);
    }
    
    public Button(int x, int y, int width, int height, String t, String bgName, int nbClicksMax){
        build(x ,y, width, height, t, bgName, nbClicksMax);
    }
    
    private void build(int x, int y, int width, int height, String t, String bgName, int nbClicksMax){
        this.x = x+width/2;
        this.y = y+height/2;
        this.width = width;
        this.height = height;
        text = new Text(0, 0, t, Towser.normal);
        text.setX(x+width/2-text.getWidth()/2);
        text.setY(y+height/2);
        this.nbClicksMax = nbClicksMax;
        try {
            bg = TextureLoader.getTexture("PNG", new FileInputStream(new File("images/"+bgName+"_button.png")));
            bgHover = TextureLoader.getTexture("PNG", new FileInputStream(new File("images/"+bgName+"_button_hover.png")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void render(){
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
            glDisable(GL_TEXTURE_2D);
            if(text != null)
                text.render();
        }
        
    }

    private boolean isMouseIn(){
        int MX = Mouse.getX(), MY = Towser.windHeight-Mouse.getY();
        return (!hidden && MX >= x-width/2 && MX <= x+width/2 && MY >= y-height/2 && MY <= y+height/2);
    }
    
    public boolean isClicked(int but){
        return (!hidden && isMouseIn() && Mouse.isButtonDown(but));
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
        text.setX(x+width/2-text.getWidth()/2);
    }
    
    public void setY(int y){
        this.y = y;
        text.setY(y+height/2);
    }
    
    public Text getText(){
        return text;
    }
    
    public int getW(){
        return width;
    }
    
    public int getH(){
        return height;
    }
}
