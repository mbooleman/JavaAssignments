import java.util.*;

public class Nim{
	int matches = 11;
	int taken = 0;
	int numPlayers;
	public static final String defaultColour = "\u001B[0m";
    public static final String playerOneColour = "\u001B[35m";
    public static final String playerTwoColour = "\u001B[32m";


	public void numberPlayers(){
		Scanner num = new Scanner(System.in);
		System.out.println("Will you play a 1 or 2 player game?");
		numPlayers = num.nextInt();
		if(numPlayers == 2){
			System.out.println(playerOneColour+"Player one is this colour "+defaultColour+"and "+playerTwoColour+"player two is this colour");				
		}
	}

	public void computerTurn(){
		System.out.println("There are "+matches+" matches.");

		switch (matches){
			case 11:
			case 7:
			case 6:
			case 2:
			case 1:
				taken = 1;
				break;
			case 8:
			case 3:
				taken = 2;
				break;
			case 9:
			case 4:
				taken = 3;
				break;
			case 10:
			case 5:
				taken = 4;
				break;
		}
		matches = matches - taken;
		System.out.println("Computer takes "+taken+" matches.");
	}

	public void playerTurn(int playerNumber){
		Scanner sc = new Scanner(System.in);
		String playerColour;
		if(playerNumber == 1){
			playerColour = playerOneColour;
		} else {
			playerColour = playerTwoColour;
		}
		System.out.println(defaultColour+"There are "+matches+" matches");
		System.out.print("How many"+ playerColour+" do"+defaultColour+" you want to take? ");
		int i = 0;
		while(true){
			try{
				taken = sc.nextInt();
				if(i==3){
					throw new tooManyAttemptsException("You've entered an incorrect too many times, please restart the game");
					
				}
				if (taken < 1 || taken > 4 || matches - taken < 0){
					i++;
					throw new unacceptedAmountException("You've entered an amount of matches that is not allowed, try again please.");
				}
				break;
				} catch (unacceptedAmountException e){
				System.out.println(e.getMessage());
				} catch (tooManyAttemptsException ex){
				System.out.println(ex.getMessage());
				System.exit(0);
				}
		}
		matches = matches - taken;
	}

	public void checkGameEnd(int player){
		if (matches == 0){
			switch (player){
				case 0: 
					System.out.println("The computer took the last match. \nYou Won!");
					System.exit(0);
					break;
				case 1:
					if(numPlayers ==2){
						System.out.println("Player one took the last match and loses.\nCongratulations player two!");
					} else {
						System.out.println("You took the last match.\nYou lost!");
					}
					System.exit(0);
					break;
				case 2:
					System.out.println("Player two took the last match and loses.\nCongratulations player one!");
					System.exit(0);
					break;
			}	
		}
	}

	public void playGame(){
		int i = 1;
		while (true){
			if(numPlayers==2){
				playerTurn(i);
				checkGameEnd(i);
				i++;
				playerTurn(i);
				checkGameEnd(i);
				i--;
			} else {
				playerTurn(i);
				checkGameEnd(i);
				i--;
				computerTurn();
				checkGameEnd(i);
				i++;
			}
		}
	}


	public static void main(String[] args){
		Nim game = new Nim();
		game.numberPlayers();
		game.playGame();
	}

}

class tooManyAttemptsException extends Exception{
	public tooManyAttemptsException(String errorMessage){
		super(errorMessage);
	}
}

class unacceptedAmountException extends Exception{
	public unacceptedAmountException(String errorMessage){
		super(errorMessage);
	}
}