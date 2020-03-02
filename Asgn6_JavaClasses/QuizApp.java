import java.io.FileNotFoundException;

public class QuizApp
{
	public static void main(String[] args)
	{
		try
		{
				Quiz newQuiz = new Quiz("testfile.txt");
				
				newQuiz.deliverQuiz();
		}
		catch (FileNotFoundException err)
		{
			System.err.println("File is not found");
		}
	}
}

// are some modifications all right?
