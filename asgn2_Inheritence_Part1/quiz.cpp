#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <sstream>
#include "quiz.h"
#include "question.h"

using namespace std;

/*-----------------------*/
Quiz::Quiz()
{
 
}

/*---------------------------------------------*/
bool Quiz::loadQuestions(string dataFileName)
{
  ifstream fin;
  string tmp;
  Question newQuestion;
  string fileLine;
  
  fin.open(dataFileName.c_str()); // has to take in a cString equivelant

  int count;

  if (fin.is_open())
  {
    while (getline(fin, fileLine)) // fin.eof() was giving me an infinite loop
    {
      count = 0;

      istringstream stringParse(fileLine);

      while (getline(stringParse, tmp, '|'))
      {
        count++;
  
        if (count == 3)
        {
          newQuestion.setQuestion(tmp);
        }
        else if (count == 4)
        {
          newQuestion.setAnswer(tmp);
          questions.push_back(newQuestion);
        }
      }
    }
  }
  else
  {
    fin.close();
    cout << "ERROR while opening file: " << dataFileName << endl;
    return false;
  }

  return true;
}

/*------------------------------*/
void Quiz::dumpQuestions()
{
  if (questions.empty())
  {
    cout << "No questions given" << endl;
  }
  else
  {
    for (unsigned int i = 0; i < questions.size(); i++)
    {
      questions[i].showQuestion();
    }
  }
}

/*-------------------------------*/
int Quiz::deliverQuiz()
{
  int numCorrect = 0;
  int total = questions.size();
  int i = 0;
  string givenAnswer;
  bool correct;
  string controlString;

  cout << endl;
  
  while (i < total)
  {
    questions[i].showQuestion();

    getline(cin, givenAnswer);

    correct = questions[i].checkAnswer(givenAnswer);

    if (correct)
    {
      questions[i].markCorrect();
      numCorrect++;
    }
    else
    {
      cout << "Sorry, the answer is ";
      questions[i].showAnswer();
    }

    i++;

    getline(cin, controlString, '\n');
  }

  return numCorrect;
}

/*----------------------*/
int Quiz::getLength()
{
  return questions.size();
}
