#ifndef QUESTIONSA_H
#define QUESTIONSA_H

/*
Author: Alex Novitchkov
File: QuestionSA.h, definitions for the QuestionSA class
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

Contains functions such as showQuestion, checkAnswer and more
 */

#include <string>
#include "question.h"

class QuestionSA : public Question
{
	public:
		/*
		   Plugs questionText and answerText into the appropriate slots in a Question object

		   @param questionText (letters or numbers only)
		   @param answerText (letters or numbers only)
		 */
		QuestionSA(string questionText, string answerText);

		/*
		   Sets Question's questionText with questionText given

		   @param questionText, input from file (letters or numbers only)
		 */
		virtual void setQuestion(string questionText);

		/*
		   Sets Question's answerText with answerText given

		   @param answerText, input from file (letters or numbers only)
		 */
		virtual void setAnswer(string answerText);

		/*
		   Gets the question from Question's questionText variable

		   @return string, questionText (letters or numbers only)
		 */
		virtual string getQuestion();

		/*
		   Gets the question from Question's answerText variable

		   @return string, answerText (letters or numbers only)
		 */
		virtual string getAnswer();

		/*
		   Prints the contents of questionText to the console
		 */
		virtual void showQuestion();

		/*
		   Prints the contents of answerText to the console
		 */
		virtual void showAnswer();

		/*
		   Checks the inputted answer versus Question's answerText to see whether correct or not

		   @param givenAnswer, inputted line of text (letters or numbers only)

		   @return bool whether correct or not
		 */
		virtual bool checkAnswer(string givenAnswer);
};

#endif
