
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
* Quiz.java
* Contains the necessary items for doing various tasks with a quiz
* such as loadQuestions, lineValidator, and deliverQuiz
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/8/2020
*/

public class Quiz
{
	ArrayList<Question> questions;
	String filename;
	boolean filenameIsValid;
	
	//------------------------------------------------------------------------
	/**
	 * Creates a new File from the parameter dataFile
	 * @param dataFile
	 * @throws FileNotFoundException
	 * @see FileNotFoundException
	 */
	Quiz(String dataFile) throws FileNotFoundException
	{
		questions = new ArrayList<Question>();
		
		filename = dataFile;
		
		try
		{
			File file = new File(dataFile);
			
			loadQuestions(file);
			
			filenameIsValid = true;
		}
		catch (FileNotFoundException error)
		{
			filenameIsValid = false;
			
			throw(error);
		}
	}

	//------------------------------------------------------------------------
	/**
	 * Delivers a quiz to the user
	 * @param showIncorrectOnly, will only show the incorrect questions
	 * @param input, scanner input
	 */
	public void deliverQuiz(boolean showIncorrectOnly, Scanner input)
	{
		Iterator<Question> i = questions.iterator();
		Question aQuestion;
		String userAnswer;
		boolean correctAnswer;
		int correct;
		int incorrect;
		int total;
		int iterator = 0;
		
		printHeader();
		
		while (i.hasNext())
		{
			aQuestion = (Question) i.next();
			
			if (!showIncorrectOnly || (!aQuestion.isCorrect() && showIncorrectOnly))
			{
				aQuestion.showQuestion();
				userAnswer = input.nextLine();
				correctAnswer = aQuestion.checkAnswer(userAnswer);
				
				if (correctAnswer)
				{
					aQuestion.markCorrect();
					printCorrect();
				}
				else
				{
					aQuestion.markIncorrect();
					printIncorrect();
					aQuestion.showAnswer();
				}

				questions.set(iterator, aQuestion);

				iterator++;
			}
		}
		
		correct = getCorrectCount();
		incorrect = getIncorrectCount();
		total = totalQuestions();
		
		Utility.printOverallStatistics(correct, incorrect, total);
	}
	
	//------------------------------------------------------------------------
	/**
	 * Returns the total number of questions processed
	 * @return the question container size
	 */
	public int totalQuestions()
	{
		return questions.size();
	}

	//------------------------------------------------------------------------
	/**
	 * Returns the number of correct questions processed
	 * @return the correct questions in the question container
	 */
	public int getCorrectCount()
	{
		int correctCount = 0;
		Iterator<Question> i = questions.iterator();
		Question aQuestion = null;
		
		while (i.hasNext())
		{
			aQuestion = i.next();
			
			if (aQuestion.isCorrect())
			{
				correctCount++;
			}
		}
		
		return correctCount;
	}
	
	//------------------------------------------------------------------------
	/**
	 * Returns the number of incorrect questions processed
	 * @return the incorrect questions in the question container
	 */
	public int getIncorrectCount()
	{
		int incorrectCount = 0;
		Iterator<Question> i = questions.iterator();
		Question aQuestion = null;
		
		while (i.hasNext())
		{
			aQuestion = i.next();
			
			if (!aQuestion.isCorrect())
			{
				incorrectCount++;
			}
		}
		
		return incorrectCount;
	}
	
	//------------------------------------------------------------------------
	/**
	 * Prints out the quiz header text
	 */
	public void printHeader()
	{
		System.out.println("Hope your brain's warmed up, it's Quiz Time!!!");
		System.out.println("After each answer is displayed, press enter to see the next question.");
		System.out.println();
	}
	
	//------------------------------------------------------------------------
	/**
	 * Prints out a correct message
	 */
	public void printCorrect()
	{
		System.out.println("Correct, good job!");
		System.out.println();
	}
	
	//------------------------------------------------------------------------
	/**
	 * Prints out an incorrect message
	 */
	public void printIncorrect()
	{
		System.out.print("Sorry that is incorrect, the answer is ");
	}
	
	/**
	 * Displays the raw questions and answers (if any) from the given file (if any)
	 */
	//------------------------------------------------------------------------
	public void dataDump()
	{
		System.out.println();
		
		if (questions.isEmpty())
		{
			System.out.println("No questions given!");
		}
		else
		{
			Question aQuestion = null;
			Iterator<Question> i = questions.iterator();
			
			while (i.hasNext())
			{	
				aQuestion = i.next();
				
				aQuestion.showQuestion();
				aQuestion.showAnswer();
			}
		}
	}
	
	//------------------------------------------------------------------------
	private void loadQuestions(File file) throws FileNotFoundException
	{
		boolean valid;
		String fileLine = null;
		Scanner fileScanner = new Scanner(file);
		int lineNumber = 0;
		
		while (fileScanner.hasNextLine())
		{
			lineNumber++;
			
			fileLine = fileScanner.nextLine();
			
			valid = lineValidator(fileLine, lineNumber);
			
			if (valid)
			{
				loadQuestionsBasedOnType(fileLine);
			}
		}
		
		fileScanner.close();
	}

	//------------------------------------------------------------------------
	private void loadQuestionsBasedOnType(String fileLine)
	{
		String pipeDelimeter = "\\|"; // Need to do the \\ for it to recognize delimiter
		Question newQuestion;
		
		String[] parsedFileLine = Utility.parseString(fileLine, pipeDelimeter);
		
		String type = parsedFileLine[0].toUpperCase();
		String question = parsedFileLine[2];
		String answerLine = parsedFileLine[3];
		
		if (type.equals("SA"))
		{
			newQuestion = new QuestionSA(question, answerLine);
		}
		else if (type.equals("TF"))
		{
			newQuestion = new QuestionTF(question, answerLine);
		}
		else
		{
			char correctAnswerLetter = parsedFileLine[4].charAt(0);
			
			correctAnswerLetter = Character.toUpperCase(correctAnswerLetter);
			
			newQuestion = new QuestionMC(question, answerLine, correctAnswerLetter);
		}
		
		this.questions.add(newQuestion);
		newQuestion = null;
	}

	//------------------------------------------------------------------------
	private boolean lineValidator(String fileLine, int lineNumber)
	{
		boolean valid = false;
		String[] parsedFileLine = null;
		String pipeDelimeter = "\\|";
		boolean commentOrBlankLine = false;
		
		valid = true;
				
		fileLine = Utility.frontWhitespaceStripper(fileLine);
		
		commentOrBlankLine = Validation.commentOrBlankLine(fileLine);
		
		if (!commentOrBlankLine)
		{
			parsedFileLine = Utility.parseString(fileLine, pipeDelimeter);
			valid = Validation.tokenByTokenValidator(parsedFileLine, fileLine, lineNumber);
		}
		else
		{
			valid = false;
		}
		
		return valid;
	}
}
