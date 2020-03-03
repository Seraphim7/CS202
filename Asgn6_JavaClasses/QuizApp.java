
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
* QuizApp.java
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
		boolean userWantsToContinue = true;
		boolean showIncorrectOnly = false;
		Scanner userInput = new Scanner(System.in);
		
		try
		{
			if (args.length == 1)
			{
				Quiz newQuiz = new Quiz(args[0]);
				
				while (userWantsToContinue)
				{
					newQuiz.deliverQuiz(showIncorrectOnly, userInput);
					userWantsToContinue = Utility.userResponseToContinue(userInput);
					
					if (userWantsToContinue)
					{
						showIncorrectOnly = Utility.userResponseIfOnlyShowIncorrect(userInput);
					}
				}
				
				System.out.println("Quiting...");
				
				userInput.close();
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
