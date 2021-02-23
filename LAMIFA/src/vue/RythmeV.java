package vue;

import controleur.ActionBoutton;
import controleur.ActionRetour;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import metier.ConnexionBD;
import metier.RythmeM;


public class RythmeV {
    private RythmeM controleur;
    private FenPrincipale fen;
    private int hauteur, largeur, diff = 0;
    private Integer bpm = null;
    private Partition part;
    private ToucheV touche;
    private JPanel feedBack;
    private String nomPanel;
    
    public RythmeV(FenPrincipale fen){
        super();
        nomPanel = "Rythme";
        fen.getContentPane().setName(nomPanel);
        this.fen = fen;
        hauteur = fen.hauteur;
        largeur = fen.largeur;
        
        String[] difficultes = {"Très facile","Facile","Moyen","Difficile","Expert"};
        String reponse = (String) JOptionPane.showInputDialog(null, "Choisis une difficulté", "Difficulté", QUESTION_MESSAGE, null, difficultes, "Très facile");
        
        switch(reponse){
            case "Très facile" : diff = 1;
                                break;
            case "Facile" : diff = 2;
                                break;
            case "Moyen" : diff = 3;
                                break;
            case "Difficile" : diff = 7;
                                break;
            case "Expert" : diff = 8;
                                break;
        }
        
        Integer[] listeBPM = {50,55,60,65,70,75,80,85,90,95,100,110,120};
        bpm = (Integer) JOptionPane.showInputDialog(null, "Choisis un BPM", "BPM", QUESTION_MESSAGE, null, listeBPM, 60);
        
        if(diff != 0 && bpm != null){
            build();
            touche.getToucheM().setSon(false);
            fen.getContentPane().setName(nomPanel);
        }
    }
    
    private void build(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        JPanel rythme = construitRythmePan();
        
        controleur = new RythmeM(fen, this, part, bpm, diff);
        
        JPanel touche = construitTouchePan();
        JPanel info = construitInfoPan();
        JPanel infoEtTouche = fen.construitAssemblePane(info, touche, 0, 0, 30, 0, 100);
        
        JPanel retour = new JPanel();
        retour.setBackground(Color.white);
        JButton boutonRetour = new JButton(new ActionRetour("Retour", fen, "FenRythme"));
        retour.add(boutonRetour);
        
        cp.add(rythme);
        cp.add(infoEtTouche);
        cp.add(retour);
        
        fen.setContentPane(cp);
        fen.setVisible(true);
    }

    private JPanel construitRythmePan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        part = new Partition(largeur, hauteur/3, null, "Do 60", null, diff, null, true, false);
        part.setPreferredSize(new Dimension(largeur,hauteur/3));
        
        cp.add(part);
        return cp;
    }

    private JPanel construitTouchePan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        touche = new ToucheV(Color.white, 100, hauteur/5, "Do 60", fen, fen.getSynthétiseurM(), controleur);
        
        cp.add(touche);
        return cp;
    }
    
    public void noteSuivante(boolean succes){
        if(succes) part.getListeNotesV().get(controleur.getIndiceNote()-1).colorier(Color.green);
        else part.getListeNotesV().get(controleur.getIndiceNote()-1).colorier(Color.red);
    }

    private JPanel construitInfoPan() {
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        cp.setLayout(new BorderLayout());
        cp.setPreferredSize(new Dimension(largeur, hauteur/6));
        
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
        titreVolume.setBackground(Color.white);
        titreVolume.setText("Volume du métronome");
        titreVolume.setBackground(Color.white);
        titreVolume.setEditable(false);
        Font titreVolumeFont = new Font("Arial", Font.BOLD, 16);
        titreVolume.setFont(titreVolumeFont);
        volume.add(titreVolume);
        volume.add(slider);
        
        cp.add(bpmTxt, BorderLayout.NORTH);
        cp.add(volume, BorderLayout.CENTER);
        
        return cp;
    }
    
    public JPanel getFeedBack(){
        return feedBack;
    }
    
    public String getNomPanel(){
        return nomPanel;
    }

    public void setFinRythmePan(){
        fen.getContentPane().setName("FinRythmePan");
        fen.setContentPane(construitFinRythmePan());
        fen.setVisible(true);
    }
    
    private JPanel construitFinRythmePan(){
        JPanel titreP = new JPanel();
        titreP.setBackground(Color.white);
        JTextArea titre = new JTextArea();
        titre.setBackground(Color.white);
        String text = "Un percussionniste né !";
        if(controleur.getTauxReussite() < 100) text = "Trop fort !";
        if(controleur.getTauxReussite() < 90) text = "Super !";
        if(controleur.getTauxReussite() < 80) text = "Bravo !";
        if(controleur.getTauxReussite() < 70) text = "Pas mal";
        if(controleur.getTauxReussite() < 50) text = "A revoir";
        if(controleur.getTauxReussite() < 30) text = "Choisis un bpm plus lent !";
        titre.setText(text);
        titre.setEditable(false);
        Font titreFont = new Font("Arial", Font.BOLD, 42);
        titre.setFont(titreFont);
        titreP.add(titre);
        
        JPanel cp1 = new JPanel();
        cp1.setBackground(Color.white);
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
        resultat.setBackground(Color.white);
        resultat.setText("Tu as eu un taux de réussite de " + controleur.getTauxReussite() + "%.\n\nScore : " + controleur.getScore() + " / 100");
        resultat.setEditable(false);
        Font resultatFont = new Font("Arial", Font.PLAIN, 20);
        resultat.setFont(resultatFont);
        R.add(resultat);
        
        cp1 = fen.construitAssemblePane(TR, R, 0, 0, 0, 0, fen.hauteur/10);
        
        JPanel cp2 = new JPanel();
        cp2.setBackground(Color.white);
        JPanel TRT = new JPanel();
        TRT.setBackground(Color.white);
        JPanel RT = new JPanel();
        RT.setBackground(Color.white);
        
        JTextArea titreResultatTotal = new JTextArea();
        titreResultatTotal.setBackground(Color.white);
        titreResultatTotal.setText("Au total");
        titreResultatTotal.setEditable(false);
        Font titreResultatTotalFont = new Font("Arial", Font.BOLD, 30);
        titreResultatTotal.setFont(titreResultatTotalFont);
        TRT.add(titreResultatTotal);
        
        JTextArea resultatTotal = new JTextArea();
        resultatTotal.setBackground(Color.white);
        resultatTotal.setText("Tu as un taux de réussite de " + controleur.getTauxReussiteTotal() + "%.");
        resultatTotal.setEditable(false);
        Font resultatTotalFont = new Font("Arial", Font.PLAIN, 20);
        resultatTotal.setFont(resultatTotalFont);
        RT.add(resultatTotal);
        
        cp2 = fen.construitAssemblePane(TRT, RT, 0, 0, 0, 0, fen.hauteur/10);
        
        JPanel boutons = fen.construitBoutonsRetQuit("FenLectureNotes");
        
        JPanel cp1cp2 = fen.construitAssemblePane(cp1, cp2, fen.hauteur/6, 0, fen.hauteur/6, 0, 0);
        cp1cp2.setBackground(Color.white);
        
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        cp.setLayout(new BorderLayout());
        cp.add(titreP, BorderLayout.NORTH);
        cp.add(cp1cp2, BorderLayout.CENTER);
        cp.add(boutons, BorderLayout.SOUTH);
        
        return cp;
    }
}
