/*
Author: Alex Novitchkov
File: question.cpp, contains the implementation of the class Question methods
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

Contains all Question related class function including the question and answer setters and checkAnswer.
 */

#include <string>
#include <iostream>
#include "question.h"

using namespace std;

/*-------------------*/
Question::Question()
{

}

/*---------------------------------------------------------*/
Question::Question(string questionText, string answerText)
{
	this->questionText = questionText;
	this->answerText = answerText;
	correct = false;
}

/*---------------------------------------------*/
void Question::setQuestion(string questionText)
{
	this->questionText = questionText;
}

/*-----------------------------------------*/
void Question::setAnswer(string answerText)
{
	this->answerText = answerText;
}

/*------------------------------*/
void Question::markCorrect()
{
	correct = true;

	if (correct)
	{
		cout << "Correct! Great job!" << endl;
	}
}

/*----------------------------*/
void Question::markIncorrect()
{
	correct = false;
}

/*----------------------------*/
string Question::getQuestion()
{
	return questionText;
}

/*--------------------------*/
string Question::getAnswer()
{
	return answerText;
}

/*-------------------------*/
bool Question::getCorrect()
{
	return correct;
}

/*-----------------------*/
Question::~Question() {}
