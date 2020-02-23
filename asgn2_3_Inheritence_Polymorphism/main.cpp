/*
Author: Alex Novitchkov
File: main.cpp, the controller of the program
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20 

Contains functions that control and manipulate the program
 */

#include <iostream>
#include <vector>
#include <string>
#include <math.h>
#include "quiz.h"

using namespace std;

/*
   Calculates the percentage correct based on the input

   @param correct, how many questions the user got correct (int numbers only)
   @param total, how may total questions were there (int numbers only)

   @return percentage correct as a trucated int (int numbers only)
 */

int calculatePercentage(int correct, int total);

/*
   Displays the users results of how well they did on the quiz

   @param correct, how many questions the user got correct (int numbers only)
   @param total, how many total questions were there (int numbers only)
   @param percentage, percent correct (int numbers only)
 */
void finalMessage(int correct, int total, int percentage);

/*----------------------------------*/
int main(int argc, char* argv[])
{
	string dataFileName;
	int numCorrect;
	int totalQuestions;
	bool fileOpened;
	int percentage;
	bool incorrectOnly = false;
	bool quit = false;

	try
	{
		if (argc < 2)
		{
			throw ("ERROR! Not enough symbols given! ");
		}

		Quiz aQuiz(argv[1]);

		if (argc > 2)
		{
			string thirdArgument = argv[2];

			if (argc == 3 && thirdArgument == "-d")
			{

				cout << endl;
				cout << "Hope your brain's warmed up, ";
				cout << "it's Quiz Time!!!" << endl;
				cout << "After each aswer is displayed, ";
				cout << "press enter to see the next question." << endl;

				dataFileName = argv[1];
				fileOpened = aQuiz.loadQuestions(dataFileName);

				if (fileOpened)
				{
					aQuiz.dumpQuestions();
				}
			}
			else
			{

				throw ("ERROR! Too many symbols given! ");
			}
		}
		else
		{

			dataFileName = argv[1];

			fileOpened = aQuiz.loadQuestions(dataFileName);

			if (fileOpened)
			{
				cout << endl;
				cout << "Hope your brain's warmed up, ";
				cout << "it's Quiz Time!!!" << endl;
				cout << "After each aswer is displayed, ";
				cout << "press enter to see the next question." << endl;

				while (quit == false)
				{
					string showIncorrectQuestions;
					string startAgain;

					numCorrect = aQuiz.deliverQuiz(incorrectOnly);

					totalQuestions = aQuiz.getLength();

					percentage = calculatePercentage(numCorrect, totalQuestions);

					finalMessage(numCorrect, totalQuestions, percentage);

					cout << "Would you like to start again? (y/n): ";
					getline(cin, startAgain, '\n');

					for (unsigned int i = 0; i < startAgain.size(); i++)
					{
						if (i >= 0 && i <= 2)
						{
							startAgain[i] = startAgain[i] & 0xdf;
						}
					}

					if (startAgain.compare("YES") == 0 || startAgain.compare("YEAH") == 0 || startAgain.compare("Y") == 0)
					{
						if (percentage != 100)
						{
							cout << "Would you like to be shown the incorrect questions again? (y/n): ";
							getline(cin, showIncorrectQuestions, '\n');

							for (unsigned int i = 0; i < showIncorrectQuestions.size(); i++)
							{
								if (i >= 0 && i <= 2)
								{
									showIncorrectQuestions[i] = showIncorrectQuestions[i] & 0xdf;
								}
							}

							if (showIncorrectQuestions.compare("YES") == 0 || showIncorrectQuestions.compare("YEAH") == 0 || showIncorrectQuestions.compare("Y") == 0)
							{
								incorrectOnly = true;
							}
							else
							{
								incorrectOnly = false;
							}
						}
						else
						{
							incorrectOnly = false;
						}
					}
					else
					{
						quit = true;
						cout << "Quiting..." << endl;
					}
				}
			}
		}
	}
	catch (const char* error)
	{
		cerr << error << "Main aborting!" << endl;
	}
	catch (string error)
	{
		cerr << error << "Main aborting!" << endl;
	}
	catch (...)
	{
		cerr << "Something went wrong! Main aborting!" << endl;
	}

	return 1;
}

/*---------------------------------------------------*/
int calculatePercentage(int correct, int total)
{
	int percentageCorrect;

	if (total == 0)
	{
		return 0;
	}

	/* doing floating point arithmetic rather than integer but then turning around ant flooring it ex: floor(66.67) = 66 */
	percentageCorrect = floor(((float) correct / (float) total) * 100.0);

	return percentageCorrect;
}

/*-----------------------------------------------------------*/
void finalMessage(int correct, int total, int percentage)
{
	cout << "You got " << correct << " of " << total
		<< " correct: " << percentage << "%.";

	if (percentage < 60)
	{
		cout << "   Better study more!" << endl;
	}
	else
	{
		cout << "   Great work!" << endl;
	}
}
