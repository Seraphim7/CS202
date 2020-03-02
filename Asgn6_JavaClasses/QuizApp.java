
import java.io.FileNotFoundException;

/**
* <h1>QuizApp.java</h1>
* This file is the controller of this quiz program
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/2/2020
*/

public class QuizApp
{
	//------------------------------------------------------------------------
	/**
	 * Controller of the program
	 * @param args, arguments to the program
	 */
	public static void main(String[] args)
	{
		try
		{
			if (args.length == 1)
			{
				Quiz newQuiz = new Quiz(args[0]);
				
				newQuiz.deliverQuiz();
			}
			else
			{
				System.err.println("Exactly 1 argument needs to be provided!");
			}
		}
		catch (FileNotFoundException err)
		{
			System.err.println("File is not found");
		}
		catch (NullPointerException err)
		{
			System.err.println("Null pointer exception");
		}
	}
}
