package controleur;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import vue.*;

public class ListeStringModele extends AbstractListModel implements ComboBoxModel{
    private ArrayList<String> liste;
    private String selectedIt;
    private FenPrincipale fen;
    private int option;
    
    public ListeStringModele(FenPrincipale fen){
        super();
        liste = new ArrayList<String>();
        this.fen = fen;
    }
    
    public ListeStringModele(FenPrincipale fen, String[] liste){
        super();
        this.liste = new ArrayList<String>();
        this.fen = fen;
        for(String s : liste)
            this.liste.add(s);
        if(((String)fen.getContentPane().getName()).equals("FenAppTheo"))
            option = 1;
        else
            option = 2;
        setSelectedItem(this.liste.get(0));
    }
    
    public void addElement(String s){
        liste.add(s);
        int size = liste.size();
        fireIntervalAdded(this, size-1, size-1);
        setSelectedItem(liste.get(0));
    }
    
    public void delElement(String s){
        int position = liste.indexOf(s);
        if(position > 0)
            liste.remove(position);
        fireIntervalRemoved(this,position,position);
    }
    
    public void delAll(){
        int ancienTaille = liste.size();
        liste.clear();
        fireIntervalRemoved(this, 0, ancienTaille);
    }
    
    public int getIndex(String s){
        return liste.indexOf(s);
    }
    
    public ArrayList<String> getList(){
        return liste;
    }
    
    @Override
    public int getSize() {
        return liste.size();
    }

    @Override
    public Object getElementAt(int index) {
        return liste.get(index);
    }
    
    public void setSelectedItem(Object item) {
        if(item instanceof String){
            selectedIt = (String) item;
            setLabelText(fen.getDescri());
        }
        else 
            selectedIt = null;
    }

    public Object getSelectedItem() {
        return selectedIt;
    }
    
    private void setLabelText(JTextArea descri) {
    	if(option == 1)
	        switch(getIndex(selectedIt)){
	            case 0: descri.setText("Qu'est-ce que le rythme ? Comment retranscrire du rythme ? Comment lire du rythme ? Tout ce dont vous avez besoin sur le rythme, c'est ici !");
	                    break;
	            case 1: descri.setText("Une note, un terme qu'on entend souvent et qui est à la base de la musique ! Etudiez, apprenez, comprenez la musique !");
	                    break;
	            case 2: descri.setText("Les accords ! La suite logique des notes ! Comment créer un accord qui sonne bien, le rendre mélancolique ou joyeux, c'est ici !");
	                    break;
	            case 3: descri.setText("Rien ici ATM");
	                    break;
	            case 4: descri.setText("Rien ici ATM");
	                    break;
	            case 5: descri.setText("Rien ici ATM");
	                    break;
	        }
	   else if(option == 2)
    		switch(getIndex(selectedIt)){
	            case 0: descri.setText("Une partition va s'afficher à l'écran et à l'aide de votre clavier, vous devrez appuyer sur les touches correspondantes aux notes sur la portée.");
	                    break;
	            case 1: descri.setText("Vous allez écouter une série de notes et les rejouer dans le bon ordre avec votre clavier. Avant la première écoute vous pouvez utiliser le clavier pour jouer des notes et avoir une référence.");
	                    break;
	            case 2: descri.setText("Une partition composée de différents rythmes va s'afficher à l'écran. Vous devez reproduire les rythmes en fonction du BPM choisi.");
	                    break;
	            case 3: descri.setText("Un mélange de l'exercice lecture de notes et de l'exercice rythme. Vous devrez jouer les notes correspondantes tout en tenant le rythme.\nExercice difficile !");
	                    break;
	            case 4: descri.setText("Rien ici ATM");
	                    break;
	            case 5: descri.setText("Rien ici ATM");
	                    break;
    		}
    		
    }
}
