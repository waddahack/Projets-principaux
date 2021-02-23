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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import metier.*;

public class LectureEtRythmeV{
    private FenPrincipale fen;
    private int hauteur, largeur, bpm = 0, diff = 0;
    private Partition part;
    private ClavierV clavier;
    // Appelle de la classe metier qui va gérer l'algo de l'éxo
    private LectureEtRythmeM controleur;
    private String clef, nomPanel;
    private JPanel feedBack;
        
    public LectureEtRythmeV(FenPrincipale fen){
        super();
        fen.getContentPane().setName("LectureEtRythme");
        this.fen = fen;
        hauteur = fen.hauteur;
        largeur = fen.largeur;
        nomPanel = "LectureEtRythme";
        
        String[] listeClefs = {"Sol", "Fa", "Sol et Fa"};
        String c = (String) JOptionPane.showInputDialog(null, "Choisis la clef", "Clef", QUESTION_MESSAGE, null, listeClefs, "Sol");
        switch(c){
            case "Sol" : clef = "sol";
                        break;
            case "Fa" : clef = "fa";
                        break;
            case "Sol et Fa" : clef = "solFa";
                               break;
        }
        
        String[] difficultes = {"Facile","Moyen","Difficle","Expert","Maitre"};
        String reponse = (String) JOptionPane.showInputDialog(null, "Choisis une difficulté", "Difficulté", QUESTION_MESSAGE, null, difficultes, "Facile");
        
        switch(reponse){
            case "Facile" : diff = 1;
                                break;
            case "Moyen" : diff = 2;
                                break;
            case "Difficile" : diff = 3;
                                break;
            case "Expert" : diff = 7;
                                break;
            case "Maitre" : diff = 8;
                                break;
        }
        
        Integer[] listeBPM = {50,55,60,65,70,75,80,85,90,95,100,110,120};
        bpm = (int) JOptionPane.showInputDialog(null, "Choisis un BPM", "BPM", QUESTION_MESSAGE, null, listeBPM, 60);
        
        
        if(clef != null && diff != 0 && bpm != 0){
            build();
            fen.getContentPane().setName("LectureEtRythme");
        }
        
    }
    
    private void build(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        JPanel partition = construitPartitionPan();
        
        controleur = new LectureEtRythmeM(fen, this, bpm, diff, part);
        
        JPanel clavier = construitClavierPan();
        
        JButton retour = new JButton(new ActionRetour("Retour", fen, "FenLectureEtRythme"));
        
        JPanel info = construitInfoPan();
        
        cp.add(partition);
        cp.add(info);
        cp.add(clavier);
        cp.add(retour);
        
        fen.setContentPane(cp);
        fen.setVisible(true);
        
    }
    
    private JPanel construitInfoPan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        cp.setLayout(new BorderLayout());
        cp.setPreferredSize(new Dimension(largeur, hauteur/8));
        
        JTextField bpmTxt = new JTextField();
        bpmTxt.setText("BPM à " + bpm);
        bpmTxt.setEditable(false);
        bpmTxt.setBackground(Color.white);
        Font font = new Font("TimesRoman", Font.BOLD, 26);
        bpmTxt.setFont(font);
        bpmTxt.setPreferredSize(new Dimension(largeur,28));
        bpmTxt.setHorizontalAlignment(JTextField.CENTER);
        
        JPanel volume = new JPanel();
        volume.setBackground(Color.white);
        JSlider slider = new JSlider(0, 100, 50);
        slider.setBackground(Color.white);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.addChangeListener((ChangeEvent event) -> {
            int value = slider.getValue();
            controleur.getMetronome().setVolume(value);
        });
        JTextArea titreVolume = new JTextArea();
        titreVolume.setText("Volume du métronome");
        titreVolume.setBackground(Color.white);
        titreVolume.setEditable(false);
        Font titreVolumeFont = new Font("Arial", Font.BOLD, 16);
        titreVolume.setFont(titreVolumeFont);
        volume.add(titreVolume);
        volume.add(slider);
        
        JPanel cp2 = new JPanel();
        cp2.setBackground(Color.white);
        feedBack = new JPanel();
        feedBack.setPreferredSize(new Dimension(100, 50));
        feedBack.setBorder(BorderFactory.createEmptyBorder(0, (largeur-100)/2, 0, 0));
        feedBack.setBackground(Color.white);
        cp2.add(feedBack);
        
        cp.add(bpmTxt, BorderLayout.NORTH);
        cp.add(volume, BorderLayout.CENTER);
        cp.add(cp2, BorderLayout.SOUTH);
        
        return cp;
    }
    
    private JPanel construitPartitionPan(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        part = new Partition(largeur, hauteur/2, clef, null, null, diff, null, true, false);
        part.setPreferredSize(new Dimension(largeur,hauteur/2));
        
        
        cp.add(part);
        
        return cp;
    }
    
    private JPanel construitClavierPan(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        clavier = new ClavierV(2*largeur/3, hauteur/4, fen, controleur); // Ici mettre notre controleur en paramètre
        clavier.setPreferredSize(new Dimension(largeur,hauteur/4));
        clavier.getSynthe().setVolume(0);
        
        cp.add(clavier);
        
        return cp;
    }
    
        
    public void setFinLectureEtRythmePan(){
        fen.getContentPane().setName("FinLectureEtRythmePan");
        fen.setContentPane(construitFinLectureEtRythmePan());
        fen.setVisible(true);
    }
    
    private JPanel construitFinLectureEtRythmePan(){
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
        String text = "Incroyable !";
        if(controleur.getTauxReussite() < 100) text = "Trop fort !";
        if(controleur.getTauxReussite() < 90) text = "Super !";
        if(controleur.getTauxReussite() < 80) text = "Bravo !";
        if(controleur.getTauxReussite() < 70) text = "Pas mal";
        if(controleur.getTauxReussite() < 50) text = "A revoir";
        if(controleur.getTauxReussite() < 30) text = "Choisis un bpm plus lent !";
        fin.setText(text);
        fin.setBackground(Color.white);
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
        titreResultat.setText("Sur cette dernière partition");
        titreResultat.setBackground(Color.white);
        titreResultat.setEditable(false);
        Font titreResultatFont = new Font("Arial", Font.BOLD, 30);
        titreResultat.setFont(titreResultatFont);
        TR.add(titreResultat);
        
        JTextArea resultat = new JTextArea();
        resultat.setText("Tu as eu un taux de réussite de " + controleur.getTauxReussite() + "%.\n\nScore : " + controleur.getScore() + " / 100");
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
        if(succes) part.getListeNotesV().get(controleur.getIndiceNote()-1).colorier(Color.green);
        else part.getListeNotesV().get(controleur.getIndiceNote()-1).colorier(Color.red);
    }
    
    public ClavierV getClavier(){
        return clavier;
    }
    
    public LectureEtRythmeM getMetier() {
        return controleur;
    }
    
    public String getNomPanel(){
        return nomPanel;
    }

    public void echecNote(int idTouche) {
        clavier.getLesTouchesBlanches().get(idTouche).setBackground(Color.RED);
    }
    
    public void succesNote(int idTouche) {
        clavier.getLesTouchesBlanches().get(idTouche).setBackground(Color.GREEN);
    }

    public void clearClavier() {
        clavier.getLesTouchesBlanches().clear();
        clavier.getLesTouchesNoires().clear();
        clavier = null;
    }

    public void succesRythme() {
        feedBack.setBackground(Color.GREEN);
    }

    public void echecRythme() {
        feedBack.setBackground(Color.RED);
    }
}
