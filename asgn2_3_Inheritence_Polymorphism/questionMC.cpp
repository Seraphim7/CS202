/*
Author: Alex Novitchkov
File: questionMC.cpp, the implementation of class QuestionMC
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

Contains the constructors, check, show and more functions pertaining to QuestionsMC
 */

#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include "questionMC.h"
#include "question.h"

/*----------------------*/
QuestionMC::QuestionMC()
{

}

/*---------------------------------------------------------------------------------------*/
QuestionMC::QuestionMC(string questionText, string answerLine, char correctAnswerLetter)
{
	string token;
	string correctAnswer;
	int count = 0;
	char letter;
	int correctAnswerNumber = (int) correctAnswerLetter & 0x3f; // converts char to int successfully from 0-15
	string correctAnswerLetterAsString;
	istringstream answers(answerLine);

	while (getline(answers, token, ':'))
	{
		count++;
		letter = (char) count | 0x60; // converts 1 to a, 2 to b, 3 to c, etc.

		questionText = questionText + "\n\t" + letter + ") " + token;

		if (count == correctAnswerNumber)
		{
			correctAnswer = token;
			correctAnswerLetterAsString = correctAnswerLetter;
			Question::setAnswer(correctAnswerLetterAsString);
		}
		else
		{
			wrongAnswers.push_back(token);
		}
	}

	Question::setQuestion(questionText);
}

/*------------------------------------------------*/
void QuestionMC::setQuestion(string questionText, string answerLine)
{
	string token;
	istringstream answers(answerLine);
	int count = 0;
	char letter;

	while (getline(answers, token, ':'))
	{
		count++;

		letter = (int) count | 0x60;

		questionText = questionText + "\n" + letter + ")" + token;
	}

	Question::setQuestion(questionText);
}

/*-----------------------------------------------------------------------*/
void QuestionMC::setAnswer(string answerLine, char correctAnswerLetter)
{
	string token;
	string correctAnswer;
	int count = 0;

	/* converts successfully from 0-15 */
	int correctAnswerNumber = (int) (correctAnswerLetter & 0x0f);

	istringstream answers(answerLine);

	while (getline(answers, token, ':'))
	{
		count++;

		if (count == correctAnswerNumber)
		{
			Question::setAnswer(token);
		}
		else
		{
			wrongAnswers.push_back(token);
		}
	}
}

/*------------------------------------------*/
string QuestionMC::getQuestion()
{
	return Question::getQuestion();
}

/*------------------------------------*/
string QuestionMC::getCorrectAnswer()
{
	return Question::getAnswer();
}

/*--------------------------------*/
void QuestionMC::showQuestion()
{
	cout << Question::getQuestion() << endl;
}

/*---------------------------------*/
void QuestionMC::showAnswer()
{
	cout << Question::getAnswer() << endl;
}

/*-------------------------------------*/
bool QuestionMC::checkAnswer(string givenAnswer)
{
	givenAnswer[0] = givenAnswer[0] & 0xdf;

	if (givenAnswer.compare(Question::getAnswer()) == 0)
	{
		return true;
	}	
	return false;
}
