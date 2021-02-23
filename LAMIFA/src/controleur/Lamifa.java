package controleur;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Lamifa {

    public static void main(String[] args) throws IOException {
        try {
            new vue.FenPrincipale();
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException ex) {
            Logger.getLogger(Lamifa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
