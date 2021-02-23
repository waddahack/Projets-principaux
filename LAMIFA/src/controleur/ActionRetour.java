package controleur;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import vue.*;
import static vue.FenPrincipale.accesBD;

public class ActionRetour extends AbstractAction{
    private FenPrincipale fen;
    private String nomPanel;
    
    public ActionRetour(String texte, FenPrincipale fen, String nomPanel){
        super(texte);
        this.fen = fen;
        this.nomPanel = nomPanel;
        if(nomPanel == null) this.nomPanel = "";
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(nomPanel){
            case "FenJouer" : fen.setFenPrincipale();
                              break;
            case "FenConnection" : fen.setFenPrincipale();
                              break;
            case "FenApprentissage" : fen.setFenJouer();
                              break;
            case "FenAppTheo" : fen.setFenApprentissage();
                              break;
            case "FenAppPra" : fen.setFenApprentissage();
                              break;
            case "FenLectureNotes" : fen.setFenAppPra();
                                     break;
            case "FenRythme" : fen.setFenAppPra();
                                     break;
            case "FenLectureEtRythme" : fen.setFenAppPra();
                                     break;
            case "FenCour" : fen.setFenAppTheo();
                             break;
            case "FenStats" : fen.setFenProfil();
                             break;
            case "" : 
                System.exit(0);
                try {
                    if(accesBD.getConnection() != null) accesBD.getConnection().close();
                } catch (SQLException ex) {
                    Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
    
}
