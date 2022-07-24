package map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip ;
    private String nameOfFile[] = new String[20];

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

    public void setVolume(int i, float reduceVolume) throws LineUnavailableException, IOException {
		
			File file = new File(nameOfFile[i]);
			AudioInputStream ais = null;
			try {
				ais = AudioSystem.getAudioInputStream(file);
				this.clip = AudioSystem.getClip();
	    		clip.open(ais);
	    		FloatControl gainControl = 
	    		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    		gainControl.setValue(reduceVolume); // Reduce volume by 10 decibels.
	    		clip.start();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	
	public void turnOnMusicLoopReduceVolume(int i, float f) throws LineUnavailableException, IOException{
	      setVolume(i,f);
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
    
    public void turnOnMusicReduceVolume(int i, float reduceVolume) throws LineUnavailableException, IOException {
    	setVolume(i, reduceVolume);
    }
}