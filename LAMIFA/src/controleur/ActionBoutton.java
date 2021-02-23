package controleur;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import vue.*;
import metier.*;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class ActionBoutton extends AbstractAction{	
	private FenPrincipale fen;
	private String option;
        private String nomPane;
    
    public ActionBoutton(String texte, FenPrincipale fen){ 
        super(texte);
        this.fen = fen;
        option = texte;
        nomPane = (String) fen.getContentPane().getName();
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(option) {
			case "Jouer" : fen.setFenConnection();
						   break;
                        case "Apprentissage" : fen.setFenApprentissage();
                                                            break;
			case "La théorie" : fen.setFenAppTheo();
			   				    break;
			case "La pratique" : fen.setFenAppPra();
			   				   break;
                        case "C'est parti !" :  if(nomPane.equals("FenAppPra")){
                                                   ListeStringModele model = (ListeStringModele) fen.getListeCBPra().getModel();
                                                   String SI = (String) model.getSelectedItem();
                                                   switch(SI){
                                                       case "1. Lecture de notes" : new LectureNotesV(fen);
                                                                                    break;
                                                       case "2. Reconnaissance de notes" : new ReconnaissanceNoteV(fen);
                                                                                           break;
                                                       case "3. Rythme" : new RythmeV(fen);
                                                                                           break;
                                                        case "4. Lecture de notes en rythme" : new LectureEtRythmeV(fen);
                                                                                               break;
                                                   }
                                                   
                                                }
                                                if(nomPane.equals("FenAppTheo")) {
                                            	   ListeStringModele model = (ListeStringModele) fen.getListeCBTheo().getModel();
                                                   String SI = (String) model.getSelectedItem();
                                                   switch(SI){
                                                       case "1. Les notes" : new CourV(fen,1);
                                                                                    break;
                                                       case "2. Le rythme" : new CourV(fen,2);
                                                                                    break;
                                                   }
                                               }
                                               break;
                        case "Recommencer" : switch(nomPane){
                                                 case "FinLectureNotesPan" : new LectureNotesV(fen);
                                                                       break;
                                                 case "FinRythmePan" : new RythmeV(fen);
                                                                        break;
                                                 case "ReconnaissanceNotes" : new ReconnaissanceNoteV(fen);
                                                                       break;
                                                 case "FinLectureEtRythmePan" : new LectureEtRythmeV(fen);
                                                                                break;
                                             }
                                              break;
                        case "Profil" : fen.setFenProfil();
                                        break;
                        case "Statistiques" : fen.setFenStats();
                                        break;
                        case "Jouer hors ligne" : fen.setFenJouer();
                                        break;
		}
	}

}
