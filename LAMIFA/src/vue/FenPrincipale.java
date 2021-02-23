package vue;

import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import static javax.swing.BoxLayout.PAGE_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import metier.*;
import controleur.*;
import static java.awt.Color.black;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import metier.ConnexionBD;

public class FenPrincipale extends JFrame implements ActionListener{
    private JTextArea description = new JTextArea();
    private SynthétiseurM synthé;
    private JComboBox listeCBTheo,listeCBPra;
    public int largeur = 1200, hauteur = 800;
    private JPanel FenPrincipale, FenApprentissage, FenJouer, FenAppTheo, FenAppPra, FenConnection, FenProfil;
    private String[] listeAppTheo = {"1. Les notes","2. Le rythme"};
    private String[] listeAppPra = {"1. Lecture de notes","2. Reconnaissance de notes","3. Rythme","4. Lecture de notes en rythme", "5. QCM"};
    private ArrayList<String> lesClefs;
    public static ConnexionBD accesBD;

    
    // -----------------------------------------------------------------------------------------------------------
    // -------------------------------------------------FENETRE PRINCIPALE----------------------------------------
    public FenPrincipale() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException, IOException{
        super();
        /*try {
            accesBD = new ConnexionBD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        // S'INSCRIRE A LA RACHE
        // accesBD.inscription("", "");
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        hauteur = (int)dimension.getHeight();
        largeur  = (int)dimension.getWidth();
        synthé = new SynthétiseurM();
        lesClefs = new ArrayList<String>();
        lesClefs.add("clef_sol");
        lesClefs.add("clef_fa");
        UIManager.put("ButtonUI", ButtonUIV.class.getName()); 
        construit_fenetre();
        this.setVisible(true);
    }
    
    
    private void construit_fenetre() throws IOException{
        
        // Création de tous les panels du logiciel
        FenPrincipale = construitContentPane();
        FenAppTheo = construitFenSelection("Que voulez-vous apprendre ?", "FenAppTheo", listeAppTheo);
        FenAppPra = construitFenSelection("Quel exercice souhaitez-vous faire ?", "FenAppPra", listeAppPra);
        FenJouer = construitFenJouer();
        FenApprentissage = construitFenApprentissage();
        FenConnection = construitFenConnection();
        FenProfil = construitFenProfil();
        //
        
                
        setTitle("LAMIFA");
        setSize(largeur,hauteur);
        setResizable(false);
        setLocationRelativeTo(null);
        WindowListener ecoutSortie = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                String[] options = {"Oui","Non"};
                int confirm = JOptionPane.showOptionDialog(null, "Êtes-vous sûr de vouloir quitter Lamifa ?", "Quitter", 1, 3, null, options, EXIT_ON_CLOSE);
                if(confirm == JOptionPane.YES_OPTION)
                    try {
                        if(accesBD.getConnection() != null) accesBD.getConnection().close();
                    } catch (SQLException ex) {
                        Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
            }
        };
        addWindowListener(ecoutSortie);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setContentPane(construitContentPane());
    }
   
    
    private JPanel construitContentPane() throws IOException{
        JPanel panelImage = new PanelImage("background.jpg");
        panelImage.setLayout(new GridBagLayout());
        
        JPanel main = construitMenu("LAMIFA", "Jouer", "Jouer hors ligne", "Paramètres", "Quitter", null);
        
        panelImage.add(main);
        return panelImage;
        
    }
    
    // -----------------------------------------------------------------------------------------------------------
    // -------------------------------------------------FENETRE MENU----------------------------------------------
    public JPanel construitMenu(String texte, String bouton1, String bouton2, String bouton3, String bouton4, String panelRetour) throws IOException {
        JPanel main = new JPanel();
        main.setPreferredSize(new Dimension(500, 800));
        main.setBackground(Color.white);
        main.setBorder(new EmptyBorder(50,80,0,80));
        
        JTextPane titre = construitTitre(texte, null);
        
        JPanel panelBtn = new JPanel();
        panelBtn.setPreferredSize(new Dimension(200, 300));
        int lines = 3;
        if(bouton2 != null) lines = 4;
        GridLayout layout = new GridLayout(lines,1);
        layout.setVgap(120-lines*20);
        panelBtn.setLayout(layout);
        panelBtn.setBackground(Color.white);
        
        JButton b1 = new JButton(new ActionBoutton(bouton1,this));
        JButton b2 = new JButton(new ActionBoutton(bouton2,this));
        JButton b3 = new JButton(new ActionBoutton(bouton3,this));
        JButton b4 = new JButton(new ActionRetour(bouton4, this, panelRetour));
        
        panelBtn.add(b1);
        if(bouton2 != null) panelBtn.add(b2);
        panelBtn.add(b3);
        panelBtn.add(b4);
        
        int nombre = (int)(Math.random() * 2);
        String nomClef = (String) lesClefs.get(nombre);
        JPanel clefSol = new PanelImage(nomClef + ".png");
        clefSol.setBackground(Color.white);
        if (nombre == 0) {
            clefSol.setPreferredSize(new Dimension(66, 180));
        } else {
            clefSol.setPreferredSize(new Dimension(100, 115));
        }
        
        JPanel panelClef = new JPanel();
        panelClef.setPreferredSize(new Dimension(400, 300));
        panelClef.setBackground(Color.white);
        panelClef.add(clefSol);
        panelClef.setBorder(new EmptyBorder(50,0,0,0));
        
        main.add(titre);
        main.add(panelBtn);
        main.add(panelClef);
        return main;
    }
    
    // -----------------------------------------------------------------------------------------------------------
    // -------------------------------------------------FENETRE CHOIX----------------------------------------------
    public JPanel construitPanelChoix(String texte, String sousTexte, JPanel liste, String bouton1, String bouton2, String panelRetour) throws IOException {
        JPanel main = new JPanel();
        main.setPreferredSize(new Dimension(500, 800));
        main.setBackground(Color.white);
        main.setBorder(new EmptyBorder(50,80,0,80));
        
        JTextPane titre = construitTitre(texte, sousTexte);
        
        JPanel panelBtn = new JPanel();
        panelBtn.setPreferredSize(new Dimension(200, 300));
        GridLayout layout = new GridLayout(3,1);
        layout.setVgap(60);
        panelBtn.setLayout(layout);
        panelBtn.setBackground(Color.white);
        
        JButton b1 = new JButton(new ActionBoutton(bouton1,this));
        JButton b2 = new JButton(new ActionRetour(bouton2, this, panelRetour));
        
        liste.setPreferredSize(new Dimension(500, 300));
        panelBtn.add(b1);
        panelBtn.add(b2);
        
        
        main.add(titre);
        main.add(liste);
        main.add(panelBtn);
        return main;
    }
    
    public JTextPane construitTitre(String texte, String sousTexte){
        JTextPane titre = new JTextPane();
        
        StyledDocument doc = titre.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        titre.setPreferredSize(new Dimension(500, 120));
        titre.setText(texte);
        titre.setBackground(Color.white);
        titre.setEditable(false);
        Font font = new Font("TimesRoman", Font.BOLD, 60);
        titre.setFont(font);
        
        if (sousTexte != null) {
            try {
                //on redimensionne la taille du TextPane
                titre.setPreferredSize(new Dimension(500, 200));
                
                SimpleAttributeSet keyWord = new SimpleAttributeSet();
                font = new Font("TimesRoman", Font.PLAIN, 30);
                StyleConstants.setFontFamily(keyWord, "TimesRoman");
                StyleConstants.setFontSize(keyWord, 30);
                //  Add some text
                doc.insertString(doc.getLength(), "\n" + sousTexte, keyWord );
            } catch (BadLocationException ex) {
                Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return titre;
    }
    // -----------------------------------------------------------------------------------------------------------
    // -------------------------------------------------FENETRE CONNECTION----------------------------------------
    private JPanel construitFenConnection() throws IOException{
        JPanel panelImage = new PanelImage("background.jpg"); 
        panelImage.setLayout(new GridBagLayout());
        
        JPanel conteneur = construitConteneur();
        panelImage.add(conteneur);
        
        return panelImage;
    }
    
    private JPanel construitConteneur() throws IOException{
        JPanel cp = new JPanel();
        cp.setPreferredSize(new Dimension(500, 800));
        cp.setBackground(Color.white);
        cp.setBorder(new EmptyBorder(50,100,0,100));
        
        JTextPane titre = construitTitre("LOGIN", null);
        
        Image profileImg = ImageIO.read(new File("images/profile.png")).getScaledInstance(120, 120,Image.SCALE_SMOOTH);
        JLabel profileLbl = new JLabel("", new ImageIcon(profileImg),JLabel.CENTER);
        profileLbl.setSize(new Dimension(120, 120));
        
        cp.add(titre);
        cp.add(profileLbl);
        cp.add(construitFormulaire());
        
        return cp;
    }
    
    private JPanel construitFormulaire(){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        cp.setPreferredSize(new Dimension(200, 300));
        GridLayout layout = new GridLayout(5,1);
        layout.setVgap(30);
        cp.setLayout(layout);
        
        JPanel loginPane =new JPanel(new GridLayout(2,1));
        loginPane.setBackground(Color.white);
        JLabel loginLabel = new JLabel("Nom d'utilisateur");
        JTextField login = new JTextField();
        loginPane.add(loginLabel);
        loginPane.add(login);
        
        JPanel pswPane =new JPanel(new GridLayout(2,1));
        pswPane.setBackground(Color.white);
        JLabel pswLabel = new JLabel("Mot de passe:");
        JPasswordField psw = new JPasswordField();
        psw.setEchoChar('*');
        pswPane.add(pswLabel);
        pswPane.add(psw);
        
        JButton connection = new JButton("Connexion");
        connection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String connectUser = login.getText();
                String connectPsw = psw.getPassword().toString();
                
                if (accesBD.connexionRequ(connectUser,connectPsw)){ 
                    setFenJouer();
                }
                else {
                    if(accesBD.getConnection() != null) JOptionPane.showMessageDialog(null, "Mauvais mots de passe et/ou nom d'utilisateur", "Champs invalides", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton inscription = new JButton("S'inscrire");
        inscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String user = login.getText();
                String pwd = psw.getPassword().toString();              
                
                if(user != null && user.length() >= 3 && pwd != null && pwd.length() >= 4){ 
                    try {
                        accesBD.inscription(user, pwd);
                        if(accesBD.getConnection() != null) JOptionPane.showMessageDialog(null, "Inscription réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Pseudo déjà pris.", "Echec", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
                else{
                    JOptionPane.showMessageDialog(null, "Veuillez remplir les champs.\nLogin : 3 caractères | Mot de passe : 4 caractères\nminimum.", "Champs invalides", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton retour = new JButton(new ActionRetour("Retour", this, "FenConnection"));
        
        cp.add(loginPane);
        cp.add(pswPane);
        cp.add(connection);
        cp.add(inscription);
        cp.add(retour);
        
        return cp;
    }
    
    // -----------------------------------------------------------------------------------------------------------
    // -------------------------------------------------FENETRE JOUER---------------------------------------------
    private JPanel construitFenJouer() throws IOException{
        JPanel panelImage = new PanelImage("background.jpg");
        panelImage.setLayout(new GridBagLayout());
        
        JPanel cp = construitMenu("LAMIFA", "Apprentissage", null, "Profil", "Retour", "FenConnection");
       
        panelImage.add(cp);
        return panelImage;
    }
    
    public JPanel construitAssemblePane(JPanel cp1, JPanel cp2, int top, int left, int bottom, int right, int btw){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        cp.setLayout(new BorderLayout());
        cp.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        cp.add(cp1, BorderLayout.NORTH);
        cp.add(Box.createRigidArea(new Dimension(0,btw)));
        cp.add(cp2, BorderLayout.SOUTH);
        return cp;
    }
    
    public JPanel construitTitrePane(String texte){
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        JTextArea titre = new JTextArea();
        titre.setText(texte);
        titre.setBackground(Color.white);
        titre.setEditable(false);
        Font font = new Font("TimesRoman", Font.ITALIC, 46);
        titre.setFont(font);
        
        cp.add(titre);
        return cp;
    }
    
    public JPanel construitSousTitrePane(String texte){
    	JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        
        JTextArea titre = new JTextArea();
        titre.setText(texte);
        titre.setBackground(Color.white);
        titre.setEditable(false);
        Font font = new Font("TimesRoman", Font.PLAIN, 26);
        titre.setFont(font);
        
        cp.add(titre);
        return cp;
    }
    
    public JPanel construitButtonPane(String txt, int x, int y){
        JPanel cp = new JPanel();
        
        JButton butt = new JButton(new ActionBoutton(txt, this));
        butt.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        butt.setPreferredSize(new Dimension(x,y));
        cp.add(butt);
        
        return cp;
    }
    
    //Panel inséré à la fin de chaque exercice
    public JPanel construitBoutonsRetQuit(String panelRetour) {
        JPanel cp = new JPanel();
       
        JButton recommencer = new JButton(new ActionBoutton("Recommencer", this));
        recommencer.setPreferredSize(new Dimension(150,50));
        
        JButton quitter = new JButton(new ActionRetour("Quitter", this, panelRetour));
        quitter.setPreferredSize(new Dimension(150,50));
        
        cp.add(recommencer);
        cp.add(quitter);
        cp.setBackground(Color.white);
        return cp;
    }
    
    public JPanel construitRetQuitPane(String txt, String nomPanel){
        JPanel cp = new JPanel();
        
        JButton ret = new JButton(new ActionRetour(txt, this, nomPanel));
        ret.setPreferredSize(new Dimension(90,30));
        
        cp.add(ret);
        
        return cp;
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------FENETRE APPRENTISSAGE--------------------------------------------
    private JPanel construitFenApprentissage() throws IOException{
        JPanel panelImage = new PanelImage("background.jpg");
        panelImage.setLayout(new GridBagLayout());
        
        JPanel cp = construitMenu("Apprentissage", "La théorie", null, "La pratique", "Retour", "FenApprentissage");
        panelImage.add(cp);
        return panelImage;
    }
    
    private JPanel construit2BouttonsPane(String text1, String text2){
        JPanel cp = new JPanel();
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 17*largeur/64, 0));
        cp.setBorder(BorderFactory.createEmptyBorder(110, 0, 0, 0));
        
        JPanel but1 = construitButtonPane(text1, 172, 70);
        
        JPanel but2 = construitButtonPane(text2, 172, 70);
        
        cp.add(but1);
        cp.add(but2);

        return cp;
    }
    
    // ---------------------------------------------------------------------------------------------------------------
    // ------------------------------------FENETRE APPRENTISSAGE THEORIQUE ET PRATIQUE--------------------------------
    private JPanel construitFenSelection(String sousTitreTxt, String nomPanel, String[] liste) throws IOException{
        JPanel panelImage = new PanelImage("background.jpg");
        panelImage.setLayout(new GridBagLayout());
        
        getContentPane().setName(nomPanel);
        
        JPanel listeP = construitListePane(liste, nomPanel);
        JPanel affiche = construitAffiche();
        JPanel listeaffiche = construitAssemblePane(listeP,affiche, 40, 0, 15, 0, 0);
        
        listeP.setBackground(Color.white);
        affiche.setBackground(Color.white);
        listeaffiche.setBackground(Color.white);
        
        JPanel cp = construitPanelChoix("Apprentissage", sousTitreTxt, listeaffiche, "C'est parti !", "Retour", nomPanel);
        panelImage.add(cp);
        return panelImage;
    }
    
    @SuppressWarnings("unchecked")
    private JPanel construitListePane(String[] liste, String nomPanel){
        JPanel cp = new JPanel();
        cp.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        cp.setBackground(Color.white);
        
        JComboBox listeCBTmp = new JComboBox(new ListeStringModele(this, liste));
        listeCBTmp.setBorder(BorderFactory.createLineBorder(Color.black,2,false));
        listeCBTmp.setPreferredSize(new Dimension(230,listeCBTmp.getMinimumSize().height));
        
        if (nomPanel.equals("FenAppTheo")){
            this.listeCBTheo=listeCBTmp;
        }
        if (nomPanel.equals("FenAppPra")){
            this.listeCBPra=listeCBTmp;
        }
        
        cp.add(listeCBTmp);
        
        return cp;
    }
    
    private JPanel construitAffiche(){
        JPanel cp = new JPanel();
        
        Font font = new Font("TimesRoman", Font.PLAIN, 18);
        description.setFont(font);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        description.setPreferredSize(new Dimension(400,150));
        
        cp.add(description);
        
        return cp;
    }
    
    //---------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------FENETRE PROFIL---------------------------------------------
     private JPanel construitFenProfil() throws IOException{
        JPanel panelImage = new PanelImage("background.jpg");
        panelImage.setLayout(new GridBagLayout());
        
        JPanel conteneur = construitMenu("Profil", "Statistiques", null, "Informations", "Retour", "FenApprentissage");
        
        panelImage.add(conteneur);
        return panelImage;   
    }
        
    //----------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------FENETRE STATISTIQUES----------------------------------------
     private JPanel construitFenStats() throws IOException{
        JPanel panelImage = new PanelImage("background.jpg");
        panelImage.setLayout(new GridBagLayout());
        getContentPane().setName("FenStats");
        
        JPanel cp = new JPanel();
        cp.setBackground(Color.white);
        cp.setPreferredSize(new Dimension(800, 1000));
        cp.setBorder(new EmptyBorder(30,10,0,10));
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        
        int reussiteTot1 = 0, reussiteTot2 = 0, reussiteTot3 = 0, reussiteTot4 = 0, scoreTot1 = 0, scoreTot2 = 0, scoreTot3 = 0, scoreTot4 = 0, scoreMax1 = 0, scoreMax2 = 0, scoreMax3 = 0, scoreMax4 = 0, nbEssais1 = 0, nbEssais2 = 0, nbEssais3 = 0, nbEssais4 = 0;
        try {
            reussiteTot1 = accesBD.GetStatsExo(1, "reussiteTot");
            reussiteTot2 = accesBD.GetStatsExo(2, "reussiteTot");
            reussiteTot3 = accesBD.GetStatsExo(3, "reussiteTot");
            reussiteTot4 = accesBD.GetStatsExo(4, "reussiteTot");
            scoreTot1 = accesBD.GetStatsExo(1, "scoreTot");
            scoreTot2 = accesBD.GetStatsExo(2, "scoreTot");
            scoreTot3 = accesBD.GetStatsExo(3, "scoreTot");
            scoreTot4 = accesBD.GetStatsExo(4, "scoreTot");
            scoreMax1 = accesBD.GetStatsExo(1, "scoreMax");
            scoreMax2 = accesBD.GetStatsExo(2, "scoreMax");
            scoreMax3 = accesBD.GetStatsExo(3, "scoreMax");
            scoreMax4 = accesBD.GetStatsExo(4, "scoreMax");
            nbEssais1 = accesBD.GetStatsExo(1, "nbEssais");
            nbEssais2 = accesBD.GetStatsExo(2, "nbEssais");
            nbEssais3 = accesBD.GetStatsExo(3, "nbEssais");
            nbEssais4 = accesBD.GetStatsExo(4, "nbEssais");
        } catch (SQLException ex) {
            Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JPanel titre = construitTitrePane("Statistiques");
        
        JPanel exo1 = construitStats("Lecture de notes", reussiteTot1, scoreTot1, scoreMax1, nbEssais1);
        JPanel exo2 = construitStats("Reconnaissance de notes", reussiteTot2, scoreTot2, scoreMax2, nbEssais2);
        JPanel exo3 = construitStats("Rythme", reussiteTot3, scoreTot3, scoreMax3, nbEssais3);
        JPanel exo4 = construitStats("Lecture de notes en rythme", reussiteTot4, scoreTot4, scoreMax4, nbEssais4);
        
        JButton retour = new JButton(new ActionRetour("Retour", this, "FenStats"));
        
        cp.add(titre);
        cp.add(exo1);
        cp.add(exo2);
        cp.add(exo3);
        cp.add(exo4);
        cp.add(retour);
        
        panelImage.add(cp);
        return panelImage;  
    }
     
    private JPanel construitStats(String titre, int taux, int scoreTot, int scoreMax, int nbEssais){
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setBackground(Color.white);
        
        JPanel exoTitre = construitSousTitrePane(titre);
        JTextArea exoStats = new JTextArea();
        Font font = new Font("Arial", Font.PLAIN, 20);
        exoStats.setFont(font);
        exoStats.setBackground(Color.white);
        exoStats.setText("Taux de réussite par note moyen : " + taux + "\nScore moyen : " + scoreTot + "\nScore Maximum obtenu : " + scoreMax + "\nNombre d'essais : " + nbEssais);
        
        block.add(exoTitre);
        block.add(exoStats);       
        return block;
    }
     
    // ---------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------METHODES PUBLIQUES--------------------------------------------
    
    public void setFenAppTheo(){
        setContentPane(FenAppTheo);
        getContentPane().setName("FenAppTheo");
        setVisible(true);
    }
    
    public void setFenAppPra(){
        setContentPane(FenAppPra);
        getContentPane().setName("FenAppPra");
        setVisible(true);
    }
    
    public void setFenJouer(){
        setContentPane(FenJouer);
        getContentPane().setName("FenJouer");
        setVisible(true);
    }
    
    public void setFenApprentissage(){
        setContentPane(FenApprentissage);
        getContentPane().setName("FenApprentissage");
        setVisible(true);
    }
    
    public void setFenPrincipale(){
        setContentPane(FenPrincipale);
        getContentPane().setName("FenPrincipale");
        setVisible(true);
    }
    
    public void setFenConnection(){
        setContentPane(FenConnection);
        getContentPane().setName("FenConnection");
        setVisible(true);
    }
    
    public void setFenProfil(){
        if(accesBD.currentUser != null){
            setContentPane(FenProfil);
            getContentPane().setName("FenProfil");
            setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(null, "Vous êtes en mode hors ligne.", "Hors ligne", JOptionPane.ERROR_MESSAGE);
    }
    
    public void setFenStats(){
        try {
            setContentPane(construitFenStats());
        } catch (IOException ex) {
            Logger.getLogger(FenPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
        getContentPane().setName("FenStats");
        setVisible(true);
    }
    
    public JTextArea getDescri(){
        return description;
    }
    
    public String[] getListAppPra(){
        return listeAppPra;
    }
    
    public String[] getListAppTheo(){
        return listeAppTheo;
    }
    
    public JComboBox getListeCBTheo(){
        return listeCBTheo;
    }

    public JComboBox getListeCBPra() {
        return listeCBPra;
    }
    
    public SynthétiseurM getSynthétiseurM(){
        return synthé;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
