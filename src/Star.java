import java.util.HashSet;

public class Star{
	// star class, has a color, a size, and a name
	// it has ships, these are separated by player in p1Ships and p2Ships
	// that would have to be changed when more than two players are possible
	Color color; // color of the star
	int size; // size of star
	String name; // star's name, to differentiate from other stars
	// makes it easier for players to identify stars
	HashSet<Ship> ships; // all the ships at the star
	HashSet<Ship> p1Ships; // all the ships owned by p1 at this star
	HashSet<Ship> p2Ships; // all the ships owned by p2 at this star
	
	
	public Star(String nameIn, Color colorIn, int sizeIn){
		// star initializer
		name = nameIn; // sets the name
		color = colorIn; // sets the color
		size = sizeIn; //  sets the size
	}
	public void addShip(Ship ship){
		// adds a ship to this star
		ships.add(ship);
		// add that ship to the set of all the ships
		if(ship.player == 1){
			// add that ship to p1's ships
			p1Ships.add(ship);
		}else{
			// add that ship to p2's ships
			p2Ships.add(ship);
		}
	}
	public void removeShip(Ship ship){
		// remove a ship
		// remove it from set of all ships
		ships.remove(ship);
		if(ship.player == 1){
			// remove ship from p1's set
			p1Ships.remove(ship);
		}else{
			// remove ships from p2's set
			p2Ships.remove(ship);
		}
	}
	public Color getColor(){
		// get color of star
		return color;
	}
	public String getName(){
		// get name of star
		return name;
	}
	public int getSize(){
		// get size of star
		return size;
	}
	public HashSet<Ship> getShips(){
		// get all ships at this star
		return ships;
	}
	public HashSet<Ship> getShips(int player){
		// returns specific player's ships
		// any other player it adds all the ships
		if(player == 1){
			// p1 set
			return p1Ships;
		}else if(player == 2){
			// p2 set
			return p2Ships;
		}else{
			// return all ships if player doesn't exist
			return ships;
		}
	}
}