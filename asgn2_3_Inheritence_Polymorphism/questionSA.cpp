/*
Author: Alex Novitchkov
File: QuestionSA.cpp, implementation for the QuestionSA class
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/20/20

Contains functions such as showQuestion, checkAnswer and more
 */

#include <iostream>
#include <string>
#include "questionSA.h"
#include "question.h"

using namespace std;

/*------------------------------------------------------------*/
QuestionSA::QuestionSA(string questionText, string answerText)
	: Question(questionText, answerText)
{

}

/*------------------------------------------------*/
void QuestionSA::setQuestion(string questionText)
{
	Question::setQuestion(questionText);
}

/*-------------------------------------------*/
void QuestionSA::setAnswer(string answerText)
{
	Question::setAnswer(answerText);
}

/*-------------------------------*/
string QuestionSA::getQuestion()
{
	return Question::getQuestion();
}

/*----------------------------*/
string QuestionSA::getAnswer()
{
	return Question::getAnswer();
}

/*-----------------------------*/
void QuestionSA::showQuestion()
{
	cout << Question::getQuestion() << endl;
}

/*---------------------------*/
void QuestionSA::showAnswer()
{
	cout << Question::getAnswer() << endl;
}

/*----------------------------------------------*/
bool QuestionSA::checkAnswer(string givenAnswer)
{
	for (unsigned int i = 0; i < givenAnswer.size(); i++)
	{
		givenAnswer[i] = givenAnswer[i] & 0xdf;
	}

	if (givenAnswer.compare(getAnswer()) == 0)
	{
		return true;
	}
	return false;
}
