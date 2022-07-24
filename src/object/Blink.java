package object;

import algorithm.Cooldown;

public class Blink {
	private boolean isBlink;
	private Cooldown blinkCD = new Cooldown(10000);
	public final int BLINK_RANGE = 2;
	
	public Blink() {
		
	}

	public boolean isBlink() {
		return isBlink;
	}

	public void setBlink(boolean isBlink) {
		this.isBlink = isBlink;
	}

	public Cooldown getBlinkCD() {
		return blinkCD;
	}

	public void setBlinkCD(Cooldown blinkCD) {
		this.blinkCD = blinkCD;
	}
	
	public void setCD(long i) {
		blinkCD.setCDTime(i);
	}
}
