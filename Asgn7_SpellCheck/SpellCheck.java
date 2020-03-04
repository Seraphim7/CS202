
import java.util.LinkedHashSet;
import java.util.Scanner;

public class SpellCheck
{
	LinkedHashSet<String> dictionary;
	LinkedHashSet<String> textToSpellCheck;
	
	SpellCheck()
	{
		dictionary = new LinkedHashSet<String>();
		textToSpellCheck = new LinkedHashSet<String>();
	}
	
	public void deliverSpellChecker(String[] textParsedFileline, String[] dictionaryParsedFileline)
	{
		boolean wordExistsInDictionary = true;
		
		read
		
		for (String token : dictionaryParsedFileline)
		{
			token = token.toLowerCase();
			
			setDictionaryByToken(token);
		}
		
		System.out.print(dictionary.size());
		
		for (String token : textParsedFileline)
		{
			wordExistsInDictionary = checkTextWordAgainstDictionary(token);
			
			if (wordExistsInDictionary)
			{
				setTextByToken(token);
			}
			else
			{
				System.out.println(token);
			}
		}
	}
	
	public static String[] readFile(String filename, Scanner fileInput)
	{
		String fileLine;
		String[] parsedFileline = null;
		
		while (fileInput.hasNextLine())
		{
			fileLine = fileInput.nextLine();
			
			parsedFileline = Utility.splitByDelimeter(fileLine, " ");
			
			for (String token : parsedFileline)
			{
				
			}
		}
		
		return parsedFile;
	}
	
	public void setDictionaryByToken(String token)
	{
		dictionary.add(token);
	}
	
	public void setTextByToken(String token)
	{
		textToSpellCheck.add(token);
	}
	
	public boolean checkTextWordAgainstDictionary(String textToken)
	{
		textToken = textToken.toLowerCase();
		
		return dictionary.contains(textToken);
	}
}
