package vue;

import java.awt.Dimension;
import javax.swing.JPanel;
import controleur.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JTextArea;
import metier.*;

public class LectureNotesV{
    private FenPrincipale fen;
    private int hauteur, largeur;
    private Partition part;
    private ClavierV clavier;
    // Appelle de la classe metier qui va gérer l'algo de l'éxo
    private LectureNotesM controleur;
    private String clef;
        
    public LectureNotesV(FenPrincipale fen){
        super();
        fen.getContentPane().setName("LectureNotes");
        this.fen = fen;
        hauteur = fen.hauteur;
        largeur = fen.largeur;
        
        String[] listeClefs = {"Sol", "Fa", "Sol et Fa"};
        String c = (String) JOptionPane.showInputDialog(null, "Choisis la clef", "Clef", QUESTION_MESSAGE, null, listeClefs, "Sol");
        if(c != null)
            switch(c){
                case "Sol" : clef = "sol";
                            break;
                case "Fa" : clef = "fa";
                            break;
                case "Sol et Fa" : clef = "solFa";
                                   break;
            }
        
        if(clef != null){
            build();
            fen.getContentPane().setName("LectureNotes");
        }
        
    }
    
    private void build(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        JPanel partition = construitPartitionPan();
        
        controleur = new LectureNotesM(fen, this, part);
        
        JPanel clavier = construitClavierPan();
        JButton retour = new JButton(new ActionRetour("Retour", fen, "FenLectureNotes"));
        
        cp.add(partition);
        cp.add(clavier);
        cp.add(retour);
        
        fen.setContentPane(cp);
        fen.setVisible(true);
        
    }
    
    private JPanel construitPartitionPan(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        part = new Partition(largeur, hauteur/2, clef, null, 0, 7, null, true, false);
        part.setPreferredSize(new Dimension(largeur,hauteur/2));
        
        
        cp.add(part);
        
        return cp;
    }
    
    private JPanel construitClavierPan(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        clavier = new ClavierV(2*largeur/3, 3*hauteur/8, fen, controleur); // Ici mettre notre controleur en paramètre
        clavier.setPreferredSize(new Dimension(largeur,3*hauteur/8));
        
        cp.add(clavier);
        
        return cp;
    }
    
        
    public void setFinLectureNotesPan(){
        fen.getContentPane().setName("FinLectureNotesPan");
        fen.setContentPane(construitFinLectureNotesPan());
        fen.setVisible(true);
    }
    
    private JPanel construitFinLectureNotesPan(){
        JPanel cp = new JPanel(new BorderLayout());
        cp.setBackground(Color.white);
        
        JPanel fin = construitTitreFinPan();
        
        JPanel resultat = construitResultatPan();
        JPanel resultatTotal = construitResultatTotalPan();
        JPanel toutResultat = fen.construitAssemblePane(resultat, resultatTotal, fen.hauteur/6, 0, fen.hauteur/6, 0, 0);
        
        JPanel boutons = fen.construitBoutonsRetQuit("FenLectureNotes");
            
        cp.add(fin, BorderLayout.NORTH);
        cp.add(toutResultat, BorderLayout.CENTER);
        cp.add(boutons, BorderLayout.SOUTH);
        return cp;
    }

    private JPanel construitTitreFinPan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        JTextArea fin = new JTextArea();
        fin.setBackground(Color.white);
        String text = "Incroyable !";
        if(controleur.getScore() <  95) text = "Excellent !";
        if(controleur.getScore() < 90) text = "Bravo !";
        if(controleur.getScore() < 75) text = "Pas mal";
        if(controleur.getScore() < 50) text = "A revoir...";
        if(controleur.getScore() < 35) text = "Aïe, tu devrais relire le cours !";
        if(controleur.getScore() < 20) text = "Il faut lire le cours d'abord.";
        if(controleur.getScore() < 10) text = "Spamer le clavier n'est pas\nla bonne solution désolé";
        fin.setText(text);
        fin.setEditable(false);
        Font finFont = new Font("TimesRoman", Font.ITALIC, 46);
        fin.setFont(finFont);
        
        cp.add(fin);
        return cp;
    }

    private JPanel construitResultatPan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        JPanel TR = new JPanel();
        TR.setBackground(Color.white);
        JPanel R = new JPanel();
        R.setBackground(Color.white);
        
        JTextArea titreResultat = new JTextArea();
        titreResultat.setBackground(Color.white);
        titreResultat.setText("Sur cette dernière partition");
        titreResultat.setEditable(false);
        Font titreResultatFont = new Font("Arial", Font.BOLD, 30);
        titreResultat.setFont(titreResultatFont);
        TR.add(titreResultat);
        
        JTextArea resultat = new JTextArea();
        resultat.setText("Tu as eu un taux de réussite de " + controleur.getTauxReussite() + "%.\nTu as pris en moyenne " + String.format("%.2g", (float)controleur.getSecondesParNote()) + " secondes par note.\n\nScore : " + controleur.getScore() + " / 100");
        resultat.setBackground(Color.white);
        resultat.setEditable(false);
        Font resultatFont = new Font("Arial", Font.PLAIN, 20);
        resultat.setFont(resultatFont);
        R.add(resultat);
        
        cp = fen.construitAssemblePane(TR, R, 0, 0, 0, 0, fen.hauteur/10);
        
        return cp;
    }

    private JPanel construitResultatTotalPan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        JPanel TRT = new JPanel();
        TRT.setBackground(Color.white);
        JPanel RT = new JPanel();
        RT.setBackground(Color.white);
        
        JTextArea titreResultatTotal = new JTextArea();
        titreResultatTotal.setText("Au total");
        titreResultatTotal.setBackground(Color.white);
        titreResultatTotal.setEditable(false);
        Font titreResultatTotalFont = new Font("Arial", Font.BOLD, 30);
        titreResultatTotal.setFont(titreResultatTotalFont);
        TRT.add(titreResultatTotal);
        
        JTextArea resultatTotal = new JTextArea();
        resultatTotal.setText("Tu as un taux de réussite moyen de " + controleur.getTauxReussiteTotal() + "%.");
        resultatTotal.setBackground(Color.white);
        resultatTotal.setEditable(false);
        Font resultatTotalFont = new Font("Arial", Font.PLAIN, 20);
        resultatTotal.setFont(resultatTotalFont);
        RT.add(resultatTotal);
        
        cp = fen.construitAssemblePane(TRT, RT, 0, 0, 0, 0, fen.hauteur/10);
        
        return cp;
    }


    public void noteSuivante(boolean succes){
        if(succes) part.getListeNotesV().get(controleur.getIndiceNote()).colorier(Color.green);
        else part.getListeNotesV().get(controleur.getIndiceNote()).colorier(Color.red);
    }
    
    public LectureNotesM getMetier() {
        return controleur;
    }

    public ClavierV getClavier(){
        return clavier;
    }

    public void clearClavier() {
        clavier.getLesTouchesBlanches().clear();
        clavier.getLesTouchesNoires().clear();
        clavier = null;
    }
}
