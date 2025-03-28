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
		//print("Starting up");
		ArrayList<Token> FileTokens = new ArrayList<Token>();
		String testFile = "testFile.mp";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
			String line;
		        while ((line = reader.readLine()) != null) {
				
				ArrayList<Token> temp = Tokenizer(line);
				temp.forEach(item -> FileTokens.add(item));
			}
	       	} catch (IOException e) {
			e.printStackTrace();								                        	    
		}
		
		TokenParser(FileTokens);		
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
				int tempIndex = currentIndex;
				while(input.charAt(tempIndex) != ')') {
					if(tempIndex + 1 > input.length()) {
						throw new Exception("No ending )");
					}
					tempIndex++;
				}
				continue;
			
			}
			if(currentChar == ')') {
				Tokens.add(new Token("paren",")"));
				currentIndex++;
				continue;
			}

			if(currentChar == '"') {
				
				Token temp = new Token();
				currentIndex++;
				currentChar = input.charAt(currentIndex);
				temp.Value = "";
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
				temp.Value = "";
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

	private static void TokenParser(ArrayList<Token> Tokens) {
		int currentIndex = 0;
		while(currentIndex < Tokens.size()) {
			String type = Tokens.get(currentIndex).Type;
			String value = Tokens.get(currentIndex).Value;
			print("Parsing: " + type + " " + value);
			if(type == "name") {
				print("here? " + value.trim());
				if(value.trim() == "out") {
					print("name -> out");
					currentIndex++;
					if(Tokens.get(currentIndex).Type == "equal") {
						print("name -> out -> equal");
						currentIndex++;
						if(Tokens.get(currentIndex).Type == "string") {
							print("Here: " + Tokens.get(currentIndex).Value);
						} else {
							currentIndex++;
							continue;
						}
					} else {
						currentIndex++;
						continue;
					}
				} else {
					currentIndex++;
					continue;
				}
			} else {
				print("name != " + type);
				currentIndex++;
				continue;
			}
		}
	}

	// Helpers

	private static void print(String value) {
		System.out.println(value);
	}
}
