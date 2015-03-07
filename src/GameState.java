
import java.util.HashSet;
import java.util.Iterator;

// rules: http://www.looneylabs.com/rules/homeworlds

public class GameState{

	private int[][] bank; //2d array, pieces in the bank, [color][size]
	// should I have a different structure for just the bank?
	private HashSet<Star> stars; //stars on the field
	private int numPlayers; // number of players, set to 2 for now
	private int turn; // whose turn is it?
	private int turnNum; // how many turns have there been?
	public GameState(){ 
		//initialize the gamestate 
		turnNum = 0; // start on two 0
		numPlayers = 2; // only does 2 player at this point
		turn = 1; //random mod 2
		setBank(); // set up the bank
	}
	public void addStar(Star starIn){
		// adds input star to stars
		stars.add(starIn);
		//thoughts: can two stars have the same name?
		//thoughts: when do we check if the pieces are available?
	}
	public HashSet<Star> getStars(){
		// returns stars 
		return stars;
	}
	public int getTurnNum(){
		// lets you know what turn it is
		return turnNum;
	}
	public int getTurn(){
		// lets you know whose turn it is
		return turn;
	}
	public int nextTurn(){
		// changes turns
		turn = (turn%numPlayers) +1;
		turnNum++;
		return turn;
	}
	public void setBank(){
		// sets up the bank for a 2 player game
		bank = new int[4][3]; // 4 colors, 3 sizes
		for(int i=0; i < 4; i++){
			for(int j = 0; j < 3; j++){
				bank[i][j] = 3;
				// start out with 3 of each piece
			}
		}
		// when adding more than two player remember to also modify set bank
	}
	public void addPiece(Color color, int size) {
		// adds a piece to the bank
		// used when a ship is sacrificed or traded or when a star is destroyed
		bank[color.getCode()][size-1] += 1;
	}
	public boolean removePiece(Color color, int size) {
		// removes a piece from the bank 
		// used when a ship is traded or when moving to a new star
		if(isThere(color, size)) {
			// make sure there is something in the bank to be removed!
			bank[color.getCode()][size-1] -= 1;
			// remove the piece
			return true;
		} else {
			return false;
			// there is no such piece
		}
	}
	public int removeSmallest(Color color) {
		// removes the smallest piece of that color for build actions
		for(int x = 0; x < 3; x++) {
			if(bank[color.getCode()][x] != 0) {
				bank[color.getCode()][x] -= 1;
				// remove the piece
				return (x+1);
				// return the size of the piece removed
			}
		}
		return 0;
	}
	public boolean isThere(Color color){
		// check existence of a color in the bank
		for(int i = 0; i < 3; i++){
			if(bank[color.getCode()][i] > 0){
				return true;
				// found it!
			}
		}
		return false;
		// didn't find it
	}
	public boolean isThere(Color color, int size){
		// check existence of a piece of a certain color and size
		if(bank[color.getCode()][size-1] > 0){
			return true;
			// found it!
		}else{
			return false;
			// didn't find it 
		}
	}

	public void trade(Star star, Ship ship, Color color){
		// blue action
		// trade ship for a ship of a different color of the same size
		Ship newShip = new Ship(star, color, ship.getSize(), ship.getPlayer());
		// build a ship
		removePiece(color, ship.getSize());
		// remove the piece from the bank
		star.addShip(newShip);
		addPiece(ship.getColor(), ship.getSize());
		star.removeShip(ship);

	}
	public void build(Star star, Color color){
		// green action
		// builds a new ship of an available color at the star
		// need to check that there are any of that color left?
		Ship newShip = new Ship(star, color, removeSmallest(color), getTurn());
		star.addShip(newShip);
	}
	public void attack(Ship ship){
		// red action
		// target ship changes teams
		ship.setPlayer((ship.getPlayer()%numPlayers) + 1);
	}
	public void move(Star from, Star to, Ship ship){
		// yellow action
		// move a ship from one star to another
		// what about moving to an undiscovered star?
		from.removeShip(ship);
		// remove the ship from one star
		to.addShip(ship);
		// add it to another
	}
	public Color[] getTech(Ship ship){
		// finds tech available to a ship
		// this is based on the colors of the star 
		// and the colors of the ships at that star
		HashSet<Integer> techSet = new HashSet<Integer>();
		// this is the set of available tech in color codes
		Color[] techArr = new Color[5];
		// can have up to 5 options (in colors) 
		Iterator<Ship> itr = ship.getStar().getShips(ship.getPlayer()).iterator();
		// set up an iterator through the ships at the star that this ship is at
		techSet.add(ship.getStar().getColor().getCode());
		// add the color of the star
		// what if the star is a homeworld?
		if(ship.getStar() instanceof Homeworld){
			// if the star is a homeworld it has two colors so we need to grab that too
			techSet.add(((Homeworld)ship.getStar()).getColorPrime().getCode());
		}
		while(itr.hasNext()){
			// iterate through the ships at the star which this ship is at
			techSet.add(itr.next().getColor().getCode());
		}
		if(techSet.contains(0)){
			techArr[0] = Color.BLUE;
		}
		if(techSet.contains(1)){
			techArr[1] = Color.RED;
		}
		if(techSet.contains(2)){
			techArr[2] = Color.YELLOW;
		}
		if(techSet.contains(3)){
			techArr[3] = Color.GREEN;
		}
		techArr[4] = Color.SAC; // sacrificing is always an option
		return techArr;
	}
	public Color[] getBuild(Ship ship){
		// returns the colors that this ship can build
		// that is the colors of ships at the star
		HashSet<Integer> techSet = new HashSet<Integer>();
		// holds int values of potential tech
		Color[] techArr = new Color[4];
		// 4 possible colors to build
		Iterator<Ship> itr = ship.getStar().getShips(ship.getPlayer()).iterator();
		// iterate through the ships at the star
		while(itr.hasNext()){
			techSet.add(itr.next().getColor().getCode());
			// go through all the stars and grab their colors
		}
		if(techSet.contains(0) && isThere(Color.BLUE)){
			techArr[0] = Color.BLUE;
		}
		if(techSet.contains(1) && isThere(Color.RED)){
			techArr[1] = Color.RED;
		}
		if(techSet.contains(2) && isThere(Color.YELLOW)){
			techArr[2] = Color.YELLOW;
		}
		if(techSet.contains(3) && isThere(Color.GREEN)){
			techArr[3] = Color.GREEN;
		}
		return techArr;
	}

	public Color[] getTrade(Ship ship){
		// return the potential trades that can be made
		Color[] techArr = new Color[4];
		// 4 possible colors
		int size = ship.getSize();
		// traded ships need to be the same size
		if(isThere(Color.BLUE, size)){
			techArr[0] = Color.BLUE;
		}
		if(isThere(Color.RED,size)){
			techArr[1] = Color.RED;
		}
		if(isThere(Color.YELLOW, size)){
			techArr[2] = Color.YELLOW;
		}
		if(isThere(Color.GREEN, size)){
			techArr[3] = Color.GREEN;
		}
		return techArr;
	}
	public HashSet<Ship> getAtk(Ship ship){
		// returns which ships this ship can attack
		int size = ship.getSize();
		// size of attacking ship needs to be greater than or equal to attacked ship
		HashSet<Ship> enemySet = new HashSet<Ship>();
		// this is the enemy ships at this star
		Iterator<Ship> itr = ship.getStar().getShips((ship.getPlayer()+1)%numPlayers).iterator();
		// iterates through enemy ships at the star
		while(itr.hasNext()){
			Ship enemy = itr.next();
			if(enemy.getSize() <= size){
				// if the enemy ship's size is less than or equal to this ship's size
				// then add it to the list of potential attacked ships
				enemySet.add(enemy);
			}
		}
		return enemySet;
		// return the ships that can be attacked by the input ship
	}
	public HashSet<Star> getMove(Ship ship){
		// return the stars that can be moved to
		int size = ship.getStar().getSize();
		// star connections are based on the size
		// stars with different sizes are connected
		int sizePrime = 0;
		// size prime is for homeworlds
		// homeworlds are composed of two pieces so they have two sizes
		HashSet<Star> starSet = new HashSet<Star>();
		// set of stars that can be moved to
		if(ship.getStar() instanceof Homeworld){
			// if it is a homeworld then set size prime
			sizePrime = ((Homeworld)ship.getStar()).getSizePrime();
		}
		Iterator<Star> itr = stars.iterator();
		// iterates through all the stars
		while(itr.hasNext()){
			Star star = itr.next();
			// get the next star
			if(star instanceof Homeworld){
				// if it's a homeworld, compare both sizes
				if(star.getSize() != size && star.getSize() != sizePrime){
					if(((Homeworld)star).getSizePrime() != size && ((Homeworld)star).getSizePrime() != sizePrime){
						starSet.add(star);
						// if none of the two stars sizes overlap
						// then add star to set
					}
				}
			}else{
				// if not a homeworld then only one size to compare
				if(star.getSize() != size && star.getSize() != sizePrime){
					// add star if they're connected
					starSet.add(star);
				}
			}
		}
		return starSet;
	}
}