import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Validate 
{
	public static void main(String[] args)
	{
		try
		{
			Scanner dataFile = null;
			boolean valid = false;
			
			if (args.length != 1)
			{
				System.err.println("ERROR Line 16: Only 1 argument must be provided!");
			}
			else
			{
				dataFile = openFile(args[0]);
				
				valid = lineByLineValidator(dataFile);
			}
		}
		catch (FileNotFoundException error)
		{
			System.err.println("ERROR Line 27: File is not found");
		}
		catch (NullPointerException error)
		{
			System.err.println("ERORR Line 31: Null pointer");
		}
	}
	
	private static boolean lineByLineValidator(Scanner dataFile)
	{
		String fileLine = null;
		String[] parsedFileLine = null;
		boolean valid = true;
		
		while (dataFile.hasNextLine())
		{
			fileLine = dataFile.nextLine();
			
			parsedFileLine = parseString(fileLine, "\\|"); // You need to do this so that pipe symbol is the delimiter
			
			valid = tokenByTokenValidator(parsedFileLine);
		}
		
		return valid;
	}
	
	private static boolean tokenByTokenValidator(String[] parsedFileLine)
	{
		boolean empty = false;
		int i = 1;
		boolean valid = true;
		String type = null;
		String answerToken = null;
		String correctAnswerToken = null;
		int pipeCount = 0;
		
		for (String token: parsedFileLine)
		{
			empty = emptyFields(token);
			
			if (empty)
			{
				valid = false;
			}
			else
			{
				valid = true;
			}
			
			if (i == 1 && valid)
			{
				valid = checkType(token);
				
				type = token;
			}
			else if (i == 2 && valid)
			{
				valid = validateLevelNumber(token);
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
			valid = questionValidatorBasedOnType(type, answerToken, correctAnswerToken, pipeCount);
		}
			
		return valid;
	}

	private static boolean questionValidatorBasedOnType(String type, String answerToken, String correctAnswerToken, int pipeCount)
	{
		boolean valid = true;
		
		if (type == "SA")
		{
			valid = shortAnswer(answerToken, pipeCount);
		}
		else if (type == "TF")
		{
			valid = trueFalse(answerToken, pipeCount);
		}
		else
		{
			valid = multipleChoiceAnswer(answerToken, correctAnswerToken, pipeCount);
		}
		
		return valid;
	}

	private static boolean checkType(String token)
	{
		boolean valid = true;
		
		token = token.toUpperCase();
		
		if (!(token.equals("SA") || token.equals("TF") || token.equals("MC") || token.equals("MA")))
		{
			System.out.println(" ERROR Line 123: Type is invalid. Type must be SA, TF, MC, or MA!");
			
			valid = false;
		}
		
		return valid;
	}

	private static Scanner openFile(String filename) throws FileNotFoundException
	{
		File dataFile = new File(filename);
		
		Scanner fileScanner = new Scanner(dataFile);
		
		return fileScanner;
	}
	
	// Function to load parts of file
	
	public static String[] parseString(String stringToParse, String token)
	{
		String[] arrayOfStrings;
		
		arrayOfStrings = stringToParse.split(token);
		
		return arrayOfStrings;
	}
	
	public static boolean shortAnswer(String answerLine, int pipeCount)
	{
		boolean valid = true;
		
		if (pipeCount != 3)
		{
			System.out.println("ERROR Line 155: Pipe count must be 3 for short answer questions!");
			
			valid = false;
		}
		else
		{
			valid = validateTrueShortAnswerLine(answerLine);
		}
		
		return valid;
	}

	public static boolean trueFalse(String answerLine, int pipeCount)
	{
		boolean valid = true;
		
			if (pipeCount != 3)
			{
				System.out.println("ERROR Line 173: Pipe count must be 3 for true/false questions!");
				
				valid = false;
			}
			else
			{
				valid = validateTrueShortAnswerLine(answerLine);
				
				if (valid)
				{
					answerLine.toUpperCase();
				
					if (answerLine != "TRUE" && answerLine != "FALSE")
					{
						System.out.println("ERROR Line 187: Answer choices for true/false questions must be true or false!");
						
						valid = false;
					}
				}
			}
			
			return valid;
		}

	private static boolean validateTrueShortAnswerLine(String answerLine)
	{
		boolean valid = true;
		
		if (answerLine.contains(":"))
		{
			valid = false;
		}
		
		return valid;
	}
	
	public static boolean multipleChoiceAnswer(String answerLine, String correctAnswerLine, int pipeCount)
	{
		boolean valid = true;
		
		System.out.print(pipeCount);
		
		if (pipeCount != 4)
		{
			System.out.println("ERROR Line 215: Pipe count must be 4 for multiple choice/answer!");
			
			valid = false;
		}
		else
		{
			valid = validateMultipleChoiceAnswerLine(answerLine);
			
			if (valid)
			{
				int colonCount = countColons(answerLine);
				
				valid = validateCorrectAnswerLetters(correctAnswerLine, colonCount);
			}
		}
		
		return valid;
	}
	
	private static int countColons(String words)
	{
		int numColons = 0;
		
		String[] tokens = words.split("\\:");
		
		for (String answers : tokens)
		{
			numColons++;
		}
		
		return numColons;
	}

	private static boolean validateLevelNumber(String level)
	{
		boolean valid = true;
		
		try
		{			
			if (level.length() != 1)
			{
				System.out.println("ERROR Line 261: The level must be 1-9 inclusive!");
				
				valid = false;
			}
			
			if (level.charAt(0) <= '0' && valid)
			{
				System.out.println("ERROR Line 254: The level must not be 0!");
				
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

	private static boolean validateMultipleChoiceAnswerLine(String answerLine)
	{
		boolean valid;
		
		valid = answerLine.contains(":");
		
		if (!valid)
		{
			System.out.println("ERROR Line 277: The multiple choice/answer line must contain colons as answer seperators!");
		}
			
		return valid;
	}
	
	private static boolean validateCorrectAnswerLetters(String correctAnswerLetters, int colonCount)
	{
		boolean valid = true;
		
		for (int i = 0; i < correctAnswerLetters.length() && valid; i++)
		{
			int correctAnswerNumber = (int) correctAnswerLetters.indexOf(i);
			
			if (colonCount != correctAnswerNumber)
			{
				System.out.println("ERROR Line 293: There must be a correctAnswer in the bounds of the answer(s)");
				
				valid = false;
			}
		}
		
		return valid;
	}
	
	private static boolean emptyFields(String field)
	{
		boolean empty = false;
		
		if (field.isEmpty())
		{
			empty = true;
		}
		
		return empty;
	}
}
