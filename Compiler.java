import Tokens.Token;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Compiler {
	
	// Define the accepted chars
	public static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final String WHITESPACE = " ";
	
	public static void main(String []args) throws Exception {
		System.out.println("Starting up");
		ArrayList<Token> FileTokens = new ArrayList<Token>();
		String testFile = "testFile.mp";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
			String line;
		        while ((line = reader.readLine()) != null) {
				System.out.println(line);
				ArrayList<Token> temp = Tokenizer(line);
				temp.forEach(item -> FileTokens.add(item));
			}
	       	} catch (IOException e) {							                                       e.printStackTrace();								                        }
		
		FileTokens.forEach(item -> System.out.println("Type: " + item.Type + "Value: " + item.Value));		
	}

	public static ArrayList<Token> Tokenizer(String input) throws Exception {
		ArrayList<Token> Tokens = new ArrayList<Token>();
		int currentIndex = 0;

		while(currentIndex < input.length()) {
			char currentChar = input.charAt(currentIndex);
			// Define rules here
			if(currentChar == '(') {
				Tokens.add(new Token("paren","("));		
				currentIndex++;
				continue;
			
			}
			if(currentChar == ')') {
				Tokens.add(new Token("paren",")"));
				currentIndex++;
				continue;
			}

			if(currentChar == '"') {
				print("entering string");
				Token temp = new Token();
				currentIndex++;
				currentChar = input.charAt(currentIndex);
				while(currentChar != '"') {
					print("" + currentChar);
					temp.Value += String.valueOf(currentChar);
					currentChar = input.charAt(++currentIndex);
				}
				temp.Type = "string";
				Tokens.add(temp);
				currentIndex++; // Move past the closing
				continue;
			}

			if(currentChar == '=') {
				Tokens.add(new Token("equal", "="));
				currentIndex++;
				continue;
			}

			if(LETTERS.contains(String.valueOf(currentChar))) {
				Token temp = new Token();
				while(LETTERS.contains(String.valueOf(currentChar))) {
					temp.Value += String.valueOf(currentChar);
					currentChar = input.charAt(++currentIndex);
				}
				temp.Type = "name";
				Tokens.add(temp);
				continue;
			}

			if(WHITESPACE.contains(String.valueOf(currentChar))) {
				currentIndex++;
				continue;
			}				
			throw new Exception("Unknown character " + currentChar);
		}
		return Tokens;
	}

	private static void print(String value) {
		System.out.println(value);
	}
}
