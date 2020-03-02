
abstract public class Question
{
	protected String text;
	protected String answer;
	protected boolean correct;
	
	abstract public void markCorrect();
	abstract public boolean isCorrect();
	abstract public void showQuestion();
	abstract public void showAnswer(); // Added
	abstract public boolean checkAnswer(String givenAnswer);
}
