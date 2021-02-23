/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.KeyStroke;
import vue.*;

/**
 *
 * @author guizo
 */
public class ToucheM {
    
    private SynthétiseurM synthétiseur;
    String nomNote;
    int valeurMidi;
    ToucheV touche;
    
    //booléen : true=la touche est relachée, false=la touche est pressée
    private boolean estRelachée = true, son = true;
    //booléen : true=enregistrement en cours, false=pas d'enregistrement
    static boolean estEnregistré = false;
    
    //quand on enregistre, on place les valeurs des notes midi dans la liste suivante
    static ArrayList<Integer> lesNotesUtilisateur;
    private MetierExo controleur;
    private FenPrincipale fen;
    
    //Ce constructeur associe une action à réaliser quand on appuie sur la touche de clavier spécifiée
    public ToucheM(ToucheV touche, String nomTouche, String nomNote, SynthétiseurM synthétiseur, FenPrincipale fen, MetierExo controleur)  {
        this.synthétiseur = synthétiseur;
        this.touche = touche;
        this.nomNote = nomNote;
        this.valeurMidi = affecterValeurMidi();
        this.controleur = controleur;
        this.fen = fen;
        
        if (lesNotesUtilisateur == null) {
            lesNotesUtilisateur = new ArrayList<Integer>();
        }
        
        
        char numASCII = nomTouche.charAt(0);
        
        //le nom de l'action qui fait le lien entre InputMap et ActionMap
        String nomActionJouer = "jouer"+nomTouche;
        String nomActionArrêter = "arrêter"+nomTouche;
        
        //quand la fenêtre a le focus, on associe au bouton (touche), une touche du claver (KeyStroke) et une action spécififique(nomAction)
        touche.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(numASCII,0,false), nomActionJouer);
        
        //on donne au bouton (touche) l'action (nomAction) et la fonction associée
        //ici ce n'est pas une fonction mais un objet anonyme JouerNote(nomNote) qui extends AbstraAction
        touche.getActionMap().put(nomActionJouer, new JouerNote());
        
        
        //on réutilise les inputMap et actionMap pour arrêter de jouer la note
        //true : key released
        touche.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(numASCII,0,true), nomActionArrêter);
        touche.getActionMap().put(nomActionArrêter, new ArrêterNote());
        
        touche.addActionListener(new JouerNote());
        touche.addActionListener(new ArrêterNote());
    }
    
    public int affecterValeurMidi() {
        int nbCaractères = 2; // on veut les 2 derniers caractères
        int longueur = nomNote.length();
        return Integer.parseInt(nomNote.substring(longueur - nbCaractères, longueur));
    }
    
    //on lance ou non l'enregistrement
    public static void setEnregistrement(Boolean bool) {
        estEnregistré = bool;
    }
    
    public ArrayList getLesNotesUtilisateur() {
        return lesNotesUtilisateur;
    }
    
    public void setSon(boolean son){
            this.son = son;
    }
    
    private class JouerNote extends AbstractAction {
        
        
        public JouerNote(){
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            //on joue la note qu'une seule fois, quand la touche est pressée, estRelachée sera a false et on attend de relâcher la touche
            if (estRelachée) {
                if(son) synthétiseur.jouerNoteMidi(valeurMidi);
                estRelachée = false;
                
                //si l'enregistrement est lancé, on ajoute les notes de l'utilisateur dans un array list
                if (estEnregistré) {
                    lesNotesUtilisateur.add(valeurMidi);
                }
                
                //problème à régler
                if(fen.getContentPane().getName() == null){
                    fen.getContentPane().setName("ReconnaissanceNotes");
                }
                
                if(fen.getContentPane().getName() != null){
                    // Si on se trouve dans l'exerice Lecture notes
                    if(fen.getContentPane().getName().equals("LectureNotes"))
                        controleur.checkNote(nomNote);
                    // Si on se trouve dans l'exerice Reconnaissance Notes
                    else if(fen.getContentPane().getName().equals("ReconnaissanceNotes"))
                        controleur.checkNote(touche);
                    // Si on se trouve dans l'exerice Rythme
                    else if(fen.getContentPane().getName().equals("Rythme"))
                        controleur.checkRythme();
                    // Si on se trouve dans l'exerice Lecture de notes en rythme
                    else if(fen.getContentPane().getName().equals("LectureEtRythme"))
                        controleur.checkNoteEtRythme(nomNote);
                }
            }
        }
    }
    
    private class ArrêterNote extends AbstractAction {
        
        public ArrêterNote(){
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            estRelachée = true;
            synthétiseur.arrêterNoteMidi(valeurMidi);
        }
    }
}
