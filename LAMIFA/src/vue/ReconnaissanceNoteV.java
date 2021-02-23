package vue;

import controleur.ActionBoutton;
import controleur.ActionRetour;
import metier.ReconnaissanceNoteM;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JPanel;
import vue.*;
/**
 *
 * @author guizo
 */

public class ReconnaissanceNoteV extends AbstractAction{
    
    private ClavierV clavier;
    private String nomDifficulté;
    private int difficulté; //Correspond à la difficulté sous forme d'n nombre (1 = facile, 4 = expert)
    private boolean premièreEcoute;
    private FenPrincipale fenetre;
    private ReconnaissanceNoteM exerciceMétier;
    
    public ReconnaissanceNoteV(FenPrincipale fen) {
        //clavier = new Clavier();
        fenetre = fen;
        
        
        String[] listeNbMesure = {"1. Facile","2. Intermédiaire","3. Difficile","4. Expert"};
        nomDifficulté = (String) JOptionPane.showInputDialog(null, "Choisis une difficulté", "Niveau de difficulté", QUESTION_MESSAGE, null, listeNbMesure, 2);
        
        if (nomDifficulté != null) {
            premièreEcoute = true;
            difficulté = Integer.parseInt(nomDifficulté.substring(0,1));
            exerciceMétier = new ReconnaissanceNoteM(fenetre,this,difficulté);
            
            //création d'un clavier (qui hérite de JPanel)
            clavier = new ClavierV(2*fenetre.largeur/3, 3*fenetre.hauteur/8, fenetre, exerciceMétier); // Ici mettre notre controleur en paramètre
            clavier.setPreferredSize(new Dimension(fenetre.largeur,3*fenetre.hauteur/6));
            
            fenetre.setContentPane(constructionPanel());
            fenetre.setVisible(true);
            fenetre.getContentPane().setName("ReconnaissanceNotes");
        }
    }
    
    public JPanel constructionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel titre = fenetre.construitTitrePane("RECONNAISSANCE DE NOTES");
        JPanel sousTitre = fenetre.construitSousTitrePane("Difficulté : " + nomDifficulté.substring(3));
        JPanel ecriture = fenetre.construitAssemblePane(titre, sousTitre, 30, 0, 0, 0, 0);
        
        JPanel écouteRestante = fenetre.construitSousTitrePane("Écoutes restantes : " + exerciceMétier.getNbEcoutes());
        JPanel panelBEcouter = new JPanel();
        
        JButton bEcouter = new JButton("Écouter");
        
        bEcouter.addMouseListener(new MouseAdapter() {
         Color oldcolor = bEcouter.getForeground();
         public void mouseEntered(MouseEvent me) {
            oldcolor = bEcouter.getForeground();
            bEcouter.setForeground(Color.red);
            bEcouter.setBackground(Color.gray);
         }
         public void mouseExited(MouseEvent me) {
            bEcouter.setForeground(oldcolor);
         }
        });
        
        bEcouter.setPreferredSize(new Dimension(150, 50));
        bEcouter.setMargin(new Insets(15,50,15,50));
        bEcouter.addActionListener(this);
        panelBEcouter.add(bEcouter);
        JPanel écoute = fenetre.construitAssemblePane(écouteRestante, panelBEcouter, 20, 0, 0, 0, 0);
        
        JPanel panelNotes = new JPanel();
        panelNotes.setLayout(new GridLayout(1,4));
        JPanel panelNotesATrouvées = fenetre.construitSousTitrePane("Notes à trouver : " + exerciceMétier.getNbNotesJouées());
        JPanel panelNotesTrouvées = fenetre.construitSousTitrePane("Notes trouvées : " + exerciceMétier.getNbNotesTrouvées());
        panelNotes.add(panelNotesATrouvées);
        panelNotes.add(panelNotesTrouvées);
        
        JPanel centre = new JPanel();
        centre.setLayout(new BorderLayout());
        centre.add(écoute, BorderLayout.NORTH);
        centre.add(panelNotes, BorderLayout.CENTER);
        centre.add(clavier, BorderLayout.SOUTH);
        
        JPanel retour = fenetre.construitRetQuitPane("Retour","FenLectureNotes");
        
        ArrayList<JPanel> lesPanels = new ArrayList<JPanel>();
        lesPanels.add(panel);
        lesPanels.add(titre);
        lesPanels.add(sousTitre);
        lesPanels.add(ecriture);
        lesPanels.add(écouteRestante);
        lesPanels.add(panelBEcouter);
        lesPanels.add(écoute);
        lesPanels.add(panelNotes);
        lesPanels.add(panelNotesATrouvées);
        lesPanels.add(panelNotesTrouvées);
        lesPanels.add(centre);
        lesPanels.add(retour);
        for (JPanel p : lesPanels) {
            p.setBackground(Color.white);
        }
         
        panel.add(ecriture, BorderLayout.NORTH);
        panel.add(centre, BorderLayout.CENTER);
        panel.add(retour, BorderLayout.SOUTH);
        return panel;
    }
    
    public void setPanelRésultat(){
        fenetre.remove(fenetre.getContentPane());
        fenetre.getContentPane().setName("ReconnaissanceNotes");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel titre = fenetre.construitTitrePane("RECONNAISSANCE DE NOTES");
        JPanel sousTitre = fenetre.construitSousTitrePane("Difficulté : " + nomDifficulté.substring(3));
        JPanel ecriture = fenetre.construitAssemblePane(titre, sousTitre, 30, 0, 0, 0, 0);
        
        JPanel titre2 = fenetre.construitSousTitrePane("EXERCICE TERMINÉ !");
        JPanel sousTitre2 = fenetre.construitSousTitrePane("Tu as eu un taux de réussite de " + exerciceMétier.getTauxRéussite() + "%");
        JPanel ecriture2 = fenetre.construitAssemblePane(titre2, sousTitre2, 80, 0, 0, 0, 0);
        
        JPanel panelNotes = fenetre.construitSousTitrePane("Tu avais " + exerciceMétier.getNbNotesJouées() + " notes à trouver.");
        JPanel panelEssais = fenetre.construitSousTitrePane("Tu les as retrouvé en " + exerciceMétier.getNbEssais() + " essais.");
        JPanel ecriture3 = fenetre.construitAssemblePane(panelNotes, panelEssais, 80, 0, 0, 0, 0);
        
        JPanel titre4 = fenetre.construitSousTitrePane("Essaye de faire un meilleur score en cliquant sur Recommencer.");
        JPanel sousTitre4 = fenetre.construitSousTitrePane("Tu peux également changer la difficulté en cliquant sur Quitter et en sélectionnant cet exercice.");
        JPanel ecriture4 = fenetre.construitAssemblePane(titre4, sousTitre4, 80, 0, 0, 0, 0);
        
        JPanel centre = new JPanel();
        centre.setLayout(new BorderLayout());
        centre.add(ecriture2, BorderLayout.NORTH);
        centre.add(ecriture3, BorderLayout.CENTER);
        centre.add(ecriture4, BorderLayout.SOUTH);
        
        JPanel boutons = fenetre.construitBoutonsRetQuit("FenLectureNotes");
        
        panel.add(ecriture, BorderLayout.NORTH);
        panel.add(centre, BorderLayout.CENTER);
        panel.add(boutons, BorderLayout.SOUTH);
        
        fenetre.setContentPane(panel);
        
        fenetre.revalidate();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //On vérifie qu'il reste assez d'écoutes pour mettre le panel principal à jour et lancer une écoute
        if (exerciceMétier.getNbEcoutes() - 1 >= 0) {
            //A la première écoute on initialise la séquence de notes
            if (premièreEcoute == true) {
                exerciceMétier.initialiserSéquence(clavier);
                premièreEcoute = false;
            } else {
                //on rejoue la séquence de notes
                exerciceMétier.rejouerSéquence();
            }
            //On met à jour les données affichées
            exerciceMétier.décrémenterNbEcoutes();
            actualisationFenetre();
        }
    }
    
    public ClavierV getClavier() {
        return clavier;
    }
    
    public void actualisationFenetre() {
        fenetre.remove(fenetre.getContentPane());
        fenetre.setContentPane(constructionPanel());
        fenetre.revalidate();
    }
    
  
    
   
}
