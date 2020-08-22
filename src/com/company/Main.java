package com.company;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
	
	static Scanner in;
	
	enum Op {
		ADD, SUB, MUL, DIV, MOD
	}
	
	static long parseInteger(String str) throws Exception{
		boolean negative = false;
		String[] words = str.split(" ");
		NumNode prev_node = null, new_node = null;
		
		int i = 0;
		while (i < words.length){
			String word = words[i];
			word = word.toLowerCase();
			
			switch (word){
				case "negative":
					negative = true;
					break;
				case "zero":
					if (words.length > 1) throw new Exception("ERROR PARSING NUMBER: zero not used alone");
				case "one":
					new_node = new NumNode(1,null, prev_node); break;
				case "two":
					new_node = new NumNode(2,null, prev_node); break;
				case "three":
					new_node = new NumNode(3,null, prev_node); break;
				case "four":
					new_node = new NumNode(4,null, prev_node); break;
				case "five":
					new_node = new NumNode(5,null, prev_node); break;
				case "six":
					new_node = new NumNode(6,null, prev_node); break;
				case "seven":
					new_node = new NumNode(7,null, prev_node); break;
				case "eight":
					new_node = new NumNode(8,null, prev_node); break;
				case "nine":
					new_node = new NumNode(9,null, prev_node); break;
				case "ten":
					new_node = new NumNode(10,null, prev_node); break;
				case "eleven":
					new_node = new NumNode(11,null, prev_node); break;
				case "twelve":
					new_node = new NumNode(12,null, prev_node); break;
				case "thirteen":
					new_node = new NumNode(13,null, prev_node); break;
				case "fourteen":
					new_node = new NumNode(14,null, prev_node); break;
				case "fifteen":
					new_node = new NumNode(15,null, prev_node); break;
				case "sixteen":
					new_node = new NumNode(16,null, prev_node); break;
				case "seventeen":
					new_node = new NumNode(17,null, prev_node); break;
				case "eighteen":
					new_node = new NumNode(18,null, prev_node); break;
				case "nineteen":
					new_node = new NumNode(19,null, prev_node); break;
				case "twenty":
					new_node = new NumNode(20,null, prev_node); break;
				case "thirty":
					new_node = new NumNode(30,null, prev_node); break;
				case "forty":
					new_node = new NumNode(40,null, prev_node); break;
				case "fifty":
					new_node = new NumNode(50,null, prev_node); break;
				case "sixty":
					new_node = new NumNode(60,null, prev_node); break;
				case "seventy":
					new_node = new NumNode(70,null, prev_node); break;
				case "eighty":
					new_node = new NumNode(80,null, prev_node); break;
				case "ninety":
					new_node = new NumNode(90,null, prev_node); break;
				case "hundred":
					new_node = new NumNode(100,null, prev_node); break;
				case "thousand":
					new_node = new NumNode(1000,null, prev_node); break;
				case "million":
					new_node = new NumNode(1000000,null, prev_node); break;
				case "billion":
					new_node = new NumNode(1000000000,null, prev_node); break;
				case "trillion":
					new_node = new NumNode(1000000000000L,null, prev_node); break;
				case "quadrillion":
					new_node = new NumNode(1000000000000000L,null, prev_node); break;
				default:
					throw new Exception("ERROR PARSING NUMBER WORD: " + word);
			}
			if (prev_node != null) prev_node.next = new_node;
			prev_node = new_node;
			i += 1;
		}
		if (prev_node == null) throw new Exception("Unknown number parsing error");
		
		NumNode root = prev_node;
		while (root.prev != null) root = root.prev;
		
		return root.computeValue() * (negative ? -1 : 1);
	}

	static String integerToFormattedString(long val){
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(val);
	}
	
	static String integerToWordString(long val) {
		if (val == 0) return "zero";
		boolean negative = (val < 0);
		
		String words = "";
		int mod = 0;
		while (val > 0){
			int x = (int)(val % 1000);
			String str = "";
			
			// hundreds
			int h = x / 100;
			if (h > 0){
				switch (h){
					case 1: str += "one"; break;
					case 2: str += "two"; break;
					case 3: str += "three"; break;
					case 4: str += "four"; break;
					case 5: str += "five"; break;
					case 6: str += "six"; break;
					case 7: str += "seven"; break;
					case 8: str += "eight"; break;
					case 9: str += "nine"; break;
				}
				str += " hundred";
			}
			
			// tens
			int t = (x % 100) / 10;
			if (t > 0){
				if (str.length() > 0) str += " ";
				switch (t){
					case 1: break;
					case 2: str += "twenty"; break;
					case 3: str += "thirty"; break;
					case 4: str += "forty"; break;
					case 5: str += "fifty"; break;
					case 6: str += "sixty"; break;
					case 7: str += "seventy"; break;
					case 8: str += "eighty"; break;
					case 9: str += "ninety"; break;
				}
			}
			
			// ones
			int o = (x % 10);
			if (str.length() > 0 && t != 1) str += " ";
			switch (o){
				case 0: if (t == 1) str += "ten"; break;
				case 1:
					if (t == 1) str += "eleven";
					else str += "one";
					break;
				case 2:
					if (t == 1) str += "twelve";
					else str += "two";
					break;
				case 3:
					if (t == 1) str += "thirteen";
					else str += "three";
					break;
				case 4:
					if (t == 1) str += "fourteen";
					else str += "four";
					break;
				case 5:
					if (t == 1) str += "fifteen";
					else str += "five";
					break;
				case 6:
					if (t == 1) str += "sixteen";
					else str += "six";
					break;
				case 7:
					if (t == 1) str += "seventeen";
					else str += "seven";
					break;
				case 8:
					if (t == 1) str += "eighteen";
					else str += "eight";
					break;
				case 9:
					if (t == 1) str += "nineteen";
					else str += "nine";
					break;
			}
			
			// do we add a modifier?
			switch (mod){
				case 1: str += " thousand"; break;
				case 2: str += " million"; break;
				case 3: str += " billion"; break;
				case 4: str += " trillion"; break;
				case 5: str += " quadrillion"; break;
			}
			mod += 1;
			
			if (words.length() > 0) words = str + " " + words;
			else words = str + words;
			val /= 1000;
		}
		
		if (negative) words = "negative " + words;
		return words;
	}
	
	static long parseEquation(String str, boolean print) throws Exception {
		String[] ops;
		
		if (str.contains(" plus ")){
			ops = str.split(" plus ");
			if (ops.length != 2) throw new Exception("Invalid Equation Format");
			long op1 = parseInteger(ops[0]);
			long op2 = parseInteger(ops[1]);
			long out = op1 + op2;
			if (print) System.out.println("" + integerToFormattedString(op1)
					+ " + " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
			return out;
		}
		else if (str.contains(" minus ")){
			ops = str.split(" minus ");
			if (ops.length != 2) throw new Exception("Invalid Equation Format");
			long op1 = parseInteger(ops[0]);
			long op2 = parseInteger(ops[1]);
			long out = op1 - op2;
			if (print) System.out.println("" + integerToFormattedString(op1)
					+ " - " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
			return out;
		}
		else if (str.contains(" times ")){
			ops = str.split(" times ");
			if (ops.length != 2) throw new Exception("Invalid Equation Format");
			long op1 = parseInteger(ops[0]);
			long op2 = parseInteger(ops[1]);
			long out = op1 * op2;
			if (print) System.out.println("" + integerToFormattedString(op1)
					+ " * " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
			return out;
		}
		else if (str.contains(" divided by ")){
			ops = str.split(" divided by ");
			if (ops.length != 2) throw new Exception("Invalid Equation Format");
			long op1 = parseInteger(ops[0]);
			long op2 = parseInteger(ops[1]);
			long out = op1 / op2;
			if (print) System.out.println("" + integerToFormattedString(op1)
					+ " / " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
			return out;
		
		}
		else if (str.contains(" modulus ")){
			ops = str.split(" modulus ");
			if (ops.length != 2) throw new Exception("Invalid Equation Format");
			long op1 = parseInteger(ops[0]);
			long op2 = parseInteger(ops[1]);
			long out = op1 % op2;
			if (print) System.out.println("" + integerToFormattedString(op1)
					+ " % " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
			return out;
		}
		else {
			long op1 = parseInteger(str);
			if (print) System.out.println("= " + integerToFormattedString(op1));
			return  op1;
		}
	}
	
    public static void main(String[] args) {
		// write your code here
	    in = new Scanner(System.in);
	    
	    while (true) {
		    long i = 0;
		    try {
			    i = parseEquation(in.nextLine(), true);
		    } catch (Exception e) {
			    System.err.println(e.getMessage());
		    }
			
		    System.out.println("= " + integerToWordString(i));
	    }
    }
}
