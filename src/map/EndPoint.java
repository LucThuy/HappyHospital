package map;

public class EndPoint {
	
	private int ID;
	private int doorID;
	
	public EndPoint() {
		
	}
	
	public EndPoint(int doorID) {
		this.setDoorID(doorID);
	}
	
	public EndPoint(int ID, int doorID) {
		this.setID(ID);
		this.setDoorID(doorID);
	}

	public int getDoorID() {
		return doorID;
	}

	public void setDoorID(int doorID) {
		this.doorID = doorID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
