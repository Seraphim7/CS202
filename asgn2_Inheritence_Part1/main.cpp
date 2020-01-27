#include <iostream>
#include <vector>
#include <string>
#include "quiz.h"
#include <iomanip>

using namespace std;

/*---------------------------------------------------*/
float calculatePercentage(int correct, int total);

/*----------------------------------------------------------*/
void finalMessage(int correct, int total, float percentage);

/*----------------------------------*/
int main(int argc, char* argv[])
{
  string dataFileName;
  Quiz aQuiz;
  int numCorrect;
  int totalQuestions;
  bool fileOpened;
  float percentage;

  if (argc < 2)
  {
    cout << "ERROR! Not enough arguments. 2 must be given!" << endl;
  }
  else if (argc > 2)
  {
    cout << "ERROR! Too many arguments. Only 2 must be given!" << endl;
  }
  else
  {
    dataFileName = argv[1];

    fileOpened = aQuiz.loadQuestions(dataFileName);
    
    if (fileOpened)
    {
      cout << endl;
      cout << "Hope your brain's warmed up, it's Quiz Time!!!" << endl;
      cout << "After each aswer is displayed, ";
      cout << "press enter to see the next question. " << endl;

      numCorrect = aQuiz.deliverQuiz();

      totalQuestions = aQuiz.getLength();

      percentage = calculatePercentage(numCorrect, totalQuestions);

      finalMessage(numCorrect, totalQuestions, percentage);
    }
  }

  return 1;
}

/*---------------------------------------------------*/
float calculatePercentage(int correct, int total)
{
  float percentageCorrect;

  percentageCorrect = ((float) correct / (float) total) * 100.0;

  return percentageCorrect;
}

/*-----------------------------------------------------------*/
void finalMessage(int correct, int total, float percentage)
{
  cout << "You got " << correct << " of "
       << total << " correct: "
       << setprecision(4) << percentage << "%.";

  if (percentage < 60)
  {
    cout << "   Better study more!" << endl;
  }
  else
  {
    cout << "   Great work!" << endl;
  }
}
