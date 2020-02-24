import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Validate 
{
	private static void printErrorLineNumber(String fileLine, int lineNumber)
	{
		System.out.print("Line ");
		System.out.print(lineNumber);
		System.out.print(" - ");
		System.out.print(fileLine);
		System.out.println();
		System.out.print("\tERROR: ");
	}
	
	public static void main(String[] args)
	{
		try
		{
			Scanner dataFile = null;
			
			if (args.length == 1)
			{
				dataFile = openFile(args[0]);
				
				lineByLineValidator(dataFile);
			}
			else
			{
				System.out.println("ERROR: There should be 1 argument provided!");
			}
		}
		catch (FileNotFoundException error)
		{
			System.err.println("ERROR: File is not found!");
		}
		catch (NullPointerException error)
		{
			System.err.println("ERROR: Null pointer!");
		}
		catch (Exception error)
		{
			System.err.println("Unknown error");
		}
	}
	
	private static Scanner openFile(String filename) throws FileNotFoundException
	{
		File dataFile = new File(filename);
		
		Scanner fileScanner = new Scanner(dataFile);
		
		return fileScanner;
	}
	
	private static boolean lineByLineValidator(Scanner dataFile)
	{
		int lineNumber = 0;
		String fileLine = null;
		String[] parsedFileLine = null;
		boolean valid = true;
		
		while (dataFile.hasNextLine())
		{
			lineNumber++;
			
			fileLine = dataFile.nextLine();
			
			fileLine = frontWhitespaceStripper(fileLine);
			
			if (pipeIsFirstCharacter(fileLine.charAt(0)))
			{
				printErrorLineNumber(fileLine, lineNumber);
				System.out.println("A Pipe cannnot be the non whitespace character on a line!");
			}
			else
			{
				if (!commentOrBlankLine(fileLine))
				{
					parsedFileLine = parseString(fileLine, "\\|"); // You need to do this so that pipe symbol is the delimiter
				
					valid = tokenByTokenValidator(parsedFileLine, lineNumber, fileLine);
				}
			}
		}
		
		return valid;
	}
	
	private static String frontWhitespaceStripper(String fileLine)
	{
		boolean noWhitespaceInFront = false;
		
		for (int i = 0; i < fileLine.length() && !noWhitespaceInFront; i++)
		{
			if (fileLine.charAt(i) == '|')
			{
				noWhitespaceInFront = true;
			}
			else
			{
				if (fileLine.charAt(0) == ' ' || fileLine.charAt(0) == '\t')
				{
					fileLine = fileLine.substring(1, fileLine.length());
				}
			}
		}
		
		return fileLine;
	}

	private static boolean pipeIsFirstCharacter(char firstCharacterOfLine)
	{
		boolean isPipe = false;
		
		if (firstCharacterOfLine == '|')
		{
			isPipe = true;
		}
		
		return isPipe;
	}

	private static boolean commentOrBlankLine(String fileLine) 
	{
		boolean commentOrBlank = false;
	
		if (fileLine.isEmpty() || (fileLine.length() == 1 && !(fileLine.equals(" ") || fileLine.equals("#") || fileLine.equals("/"))))
		{
			commentOrBlank = true;
		}
		else
		{
			if (fileLine.charAt(0) == '#' || (fileLine.charAt(0) == '/' && fileLine.charAt(1) == '/'))
			{
				commentOrBlank = true;
			}
		}
		
		return commentOrBlank;
	}
	
	public static String[] parseString(String stringToParse, String token)
	{
		String[] arrayOfStrings;
		
		arrayOfStrings = stringToParse.split(token);
		
		return arrayOfStrings;
	}

	private static boolean tokenByTokenValidator(String[] parsedFileLine, int lineNumber, String fileLine)
	{
		boolean empty = false;
		int i = 1;
		boolean valid = true;
		String type = null;
		String answerToken = null;
		String correctAnswerToken = null;
		int pipeCount = -1;
		
		for (String token: parsedFileLine)
		{
			empty = emptyFields(token, fileLine, lineNumber);
			
			if (empty)
			{
				valid = false;
			}
			
			if (i == 1 && valid)
			{
				valid = checkType(token, fileLine, lineNumber);
				
				type = token;
			}
			else if (i == 2 && valid)
			{
				valid = validateLevelNumber(token, fileLine, lineNumber);
			}
			else if (i == 4 && valid)
			{
				answerToken = token;
			}
			else if (i == 5 && valid)
			{
				correctAnswerToken = token;
			}
			
			pipeCount++;
			i++;
		}
		
		if (valid)
		{
			valid = questionValidatorBasedOnType(type, answerToken, correctAnswerToken, pipeCount, fileLine, lineNumber);
		}
			
		return valid;
	}
	
	private static boolean emptyFields(String field, String fileLine, int lineNumber)
	{
		boolean empty = false;
		
		if (field.isEmpty())
		{
			printErrorLineNumber(fileLine, lineNumber);
			System.out.println("An empty field is not valid");
			
			empty = true;
		}
		
		return empty;
	}
	
	private static boolean checkType(String token, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		token = token.toUpperCase();
		
		if (!(token.equals("SA") || token.equals("TF") || token.equals("MC") || token.equals("MA")))
		{
			printErrorLineNumber(fileLine, lineNumber);
			System.out.println("Type is invalid. Type must be SA, TF, MC, or MA!");
			
			valid = false;
		}
		
		return valid;
	}
	
	private static boolean validateLevelNumber(String level, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		try
		{			
			if (level.length() != 1)
			{
				printErrorLineNumber(fileLine, lineNumber);
				System.out.println("The level must be 1-9 inclusive!");
				
				valid = false;
			}
			
			if (level.charAt(0) <= '0' && valid)
			{
				printErrorLineNumber(fileLine, lineNumber);
				System.out.println("The level must not be 0!");
				
				valid = false;
			}
		}
		catch (IndexOutOfBoundsException error)
		{
			System.err.print("out of bounds");
			
			valid = false;
		}
		
		return valid;
	}

	private static boolean questionValidatorBasedOnType(String type, String answerToken, String correctAnswerToken, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (type.equals("SA"))
		{
			valid = shortAnswer(answerToken, pipeCount, fileLine, lineNumber);
		}
		else if (type.equals("TF"))
		{
			valid = trueFalse(answerToken, pipeCount, fileLine, lineNumber);
		}
		else
		{
			valid = multipleChoiceAnswer(answerToken, correctAnswerToken, pipeCount, fileLine, lineNumber);
		}
		
		return valid;
	}
	
	public static boolean shortAnswer(String answerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 3)
		{
			printErrorLineNumber(fileLine, lineNumber);
			System.out.println("Pipe count must be 3 for short answer questions!");
			
			valid = false;
		}
		else
		{
			valid = validateTrueShortAnswerLine(answerLine, fileLine, lineNumber);
		}
		
		return valid;
	}

	public static boolean trueFalse(String answerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
			if (pipeCount != 3)
			{
				printErrorLineNumber(fileLine, lineNumber);
				System.out.println("Pipe count must be 3 for true/false questions!");
				
				valid = false;
			}
			else
			{
				valid = validateTrueShortAnswerLine(answerLine, fileLine, lineNumber);
				
				if (valid)
				{
					answerLine.toUpperCase();
				
					if (answerLine.equals("TRUE") || answerLine.equals("FALSE"))
					{
						printErrorLineNumber(fileLine, lineNumber);
						System.out.println("Answer choices for true/false questions must be true or false!");
						
						valid = false;
					}
				}
			}
			
			return valid;
		}
	
	public static boolean multipleChoiceAnswer(String answerLine, String correctAnswerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 4)
		{
			printErrorLineNumber(fileLine, lineNumber);
			System.out.println("Pipe count must be 4 for multiple choice/answer!");
			
			valid = false;
		}
		else
		{
			valid = validateMultipleChoiceAnswerLine(answerLine, fileLine, lineNumber);
			
			if (valid)
			{
				int colonCount = countColons(answerLine);
				
				valid = validateCorrectAnswerLetters(correctAnswerLine, colonCount, fileLine, lineNumber);
			}
		}
		
		return valid;
	}

	private static boolean validateTrueShortAnswerLine(String answerLine, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (answerLine.contains(":"))
		{
			printErrorLineNumber(fileLine, lineNumber);
			System.out.println("SA or TF type questions cannot contain colons!");
			
			valid = false;
		}
		
		return valid;
	}
	
	private static int countColons(String words)
	{
		int numColons = 0;
		
		String[] tokens = words.split("\\:");
		
		numColons = tokens.length;
		
		return numColons;
	}

	private static boolean validateMultipleChoiceAnswerLine(String answerLine, String fileLine, int lineNumber)
	{
		boolean valid;
		
		valid = answerLine.contains(":");
		
		if (!valid)
		{
			printErrorLineNumber(fileLine, lineNumber);
			System.out.println("The multiple choice/answer line must contain at least 1 colon as answer seperators!");
		}
			
		return valid;
	}
	
	private static boolean validateCorrectAnswerLetters(String correctAnswerLetters, int colonCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		for (int i = 0; i < correctAnswerLetters.length() && valid; i++)
		{
			int correctAnswerNumber = correctAnswerLetters.charAt(0) & 0x3f;
			
			if (colonCount < correctAnswerNumber)
			{
				printErrorLineNumber(fileLine, lineNumber);
				System.out.println("There must be a correctAnswer in the bounds of the answer(s)!");
				
				valid = false;
			}
		}
		
		return valid;
	}
	
	// Function to load parts of file
}
