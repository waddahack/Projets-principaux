package metier;

import vue.FenPrincipale;
import vue.ToucheV;

public abstract class MetierExo {
    private FenPrincipale fen;
    
    public MetierExo(FenPrincipale fen){
        this.fen = fen;
    }
    
    public void checkNote(String note){
        
    }
    
    public void checkNote(ToucheV touche){
        
    }

    void checkRythme() {
        
    }
    
    public FenPrincipale getFen(){
        return fen;
    }

    void checkNoteEtRythme(String nomNote) {
        
    }
}
