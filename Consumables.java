import java.util.ArrayList;

public class Consumables extends Objects {

    ArrayList<Objects> dropObjects = new ArrayList<Objects>();
    Location currentLocation;
    public Consumables(Subject[] subject, String name, String description, Location spawnLocation) {
        super(subject,name,description);
        currentLocation = spawnLocation;
    }
    public Consumables(String name, String description,Location spawnLocation) {
		super(name,description);
        currentLocation = spawnLocation;
	}
	
	public void consume(){
        for(int i=0;i<dropObjects.size();i++)
        {
            currentLocation.currentlyPlacedObjects.add(dropObjects.get(i));
        }
        currentLocation.currentlyPlacedObjects.remove(this);
	}
    

}