
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.TreeMap;

public class SpellCheck
{
	LinkedHashSet<String> dictionary;
	LinkedHashSet<String> textToSpellCheck;
	TreeMap<String, Integer> misspelledWordCountContainer;
	
	SpellCheck()
	{
		dictionary = new LinkedHashSet<String>();
		textToSpellCheck = new LinkedHashSet<String>();
		misspelledWordCountContainer = new TreeMap<String, Integer>();
	}
	
	public void deliverSpellChecker(Scanner textFileInput, Scanner dictionaryFileInput)
	{
		setDictionary(dictionaryFileInput);
		setText(textFileInput);
	}

	public void setDictionary(Scanner dictionaryFileInput)
	{
		String dictionaryFileline;
		String[] dictionaryParsedFileline = null;

		while (dictionaryFileInput.hasNextLine())
		{
			dictionaryFileline = dictionaryFileInput.nextLine();
			
			dictionaryParsedFileline = Utility.splitByDelimeter(dictionaryFileline, " ");
			
			for (String token : dictionaryParsedFileline)
			{
				token = token.toLowerCase();
				token = invalidCharacterStripper(token);
				
				dictionary.add(token);
			}
		}
	}
	
	public void setText(Scanner textFileInput)
	{
		String textFileline;
		String[] textParsedFileline = null;
		Integer i = 0;
		
		while (textFileInput.hasNextLine())
		{
			textFileline = textFileInput.nextLine();
			
			textParsedFileline = Utility.splitByDelimeter(textFileline, " ");
			
			for (String token : textParsedFileline)
			{
				token = token.toLowerCase();
				token = invalidCharacterStripper(token);
				
				if (checkTextWordAgainstDictionary(token))
				{
					textToSpellCheck.add(token);
				}
				else
				{	
					if (misspelledWordCountContainer.containsKey(token))
					{
						i = misspelledWordCountContainer.get(token);
						misspelledWordCountContainer.remove(token);
					}
					
					misspelledWordCountContainer.put(token, i + 1);
				}
			}
		}
	}

	public boolean checkTextWordAgainstDictionary(String textToken)
	{
		textToken = textToken.toLowerCase();
		
		return dictionary.contains(textToken);
	}
	
	public String invalidCharacterStripper(String token)
	{
		char charAtTokenIndex;
		
		for (int i = 0; i < token.length(); i++)
		{
			charAtTokenIndex = token.charAt(i);
			
			if (charAtTokenIndex < 'A' || charAtTokenIndex > 'z')
			{
				token = token.replace(charAtTokenIndex, ' ');
			}
		}
		
		return token;
	}
}
