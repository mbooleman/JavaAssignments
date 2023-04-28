import java.util.*;

public class ExceptionalUsers{
	
	public static boolean validatePassword(String password){
		char[] characters = password.toCharArray();
		boolean hasCapital = false;
		boolean hasDigit = false;
		boolean hasLowerCase = false;
		boolean valid = false;
		for (int i=0;i < characters.length;i++){
			if(Character.isUpperCase(characters[i])){
				hasCapital = true;
			} else if (Character.isLowerCase(characters[i])){
				hasLowerCase = true;
			} else if (Character.isDigit(characters[i])){
				hasDigit = true;
			}
		}
		valid = (hasCapital && hasDigit && hasLowerCase);
		return valid;
	}

	public static boolean registerUser(String[] user){
		boolean validPassword = validatePassword(user[1]);
		if (validPassword){
			System.out.println("Registered user "+user[0]+".");
		}
		
		return validPassword;
	}

	public static void main(String[] args){
		String[] user = new String[2];
		Boolean validPassword = false;
		System.out.println("Enter a username");
	    Scanner scan = new Scanner(System.in);
	    user[0] = scan.nextLine();
		while(!validPassword){
			try {
				/*System.out.println("Enter a username");
	            Scanner scan = new Scanner(System.in);
	            user[0] = scan.nextLine();*/
	            System.out.println("Enter a password");
				user[1] = scan.nextLine();
				validPassword = registerUser(user);
				if(!validPassword){
					throw new IncorrectPasswordException("This is not a valid password. Please enter a stronger password.");
				}

	          } catch (IncorrectPasswordException e) {
	               System.out.println(e.getMessage());
	               // e.printStackTrace();
	          }
      }

	}
}

class IncorrectPasswordException extends Exception{
	public IncorrectPasswordException(String errorMessage){
		super(errorMessage);
	}
}