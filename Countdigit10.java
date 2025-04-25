package task4;
import java.util.Scanner;

public class Countdigit10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,digit=0;
		System.out.println("Enter a number to find length");
		Scanner sc=new Scanner(System.in);
		i=sc.nextInt();
		if(i==0)
		{System.out.println("The no of digit is 1");}
		while(i!=0) {
			i=i/10;
			++digit;
			
		}
		System.out.println("The no of digit is "+digit);
		
	}

}
