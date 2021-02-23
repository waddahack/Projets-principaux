/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;

/**
 *
 * @author guizo
 */
    
public class ThreadEnregistrement extends Thread {

    private volatile boolean running = true;
    private int nbNotesJouées;
    private ArrayList lesNotesMidi;
    
    public ThreadEnregistrement(int nbNotesJouées, ArrayList lesNotesMidi) {
        this.nbNotesJouées = nbNotesJouées;
        this.lesNotesMidi = lesNotesMidi;
    }
    
    public void arreter() {
        System.out.println("enregistrement terminé");
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            if (ReconnaissanceNoteM.terminé == true) {
                ToucheM.setEnregistrement(false);
                arreter();
            }
        }
    }

}
