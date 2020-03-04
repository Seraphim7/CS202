import java.util.Scanner;

public class Utility
{
	public static String[] splitByDelimeter(String fileLine, String delimiter)
	{
		return fileLine.split(delimiter);
	}
	
	public static String getFilename(Scanner userInput)
	{
		String textFilename;
		
		System.out.print("Enter the filename of the text to spell check: ");
		textFilename =  userInput.nextLine();
		
		return textFilename;
	}
}
