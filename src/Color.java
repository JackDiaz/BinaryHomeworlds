



public enum Color {
	// enum for colors
	BLUE(0),RED(1),YELLOW(2),GREEN(3),SAC(4);

	public int code;

	private Color(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}
}
