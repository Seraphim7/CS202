
/**
* QuestionMC.java
* Functions dealing with a multiple choice question
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/8/2020
*/

import java.util.ArrayList;

public class QuestionMC extends Question
{
	ArrayList<String> incorrectAnswers;
	
	//------------------------------------------------------------------------
	/**
	 * Sets the question, answers, and correctAnswerLetter
	 * @param question, question text
	 * @param answers, line of answers
	 * @param correctAnswerLetter, The correct answer letter
	 */
	QuestionMC(String question, String answers, char correctAnswerLetter)
	{
		incorrectAnswers = new ArrayList<String>();
		
		text = question;
		
		String[] stringOfAnswers = Utility.splitByDelimiter(answers, "\\:");
		
		addAnswerChoicesToQuestion(stringOfAnswers);
		
		setAnswers(stringOfAnswers, correctAnswerLetter);
	}
	
	//------------------------------------------------------------------------
	/**
	 * Shows the question
	 */
	public void showQuestion()
	{
		System.out.println(text);
	}
	
	//------------------------------------------------------------------------
	/**
	 * Shows the answer
	 */
	public void showAnswer()
	{
		System.out.println(answer);
		System.out.println();
	}
	
	//------------------------------------------------------------------------
	/**
	 * Checks parameter givenAnswer versus protected member answer
	 * @param givenAnswer, user inputed answer
	 * @return whether equal or not
	 */
	public boolean checkAnswer(String givenAnswer)
	{
		givenAnswer = givenAnswer.toLowerCase();
		
		if (!givenAnswer.equals(answer))
		{
			return false;
		}
		
		return true;
	}

	//------------------------------------------------------------------------
	/**
	 * Marks whether question was answered correctly
	 */
	public void markCorrect()
	{
		correct = true;
	}

	//------------------------------------------------------------------------
	/**
	 * Marks whether question was answered incorrectly
	 */
	public void markIncorrect()
	{
		correct = false;
	}

	//------------------------------------------------------------------------
	/**
	 * Checks whether the question's correct member is true
	 * @return true or false
	 */
	public boolean isCorrect()
	{
		if (!correct)
		{
			return false;
		}
		
		return true;
	}
	
	//------------------------------------------------------------------------
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

	//------------------------------------------------------------------------
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
