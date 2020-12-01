package com.company;

import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
		// write your code here
	    Scanner in = new Scanner(System.in);
	    
	    while (true) {
	    	String input = in.nextLine();

	    	if (input.equals("exit"))
	    		break;

		    long i = 0;
		    try {
			    i = Expression.parseEquation(input, true);
		    } catch (Exception e) {
			    System.err.println(e.getMessage());
		    }
			
		    System.out.println("= " + Number.integerToWordString(i));
	    }
    }
}
