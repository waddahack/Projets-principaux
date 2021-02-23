package towser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.System.Logger;
import ui.Button;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Drawable;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static towser.Towser.windHeight;
import static towser.Towser.windWidth;
import static towser.Towser.woodBG;
import ui.Overlay;


public class Menu {
    
    private Button start, option, exit;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    
    public Menu(){
        start = new Button(windWidth/2, windHeight/3, 200, 50, "Jouer", "blue");
        option = new Button(windWidth/2, windHeight/3+100, 200, 50, "Options", "blue");
        exit = new Button(windWidth/2,windHeight/3+300, 200, 50, "Quitter", "blue");
        buttons.add(start);
        buttons.add(option);
        buttons.add(exit);
    }
    
    public Button getStart(){
        return start;
    }
    
    public Button getOption(){
        return option;
    }
    
    public Button getExit(){
        return exit;
    }
    
    public void render(){
        glEnable(GL_TEXTURE_2D);
        glColor3f(1, 1, 1);
        Towser.woodBG.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2i(0, 0);
            glTexCoord2f(1, 0);
            glVertex2i(windWidth, 0);
            glTexCoord2f(1, 1);
            glVertex2i(windWidth, windHeight);
            glTexCoord2f(0, 1);
            glVertex2i(0, windHeight);
        glEnd();
        glDisable(GL_TEXTURE_2D);
        for(Button b : buttons){
            b.render();
        }
    }
    
    private Texture loadTexture(String key) {
        try {
            return TextureLoader.getTexture("PNG", new FileInputStream(new File("images/"+key+".png")));
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Button> getButtons(){
        return buttons;
    }
}
