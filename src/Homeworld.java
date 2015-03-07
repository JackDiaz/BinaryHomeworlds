



public class Homeworld extends Star{
	// if a star is a homeworld then it could hve two sizes and colors
	Color colorPrime; // second color
	int sizePrime; // second size
	
	public Homeworld(String nameIn, Color colorIn, Color colorInPrime, 
			int sizeIn, int sizeInPrime){
		// initialize homeworld
		super(nameIn,colorIn,sizeIn);
		// initialize as a star
		colorPrime = colorInPrime;
		// initilize second color
		sizePrime = sizeInPrime;
		// initialize second size
	}
	public int getSizePrime(){
		// getter for sizePrime
		return sizePrime;
	}
	public Color getColorPrime(){
		// getter for colorPrime
		return colorPrime;
	}
}