package algorithm;

public class Cooldown {
	
	public long time;
	public long cdTime;
	
	public Cooldown() {
		
	}
	
	public Cooldown(long cdTime) {
		this.time = 0;
		this.cdTime = cdTime;
	}
	
	public boolean isCD() {
		long curTime = System.currentTimeMillis();
		if(curTime > time + cdTime) {
			time = curTime;
			return false;
		}
		return true;
	}
	
	public void setTime() {
		this.time = System.currentTimeMillis();
	}
}
