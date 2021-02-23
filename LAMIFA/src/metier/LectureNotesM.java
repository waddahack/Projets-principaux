package metier;

import java.awt.Color;
import static java.awt.Color.*;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import vue.*;

// /!\ CETTE CLASSE A BESOIN DE LA CLASSE NoteM. METTRE DES COMMENTAIRES POUR ENLEVER LES ERREURS SI BESOIN
public class LectureNotesM extends MetierExo{
    private ArrayList<NoteM> listeNotes;
    private Partition part;
    private int indiceNote = 0; // Exemple : si indiceNote vaut 5, cela veut dire que c'est la 5ème note de la partition de gauche à droite de haut en bas
    private int nbFautes = 0, nbNotes, nbNotesJouees = 0;
    private static int nbFautesTotale = 0, nbNotesJoueesTotale = 0;
    private float secondesParNote;
    private int tauxReussite, tauxReussiteTotal, score;
    private LectureNotesV vue;
    private long tempsDepart, tempsArrive;
    private FenPrincipale fen;
    
    public LectureNotesM(FenPrincipale fen, LectureNotesV vue, Partition part){
        super(fen);
        this.vue = vue;
        this.part = part;
        listeNotes = this.part.getListeNotesM();
        nbNotes = part.getNbNotesM();
        tempsDepart = System.nanoTime();
    }
    
    public void checkNote(String note){
        int idTouche = 0;
        switch(note){
            case "Sol67" : idTouche = 4;
                         break;
            case "Fa 65" : idTouche = 3;
                        break;
            case "Mi 64" : idTouche = 2;
                        break;
            case "Re 62" : idTouche = 1;
                        break;
            case "Do 60" : idTouche = 0;
                        break;
            case "Si 71" : idTouche = 6;
                        break;
            case "La 69" : idTouche = 5;
                        break;
            case "Do 72" : idTouche = 7;
                        break;
        }
        nbNotesJouees++;
        nbNotesJoueesTotale++;
        // On remet toutes les touches en blanc
        for(ToucheV touche : vue.getClavier().getLesTouchesBlanches()){
            touche.setBackground(touche.getCouleur());
        }
        if(listeNotes.get(indiceNote).getNom().equals(note))
            vue.noteSuivante(true);
        else{
            vue.noteSuivante(false);
            nbFautes++;
            nbFautesTotale++;
        }
        indiceNote ++;
        if(indiceNote == nbNotes){
            indiceNote = 0;
            tempsArrive = System.nanoTime();
            updateStats();
            vue.clearClavier();
            vue.setFinLectureNotesPan();
            try {
                fen.accesBD.UpdateStatsExo(1, tauxReussiteTotal, score);
            } catch (SQLException ex) {
                Logger.getLogger(LectureNotesM.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LectureNotesM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void updateStats() {
        // Calcul des stats
        tauxReussite = (100-(100*nbFautes/nbNotesJouees));
        tauxReussiteTotal = (100-(100*nbFautesTotale/nbNotesJoueesTotale));
        secondesParNote = (((float)(tempsArrive-tempsDepart)/(float)1000000000)/(float)nbNotes);
        // Calcul du score
        if(tauxReussite < 30 && secondesParNote < 0.3) // Si la personne a spamé
            score = 0;
        else{
            float SPN = secondesParNote;
            if(SPN < 0.5) SPN = (float) 0.5;
            else if(SPN > 10.5) SPN = (float) 10.5;
            float x = (float) (1 - (2*(SPN-0.5) * 0.05));
            score = (int) (tauxReussite*x);
        }
    }
    
    public int getScore(){
        return score;
    }
    
    public int getTauxReussite(){
        return tauxReussite;
    }
    
    public int getTauxReussiteTotal(){
        return tauxReussiteTotal;
    }
    
    public float getSecondesParNote(){
        return secondesParNote;
    }
    
    public int getNbNotes(){
        return nbNotes;
    }
    
    public int getNbNotesJouees(){
        return nbNotesJouees;
    }
    
    public int getNbFautes(){
        return nbFautes;
    }
    
    public int getNbNotesJoueesTotale(){
        return nbNotesJoueesTotale;
    }
    
    public int getNbFautesTotale(){
        return nbFautesTotale;
    }
    
    public int getIndiceNote(){
        return indiceNote;
    }
    
    public long getTempsDepart(){
        return tempsDepart;
    }
    
    public long getTempsArrive(){
        return tempsArrive;
    }
}