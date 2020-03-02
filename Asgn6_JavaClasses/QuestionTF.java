
public class QuestionTF extends Question
{
	QuestionTF(String question, String answer)
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
		
		if (!(givenAnswer.equals(answer) || givenAnswer.equals("T") || givenAnswer.contentEquals("F") || givenAnswer.equals("t") || givenAnswer.equals("f")))
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
