import java.util.*;  

public class hangman {
	
	public static void main(String[] args){
		//create random word list:
		String[] wordList = {"recognize",
											"composer",
											"revoke",
											"knit",
											"revise",
											"point",
											"latest",
											"copper",
											"monk",
											"recovery",
											"stadium",
											"blue",
											"use",
											"cheque",
											"curl",
											"obese",
											"scene",
											"identity",
											"slot",
											"appreciate",
											"tumour",
											"arrest",
											"association",
											"proper",
											"policy",
											"cancer",
											"polite",
											"convention",
											"folk",
											"eyebrow",
											"tribe",
											"fox",
											"harmful",
											"lion",
											"leader",
											"experiment",
											"assault",
											"pollution",
											"artificial",
											"virus",
											"metal",
											"swallow",
											"continental",
											"battery",
											"lie",
											"first-hand",
											"mind",
											"panic",
											"plead",
											"conservative"};
		// Initiate random and Scanner object for choosing word and requesting inputs
		Random random = new Random();
		Scanner sc = new Scanner(System.in);    //System.in is a standard input stream  
		
		// set starting lifes
		int guessesLeft = 10;
		
		//set start if word has been guessed or not and initiate char and string for guesses
		boolean wordguessed = false;
		char guess = 'A';
		String guessString = "A";
	
		// Choose word
		String chosenWord = wordList[random.nextInt(wordList.length)];

		// set word length so we can use it in loops more easily
		int lengthWord = chosenWord.length();
		char[] chosenWordArray = new char[lengthWord];

		for (int i = 0; i < lengthWord;i++){
			chosenWordArray[i] = chosenWord.charAt(i);
		}

		//ArrayList<Character>chosenWordArrayList = Arrays.asList(chosenWordArray);
		//print statement to start start of the game
		System.out.println("Welcome to hangman you get 10 guesses, the word looks like:");
		
		//create empty ArrayLists to iterate over and check for each letter in the words
		ArrayList<Character> attemptedLetters = new ArrayList<Character>();
		
		//Set that is used to show on the terminal empty with underscores
		
		char[] wordVisualisation = new char[lengthWord*2];
		for (int i = 0; i < lengthWord*2;i+=2){
			wordVisualisation[i] = '_';
			wordVisualisation[i+1]= ' ';
		}
		
		//print the word to terminal
		
		while (guessesLeft == 0 || !wordguessed){
			int countTries = 0;
			int maxTries = 3;
			while (countTries < maxTries){
				
					System.out.println(wordVisualisation);
					System.out.println("You have "+guessesLeft+" guesses left. You've already tried the following letters:");
					System.out.println(attemptedLetters.toString().replace("[","").replace("]",""));
					System.out.println("Enter a letter: ");  
					String inputScanner = sc.next();
					guess = inputScanner.charAt(0);
					if(!Character.isLetter(guess) || inputScanner.length() != 1 ){
						countTries++;
						if(countTries == maxTries){
							System.out.println("You've not entered a letter 3 times, please restart until you behave");
							System.exit(0);
						}
						System.out.println("You've not entered a letter, please try again!");

					} else {
						guess = Character.toLowerCase(guess);
						guessString = String.valueOf(guess);
					 			
			  
						if(chosenWord.contains(guessString)){
							for(int i =0; i < lengthWord;i++){
								if (chosenWordArray[i] == guess){
								wordVisualisation[i*2] = guess;
								//System.out.println(wordVisualisation.toString());
								//System.out.println(wordVisualisation);
									if(!(String.valueOf(wordVisualisation).contains("_"))){
										System.out.println("Congratulations, you've won. The word was "+chosenWord+"!");
										System.exit(0);
									}
								}
							}
						} else {
							attemptedLetters.add(guess);
							guessesLeft--;
							if (guessesLeft == 0){
								System.out.println("You lost your last guess, you've been hanged. The word was " +chosenWord);
								System.exit(0);
							}
						}
				}
			}
		}	
	}
}