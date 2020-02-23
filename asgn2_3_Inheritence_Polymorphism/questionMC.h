#ifndef QUESTIONMC_CPP
#define QUESTIONMC_CPP

/*
Author: Alex Novitchkov
File: QuestionMC.h, definitions of class QuestionMC, MC standing for multiple choice.
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

Contains virtual and non-virtual class definitions for showQuestion, showAnswer, checkAnswer, and more.
 */

#include <string>
#include <vector>
#include "question.h"

using namespace std;

class QuestionMC : public Question
{
	public:
		QuestionMC();

		/*
		   Puts incorrect answers into variable wrongAnswers and the right answer is plugged into Question's answer

		   @param questionText, a question (letters or numbers only)
		   @param answerLine, a line of answers (letters or numbers only)
		   @param correctAnswerLetter, a letter denoting the correct answer (letter only)
		 */
		QuestionMC(string questionText, string answerLine, char correctAnswerLetter);


		/*
		   Sets the contents of question based on the input text

		   @param questionText, input text (letters or numbers only)
		   @param answerLine, input line of text (letters only)
		 */
		void setQuestion(string questionText, string answerLine);

		/*
		   Sets the contents of answer and wrongAnswers to answerLine

		   @param answerLine, input line of text (letters or numbers only)
		   @param correctAnswerLetter, correct answer letter (letter only)
		 */
		void setAnswer(string answerLine, char correctAnswerLetter);

		/*
		   Gets the questionText

		   @return string, questionText (letters or numbers only)
		 */
		virtual string getQuestion();

		/*
		   Gets the correctAnswer as a string

		   @return string, correctAnswer (letters or numbers only)
		 */
		string getCorrectAnswer();

		/*
		   Prints out the question to the command line
		 */
		virtual void showQuestion();

		/*
		   Prints out the answer to the command line
		 */
		virtual void showAnswer();

		/*
		   Checks the answer whether true or false

		   @param givenAnswer, inputted answer (letters or numbers only)

		   returns bool whether correct or not (true or false only)
		 */
		virtual bool checkAnswer(string givenAnswer);
	private:
		vector<string> wrongAnswers;
};

#endif
