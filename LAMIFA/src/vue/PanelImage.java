/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 *
 * @author guizo
 */
public class PanelImage extends JPanel {
    
    Image lamifa;
    
    public PanelImage(String img) throws IOException{
    
            lamifa = ImageIO.read(new File("images/"+img));

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(lamifa, 0, 0, getWidth(), getHeight(), this);
    }
    
}
