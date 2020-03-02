
/**
* Question.java
* An abstract class for any question type
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/2/2020
*/

abstract public class Question
{
	protected String text;
	protected String answer;
	protected boolean correct;
	
	//------------------------------------------------------------------------
	/**
	 * Shows the question
	 */
	abstract public void showQuestion();
	
	//------------------------------------------------------------------------
	/**
	 * Shows the answer
	 */
	abstract public void showAnswer();
	
	//------------------------------------------------------------------------
	/**
	 * Checks parameter givenAnswer versus protected member answer
	 * @param givenAnswer, user inputed answer
	 * @return whether equal or not
	 */
	abstract public boolean checkAnswer(String givenAnswer);
	
	//------------------------------------------------------------------------
	/**
	 * Marks whether question was answered correctly
	 */
	abstract public void markCorrect();
	
	//------------------------------------------------------------------------
	/**
	 * Checks whether the question's correct member is true
	 * @return true or false
	 */
	abstract public boolean isCorrect();
}
