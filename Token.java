package Tokens;

public class Token {
	public String Type;
	public String Value;

	public Token() { 
		this.Value = "";
		this.Type = "";
	}
	public Token(String type, String value) {
		this.Type = type;
		this.Value = value;
	}
}
