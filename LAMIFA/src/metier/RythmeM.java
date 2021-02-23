package metier;

import java.awt.Color;
import static java.lang.String.format;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import vue.FenPrincipale;
import vue.Partition;
import vue.RythmeV;

public class RythmeM extends MetierExo{
    
    private Partition part;
    private RythmeV vue;
    private ArrayList<NoteM> listeNotesM;
    private FenPrincipale fen;
    private int indiceNote, bpm, nbNotesJustes, tauxReussite, score, nbNotes, diff;
    private static int nbNotesJustesTotal = 0, nbNotesTotal = 0, tauxReussiteTotal;
    private double tempsDepart, tempsArrivee, fourchette, dureeTemps;
    private Metronome metronome;
    
    public RythmeM(FenPrincipale fen, RythmeV vue, Partition part, int bpm, int diff){
        super(fen);
        this.vue = vue;
        this.part = part;
        this.bpm = bpm;
        this.diff = diff;
        nbNotesJustes = 0;
        indiceNote = 0;
        tempsDepart = 0;
        tempsArrivee = 0;
        fourchette = 0.08;
        if(bpm > 90) fourchette = 0.05;
        dureeTemps = 60d/bpm; // en seconde(s)
        listeNotesM = part.getListeNotesM();
        nbNotes = listeNotesM.size()-1;
        nbNotesTotal += nbNotes;
        metronome = new Metronome(this, vue.getNomPanel(), dureeTemps, 50);
        metronome.start();
    }
    
    @Override
    public void checkRythme(){
        boolean succes = true;
        tempsArrivee = (double)(System.nanoTime()/1000000000d);
        
        if(indiceNote > 0){

            double tempsEcart = (double)(listeNotesM.get(indiceNote-1).getType()*dureeTemps/4d);
            if(tempsArrivee-tempsDepart >= tempsEcart-fourchette && tempsArrivee-tempsDepart <= tempsEcart+fourchette){
                nbNotesJustes++;
                nbNotesJustesTotal++;
            }
            else
                succes = false;
        }
        tempsDepart = (double)(System.nanoTime()/1000000000d);
        indiceNote++;
        vue.noteSuivante(succes);
        if(indiceNote == part.getListeNotesM().size()){
            indiceNote = 0;
            updateStats();
            vue.setFinRythmePan();
            try {
                fen.accesBD.UpdateStatsExo(3, tauxReussiteTotal, score);
            } catch (SQLException ex) {
                Logger.getLogger(LectureNotesM.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LectureNotesM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void updateStats(){
        tauxReussite = 100 * nbNotesJustes / nbNotes;
        tauxReussiteTotal = 100 * nbNotesJustesTotal / nbNotesTotal;
        float m = 0, v = 0;
        switch(diff){
            case 1 : m = 0.80f;
                    break;
            case 2 : m = 0.85f;
                    break;
            case 3 : m = 0.90f;
                    break;
            case 7 : m = 0.95f;
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
                       break;//
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
            case 70 : v = 0.925f;
                       break;
            case 65 : v = 0.92f;
                       break;
            case 60 : v = 0.915f;
                       break;
            case 55 : v = 0.91f;
                       break;
            case 50 : v = 0.905f;
                       break;
        }
        score = (int) (tauxReussite*m*v);
        if(score > 100) score = 100;
    }
    //
    public int getTauxReussite(){
        return tauxReussite;
    }
    
    public int getTauxReussiteTotal(){
        return tauxReussiteTotal;
    }
    
    public int getScore(){
        return score;
    }
    
    public int getIndiceNote(){
        return indiceNote;
    }

    public Metronome getMetronome() {
        return metronome;
    }
}
