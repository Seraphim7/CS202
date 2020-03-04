
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
* QuizApp.java
* This file is the controller of this quiz program
* <p>
*
* @author  Alex Novitchkov
* @version 1.0
* @since   3/4/2020
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
			System.out.println();
			
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
				
				System.out.println("Quitting...");
				
				userInput.close();
			}
			else if (args.length == 2 && args[1].equals("-d"))
			{
				Quiz newQuiz = new Quiz(args[0]);
				
				newQuiz.dataDump();
			}
			else
			{
				System.err.println("2 (2nd argument -d optional) arguments needs to be provided!");
			}
		}
		catch (NullPointerException error)
		{
			System.err.println(error.getMessage());
		} 
		catch (FileNotFoundException error)
		{
			System.err.println(error.getMessage());
		}
	}
}
