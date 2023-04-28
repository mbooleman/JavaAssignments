import java.util.*;  
public class lists { 
	
	public static void main(String[] args) {
		// draw random numbers using random object

		Random random = new Random();
		int[] randomValues = new int[10];
		for (int i = 0; i < 10; i++){
			randomValues[i] = random.nextInt(101);
		}
		System.out.println(Arrays.toString(randomValues));
		/*
		Find the maximum value of the randomly generated numbers, 
		initiated with the first. so we can reduce the loop by one iteration
		*/
		int maxValue = randomValues[0];
		for (int i = 1; i < 10; i++){
			if (maxValue < randomValues[i]){
				maxValue = randomValues[i];
			}
		}
		System.out.println("The maximum value is "+maxValue);
		/* 
		find the 2 minimum values, 
		the second minValues is the lowest value of the numbers
		*/
		int[] minValues = new int[2];

		if(randomValues[0]<randomValues[1]){
			minValues[1] = randomValues[0];
			minValues[0] = randomValues[1];
		} else {
			minValues[1] = randomValues[1];
			minValues[0] = randomValues[0];
		}

		for (int i = 2; i < 10; i++){
			if (randomValues[i] < minValues[1] && randomValues[i] < minValues[0]){
				minValues[0] = minValues[1];
				minValues[1] = randomValues[i];
			} else if (randomValues[i] < minValues[0]){
				minValues[0] = randomValues[i];
			}
		}
		if (minValues[0] == minValues[1]){
			System.out.println("There are two instances of the lowest number, it is "+minValues[0]);
		} else {
			System.out.println("The lowest number is "+ minValues[1]+" and the second lowest number is "+ minValues[0]);
		}

		//find only even numbers and display

		ArrayList<Integer> evenNumbers = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++){
			if(randomValues[i] % 2 == 0){
				evenNumbers.add(randomValues[i]);
			}
		}
		System.out.println("The even numbers are: \n"+evenNumbers.toString());

		//Splitting lists to divisible by 2, 3, 5 or none of these.
		ArrayList<Integer> div2Numbers = new ArrayList<Integer>();
		ArrayList<Integer> div3Numbers = new ArrayList<Integer>();
		ArrayList<Integer> div5Numbers = new ArrayList<Integer>();
		ArrayList<Integer> restNumbers = new ArrayList<Integer>();

		for (int i = 0; i < 10 ; i++){
			if(randomValues[i] % 2 == 0){
				div2Numbers.add(randomValues[i]);
			} else if (randomValues[i] % 3 == 0){
				div3Numbers.add(randomValues[i]);
			} else if (randomValues[i] % 5 == 0){
				div5Numbers.add(randomValues[i]);
			} else {
				restNumbers.add(randomValues[i]);
			}
		}
		System.out.println("The numbers divisible by 2 are: \n"+div2Numbers.toString());
		System.out.println("The numbers divisible by 3 are: \n"+div3Numbers.toString());
		System.out.println("The numbers divisible by 5 are: \n"+div5Numbers.toString());
		System.out.println("The other numbers are: \n"+evenNumbers.toString());

		//implementing the bubble sort algorithm
		int[] sortedValues = randomValues;
		

		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10-i-1;j++){
				if (sortedValues[j] > sortedValues[j+1]){
					int temp = sortedValues[j];
					//int tempB = sortedValues[j+1];
					sortedValues[j] = sortedValues[j+1];
					sortedValues[j+1] = temp;
				}
			}
		}
		System.out.println("The sorted array is \n"+Arrays.toString(sortedValues));
	}

}