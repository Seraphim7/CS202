/*
Author: Alex Novitchkov
File: questionTF.h, contains function definitions needed for QuestionTF, TF standing for True False.
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

Contains functions like setQuestion, setAnswer, showQuestion, checkQuestion, and others
 */

#ifndef QUESTIONTF_CPP
#define QUESTIONTF_CPP

#include <string>
#include "question.h"

using namespace std;

class QuestionTF : public Question
{
	public:
		QuestionTF();

		/*
		   Plugs questionText and answerText in their respective spots inside Question

		   @param questionText, input text (letters or numbers only)
		   @param answerText, input text (letters or numbers only)
		 */
		QuestionTF(string questionText, string answerText);

		/*
		   Sets the questionText to Question's questionText

		   @param questionText, input text (letters or numbers only)
		 */
		virtual void setQuestion(string questionText);

		/*
		   Sets the answerText to Question's answerText

		   @param answerText, input text (letters or numbers only)
		 */
		virtual void setAnswer(string answerText);

		/*
		   Prints the question text to the command line
		 */
		virtual void showQuestion();

		/*
		   Prints the answer text to the command line
		 */
		virtual void showAnswer();

		/*
		   Checks givenAnswer to the answer in Question

		   @param givenAnswer, inputted answer (letters or numbers only)

		   @return bool, whether the answer is correct or not (true or false only)
		 */
		virtual bool checkAnswer(string givenAnswer);

		/*
		   Returns the question

		   @return string, question string (letters or numbers only)
		 */
		virtual string getQuestion();

		/*
		   Returns the answer

		   @return string, answer string (letter or numbers only)
		 */
		virtual string getAnswer();
};

#endif
