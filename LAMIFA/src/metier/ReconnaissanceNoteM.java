/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.awt.Color;
import java.util.ArrayList;
import static metier.ToucheM.lesNotesUtilisateur;
import vue.ClavierV;
import vue.FenPrincipale;
import vue.ReconnaissanceNoteV;
import vue.ToucheV;

/**
 *
 * @author guizo
 */
public class ReconnaissanceNoteM extends MetierExo{
    
    static SynthétiseurM synthé = new SynthétiseurM();
    int nbNotesJouées; //nombre de notes à jouer
    int indiceNoteMidi; //l'indice de la note midi à trouver dans l'array list
    static ArrayList<Integer> lesNotesMidi; //notes jouées par l'ordi
    int nbEssais; //nombre de notes jouées avant réussite
    int nbEcoutes; //nombre d'écoutes que l'utilisateur peut faire
    private Integer nbNotesTrouvées; //nombre de notes trouvées par l'utilisateur
    int tempsNote; //temps entre chaque note jouée (en milliseconde)
    int tauxRéussite;
    
    ClavierM clavier;
    ClavierV clavierV;
    FenPrincipale fen;
    ReconnaissanceNoteV vue;
    ToucheV touchePrécédente;
    static boolean terminé;
    
    ArrayList lesBlanches;
    ArrayList lesNoires;
    ThreadEnregistrement thread;
    
    //Chaque instance de cette classe possède une séquence de notes unique
    public ReconnaissanceNoteM(FenPrincipale fen, ReconnaissanceNoteV vue, int difficulté) {
        super(fen);
        this.fen = fen;
        this.vue = vue;
        //on instancie un ArrayList pour les futurs notes midi s'il n'existe pas
        if (lesNotesMidi == null) {
            lesNotesMidi = new ArrayList<Integer>();
        }
        //s'il existe et qu'il n'est pas vide on le vide
        if (!lesNotesMidi.isEmpty()) {
            lesNotesMidi.clear();
        }
        
        switch(difficulté) {
            case 1 : {
                nbEcoutes = 2;
                tempsNote = 1800;
                break;
            }
            case 2 : {
                nbEcoutes = 2;
                tempsNote = 1700;
                break;
            }
            case 3 : {
                nbEcoutes = 3;
                tempsNote = 1600;
                break;
            }
            case 4 : {
                nbEcoutes = 3;
                tempsNote = 1500;
                break;
            }
        }
        nbNotesJouées = difficulté * 2;
        nbNotesTrouvées = 0;
        nbEssais = 0;
        terminé = false;
        indiceNoteMidi = 0;
    }
    
    //première séquence de notes
    public void initialiserSéquence(ClavierV clavierV) {
        this.clavierV = clavierV;
        //on fait le lien entre la vue clavier et la classe métier
        clavier = new ClavierM(clavierV);
        
        lesNotesMidi = synthé.jouerSéquenceAléatoire(nbNotesJouées, lesNotesMidi, tempsNote);
        //on place notre clavier en mode enregistrement pour récupérer les notes de l'utilisateur
        lancerEnregistrement();
    }
    
    public void rejouerSéquence() {
        synthé.jouerSéquence(lesNotesMidi,tempsNote);
    }
    
    //méthode permettant de récupérer la valeur des touches du clavier quand l'utilisateur appuie dessus
    public void lancerEnregistrement() {
        
        //on récupère les touches du clavier
        lesBlanches = clavierV.getLesTouchesBlanches();
        lesNoires = clavierV.getLesTouchesNoires();
        
        //on vérifie que la liste de notes utilisateur est vide
        if (!ToucheM.lesNotesUtilisateur.isEmpty()) {
            ToucheM.lesNotesUtilisateur.clear();
        }
        
        // On remet la touche précédente en blanc
        if (touchePrécédente != null) {
            touchePrécédente.setBackground(touchePrécédente.getCouleur());
        }
        
        //on lance l'enregistrement
        ToucheM.setEnregistrement(true);
        System.out.println("enregistrement lancé, c'est à vous !");
        
        thread = new ThreadEnregistrement(nbNotesJouées,lesNotesMidi);
        thread.start();
    }
    
    public void checkNote(ToucheV touche) {
        // On remet la touche précédente en blanc
        if (touchePrécédente != null) {
            touchePrécédente.setBackground(touchePrécédente.getCouleur());
        }
        
        if (ToucheM.estEnregistré == true) {
            nbEssais += 1;
            //la note utilisateur est identique à la note midi à trouver : on colore la touche en vert et on incrémente le compteur de notes trouvées
            if (ToucheM.lesNotesUtilisateur.get(ToucheM.lesNotesUtilisateur.size()-1) == lesNotesMidi.get(indiceNoteMidi)) {
                touche.setBackground(Color.green);
                indiceNoteMidi += 1;
                incrémentationNbNotesTrouvées();
                vue.actualisationFenetre();
            } else {
                touche.setBackground(Color.red);
            }
        } else {
            touche.setBackground(Color.gray);
        }
        touchePrécédente = touche;
        
        //on arrête d'enregistrer quand toutes les notes ont été trouvées seulement si l'exercice à été lancé (lesNotesMidi n'est pas vide)
        if (indiceNoteMidi  == lesNotesMidi.size() && lesNotesMidi.size() != 0) {
            terminé = true;
            tauxRéussite = lesNotesMidi.size() * 100 / nbEssais;
            vue.setPanelRésultat();
        }
    }
    
    public float getTauxRéussite(){
        return tauxRéussite;
    }
    
    public int getNbEssais(){
        return nbEssais;
    }
    
    public int getNbEcoutes() {
        return nbEcoutes;
    }
    
    public int getNbNotesJouées() {
        return nbNotesJouées;
    }
    
    public void décrémenterNbEcoutes() {
        nbEcoutes -= 1;
    }
    
    public void incrémentationNbNotesTrouvées() {
        nbNotesTrouvées += 1;
    }
    
    public int getNbNotesTrouvées() {
        return nbNotesTrouvées;
    }
}
