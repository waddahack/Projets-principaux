package vue;

import java.util.ArrayList;
import java.util.Random;
import metier.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class NoteV{
    private NoteM note;
    private Random random = new Random();
    private int rand, type, epaisseur;
    private ArrayList<NoteM> listeNotes, listeNotesTemps;
    private Partition part;
    private boolean colorier;
    private static boolean numberDone = false;
    private BufferedImage imgTrois;
    
    public NoteV(NoteM note, Partition part, Graphics g){
        this.note = note;
        this.part = part;
        colorier = false;
        listeNotes = part.getListeNotesM();
        listeNotesTemps = note.getListeNotesTemps();
        epaisseur = 2;
        if(note.getType() == (float)(4f/3f)){
            try{
                imgTrois = ImageIO.read(new File("images\\trois.png"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!note.getNom().equals("Silence")){ // Si la note est une vrai note
            dessinerNote(g);
        }
        else{ // Si la note est en fait un silence
            dessinerSilence(g);
        }
    }
    
    public void dessinerNote(Graphics g){
        int i, j;
        dessiner(g);
        if(note.isLast() && listeNotesTemps != null)
            relier(g);}
    
    private void dessiner(Graphics g){
        int i, a = 0;
        if(colorier) a = 1;
        g.fillOval(note.getPosX(), note.getPosY(), note.getDiametre()+a, note.getDiametre()+a);
        for(i = 0 ; i < epaisseur ; i++)
            g.drawLine(note.getPosXTraitTop()+i, note.getPosYTraitTop(), note.getPosXTraitBot()+i, note.getPosYTraitBot());
    }
    
    public void colorier(Color couleur){
        Graphics g = part.getGraphics();
        colorier = true;
        g.setColor(couleur);
        dessinerNote(g);
        colorier = false;
    }
    
    private void relier(Graphics g){
        int i;
        NoteM noteBasse = listeNotesTemps.get(0), derniere = listeNotesTemps.get(listeNotesTemps.size()-1);
        for(NoteM note : listeNotesTemps)
            if(note.getPosYTraitBot() > noteBasse.getPosYTraitBot())
                noteBasse = note;
        
        g.fillRect(listeNotesTemps.get(0).getPosXTraitBot(), noteBasse.getPosYTraitBot(), note.getPosX() - listeNotesTemps.get(0).getPosX() + epaisseur, 4);
        
        for(NoteM note : listeNotesTemps){
            for(i = 0 ; i < epaisseur ; i++)
                g.drawLine(note.getPosXTraitBot()+i, note.getPosYTraitBot(), note.getPosXTraitBot()+i, noteBasse.getPosYTraitBot());
        
        if(derniere != null){
            // Dessiner la particularité des sautes
            if(note.getType() == 3){
                g.fillOval(note.getPosX()+15, note.getPosY()+10, 6, 6);
                g.fillRect(derniere.getPosX() - (part.tailleTemps/4+epaisseur)/2, noteBasse.getPosYTraitBot()-4*2, (part.tailleTemps/4+epaisseur)/2, 4);
            }
            // Dessiner la particularité des doubles croche
            else if(note.getType() == 1 && note != derniere)
                g.fillRect(note.getPosX(), noteBasse.getPosYTraitBot()-4*2, part.tailleTemps/4+epaisseur, 4);
            }
            // Dessiner la particularité des triolet (le petit 3)
            if(Float.compare(note.getType(), 1.8f) < 0 && Float.compare(note.getType(), 1.1f) > 0/* && !numberDone*/){
                g.drawImage(imgTrois, listeNotesTemps.get(1).getPosXTraitBot(), noteBasse.getPosYTraitBot() + 10, 10, 15, null);
                numberDone = true;
                if(note.equals(derniere)) numberDone = false;
            }
        }
    }
    
    /* PAS UTILE ATM
    private void relierCroche(Graphics g) {
        g.fillRect(note.getPosX(), note.getPosYTraitBot(), (part.tailleTemps/4+epaisseur)/4, 3);
        g.fillRect(note.getPosX()+(part.tailleTemps/4+epaisseur)/4, note.getPosYTraitBot()-(part.tailleTemps/4+epaisseur)/4, 2, (part.tailleTemps/4+epaisseur)/4);
        g.fillRect(note.getPosX(), note.getPosYTraitBot()-2*3, (part.tailleTemps/4+epaisseur)/4, 3);
        g.fillRect(note.getPosX()+(part.tailleTemps/4+epaisseur)/4, note.getPosYTraitBot()-(part.tailleTemps/4+epaisseur)/4-2*2, 2, (part.tailleTemps/4+epaisseur)/4);
    }
    
    public void relierDoubleCroches(Graphics g){
        g.fillRect(note.getPosX(), note.getPosYTraitBot(), (part.tailleTemps/4+epaisseur)/4, 3);
        g.fillRect(note.getPosX()+(part.tailleTemps/4+epaisseur)/4, note.getPosYTraitBot()-(part.tailleTemps/4+epaisseur)/4, 2, (part.tailleTemps/4+epaisseur)/4);
        g.fillRect(note.getPosX(), note.getPosYTraitBot()-2*3, (part.tailleTemps/4+epaisseur)/4, 3);
        g.fillRect(note.getPosX()+(part.tailleTemps/4+epaisseur)/4, note.getPosYTraitBot()-(part.tailleTemps/4+epaisseur)/4-2*2, 2, (part.tailleTemps/4+epaisseur)/4);
        
        g.fillRect(note.getPosX(), note.getPosYTraitBot()-12, (part.tailleTemps/4+epaisseur)/4, 3);
        g.fillRect(note.getPosX()+(part.tailleTemps/4+epaisseur)/4, note.getPosYTraitBot()-(part.tailleTemps/4+epaisseur)/4-12, 2, (part.tailleTemps/4+epaisseur)/4);
        g.fillRect(note.getPosX(), note.getPosYTraitBot()-2*3-12, (part.tailleTemps/4+epaisseur)/4, 3);
        g.fillRect(note.getPosX()+(part.tailleTemps/4+epaisseur)/4, note.getPosYTraitBot()-(part.tailleTemps/4+epaisseur)/4-2*2-12, 2, (part.tailleTemps/4+epaisseur)/4);
    }
    */
    private void dessinerSilence(Graphics g) {
        int i;
        switch(type){
            case 1 : g.fillOval(note.getPosX(), note.getPosY(), note.getDiametre(), note.getDiametre());
                     g.fillOval(note.getPosXTraitBot()-(note.getPosXTraitTop()-note.getPosXTraitBot())/2, note.getPosY()+part.interLignes, note.getDiametre(), note.getDiametre());
                     break;
            case 2 : g.fillOval(note.getPosX(), note.getPosY(), note.getDiametre(), note.getDiametre());
                     break;
            case 3 : g.fillOval(note.getPosX(), note.getPosY(), note.getDiametre(), note.getDiametre()); // le rond du demi soupire
                     g.fillOval(note.getPosX()+part.tailleTemps/2, note.getPosY(), note.getDiametre(), note.getDiametre()); // les deux ronds du quart de soupire
                     g.fillOval(note.getPosXTraitBot()-(note.getPosXTraitTop()-note.getPosXTraitBot())/2+part.tailleTemps/2, note.getPosY()+part.interLignes, note.getDiametre(), note.getDiametre());
                     break;
        }
        if(type != 4 && type != 3){
            for(i = 0 ; i < epaisseur+1 ; i++)
                g.drawLine(note.getPosXTraitTop()+i, note.getPosYTraitTop(), note.getPosXTraitBot()+i, note.getPosYTraitBot());}
        else if(type == 3){
            for(i = 0 ; i < epaisseur+1 ; i++)
                g.drawLine(note.getPosXTraitTop()+i, note.getPosYTraitTop(), note.getPosXTraitBot()+i, note.getPosYTraitBot());
            for(i = 0 ; i < epaisseur+1 ; i++)
                g.drawLine(note.getPosXTraitTop()+part.tailleTemps/2+i, note.getPosYTraitTop(), note.getPosXTraitBot()+part.tailleTemps/2+i, note.getPosYTraitBot());
        }
        else{
            g.fillRect(note.getPosX(), note.getPosY(), 8, 3*part.interLignes/2);
        }
    }
    
    public NoteM getNote(){
        return note;
    }
}
