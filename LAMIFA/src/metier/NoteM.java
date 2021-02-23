package metier;

import java.awt.Color;
import java.util.ArrayList;
import vue.*;

/**
 *
 * @author marin
 */
public class NoteM {
    private String nom;
    private Partition part;
    private boolean isLast;
    private ArrayList<NoteM> listeNotesTemps;
    private float type;
    private int octave, posX, posY, posXTraitTop, posYTraitTop, posXTraitBot, posYTraitBot, diametre; // pos = position centre rond noire, posTraitTop = position du haut du trait de la note
    
    public NoteM(String nom, float type, String clef, int octave, boolean isLast, Partition part){
        /*
        octave = 0 ou 1 car chaque note a 2 placements différents
        type : 1 = double croche, 2 = croche, 3 = saute, 4 = noire, 4/3 = triolet
        */
        this.isLast = isLast;
        this.part = part;
        this.nom = nom;
        this.type = type;
        this.octave = octave;
        diametre = part.interLignes;
        posX = part.getDebutNotesX() + part.getInterNotes();
        posXTraitBot = posX;
        posXTraitTop = posXTraitBot;
        posY = 0;
        if(clef.equals("fa")) posY = part.interLignes; // c'est décalé de 2 placement vers le bas la clef de Fa comparé à la clef de Sol
        switch(nom){
            case "Sol67" : posY += part.getDebutNotesY() + octave*(7*part.interLignes/2);
                         break;
            case "Fa 65" : posY += part.getDebutNotesY() + octave*(7*part.interLignes/2) + part.interLignes/2;
                        break;
            case "Mi 64" : posY += part.getDebutNotesY() + octave*(7*part.interLignes/2) + 2*part.interLignes/2;
                        break;
            case "Re 62" : posY += part.getDebutNotesY() + octave*(7*part.interLignes/2) + 3*part.interLignes/2;
                        break;
            case "Do 60" : posY += part.getDebutNotesY() + octave*(7*part.interLignes/2) + 4*part.interLignes/2;
                        break;
            case "Si 71" : if(clef.equals("fa") && type == 1)
                            posY = part.getDebutNotesY();
                        else
                            posY += part.getDebutNotesY() + 5*part.interLignes/2;
                        break;
            case "La 69" : if(clef.equals("fa") && type == 1)
                            posY = part.getDebutNotesY() + part.interLignes/2;
                        else
                            posY += part.getDebutNotesY() + 6*part.interLignes/2;
                        break;
            case "Silence" : posY = part.getDebutNotesY() + 2*part.interLignes;
                             diametre -= (int)1*part.interLignes/3;
                             posXTraitTop = posX + diametre;
                             posYTraitTop = posY;
                             break;
        }
        if(!nom.equals("Silence"))
            posYTraitTop = posY + diametre/2;
        posYTraitBot = posY + 3*part.interLignes + part.interLignes/4;
        
        if(isLast){
            listeNotesTemps = new ArrayList<NoteM>();
            int i = part.getListeNotesM().size()-1, j;
            listeNotesTemps.add(this);
                float taille = part.tailleTemps - type * part.tailleTemps/4;
                while(taille > part.tailleTemps/4){
                    listeNotesTemps.add(0, part.getListeNotesM().get(i));
                    taille -= part.getListeNotesM().get(i).getType() * part.tailleTemps / 4;
                    i--;
                }
        }
    }
    
    public boolean isLast(){
        return isLast;
    }
    
    public ArrayList<NoteM> getListeNotesTemps(){
        return listeNotesTemps;
    }
    
    public String getNom(){
        return nom;
    }
    
    public float getType(){
        return type;
    }
    
    public int getOctave(){
        return octave;
    }
    
    public int getDiametre(){
        return diametre;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public int getPosXTraitBot(){
        return posXTraitBot;
    }
    
    public int getPosYTraitBot(){
        return posYTraitBot;
    }
    
    public int getPosXTraitTop(){
        return posXTraitTop;
    }
    
    public int getPosYTraitTop(){
        return posYTraitTop;
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    
    public void setPosXTraitTop(int posXTraitTop){
        this.posXTraitTop = posXTraitTop;
    }
    
    public void setPosYTraitTop(int posYTraitTop){
        this.posYTraitTop = posYTraitTop;
    }
    
    public void setPosXTraitBot(int posXTraitBot){
        this.posXTraitBot = posXTraitBot;
    }
    
    public void setPosYTraitBot(int posYTraitBot){
        this.posYTraitBot = posYTraitBot;
    }
    
    public String toString(){
        return ("Nom : " + nom + "\nType : " + type + "\nOctave : " + octave + "\n");
    }
}
