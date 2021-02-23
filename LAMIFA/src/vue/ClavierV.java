package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JPanel;
import metier.*;

public class ClavierV extends JPanel{
  
    ArrayList<ToucheV> lesNoires;
    ArrayList<ToucheV> lesBlanches;
    
    //réation du panel principal et des panels pour les touches noires et blanches
    JPanel panel;
    JPanel panelN;
    JPanel panelB;
    
    FenPrincipale fen;
    
    ToucheV touche;
    SynthétiseurM synthétiseur;
    
    private MetierExo controleur;
    private int tailleXB, tailleYB, tailleXN, tailleYN, intervalB, intervalN, nbTouches;
            
    //On instancie un JPanel
    public ClavierV(int tailleX, int tailleY, FenPrincipale fen, MetierExo controleur){
        nbTouches = 7;
        intervalB = 15;
        tailleXB = tailleX/nbTouches-2*intervalB;
        tailleYB = 2*tailleY/3;
        intervalN = tailleXB/3 + intervalB;
        tailleXN = 2*tailleXB/3;
        tailleYN = tailleY/3;
        this.fen = fen;
        this.controleur = controleur;
        synthétiseur = new SynthétiseurM();
        panel = this;
        buildPanel();
    }
    
    //On gère les éléments sur le panel
    public void buildPanel() {
        panelN = new JPanel();
        panelB = new JPanel();
        
        
        lesNoires = new ArrayList<ToucheV>();
        lesBlanches = new ArrayList<ToucheV>();
        
        creationTouchesNoires();
        creationTouchesBlanches();
        
        panelN.add(Box.createRigidArea(new Dimension(2*tailleXB-tailleXN+intervalB, 0)));
        for (ToucheV t : lesNoires) {
            panelN.add(t);
            panelN.add(Box.createRigidArea(new Dimension(intervalN, 0)));
        }
        
        for (ToucheV t : lesBlanches) {
            panelB.add(t);
            panelB.add(Box.createRigidArea(new Dimension(intervalB, 0)));
        }
        panelN.setBackground(Color.decode("#3a437e"));
        panelB.setBackground(Color.decode("#3a437e"));
        
        panel.setBackground(Color.white);
        panel.add(panelN);
        panel.add(panelB);
    }
    
    //On crée des objets Touche (noire) qu'on ajoute à la liste lesNoires
    public void creationTouchesNoires() {
        //5 touches noires + 1 vide
        int nbTouches = 7;
        for (int i=1; i <= nbTouches; i++) {
            touche = new ToucheV(Color.black, tailleXN, tailleYN, null, fen, synthétiseur, controleur);
            lesNoires.add(touche);
        }
    }
    
    //On crée des objets Touche (blanche) qu'on ajoute à la liste lesBlanches
    public void creationTouchesBlanches() {
        for (int i=1; i <= nbTouches; i++) {
            touche = new ToucheV(Color.white, tailleXB, tailleYB, null, fen, synthétiseur, controleur);
            lesBlanches.add(touche);
        }
    }

    public ArrayList<ToucheV> getLesTouchesBlanches() {
        return lesBlanches;
    }
    
    public ArrayList<ToucheV> getLesTouchesNoires() {
        return lesNoires;
    }
    
    public SynthétiseurM getSynthe(){
        return synthétiseur;
    }
}
