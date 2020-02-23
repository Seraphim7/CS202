/*
Author: Alex Novitchkov
File: questionTF.cpp, contains implentations of the QuestionTF class
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

Contains functions such as showQuestion, showAnswer, checkAnswer, and others
 */

#include <iostream>
#include <string>
#include "questionTF.h"
#include "question.h"

using namespace std;

/*-------------------------*/
QuestionTF::QuestionTF() {}

/*------------------------------------------------------------*/
QuestionTF::QuestionTF(string questionText, string answerText)
	: Question(questionText, answerText) {}

	/*-----------------------------------------------*/
void QuestionTF::setQuestion(string questionText)
{
	Question::setQuestion(questionText);
}

/*--------------------------------------------*/
void QuestionTF::setAnswer(string answerText)
{
	Question::setAnswer(answerText);
}

/*-------------------------------*/
string QuestionTF::getQuestion()
{
	return Question::getQuestion();
}

/*-----------------------------*/
string QuestionTF::getAnswer()
{
	return Question::getAnswer();
}

/*------------------------------*/
void QuestionTF::showQuestion()
{
	cout << Question::getQuestion() << endl;
}

/*----------------------------*/
void QuestionTF::showAnswer()
{
	cout << Question::getAnswer() << endl;
}

/*-----------------------------------------------*/
bool QuestionTF::checkAnswer(string givenAnswer)
{
	if (givenAnswer.size() == 1)
	{
		if (givenAnswer[0] == 'T' || givenAnswer[0] == 't')
		{
			givenAnswer = "TRUE";
		}
		else if (givenAnswer[0] == 'F' || givenAnswer[0] == 'f')
		{
			givenAnswer = "FALSE";
		}
	}
	else
	{
		for (unsigned int i = 0; i < givenAnswer.size(); i++)
		{
			givenAnswer[i] = givenAnswer[i] & 0xdf;
		}
	}

	if (givenAnswer.compare(getAnswer()) == 0)
	{
		return true;
	}
	return false;
}
