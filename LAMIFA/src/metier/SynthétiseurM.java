/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;

/**
 *
 * @author guizo
 */
public class SynthétiseurM {
    
    private Receiver récepteur;
    private int volume;
    
    public SynthétiseurM(){
        try {
            volume = 100;
            //on récupère le récepteur par auquel on enverra des messages
            récepteur = MidiSystem.getReceiver();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(SynthétiseurM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void jouerNoteMidi(int note) {
        try {
            //on crée un message
            ShortMessage message = new ShortMessage();
            
            
            //on remplit le message avec NOTE_ON
            //0 : canal midi n°0
            //100 : intensité avec laquelle est jouée la note
            message.setMessage(ShortMessage.NOTE_ON, 0, note, volume);
            
            //on envoie le message au récepteur
            //-1 : jouer la note dès que possible
            récepteur.send(message, -1);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(SynthétiseurM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void arrêterNoteMidi(int note){
        try {
            //on crée un message
            ShortMessage message = new ShortMessage();
            
            //on remplit le message avec NOTE_OFF
            //0 : canal midi n°0
            //100 : intensité avec laquelle est jouée la note
            message.setMessage(ShortMessage.NOTE_OFF, 0, note, 100);
            
            
            //on envoie le message au récepteur
            //-1 : arrêter la note dès que possible
            récepteur.send(message, -1);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(SynthétiseurM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Integer> jouerSéquenceAléatoire(int nbNotes, ArrayList<Integer> lesNotesMidi, int tempsNote) {
        
        int compteur = 1;
        int nombreAléatoire;
        int nombrePrécédent = 0;
        int min = 60; //valeur midi minimale
        int max = 71; //valeur midi maximale
        
        while (compteur <= nbNotes) {
            nombreAléatoire = min + (int)(Math.random() * ((max - min) + 1));
            while (nombreAléatoire == nombrePrécédent) {
                nombreAléatoire = min + (int)(Math.random() * ((max - min) + 1));
            }
            jouerNoteMidi(nombreAléatoire);
            compteur += 1;
            try {
                Thread.sleep(tempsNote);
            } catch (InterruptedException ex) {
                Logger.getLogger(SynthétiseurM.class.getName()).log(Level.SEVERE, null, ex);
            }
            lesNotesMidi.add(nombreAléatoire);
            nombrePrécédent = nombreAléatoire;
        }
        return lesNotesMidi;
    }
    
    public void jouerSéquence(ArrayList lesNotesMidi, int tempsNote) {
        
        for (Object note : lesNotesMidi) {
            jouerNoteMidi((int) note);
            try {
                Thread.sleep(tempsNote);
            } catch (InterruptedException ex) {
                Logger.getLogger(SynthétiseurM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setVolume(int v){
        volume = v;
    }
}
