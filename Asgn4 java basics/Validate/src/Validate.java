// Author: Alex Novitchkov
// Filename: Validate.java which contains what is needed to validate a file
// CS202 asgn4 "Java basics"
//
// This file contains some print functions, validation, and main

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Validate
{
	/*
		Prints error information such as the line number and the file line
	
		@param fileLine, line of the input file
		@param lineNumber, file line number
		@param error, the error recieved
	*/
	private static void printErrorLineNumber(String fileLine, int lineNumber, String error)
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
	
	/*---------------------------------------------------------------------------------------------*/
	public static void main(String[] args)
	{
		try
		{
			if (args.length == 1)
			{
				Scanner dataFile = null;
				
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
	
	/*
		Opens the file
	
		@param filename, the name of the file to be opened
	
		@return Scanner, an objects containing the datafile
	*/
	private static Scanner openFile(String filename) throws FileNotFoundException
	{
		File dataFile = new File(filename);
		
		Scanner fileScanner = new Scanner(dataFile);
		
		return fileScanner;
	}
	
	/*
		Validates the file line by line
		
		@param dataFile, the file
	*/
	public static lineByLineValidator(Scanner dataFile)
	{
		int lineNumber = 0;
		String fileLine = null;
		String[] parsedFileLine = null;
		boolean valid = true;
		int errorLines = 0;
		
		while (dataFile.hasNextLine())
		{
			valid = true;
			
			lineNumber++;
			
			fileLine = dataFile.nextLine();
			
			fileLine = frontWhitespaceStripper(fileLine);
			
			if (!commentOrBlankLine(fileLine))
			{
				parsedFileLine = parseString(fileLine, "\\|"); // You need to do this so that pipe symbol is the delimiter
			
				valid = tokenByTokenValidator(parsedFileLine, fileLine, lineNumber);
			}
			
			if (!valid)
			{
				errorLines++;
			}
		}
		
		printStatistics(lineNumber, errorLines);
	}

	/*
		strips whitespace from the front of the fileLine (type)
		
		@param fileLine, line in the file
		
		@return String, a stripped down version of fileLine
	*/
	public static String frontWhitespaceStripper(String fileLine)
	{
		boolean whitespaceInFront = true;
		
		for (int i = 0; i < fileLine.length() && whitespaceInFront; i++)
		{
			if (fileLine.charAt(i) == '|')
			{
				nonWhitespaceInFront = false;
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

	/*
		Checks whether line is a comment or a blank line
		
		@param fileLine, a line in the file
		
		@return boolean, whether it is a comment or blank line
	*/
	private static boolean commentOrBlankLine(String fileLine) 
	{
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
	
	/*
		Parses a string, the fileLine in this case
		
		@param StringToParse, the string to parse
		@param token, delimeter for parsing
		
		@return String[], delimeted string[]
	*/
	public static String[] parseString(String stringToParse, String token)
	{
		String[] arrayOfStrings;
		
		arrayOfStrings = stringToParse.split(token);
		
		return arrayOfStrings;
	}

	/*
		Validates a file line, token by token
		
		@param parsedFileLine, file line delimeted by a character
		@param fileLine, a line in the file (not in parts)
		@param lineNumber, the file line number
		
		@return boolean if file line is valid
	*/
	private static boolean tokenByTokenValidator(String[] parsedFileLine, String fileLine, int lineNumber)
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
	
	/*
		Checks if a line has an empty field
		
		@param field, one field in the fileLine
		@param fileLine, the whole fileLine
		@param lineNumber, the file line number
		
		@return boolean if field is empty or not
	*/
	private static boolean emptyFields(String field, String fileLine, int lineNumber)
	{
		boolean empty = false;
		
		if (field.isEmpty())
		{
			printErrorLineNumber(fileLine, lineNumber, "An empty field is not valid");
			empty = true;
		}
		
		return empty;
	}
	
	/*
		Checks the file line type
		
		@param token, type
		@param fileLine, a line in the file
		@param lineNumber, file line number
		
		@return boolean if type is valid or not
	*/
	private static boolean checkType(String token, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		token = token.toUpperCase();
		
		if (!(token.equals("SA") || token.equals("TF") || token.equals("MC") || token.equals("MA")))
		{
			printErrorLineNumber(fileLine, lineNumber, "Type is invalid. Type must be SA, TF, MC, or MA!");
			valid = false;
		}
		
		return valid;
	}
	
	/*
		Checks if the level number is valid
		
		@param level, the level field
		@param fileLine, a line in the file
		@param lineNumber, the file line number
	*/
	private static boolean validateLevelNumber(String level, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		try
		{
			if (level.length() > 1)
			{
				printErrorLineNumber(fileLine, lineNumber, "The level must be 1-9 inclusive!");
				valid = false;
			}
			else
			{
				if (level.charAt(0) <= '0')
				{
					printErrorLineNumber(fileLine, lineNumber, "The level must be 1-9 inclusive!");
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

	/*
		Validates the question based on the type
		
		@param type, type of question
		@param answerToken, answer field
		@param correctAnswerToken, correct answer field
		@param pipeCount, line seperator count
		@param fileLine, a line in the file
		@param lineNumber, the file line number
		
		@return boolean, whether question is valid
	*/
	private static boolean questionValidatorBasedOnType(String type, String answerToken, String correctAnswerToken, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
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
			valid = multipleChoiceAnswer(answerToken, correctAnswerToken, pipeCount, fileLine, lineNumber);
		}
		
		return valid;
	}
	
	/*
		Validates a shortAnswer question
		
		@param answerLine, answer field
		@param fileLine, a line in the file
		@param lineNumber, the file line number
		
		@return boolean, if given short answer question is valid
	*/
	public static boolean shortAnswer(String answerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 3)
		{
			printErrorLineNumber(fileLine, lineNumber, "Pipe count must be 3 for short answer questions!");
			valid = false;
		}
		else
		{
			valid = validateTrueShortAnswerLine(answerLine, fileLine, lineNumber);
		}
		
		return valid;
	}

	/*
		Validates a true/false question
		
		@param answerLine, answer field
		@param fileLine, a line in the file
		@param lineNumber, the file line number
		
		@return boolean, if given true/false question is valid
	*/	
	public static boolean trueFalse(String answerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 3)
		{
			printErrorLineNumber(fileLine, lineNumber, "Pipe count must be 3 for true/false questions!");
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
					printErrorLineNumber(fileLine, lineNumber, "Answer choices for true/false questions must be true or false!");
					valid = false;
				}
			}
		}
			
		return valid;
	}
	
	/*
		Validates a multiple choice/answer question
	
		@param answerLine, answer field
		@param fileLine, a line in the file
		@param lineNumber, the file line number
		
		@return boolean, if given multiple choice/answer question is valid
	*/
	public static boolean multipleChoiceAnswer(String answerLine, String correctAnswerLine, int pipeCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (pipeCount != 4)
		{
			printErrorLineNumber(fileLine, lineNumber, "Pipe count must be 4 for multiple choice/answer!");
			valid = false;
		}
		else
		{
			if (valid)
			{
				int colonCount = countColons(answerLine);
				
				valid = isLessThanTwoAnswers(colonCount, fileLine, lineNumber);
				
				if (valid)
				{
					valid = validateCorrectAnswerLetters(correctAnswerLine, colonCount, fileLine, lineNumber);
				}
			}
		}
		
		return valid;
	}

	/*
		Validate if true/false/short answer is valid
	
		@param answerLine, answer field
		@param fileLine, a line in the file
		@param lineNumber, the file line number
		
		@return boolean, if given true/false/short answer question is valid
	*/
	private static boolean validateTrueShortAnswerLine(String answerLine, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (answerLine.contains(":"))
		{
			printErrorLineNumber(fileLine, lineNumber, "SA or TF type questions cannot contain colons!");
			valid = false;
		}
		
		return valid;
	}
	
	/*
		Count colons in line
	
		@param words, a piece of a line to count colons from (if any)
		
		@return int, number of colons
	*/
	private static int countColons(String words)
	{
		int numColons = 0;
		
		String[] tokens = words.split("\\:");
		
		numColons = tokens.length;
		
		return numColons;
	}
	
	/*
		Check whether multiple choice/answer questions have at least two answers
		
		@param colonCount, number of colons
		@param fileLine, a line in the file
		@param lineNumber, the file line number
	*/
	private static boolean isLessThanTwoAnswers(int colonCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		if (colonCount < 2)
		{
			printErrorLineNumber(fileLine, lineNumber);
			valid = false;
		}
		
		return valid;
	}
	
	/*
		Validates whether there is a correct answer whithin bounds of answer line
		
		@param correctAnswerLetters, correct answer letters token within file line
		@param colonCount, number of colons
		@param fileLine, a line in the file
		@param lineNumber, the file line number
		
		@return boolean, whether there exists a correct answer in bounds or not
	*/
	private static boolean validateCorrectAnswerLetters(String correctAnswerLetters, int colonCount, String fileLine, int lineNumber)
	{
		boolean valid = true;
		
		for (int i = 0; i < correctAnswerLetters.length() && valid; i++)
		{
			int correctAnswerNumber = correctAnswerLetters.charAt(i) & 0x3f;
			
			if (colonCount < correctAnswerNumber)
			{
				printErrorLineNumber(fileLine, lineNumber, "There must be a correct answer in the bounds of the answer line!");
				valid = false;
			}
		}
		
		return valid;
	}
	
	/*
		Prints total question and how many error lines received
		
		@param lineNumber, the total number of lines in the file
		@param errorLines, lines that contain errors
	*/
	private static void printStatistics(int lineNumber, int errorLines)
	{
		System.out.print(lineNumber);
		System.out.println(" questions processed");
		System.out.print(errorLines);
		System.out.println(" errors found");
	}
}
