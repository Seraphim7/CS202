#include <string>
#include <iostream>
#include "question.h"

using namespace std;

/*------------------*/
Question::Question()
{
  questionText = "\0";
  answerText = "\0";
  correct = false;
}

/*-------------------------------*/
void Question::showQuestion()
{
  cout << questionText << "?" << endl;
}

/*---------------------------------------------*/
bool Question::checkAnswer(string givenAnswer)
{
  if (givenAnswer == answerText)
  {
    correct = true;
    return true;
  }
  
  correct = false;
  return false;
}

/*------------------------------------------*/
void Question::setQuestion(string questionText)
{
  this->questionText = questionText;
}

/*---------------------------------------*/
void Question::setAnswer(string answerText)
{
  this->answerText = answerText;
}

/*---------------------------------*/
void Question::showAnswer()
{
  cout << answerText << "." << endl;
}

/*------------------------------*/
void Question::markCorrect()
{
  if (correct)
  {
    cout << "Correct! Great job!" << endl;
  }
} 
