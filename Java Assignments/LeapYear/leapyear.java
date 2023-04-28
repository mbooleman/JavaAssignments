import java.util.*;  
public class leapyear{
	public static void main(String[] args){
		try {
		Scanner sc = new Scanner(System.in);    //System.in is a standard input stream  
		System.out.print("Enter year: ");  
		
		int year = sc.nextInt();  
		/*
		if (year.isNaN()){
			System.out.println("You have not entered a number, please try again");
		}
		*/
		if (year % 4 == 0 && year % 400 == 0){
			System.out.println("The year " +year+" is a leap year!");
		} else if (year % 100 == 0){
			System.out.println("The year " +year+" is not a leap year, but it is divisible by 4");
		} else if (year % 4 == 0){
			System.out.println("The year " +year+" is a leap year!");
		} else {
			System.out.println("The year " +year+" is not a leap year.");
		}
		} catch (InputMismatchException e){
			System.out.println("You've not entered a number, please try again!");
		}	
	}
}