
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Quiz
{
	ArrayList<Question> question;
	String filename;
	boolean filenameIsValid;
	
	Quiz(String dataFile) throws FileNotFoundException
	{
		question = new ArrayList<Question>();
		
		File file = new File(dataFile);
		Scanner fileScanner = new Scanner(file);
		
		fileScanner.close();
		
		loadQuestions(file);
	}
	
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

	private void loadQuestionsBasedOnType(String fileLine)
	{
		String pipeDelimeter = "\\|";
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
		
		this.question.add(newQuestion);
		newQuestion = null;
	}

	public boolean lineValidator(String fileLine, int lineNumber)
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
			parsedFileLine = Utility.parseString(fileLine, pipeDelimeter); // You need to do this so that pipe symbol is the delimiter
			valid = Validation.tokenByTokenValidator(parsedFileLine, fileLine, lineNumber);
		}
		else
		{
			valid = false; // Need some help on this naming
		}
		
		return valid;
	}

	public int totalQuestions()
	{
		return question.size();
	}
	
	public void deliverQuiz()
	{
		Iterator<Question> i = question.iterator();
		Question aQuestion;
		String userAnswer;
		boolean correctAnswer;
		Scanner input = new Scanner(System.in);
		
		printHeader();
		
		while (i.hasNext())
		{
			aQuestion = (Question) i.next();
			aQuestion.showQuestion();
			userAnswer = input.nextLine();
			correctAnswer = aQuestion.checkAnswer(userAnswer);
			
			if (correctAnswer)
			{
				printCorrect();
			}
			else
			{
				printIncorrect();
				aQuestion.showAnswer();
				System.out.println();
			}
		}
		
		input.close();
		
		getCorrectCount();
		getIncorrectCount();
	}

	public int getCorrectCount()
	{
		int correctCount = 0;
		Iterator<Question> i = question.iterator();
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
	
	public int getIncorrectCount()
	{
		int incorrectCount = 0;
		Iterator<Question> i = question.iterator();
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
	
	public void printHeader()
	{
		System.out.println("Hope your brain’s warmed up, it’s Quiz Time!!!");
		System.out.println("After each answer is displayed, press enter to see the next question.");
		System.out.println();
	}
	
	public void printCorrect()
	{
		System.out.println("Correct good job!");
		System.out.println();
	}
	
	public void printIncorrect()
	{
		System.out.print("Sorry that is incorrect, the answer is ");
	}
}
