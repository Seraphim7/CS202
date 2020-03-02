
/**
* <h1>Utility.java</h1>
* Contains utility functions like parseString and frontWhiteSpaceStripper
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/2/2020
*/

public class Utility
{
	//------------------------------------------------------------------------
	/**
	* Parses a string, the fileLine in this case
	* @param StringToParse, the string to parse
	* @param token, delimiter for parsing
	* @return String[], delimited string[]
	* @param token, delimiter for parsing
	* @return String[], delimited string[]
	*/
	public static String[] parseString(String fileLine, String delimeter)
	{
		String[] parsedFileLine = null;
		
		parsedFileLine = fileLine.split(delimeter);
		
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
		
		String[] tokens = words.split("\\:");
		
		numColons = tokens.length;
		
		return numColons;
	}
	
	//------------------------------------------------------------------------
	/**
	 * Splits the string based on parameter delimiter
	 * @param answers, string of answers to split
	 * @param delimeter, split by
	 * @return String[], array of split answers
	 */
	public static String[] splitByDelimeter(String answers, String delimiter)
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
	public static void printStatistics(int lineNumber, int errorLines)
	{
		System.out.print(lineNumber);
		System.out.println(" questions processed");
		System.out.print(errorLines);
		System.out.println(" errors found");
	}
}
