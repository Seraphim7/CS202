/**
* SpellCheck.java
* Spell check program
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/9/2020
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class SpellCheck
{
	LinkedHashSet<String> dictionary;
	LinkedHashSet<String> textToSpellCheck;
	TreeMap<String, Integer> misspelledWordCountContainer; // Container of misspelled words with the number of times of occurrence
	
	//------------------------------------------------------------------------
	SpellCheck()
	{
		dictionary = new LinkedHashSet<String>();
		textToSpellCheck = new LinkedHashSet<String>();
		misspelledWordCountContainer = new TreeMap<String, Integer>();
	}
	
	//------------------------------------------------------------------------
	/**
	 * Controller of the program
	 * @param args, arguments to the program
	 */
	public static void main(String[] args)
	{
		String textFilename = null;
		String dictionaryFilename = null;
		Scanner userInput = new Scanner(System.in);
		SpellCheck spellCheck = new SpellCheck();
		
		try
		{
			if (args.length > 2)
			{
				System.err.println("Must provide 0-2 arguments!");
			}
			else
			{
				if (args.length == 0 || args.length == 1)
				{
					System.out.print("Enter the filename of the text to spell check: ");
					textFilename =  userInput.nextLine();
					
					dictionaryFilename = "words.txt";
				}
				
				File textFile = new File(textFilename);
				File dictionaryFile = new File(dictionaryFilename);
				
				Scanner fileInput = new Scanner(textFile);
				Scanner dictionaryFileInput = new Scanner(dictionaryFile);
			
				spellCheck.setFile(dictionaryFileInput, 1);
				spellCheck.setFile(fileInput, 2);
				
				spellCheck.printMisspelledWordsAlphabetically();
				
				userInput.close();
				fileInput.close();
				dictionaryFileInput.close();
			}
		}
		catch (FileNotFoundException error)
		{
			System.err.println(error.getMessage());
		}
	}

	//------------------------------------------------------------------------
	private void setFile(Scanner fileInput, int i)
	{
		int wordPlaceInContainer = 0;
		String fileline;
		String[] parsedFileline = null;

		while (fileInput.hasNextLine())
		{
			fileline = fileInput.nextLine();
			
			parsedFileline = fileline.split(" ");
			
			for (String token : parsedFileline)
			{
				token = token.toLowerCase();
				
				if (i == 1)
				{
					dictionary.add(token);
				}
				else
				{
					token = invalidCharacterStripper(token);
					token = token.stripLeading();
					token = token.stripTrailing();
					
					textToSpellCheck.add(token);
					
					if (!dictionary.contains(token))
					{
						textToSpellCheck.remove(token);
						
						if (!misspelledWordCountContainer.containsKey(token))
						{
							misspelledWordCountContainer.put(token, 1);
						}
						else
						{
							wordPlaceInContainer = misspelledWordCountContainer.get(token);
							misspelledWordCountContainer.put(token, wordPlaceInContainer + 1);
						}
					}
				}
			}
		}
	}
	
	//------------------------------------------------------------------------
	private String invalidCharacterStripper(String token)
	{
		char charAtTokenIndex = ' ';
		
		for (int i = 0; i < token.length(); i++)
		{
			charAtTokenIndex = token.charAt(i);
			
			if (charAtTokenIndex < 'A' || charAtTokenIndex > 'z' || (charAtTokenIndex > 'Z' && charAtTokenIndex < 'a'))
			{
				token = token.replace(charAtTokenIndex, ' ');
			}
		}
		
		return token;
	}
	
	//------------------------------------------------------------------------
	private void printMisspelledWordsAlphabetically()
	{
		String entryKey;
		Integer entryValue;
		
		System.out.println();
		
		for (Entry<String, Integer> misspelledWord : misspelledWordCountContainer.entrySet())
		{
			entryKey = misspelledWord.getKey();
			entryValue = misspelledWord.getValue();
			
			System.out.print(entryKey);
			System.out.print(" (");
			System.out.print(entryValue);
			System.out.println(")");
		}
	}
}
