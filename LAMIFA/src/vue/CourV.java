package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import metier.CourM;

public class CourV {
		
	private FenPrincipale fen;
    private int hauteur, largeur;
    private CourM model;
	
	public CourV(FenPrincipale fen, int numCour) {
	    this.fen = fen;
            this.model= new CourM(numCour);
	    hauteur = fen.getHauteur();
	    largeur = fen.getLargeur();
            fen.setContentPane(build());
            fen.setVisible(true);
	    fen.getContentPane().setName("Cour");
            
	}
	
	private JPanel build() {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            //init editeur Pane
            JEditorPane jEditorPane = new JEditorPane();
            //en read-only
            jEditorPane.setEditable(false);

            JScrollPane scrollpane = new JScrollPane(jEditorPane);
            //init kit editeur html
            HTMLEditorKit kit = new HTMLEditorKit();
            jEditorPane.setEditorKit(kit);

            //styles html
            StyleSheet styleSheet = kit.getStyleSheet();
            styleSheet.addRule("body {"
                    + "background-color:white;"
                    + "margin: 20px 35px;"
                    + "font-family: sans-serif;}");
            
            styleSheet.addRule("h1 {"
                    + "font-size: 1.8em;"
                    + "color: #006633;"
                    + "text-align: center;}");
            
            styleSheet.addRule("h2 {"
                    + "color: #004d28;"
                    + "margin-top: 30px;"
                    + "font-size: 1.3em}");
            
            styleSheet.addRule("li {"
                    + "padding: 6px;}");
            
            CourM cours = model;
            String htmlString = cours.getStringHtml();

            Document doc = kit.createDefaultDocument();
            jEditorPane.setDocument(doc);
            jEditorPane.setText(htmlString);

            JPanel retour = fen.construitRetQuitPane("Retour","FenCour");
            retour.setPreferredSize(new Dimension(largeur, (int) (hauteur*0.075)));

            panel.add(scrollpane, BorderLayout.CENTER);
            panel.add(retour, BorderLayout.SOUTH);
            
            return panel;
	}
}
