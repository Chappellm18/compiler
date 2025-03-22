import java.io.*;
import java.util.ArrayList;
import Token.java;

public class Compiler {
	
	// Define the accepted chars
	public static final String LETTERS = "abc";
	public static final String WHITESPACE = " ";
	public static void main(String []args) {
		System.out.println("Hello");
	}

	public static void Tokenizer(String input) {
		ArrayList<Token> Tokens = new ArrayList<Token>();
		int currentIndex = 0;

		while(currentIndex < input.Length) {
			char currentChar = input.charAt(currentIndex);

			// Define rules here
			if(currentChar == '(') {
				Tokens.add(new Token("paren","("));		
				currentIndex++;
				continue;
			}

			if(LETTERS.contains(String.valueOf(currentChar))) {
				Token temp = new Token();
				while(LETTERS.contains(String.valueOf(currentChar))) {
					temp.Value += String.valueOf(currentChar);
					currentChar = input.charAt(++currentIndex);
				}
				temp.Type = "string";
				Tokens.add(temp);
				continue;
			}

			if(WHITESPACE.contains(String.valueOf(currentChar))) {
				currentIndex++;
				continue;
			}				
			throw new Exception("Unknown character " + currentChar);
		}
	}
}
