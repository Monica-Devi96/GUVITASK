package task4;
import java.util.Scanner;

public class Seniorcitizen9 {

	public static void main(String[] args) {
		int age;
		// TODO Auto-generated method stub
		System.out.println("Enter the age to check for senior citizen");
		Scanner sc=new Scanner(System.in);
		age=sc.nextInt();
		if(age>=60) {
			System.out.println("You are a senior citizen");
		}
		else {
			System.out.println("You are not a senior citizen");
		}
	}

}
