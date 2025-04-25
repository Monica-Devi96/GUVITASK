package task4;

import java.util.Scanner;

public class Swapping4 {
            public static void main(String[] args) {
            	int e,f,swap;
            	System.out.println("Enter 2 digits to swap");
        		Scanner sc=new Scanner(System.in);
        		e=sc.nextInt();
        		f=sc.nextInt();
        		swap=e;
        		e=f;
        		f=swap;
        		System.out.println(e+" "+f);
            }
}
