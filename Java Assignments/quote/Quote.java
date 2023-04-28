// package nl.sogyo.javaopdrachten.quote;
import java.time.*;
import java.util.*; 
public class Quote {
    String[][] quotes = {
        {"galileo", "eppur si muove"},
        {"archimedes", "eureka!"},
        {"erasmus", "in regione caecorum rex est luscus"},
        {"socrates", "I know nothing except the fact of my ignorance"},
        {"rené descartes", "cogito, ergo sum"},
        {"sir isaac newton", "if I have seen further it is by standing on the shoulders of giants"}
    };

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /*public int objectLength(Quote object){
        int count = 0;
        for (String[] strings : quotes ){
            count++;
        }
        return count;
    }*/

    public void printQuote(int dateDay){
        String[] quoteOfTheDay = quotes[(dateDay-1) % quotes.length];
        String[] names = quoteOfTheDay[0].split(" ");
        String fullName = "";
        String[] firstChar = new String[names.length];
        for (int i = 0; i < names.length;i++){
            firstChar[i] = names[i].substring(0, 1).toUpperCase();
            names[i] = String.join("",firstChar[i],names[i].substring(1));
            fullName = String.join(" ",fullName,names[i]);
            
            }
        
        String quote = quoteOfTheDay[1];
        char quoteLastChar = quote.charAt(quote.length()-1);
        if (quoteLastChar == '!' || quoteLastChar == '.' || quoteLastChar == '?'){
            System.out.println("\""+quote+"\""+" -- "+ fullName);
        } else {
            System.out.println(ANSI_GREEN+"\""+quote+".\""+ANSI_RESET+" -- "+ fullName);
        }
    }

    
    public static void main(String... args) {
        //TODO
        //Get local date
        LocalDate today = LocalDate.now();
        //get day
        int dateDay = Integer.valueOf(today.toString().substring(today.toString().length() -2));
        //get month name, given as all caps, so convert everything from second letter to lowercase
        Month month = Month.from(today);
        String monthName = month.toString().substring(0,1)+month.toString().substring(1).toLowerCase();
        //print the message
        System.out.println("Quote "+ANSI_PURPLE+ "for"+ ANSI_RESET+" Monday the "+dateDay+"th of "+monthName+":");

        Quote quotesObject = new Quote();
        
        quotesObject.printQuote(dateDay);


    }
}
