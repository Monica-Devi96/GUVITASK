package task4;
import java.util.Scanner;

public class Prime5 {

	public static void main(String[] args) {
		int num,count=0;
		System.out.println("Enter a number to check it is prime");
		Scanner sc=new Scanner(System.in);
		num=sc.nextInt();
		if(num<=1) {
	    System.out.println(num+"is not a prime number");
		}
		else {
		for(int k=2;k<num;k++) {
			if(num%k==0) {
			    count++;
				break;
			}}
		    }
			if(count==0) {
				System.out.println(num+" is a prime number ");
			}
			else {
				System.out.println(num+" is not a prime number ");
			}

	}

}
