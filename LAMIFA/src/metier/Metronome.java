package metier;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import metier.RythmeM;
import vue.Partition;
//
public class Metronome extends Thread {
    private double dureeTemps;
    private MetierExo exo;
    private String nomPanel;
    private int volume;
    private MidiChannel canal;
    private Synthesizer synthe;
    
    public Metronome(MetierExo exo, String nomPanel, double dureeTemps, int volume){ //durée du tempo en secondes
        this.exo = exo;
        this.dureeTemps = dureeTemps;
        this.nomPanel = nomPanel;
        this.volume = volume;
        synthe = null;
        try {
            synthe = (Synthesizer) MidiSystem.getSynthesizer();
            synthe.open();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(RythmeM.class.getName()).log(Level.SEVERE, null, ex);
        }
        canal = synthe.getChannels()[0];
        canal.programChange(13);
    }
    
    @Override
    public void run() {
      while(exo.getFen().getContentPane().getName() != null && exo.getFen().getContentPane().getName().equals(nomPanel)){
            canal.noteOn(50, volume);
            try{
                Thread.sleep((long) (1000f*dureeTemps));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public void setVolume(int v){
        volume = v;
    }
}