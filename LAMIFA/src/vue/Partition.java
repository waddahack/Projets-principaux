package vue;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import metier.*;

public class Partition extends JPanel{
    private int tailleX, tailleY;
    private int debutNotesX, debutNotesY, interNotes, nbPart, tailleTempsActuelle, difficulteRythme, randOctave; // debutLignes = là où la première ligne est dessinée
    public int interLignes, interPara, hauteurRangee, tailleTemps, margeXMesure, margeXClef;
    private boolean notes, silences;
    private String clef, uneNoteOnly, randNote;
    private Integer nbTempsMax, unRythmeOnly;
    public Graphics g;
    private Clef clefSol = null, clefFa = null;
    private ArrayList<NoteM> listeNotesM, listeSilencesTemps;
    private ArrayList<NoteV> listeNotesV;
    
    public Partition(int tailleX, int tailleY, String clef, String uneNoteOnly, Integer unRythmeOnly, int difficulteRythme, Integer nbTempsMax, boolean notes, boolean silences){
        /**
        * clef = "sol" , "fa" , ou "solFa"
        * uneNoteOnly = null si on veut toutes les notes, une note précise si on veut que celle là
        * unRythmeOnly = null si on veut tous les rythmes, sinon : allez voir les types des notes dans le grand switch sachant que 1 = double croche 2 = croche 3 = croche pointée 4 = noire
        * difficulteRythme : TRES FACILE = 1 FACILE = 2 MOYEN = 3 DIFFICILE = 7 EXPERT = 8
        * nbTempsMax = nombre de temps maximal
        * notes = true si on veut des notes, false sinon
        * silences = true si on veut des silences false sinon. PAS FONCTIONNELLE
        */
        this.setBackground(Color.white);
        interLignes = 12;
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        this.clef = clef;
        this.unRythmeOnly = unRythmeOnly;
        this.nbTempsMax = nbTempsMax;
        this.notes = notes;
        this.silences = silences;
        this.uneNoteOnly = uneNoteOnly;
        this.difficulteRythme = difficulteRythme;
        interPara = 6*interLignes;
        hauteurRangee = 4*interLignes;
        margeXClef = 25;
        margeXMesure = 15;
        debutNotesY = interLignes;
        this.clefSol = new Clef("sol", this);
        this.clefFa = new Clef("fa", this);
        debutNotesX = margeXClef + max(clefSol.getLargeur(),clefFa.getLargeur()) + 2*margeXMesure;
        tailleTemps = (this.tailleX-(debutNotesX+4*margeXMesure))/8;
        tailleTempsActuelle = tailleTemps;
        if(nbTempsMax == null) nbPart = (int) round((tailleY/(hauteurRangee+interPara))-0.5);
        else nbPart = (int)round((nbTempsMax/8));
        if(notes){
            listeNotesM = new ArrayList<NoteM>();
            listeSilencesTemps = new ArrayList<NoteM>();
            listeNotesV = new ArrayList<NoteV>();
            remplireListeNotes();
        }
    }
    
    private void remplireListeNotes(){
        // Ecriture notes
        debutNotesY = interLignes;
        Random random = new Random();
        int switchNumb, j, i, n = 8;
        if(nbTempsMax != null && nbTempsMax < 8) n = nbTempsMax;
        String clefActuelle = clef;
        
        for(i = 1 ; i <= nbPart ; i++){
            interNotes = 0;
            j = 1;
            
            while(j <= n){
                if(j == 5)
                    interNotes += 2*margeXMesure;
                // ON CHOISI UNE NOTE ET UN OCTAVE
                choisirNote();
                // ON REGARDE LA CLEF ACTUELLE
                if(clef != null){
                    if(clef.equals("solFa")){
                        if(i%2 != 0) clefActuelle = "sol";
                        else clefActuelle = "fa";
                    }
                }
                else
                    clefActuelle = "sol";
                // AJOUT DES NOTES DU TEMPS EN PRENANT UN RYTHME AU PIF
                if(unRythmeOnly != null) switchNumb = unRythmeOnly;
                else switchNumb = random.nextInt(difficulteRythme);
                switch(switchNumb){
                    case 0 : listeNotesM.add(new NoteM(randNote, 4, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps;
                             break;
                    case 1 : listeNotesM.add(new NoteM(randNote, 2, clefActuelle, randOctave, false, this));
                             interNotes += 2*tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 2, clefActuelle, randOctave, true, this));
                             interNotes += 2*tailleTemps/4;
                             break;
                    case 2 : listeNotesM.add(new NoteM(randNote, 3, clefActuelle, randOctave, false, this));
                             interNotes += 3*tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, true, this));
                             interNotes += tailleTemps/4;
                             break;
                    case 3 : listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 2, clefActuelle, randOctave, true, this));
                             interNotes += 2*tailleTemps/4;
                             choisirNote();
                             break;
                    case 4 : listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 2, clefActuelle, randOctave, false, this));
                             interNotes += 2*tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, true, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             break;
                    case 5 : listeNotesM.add(new NoteM(randNote, 2, clefActuelle, randOctave, false, this));
                             interNotes += 2*tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, true, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             break;
                    case 6 : listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, 1, clefActuelle, randOctave, true, this));
                             interNotes += tailleTemps/4;
                             choisirNote();
                             break;
                    case 7 : float t = (float)(4f/3f);
                             listeNotesM.add(new NoteM(randNote, t, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/3;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, t, clefActuelle, randOctave, false, this));
                             interNotes += tailleTemps/3;
                             choisirNote();
                             listeNotesM.add(new NoteM(randNote, t, clefActuelle, randOctave, true, this));
                             interNotes += tailleTemps/3;
                             choisirNote();
                             break;
                }
                j++;
                }
            debutNotesY += hauteurRangee + interPara;
        }
    }
    
    // LA PARTIE GRAPHIQUE (GÉRÉE EN DERNIER PAR LES THREADS)
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.g = g;
        debutNotesY = interLignes;
        int i, j, posY = debutNotesY+interLignes, posX;
        g.setColor(Color.BLACK);
        
        for(i = 1 ; i <= nbPart ; i++){
            // Dessine les clefs
            if(clef != null){
                if(clef.equals("sol")) dessinerClefSol(g, i);
                else if(clef.equals("fa")) dessinerClefFa(g, i);
                else if(clef.equals("solFa")){
                    if(i%2 == 0) dessinerClefFa(g, i);
                    else dessinerClefSol(g, i);
                }
            }
            
            // Dessine lignes séparatrices de mesures
            posX = debutNotesX - margeXMesure;
            for(j = 0 ; j < 2 ; j++){
                g.drawLine(posX, posY,posX, posY+4*interLignes);
                posX += tailleTemps*4+2*margeXMesure;
            }
            
            // Dessine lignes
            for(j = 0 ; j < 5 ; j++){
                g.drawLine(0, posY, tailleX, posY);
                if(j < 4) posY += interLignes;
            }
            posY += interPara;
            debutNotesY += hauteurRangee + interPara;
        }
        
        // Dessine les notes
        if(notes)
            for(NoteM note : listeNotesM){
                NoteV noteV = new NoteV(note, this, g);
                listeNotesV.add(noteV);
            }
    }
    
    private void choisirNote(){
        Random random = new Random();
        int rand;
        if(uneNoteOnly == null){
            if(silences && tailleTempsActuelle == tailleTemps)
                rand = random.nextInt(8); // avec silence
            else
                rand = random.nextInt(7); // sans silence
            // ON CHOISIT LA NOTE
            switch(rand){
                case 0 : randNote = "Sol67";
                         break;
                case 1 : randNote = "Fa 65";
                         break;
                case 2 : randNote = "Mi 64";
                         break;
                case 3 : randNote = "Re 62";
                         break;
                case 4 : randNote = "Do 60";
                         break;
                case 5 : randNote = "Si 71";
                         break;
                case 6 : randNote = "La 69";
                         break;
                case 7 : randNote = "Silence";
                         break;
            }
        }
        else
            randNote = uneNoteOnly;
        
        // ON CHOISIT L'OCTAVE
        if(clef != null){
            if(clef.equals("sol")){
                if(randNote.equals("Do 60") || randNote.equals("Si 71") || randNote.equals("La 69"))
                    randOctave = 0;
                else
                    randOctave = random.nextInt(2);
            }
            else{
                if(randNote.equals("Mi 64") || randNote.equals("Re 62") || randNote.equals("Do 60"))
                    randOctave = 0;
                else
                    randOctave = random.nextInt(2);
            }
        }
        else
            randOctave = 0;
    }
    
    public int getTailleX(){
        return tailleX;
    }
    
    public int getTailleY(){
        return tailleY;
    }
    
    public int getDebutNotesY(){
        return debutNotesY;
    }
    
    public int getDebutNotesX(){
        return debutNotesX;
    }
    
    public int getTailleTempsActuelle(){
        return tailleTempsActuelle;
    }
    
    public int getInterNotes(){
        return interNotes;
    }
    public ArrayList<NoteM> getListeNotesM(){
        return listeNotesM;
    }
    
    public ArrayList<NoteM> getListeSilencesTemps(){
        return listeSilencesTemps;
    }
    
    public int getNbNotesM(){
        return listeNotesM.size();
    }
    
    private void dessinerClefSol(Graphics g, int i){
        g.drawImage(clefSol.getImage(), clefSol.getPosX(), clefSol.getPosY()+(i-1)*(hauteurRangee+interPara), clefSol.getLargeur(), clefSol.getHauteur(), null);
    }
    
    private void dessinerClefFa(Graphics g, int i){
        g.drawImage(clefFa.getImage(), clefFa.getPosX(), clefFa.getPosY()+(i-1)*(hauteurRangee+interPara), clefFa.getLargeur(), clefFa.getHauteur(), null);
    }
    
    public ArrayList<NoteV> getListeNotesV(){
        return listeNotesV;
    }
}
