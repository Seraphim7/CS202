
/**
* Validation.java
* Contains validation for the file
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/8/2020
*/

public class Validation
{
	//------------------------------------------------------------------------
	/**
	* Checks whether line is a comment or a blank line
	* @param fileLine, a line in the file
	* @return boolean, whether it is a comment or blank line
	*/
	public static boolean commentOrBlankLine(String fileLine) 
	{
		/* # and // are the types of comments */
		
		boolean commentOrBlank = false;
		
		if (fileLine.isEmpty())
		{
			return true;
		}
		else if (fileLine.length() == 1)
		{
			if (fileLine.charAt(0) == '#' || fileLine.charAt(0) == '/')
			{
				commentOrBlank = true;
			}
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
	
	//------------------------------------------------------------------------
	/**
	* Validates a file line, token by token
	* @param parsedFileLine, file line delimited by a character
	* @param fileLine, a line in the file (not in parts)
	* @param lineNumber, the file line number
	* @return boolean if file line is valid
	*/
	public static boolean tokenByTokenValidator(String[] parsedFileLine, String fileLine, int lineNumber)
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
				return false;
			}
			
			if (i == 1)
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
	
	//------------------------------------------------------------------------
	private static boolean emptyFields(String field, String fileLine, int lineNumber)
	{
		boolean empty = false;
		
		if (field.isEmpty())
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "An empty field is not valid!");
			empty = true;
		}
		
		return empty;
	}
	
	//------------------------------------------------------------------------
	private static boolean checkType(String token, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		token = token.toUpperCase();
		
		if (!(token.equals("SA") || token.equals("TF") || token.equals("MC")))
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "Type is invalid. Type must be SA, TF, or MC!");
			valid = false;
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean validateLevelNumber(String level, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		try
		{
			if (level.length() > 1)
			{
				Utility.printErrorLineNumber(fileLine, lineNumber, "The level must be 1-9 inclusive!");
				valid = false;
			}
			else
			{
				if (level.charAt(0) <= '0')
				{
					Utility.printErrorLineNumber(fileLine, lineNumber, "The level must be 1-9 inclusive!");
					valid = false;
				}
			}
		}
		catch (IndexOutOfBoundsException error)
		{
			System.err.print("ERROR: Out of bounds!");
			valid = false;
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean questionValidatorBasedOnType(String type, String answerToken, String correctAnswerToken, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (!checkPipeCountGeneral(fileLine, lineNumber, pipeCount))
		{
			valid = false;
			
			return valid;
		}
		
		type = type.toUpperCase();
		
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
			valid = multipleChoice(answerToken, correctAnswerToken, pipeCount, fileLine, lineNumber);
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean shortAnswer(String answerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 3)
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "Pipe count must be 3 for short answer questions!");
			valid = false;
		}
		else
		{
			valid = validateTrueShortAnswerLine(answerLine, fileLine, lineNumber);
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean trueFalse(String answerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 3)
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "Pipe count must be 3 for true/false questions!");
			valid = false;
		}
		else
		{
			valid = validateTrueShortAnswerLine(answerLine, fileLine, lineNumber);
				
			if (valid)
			{
				answerLine = answerLine.toUpperCase();
				
				if (!(answerLine.equals("TRUE") || answerLine.equals("FALSE")))
				{
					Utility.printErrorLineNumber(fileLine, lineNumber, "Answer choices for true/false questions must be true or false!");
					valid = false;
				}
			}
		}
			
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean multipleChoice(String answerLine, String correctAnswerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 4)
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "Pipe count must be 4 for multiple choice!");
			valid = false;
		}
		else
		{
			int colonCount = Utility.countColons(answerLine);
			
			if (valid)
			{
				valid = checkValidMultipleChoiceColonCount(colonCount, fileLine, lineNumber);
			}
			
			if (valid)
			{
				valid = validateCorrectAnswerLetter(correctAnswerLine, colonCount, fileLine, lineNumber);
			}
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean validateTrueShortAnswerLine(String answerLine, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (answerLine.contains(":"))
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "SA or TF type questions cannot contain colons!");
			valid = false;
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean validateCorrectAnswerLetter(String correctAnswerLetter, int colonCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		int correctAnswerNumber;
		
		correctAnswerLetter = correctAnswerLetter.toUpperCase();

		if (correctAnswerLetter.charAt(0) > 'H')
		{
			correctAnswerNumber = 10;
		}
		else
		{
			correctAnswerNumber = correctAnswerLetter.charAt(0) & 0x3f; // Converting uppercase letters to number (1 - 9)
		}
		
		if (colonCount < correctAnswerNumber)
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "There must be a correct answer in the bounds of the answer line!");
			valid = false;
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean checkPipeCountGeneral(String fileLine, int lineNumber, int pipeCount)
	{
		boolean valid = true;
		
		if (pipeCount < 3 || pipeCount > 4)
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "Every line must have only 3 or 4 pipe symbols!");
			valid = false;
		}
		
		return valid;
	}
	
	//------------------------------------------------------------------------
	private static boolean checkValidMultipleChoiceColonCount(int colonCount, String fileLine, int lineNumber)
	{
		if (colonCount < 2 || colonCount > 10)
		{
			Utility.printErrorLineNumber(fileLine, lineNumber, "Every line must have 2-10 answers!");
			return false;
		}
		
		return true;
	}
}
