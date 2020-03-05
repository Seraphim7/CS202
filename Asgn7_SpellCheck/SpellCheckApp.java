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
				
				File textFile = new File(textFilename);
				Scanner textFileInput = new Scanner(textFile);
				
				File dictionaryFile = new File(dictionaryFilename);
				Scanner dictionaryFileInput = new Scanner(dictionaryFile);
				
				checker.deliverSpellChecker(textFileInput, dictionaryFileInput);
				
				textFileInput.close();
				dictionaryFileInput.close();
			}
		}
		catch (FileNotFoundException error)
		{
			System.err.println(error.getMessage());
		}
	}
}
