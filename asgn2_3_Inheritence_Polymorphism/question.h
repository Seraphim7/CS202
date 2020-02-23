#ifndef QUESTION_H
#define QUESTION_H

/*
Author: Alex Novitchkov
File: question.h, contains all the "outline" of class Question
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

This file contains function prototypes and instance variables of the Question class.
 */

#include <string>

using namespace std;

class Question
{
	public:
		Question();

		/*
		   Initializes the private member variables to the parameters and false

		   @param questionText, string containing the question. (letters or numbers only)
		   @param anwerText, string containing the answer (letters or numbers only)
		 */
		Question(string questionText, string answerText);

		/*
		   Prints the question to the cout
		 */
		virtual void showQuestion() = 0;

		/*
		   Sets the contents of question based on the input text

		   @param questionText, input text (letters or numbers only)
		 */
		virtual void setQuestion(string questionText);

		/*
		   Sets the contents of answer based on the input text

		   @param answerText, input text (letters or numbers only)
		 */
		virtual void setAnswer(string answerText);

		/*
		   Checks the answer to see whether it is correct or not

		   @param checkAnswer, text to compare against answerText (letters or numbers only)

		   @return bool whether the strings are equivelant or not (true or false only)
		 */
		virtual bool checkAnswer(string givenAnswer) = 0;

		/*
		   Prints the answer to cout
		 */
		virtual void showAnswer() = 0;

		/*
		   Marks instance variable correct to correct
		 */
		void markCorrect();

		/*
		   Marks instance variable correct to incorrect
		 */
		void markIncorrect();

		/*
		   Returns the question

		   @return string questionText (letters or numbers only)
		 */
		virtual string getQuestion();

		/*
		   Returns the answer

		   @return string answerText (letters or numbers only
		 */
		virtual string getAnswer();

		/*
		   Returns private member correct

		   @return bool, correct is true or false
		 */
		bool getCorrect();

		/*
		   needs to be virtual so that it will not override other destructors
		 */
		virtual ~Question();
	private:
		string questionText;
		string answerText;
		bool correct;
};

#endif
