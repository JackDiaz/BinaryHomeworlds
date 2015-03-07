


public class Ship{
	// Ship class, has a color, a size, and must reside at a star
	Color color; // color of ship
	int size; // size 1-3
	int player; // who owns the ship
	Star star; // which star does it reside at
	
	public Ship(Star starIn, Color colorIn, int sizeIn, int playerIn){
		// initialize a ship
		color = colorIn; // set the color
		size = sizeIn; // set the size
		player = playerIn; // set the owning player
		star = starIn; // set the star
	}
	public Color getColor(){
		// returns the color of the ship
		return color;
	}
	public int getSize(){
		// returns the size of the ship
		return size;
	}
	public int getPlayer(){
		// returns the player that owns it
		return player;
	}
	public void setPlayer(int newPlayer){
		// setter for player
		// sets the owning player for red actions
		player = newPlayer;
	}
	public Star getStar(){
		// gets the star that it's at
		return star;
	}
}