import java.util.Scanner;

/**
* Utility.java
* Contains utility functions like parseString and frontWhiteSpaceStripper
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/8/2020
*/

public class Utility
{
	//------------------------------------------------------------------------
	/**
	* Parses a string, the fileLine in this case
	* @param stringToParse, the string to parse
	* @param delimiter, delimiter for parsing
	* @return String[], delimited string[]
	*/
	public static String[] parseString(String stringToParse, String delimiter)
	{
		String[] parsedFileLine = null;
		
		parsedFileLine = stringToParse.split(delimiter);
		
		return parsedFileLine;
	}
	
	//------------------------------------------------------------------------
	/**
	* Prints error information such as the line number and the file line
	* @param fileLine, line of the input file
	* @param lineNumber, file line number
	* @param error, the error received
	*/
	public static void printErrorLineNumber(String fileLine, int lineNumber, String error)
	{
		System.out.print("Line ");
		System.out.print(lineNumber);
		System.out.print(" - ");
		System.out.print(fileLine);
		System.out.println();
		System.out.print("\tERROR: ");
		System.out.println(error);
		System.out.println();
	}

	//------------------------------------------------------------------------
	/**
	* Strips whitespace from the front of the fileLine (type)
	* @param fileLine, line in the file
	* @return String, a stripped down version of fileLine
	*/
	public static String frontWhitespaceStripper(String fileLine)
	{
		boolean whitespaceInFront = true;
		
		for (int i = 0; i < fileLine.length() && whitespaceInFront; i++)
		{
			if (fileLine.charAt(i) == '|')
			{
				whitespaceInFront = false;
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
	
	//------------------------------------------------------------------------
	/**
	* Count colons in line
	* @param words, a piece of a line to count colons from (if any)
	* @return int, number of colons
	*/
	public static int countColons(String words)
	{
		int numColons = 0;
		
		String[] tokens = words.split("\\:"); // Need to do the \\ for it to recognize delimiter
		
		numColons = tokens.length;
		
		return numColons;
	}
	
	//------------------------------------------------------------------------
	/**
	 * Splits the string based on parameter delimiter
	 * @param answers, string of answers to split
	 * @param delimiter, split by
	 * @return String[], array of split answers
	 */
	public static String[] splitByDelimiter(String answers, String delimiter)
	{
		String[] arrayOfStrings = null;
		
		arrayOfStrings = answers.split(delimiter);
		
		return arrayOfStrings;
	}

	//------------------------------------------------------------------------
	/**
	* Prints total question and how many error lines received
	* @param lineNumber, the total number of lines in the file
	* @param errorLines, lines that contain errors
	*/
	public static void printErrorStatistics(int lineNumber, int errorLines)
	{
		System.out.print(lineNumber);
		System.out.println(" questions processed");
		System.out.print(errorLines);
		System.out.println(" errors found");
	}
	
	//------------------------------------------------------------------------
	/**
	 * Prints the number of correct answers, incorrect answers, and the total number of answers
	 * @param correct, number of questions correct
	 * @param incorrect, number of questions incorrect
	 * @param total, number of total questions
	 */
	public static void printOverallStatistics(int correct, int incorrect, int total)
	{
		int percentage;
		boolean good;
		
		System.out.print("You got ");
		System.out.print(correct);
		System.out.print(" of ");
		System.out.print(total);
		System.out.print(" correct: ");
		
		percentage = calculatePercentage(correct, total);
		
		System.out.print(percentage);
		System.out.print("%.\t");
		
		good = goodOrBad(percentage);
		
		if (good)
		{
			System.out.println("Good job!");
		}
		else
		{
			System.out.println("Better study more!");
		}
	}

	//------------------------------------------------------------------------
	/**
	 * calculates a percentage
	 * @param correct, number of questions correct
	 * @param total, number of total questions
	 * @return int, percentage calculated rounded down
	 */
	public static int calculatePercentage(int correct, int total)
	{
		int percentage;
		
		percentage = (int) Math.floor((correct * 100.0) / total); // .0 is there to do floating point math
		
		return percentage;
	}

	//------------------------------------------------------------------------
	/**
	 * Prompts the user if they want to continue
	 * @param userInput, scanner as input
	 * @return boolean whether they want to continue
	 */
	public static boolean userResponseToContinue(Scanner userInput)
	{
		System.out.print("Do you want to continue? (Y/N): ");
		String response = userInput.nextLine();
		
		System.out.println();
		
		response = response.toUpperCase();
		
		return validateUserControlResponse(response);
	}
	
	//------------------------------------------------------------------------
	/**
	 * Prompts the user if they want to be shown only incorrect answers
	 * @param userInput, scanner as input
	 * @return boolean whether they want to be shown the incorrect answers
	 */
	public static boolean userResponseIfOnlyShowIncorrect(Scanner userInput)
	{
		System.out.print("Do you only want to be show the question(s) you got incorrect? (Y/N): ");
		String response = userInput.nextLine();
		
		System.out.println();
		
		response = response.toUpperCase();
		
		return validateUserControlResponse(response);
	}
	
	//------------------------------------------------------------------------
	private static boolean goodOrBad(int percentage)
	{
		if (percentage >= 60)
		{
			return true;
		}
		
		return false;
	}
		
	//------------------------------------------------------------------------
	private static boolean validateUserControlResponse(String response)
	{
		response = response.toUpperCase();
		
		if (response.length() == 1)
		{
			if (response.equals("Y"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (response.length() == 3)
		{
			if (response.equals("YES") || response.equals("YEA"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (response.length() == 4)
		{
			if (response.equals("YEAH"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
