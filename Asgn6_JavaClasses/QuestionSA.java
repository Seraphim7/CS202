
public class QuestionSA extends Question
{
	QuestionSA(String question, String answer)
	{
		answer = answer.toLowerCase();
		
		text = question;
		this.answer = answer;
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
}
