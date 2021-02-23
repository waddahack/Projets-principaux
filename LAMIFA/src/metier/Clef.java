package metier;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import vue.*;

public class Clef {
    private BufferedImage clef = null;
    private int largeur, hauteur, posX, posY;
    private Partition part;
    
    public Clef(String clef, Partition part){
        this.part = part;
        if(clef.equals("sol")){
            try{
                this.clef = ImageIO.read(new File("images\\clef_sol.png"));
                largeur = 3*part.interLignes;
                hauteur = 7*part.interLignes;
                posX = part.margeXClef;
                posY = part.getDebutNotesY() - part.interLignes/4;
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else if(clef.equals("fa")){
            try{
                this.clef = ImageIO.read(new File("images\\clef_fa.png"));
                largeur = 27*part.interLignes/10;
                hauteur = 13*part.interLignes/4;
                posX = part.margeXClef;
                posY = part.getDebutNotesY() + 2*part.interLignes;
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public BufferedImage getImage(){
        return clef;
    }
    
    public int getLargeur(){
        return largeur;
    }
    
    public int getHauteur(){
        return hauteur;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public int getPosY(){
        return posY;
    }
}