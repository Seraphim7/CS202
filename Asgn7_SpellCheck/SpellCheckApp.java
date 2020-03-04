import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpellCheckApp
{
	public static void main(String[] args) throws FileNotFoundException
	{
		try
		{
			String textFilename = null;
			String dictionaryFilename = null;
			Scanner userInput = new Scanner(System.in);
			String[] parsedTextFileline = null;
			String[] parsedDictionaryFileline = null;
			SpellCheck checker = new SpellCheck();
			
			if (args.length > 2)
			{
				System.err.println("Must provide 0-2 arguments!");
			}
			else
			{
				if (args.length == 0 || args.length == 1)
				{
					textFilename = Utility.getFilename(userInput);
					dictionaryFilename = "words.txt";
				}
				
				File file = new File(textFilename);
				Scanner fileInput = new Scanner(file);
				
				parsedTextFileline = Utility.readFile(textFilename, fileInput);
				
				file = new File(dictionaryFilename);
				fileInput = new Scanner(file);
				
				parsedDictionaryFileline = Utility.readFile(dictionaryFilename, fileInput);
				
				fileInput.close();
				
				checker.deliverSpellChecker(parsedTextFileline, parsedDictionaryFileline);
			}
		}
		catch (FileNotFoundException error)
		{
			System.err.println("File is not found");
			System.err.println(error.getMessage());
		}
	}
}
