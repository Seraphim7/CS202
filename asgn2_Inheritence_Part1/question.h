#ifndef QUESTION_H
#define QUESTION_H

#include <string>

using namespace std;

class Question
{
  public:
    Question();
    void showQuestion();
    void setQuestion(string questionText);
    void setAnswer(string answerText);
    bool checkAnswer(string givenAnswer);
    void showAnswer();
    void markCorrect();
  private:
    string questionText;
    string answerText;
    bool correct;
};

#endif
