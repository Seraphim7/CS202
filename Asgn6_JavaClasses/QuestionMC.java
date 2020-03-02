import java.util.ArrayList;

public class QuestionMC extends Question
{
	ArrayList<String> incorrectAnswers;
	
	QuestionMC(String question, String answers, char correctAnswerLetter)
	{
		incorrectAnswers = new ArrayList<String>();
		
		text = question;
		
		String[] stringOfAnswers = splitByDelimeter(answers, "\\:");
		
		addAnswerChoicesToQuestion(stringOfAnswers);
		
		setAnswers(stringOfAnswers, correctAnswerLetter);
	}
	
	public void showQuestion()
	{
		System.out.println(text);
	}
	
	public void showAnswer()
	{
		System.out.println(answer);
	}
	
	public boolean checkAnswer(String givenAnswer)
	{
		givenAnswer = givenAnswer.toLowerCase();
		
		if (!givenAnswer.equals(answer))
		{
			return false;
		}
		
		return true;
	}

	public void markCorrect()
	{
		correct = true;
	}

	public boolean isCorrect()
	{
		if (!correct)
		{
			return false;
		}
		
		return true;
	}
		
	public String[] splitByDelimeter(String answers, String delimeter)
	{
		String[] arrayOfStrings = null;
		
		arrayOfStrings = answers.split(delimeter);
		
		return arrayOfStrings;
	}
	
	private void addAnswerChoicesToQuestion(String[] stringOfAnswers)
	{
		int i = 1;
		char questionLetter;
		
		for (String token : stringOfAnswers)
		{
			questionLetter = (char) (i | 0x60);
			
			text = text + "\n\t" + questionLetter + ") " + token;
			i++;
		}
	}

	private void setAnswers(String[] stringOfAnswers, char correctAnswerLetter)
	{
		correctAnswerLetter = Character.toLowerCase(correctAnswerLetter);
		
		int correctAnswerNumber = correctAnswerLetter & 0x1F;
		
		int colonCount = 0;
		
		for (String token : stringOfAnswers)
		{
			colonCount++;
			
			if (colonCount == correctAnswerNumber)
			{
				token = token.toUpperCase();
				
				answer = Character.toString(correctAnswerLetter);
			}
			else
			{
				incorrectAnswers.add(token);
			}
		}
	}
}
