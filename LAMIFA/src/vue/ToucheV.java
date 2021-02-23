/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import static java.awt.Color.black;
import static java.awt.Color.white;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import metier.*;
/**
 *
 * @author guizo
 */
public class ToucheV extends JButton {
    
    private JButton touche;
    private Color couleur;
    private FenPrincipale fen;
    private SynthétiseurM synthétiseur;
    private int tailleX, tailleY;
    //On crée 2 indices statics qui vont parcourir les noms des notes dans les deux listes suivantes
    static int indiceN = 0;
    static int indiceB = 0;
    private String nom;

    //on crée un LinkedHashMap qui associe une clef touche(du clavier) à une note (Touches blanches du piano)
    static LinkedHashMap<String, String> listeTouchesBlanchesNote = new LinkedHashMap<>();
    String keyBlanche; //on crée un String qui correspond à la clef désirée du hashmap
    
    static LinkedHashMap<String, String> listeTouchesNoiresNote = new LinkedHashMap<>();
    String keyNoire;
    
    //la touche métier associée à la touche vue
    ToucheM toucheM;
    
    public ToucheV(Color couleur, int tailleX, int tailleY, String nom, FenPrincipale fen, SynthétiseurM synthétiseur, MetierExo controleur) {
        
        touche = this;
        this.couleur = couleur;
        this.fen = fen;
        this.synthétiseur = synthétiseur;
        this.nom = nom;
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        if(nom != null) 
            switch(nom){
                case "Sol67" : indiceB = 4;
                             break;
                case "Fa 65" : indiceB = 3;
                            break;
                case "Mi 64" : indiceB = 2;
                            break;
                case "Re 62" : indiceB = 1;
                            break;
                case "Do 60" : indiceB = 0;
                            break;
                case "Si 71" : indiceB = 6;
                            break;
                case "La 69" : indiceB = 5;
                            break;
                case "Do 72" : indiceB = 7;
                            break;
            }
        //initialisation du HashMap touches blanches
        if (listeTouchesBlanchesNote.isEmpty()) {
            listeTouchesBlanchesNote.put("Q","Do 60");
            listeTouchesBlanchesNote.put("S","Re 62");
            listeTouchesBlanchesNote.put("D","Mi 64");
            listeTouchesBlanchesNote.put("F","Fa 65");
            listeTouchesBlanchesNote.put("G","Sol67");
            listeTouchesBlanchesNote.put("H","La 69");
            listeTouchesBlanchesNote.put("J","Si 71");
            listeTouchesBlanchesNote.put("K","Do 72");
        }
        
        //initialisation du HashMap touches noires
        if (listeTouchesNoiresNote.isEmpty()) {
            listeTouchesNoiresNote.put("Z","Do# 61");
            listeTouchesNoiresNote.put("E","Ré# 63");
            listeTouchesNoiresNote.put("invisible1","       ");
            listeTouchesNoiresNote.put("T","Fa# 66");
            listeTouchesNoiresNote.put("Y","Sol#68");
            listeTouchesNoiresNote.put("U","La# 70");
            listeTouchesNoiresNote.put("invisible2","       ");
        }
        
        if (indiceN > 6) {
            indiceN = 0;
        }
        
        if (indiceB > 6) {
            indiceB = 0;
        }
        
        buildTouche(controleur);
    }
    
    public void buildTouche(MetierExo controleur) {
                
        if (couleur == white) {
            
            keyBlanche = (String) listeTouchesBlanchesNote.keySet().toArray()[indiceB];
            
            touche.setBackground(couleur);
            //substring(0,3) : on récupère par exemple que les caractères "Do " de "Do 60" pour l'afficher sur la touche
            //les caractères "60" de "Do 60" serviront dans la création de ToucheM
            touche.setText(générateurTexteTouche(listeTouchesBlanchesNote.get(keyBlanche).substring(0,3),keyBlanche));
            touche.setForeground(black);
            touche.setPreferredSize(new Dimension(tailleX, tailleY));
            
            //on crée la touche métier grâce aux attributs de la touche vue
            toucheM = new ToucheM((ToucheV)touche,keyBlanche , listeTouchesBlanchesNote.get(keyBlanche), synthétiseur, fen, controleur);
            
            //touche invisible
            if(nom == null) indiceB += 1;
        }
        
        //l'espacement des touches noires est provisoire car le code suivant n'est pas optimal
        else if (couleur == black) {
            
            keyNoire = (String) listeTouchesNoiresNote.keySet().toArray()[indiceN];

            if (indiceN != 2 && indiceN != 6) {
                touche.setBackground(couleur);
                //le substring(0,4) : il y a un caractère de plus avec le "#" dans le nom des touches comparé aux notes blanches
                touche.setText(générateurTexteTouche(listeTouchesNoiresNote.get(keyNoire).substring(0,4),keyNoire));
                touche.setForeground(white);
                
                //on crée la touche métier grâce aux attributs de la touche vue
                toucheM = new ToucheM((ToucheV)touche,keyNoire , listeTouchesNoiresNote.get(keyNoire), synthétiseur, fen, controleur);
                
            }
            
            else { //les touches invisibles
                touche.setText(générateurTexteTouche(listeTouchesNoiresNote.get(keyNoire).substring(0,4),keyNoire));
                touche.setOpaque(false);
                touche.setContentAreaFilled(false);
                touche.setBorderPainted(false);
            }
            touche.setPreferredSize(new Dimension(tailleX, tailleY));
            indiceN += 1;
        }
    }
    
    public String générateurTexteTouche(String note, String toucheClavier) {
        //le cas où la touche doit être invisible
        if ("invisible1".equals(toucheClavier) || "invisible2".equals(toucheClavier)) {
            return "<html>"
                    + "<p style=text-align: center>"+note+"<br> "
                    + "&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp"
                    + "</p>"
                    + "</html>";
        }
        
        //le cas par défaut
        return "<html><p style=text-align: center>"+note+"<br>("+toucheClavier+")</p></html>";
    }
    
    public ToucheM getToucheM() { 
        return toucheM;
    }
    
    public Color getCouleur(){
        return couleur;
    }
}

