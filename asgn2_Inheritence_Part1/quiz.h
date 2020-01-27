#ifndef QUIZ_H
#define QUIZ_H

#include <string>
#include <vector>
#include "question.h"

using namespace std;

class Quiz
{
  public:
    Quiz();
    bool loadQuestions(string dataFileName);
    void dumpQuestions();
    int deliverQuiz(); // returns number of questions answered correctly
    int getLength();
  private:
    vector<Question> questions;
};

#endif
