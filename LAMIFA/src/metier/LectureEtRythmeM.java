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
//
// /!\ CETTE CLASSE A BESOIN DE LA CLASSE NoteM. METTRE DES COMMENTAIRES POUR ENLEVER LES ERREURS SI BESOIN
public class LectureEtRythmeM extends MetierExo{
    private ArrayList<NoteM> listeNotes;
    private Partition part;
    private int indiceNote = 0; // Exemple : si indiceNote vaut 5, cela veut dire que c'est la 5ème note de la partition de gauche à droite de haut en bas
    private int nbJustes = 0, nbNotes, nbNotesJouees = 0;
    private static int nbJustesTotal = 0, nbNotesJoueesTotale = 0, nbExoFait = 0, nbNotesTotal = 0;
    private int tauxReussite, tauxReussiteTotal, score, bpm, diff;
    private LectureEtRythmeV vue;
    private double tempsDepart, tempsArrivee, fourchette, dureeTemps;
    private FenPrincipale fen;
    private Metronome metronome;
    
    public LectureEtRythmeM(FenPrincipale fen, LectureEtRythmeV vue, int bpm, int diff, Partition part){
        super(fen);
        this.vue = vue;
        this.part = part;
        this.bpm = bpm;
        this.diff = diff;
        nbExoFait++;
        listeNotes = part.getListeNotesM();
        nbNotes = part.getNbNotesM()-1;
        nbNotesTotal += nbNotes;
        tempsDepart = 0f;
        tempsArrivee = 0f;
        dureeTemps = 60d/bpm; // en seconde(s)
        fourchette = 0.08;
        if(bpm > 90) fourchette = 0.05;
        metronome = new Metronome(this, vue.getNomPanel(), dureeTemps, 50);
        metronome.start();
    }
    
    public void checkNoteEtRythme(String note){
        boolean succes = true;
        nbNotesJouees++;
        nbNotesJoueesTotale++;
        tempsArrivee = (double)(System.nanoTime()/1000000000d);
        
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
        
        // On remet toutes les touches en blanc
        for(ToucheV touche : vue.getClavier().getLesTouchesBlanches()){
            touche.setBackground(touche.getCouleur());
        }
        
        if(indiceNote > 0){
            double type = listeNotes.get(indiceNote-1).getType();
            double tempsEcart = (double)(type*dureeTemps/4d);

            if(listeNotes.get(indiceNote).getNom().equals(note) && ( tempsArrivee-tempsDepart >= tempsEcart-fourchette && tempsArrivee-tempsDepart <= tempsEcart+fourchette )){
                vue.succesNote(idTouche);
                vue.succesRythme();
                nbJustes++;
                nbJustesTotal++;
            }
            else if(!listeNotes.get(indiceNote).getNom().equals(note) && ( tempsArrivee-tempsDepart >= tempsEcart-fourchette && tempsArrivee-tempsDepart <= tempsEcart+fourchette )){
                succes = false;
                vue.echecNote(idTouche);
                vue.succesRythme();
            }
            else if(listeNotes.get(indiceNote).getNom().equals(note) && !(tempsArrivee-tempsDepart >= tempsEcart-fourchette && tempsArrivee-tempsDepart <= tempsEcart+fourchette)){
                succes = false;
                vue.succesNote(idTouche);
                vue.echecRythme();
            }
            else{
                succes = false;
                vue.echecNote(idTouche);
                vue.echecRythme();
            }
            
            if(indiceNote == nbNotes){
                indiceNote = 0;
                updateStats();
                vue.clearClavier();
                vue.setFinLectureEtRythmePan();
                try {
                    fen.accesBD.UpdateStatsExo(4, tauxReussiteTotal, score);
                } catch (SQLException ex) {
                    Logger.getLogger(LectureNotesM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LectureNotesM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                indiceNote ++;
                vue.noteSuivante(succes);
            }
            
        }
        else{
            if(listeNotes.get(indiceNote).getNom().equals(note))
                vue.succesNote(idTouche);
            else{
                succes = false;
                vue.echecNote(idTouche);
            }
            indiceNote ++;
            vue.noteSuivante(succes);
        }
        
        tempsDepart = (double)(System.nanoTime()/1000000000d);
    }
    
    private void updateStats() {
        // Calcul des stats
        tauxReussite = 100 * nbJustes / nbNotes;
        tauxReussiteTotal = 100 * nbJustesTotal / nbNotesTotal;
        // Calcul du score
        float m = 0, v = 0;
        switch(diff){
            case 1 : m = 0.76f;
                    break;
            case 2 : m = 0.82f;
                    break;
            case 3 : m = 0.88f;
                    break;
            case 7 : m = 0.94f;
                    break;
            case 8 : m = 1f;
                    break;
        }
        switch(bpm){
            case 120 : v = 1f;
                       break;
            case 110 : v = 0.99f;
                       break;
            case 100 : v = 0.98f;
                       break;
            case 95 : v = 0.97f;
                       break;
            case 90 : v = 0.96f;
                       break;
            case 85 : v = 0.95f;
                       break;
            case 80 : v = 0.94f;
                       break;
            case 75 : v = 0.93f;
                       break;
            case 70 : v = 0.92f;
                       break;
            case 65 : v = 0.91f;
                       break;
            case 60 : v = 0.90f;
                       break;
            case 55 : v = 0.89f;
                       break;
            case 50 : v = 0.88f;
                       break;
        }
        score = (int) (tauxReussite*m*v);
        if(score > 100) score = 100;
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
    
    public int getNbNotesJoueesTotale(){
        return nbNotesJoueesTotale;
    }
    
    public int getIndiceNote(){
        return indiceNote;
    }
    
    public double getTempsDepart(){
        return tempsDepart;
    }
    
    public double getTempsArrivee(){
        return tempsArrivee;
    }
    
    public Metronome getMetronome() {
        return metronome;
    }
}