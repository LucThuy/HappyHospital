package map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    public Clip clip ;
    public String nameOfFile[] = new String[20];

    public Sound(){
        this.nameOfFile[0] = "sound/PositiveFuseFrenchFuse.wav";
        this.nameOfFile[1] = "sound/TayDuKy.wav";
        this.nameOfFile[2] = "sound/canhbao.wav";
        this.nameOfFile[3] = "sound/game_over.wav";
        this.nameOfFile[4] = "sound/Blink.wav";
        this.nameOfFile[5] = "sound/LocCoc.wav";
        this.nameOfFile[6] = "sound/BlueBoyAdventure.wav";
        this.nameOfFile[7] = "sound/coin.wav";
        this.nameOfFile[8] = "sound/unlock.wav";
        this.nameOfFile[9] = "sound/powerup.wav";
        this.nameOfFile[10] = "sound/zaowlrd.wav";

    }

    public void setFile(int i){
        try {
            File file = new File(nameOfFile[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(){
        this.clip.start();
    }

    public void loopSound(){
        this.clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(){
        this.clip.stop();
    }
    
	public void turnOnMusicLoop(int i){
      setFile(i);
      playSound();
      loopSound();
    }
	
    public void turnOffMusic(){
      stopSound();
    }
    
    public void turnOnMusic(int i){
      setFile(i);
      playSound();
    }
}