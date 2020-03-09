
/**
* QuestionTF.java
* Functions dealing with a true/false question
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/8/2020
*/

public class QuestionTF extends Question
{
	//------------------------------------------------------------------------
	/**
	 * Sets the question and answer
	 * @param question, question text
	 * @param answer, answer text
	 */
	QuestionTF(String question, String answer)
	{
		answer = answer.toLowerCase();
		
		text = question;
		this.answer = answer;
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
		
		if (!(givenAnswer.equals(answer) || givenAnswer.equals("T") || givenAnswer.contentEquals("F") || givenAnswer.equals("t") || givenAnswer.equals("f")))
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
}
