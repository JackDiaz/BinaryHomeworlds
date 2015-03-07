import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class outer {
	public static void main(String [] args){
		GameState a = new GameState();
		while(true){

			try{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String input;
				if(a.getTurnNum() == 0){
					System.out.println("Would you like to:");
					System.out.println("1. Start a new game");
					System.out.println("2. Quit");
					if ((input=br.readLine())!=null){
						if(input.equals("1")){
							System.out.println("PLAY!");
						}else if (input.equals("2")){
							System.out.println("Are you sure you want to quit?");
							while((input=br.readLine())!=null){
								if(input.equals("yes")){
									System.exit(0);
								}
							}
						}
					}
				}
				while((input=br.readLine())!=null){
					
				
					
					if(input.equals("quit")){
						System.out.println("Are you sure you want to quit?");
						while((input=br.readLine())!=null){
							if(input.equals("yes")){
								System.exit(0);
							}
						}
					}
					
				
				
				
				}
			}catch(IOException io){
				io.printStackTrace();
			}	
		}
	}
}
