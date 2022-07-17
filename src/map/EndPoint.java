package map;

public class EndPoint {
	
	public int ID;
	public int doorID;
	
	public EndPoint() {
		
	}
	
	public EndPoint(int doorID) {
		this.doorID = doorID;
	}
	
	public EndPoint(int ID, int doorID) {
		this.ID = ID;
		this.doorID = doorID;
	}
	
}
