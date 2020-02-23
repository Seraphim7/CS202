/*
Author: Alex Novitchkov
File: quiz.cpp, implementation of the functions defined in the Quiz class
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

implementation of all the Quiz class functions
including deliverQuiz and loadQuestions
 */

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <sstream>
#include "quiz.h"
#include "question.h"
#include "questionSA.h"
#include "questionTF.h"
#include "questionMC.h"

using namespace std;

/*-----------------------*/
Quiz::Quiz(string filename)
{
	ifstream fin;
	fin.open(filename.c_str());

	try
	{
		bool valid = checkFilename(filename);

		if (valid)
		{
			this->filename = filename;
		}

		if (!fin.is_open() && valid)
		{
			cerr << "ERROR! Valid file name but cannot be opened! It either does not exist or has no read permissions! ";
			throw("Cannot load questions without file opened! ");
			fin.close();
		}
	}
	catch (const char* error)
	{
		cerr << error << "Quiz construction aborted! ";
		throw("Abort! ");
	}
}

/*---------------------------------------------*/
bool Quiz::loadQuestions(string dataFileName)
{
	ifstream fin;
	char type;
	string questionText;
	string answerText;
	char correctAnswerLetter;
	string fileLine;
	string tmp;
	int lineNumber = 0;
	string error;
	bool disregardLine;
	int colonCount = 0;
	int correctAnswerLengthCount = 0;
	bool constructQuestion = true;

	fin.open(filename.c_str());

	filename = dataFileName;

	cout << endl;

	try
	{
		if (fin.is_open())
		{
			while (getline(fin, fileLine))
			{
				fileLine.push_back('\0');
				type = '\0';
				questionText = "";
				answerText = "";
				disregardLine = false;
				colonCount = 0;
				correctAnswerLengthCount = 0;

				lineNumber++;

				/* stringParse acts like cin */
				istringstream stringParse(fileLine);

				disregardLine = validateLine(fileLine, lineNumber);

				if (!disregardLine)
				{
					constructQuestion = loadingPartsOfLine(fileLine, type, questionText, answerText, correctAnswerLetter, correctAnswerLengthCount, colonCount, lineNumber);

					if (constructQuestion)
					{
						constructQuestion = constructingQuestions(fileLine, type, questionText, answerText, correctAnswerLetter, correctAnswerLengthCount, colonCount, lineNumber);
					}
				}
			}

			fin.close();
			cout << endl;
		}
		else
		{
			throw ("File not visible! ");
		}
	}
	catch (const char* error)
	{
		cerr << error << "LoadQuestions aborting! ";
		throw ("Abort! ");
	}
	catch (string error)
	{
		cerr << error << "LoadQuestions aborting! ";
		throw ("Abort! ");
	}
	catch (...)
	{
		cerr << "Something went wrong! LoadQuestions aborting! ";
		throw ("Abort! ");
	}

	return true;
}

/*------------------------------*/
void Quiz::dumpQuestions()
{
	if (questions.empty())
	{
		throw ("No questions given! ");
	}
	else
	{
		for (unsigned int i = 0; i < questions.size(); i++)
		{
			cout << endl;
			questions[i]->showQuestion();
			questions[i]->showAnswer();
		}
	}
}

/*-------------------------------*/
int Quiz::deliverQuiz(bool showIncorrectOnly)
{
	int numCorrect = 0;
	int total = questions.size();
	int i = 0;
	string givenAnswer;
	bool correct;
	string controlString;

	cout << endl;

	while (i < total && total > 0)
	{
		if ((showIncorrectOnly == true && questions[i]->getCorrect() == false) || showIncorrectOnly == false)
		{
			questions[i]->showQuestion();

			getline(cin, givenAnswer, '\n');

			correct = questions[i]->checkAnswer(givenAnswer);

			if (correct)
			{
				questions[i]->markCorrect();
				numCorrect++;
			}
			else
			{	questions[i]->markIncorrect();
				cout << "Sorry, that is incorrect. The answer is ";
				questions[i]->showAnswer();
			}

			getline(cin, controlString, '\n');

		}
		else // If the the question was previously answered correctly, then add it into the total
		{
			numCorrect++;
		}

		i++;
	}

	if (total == 0)
	{
		cout << "No valid questions exist! Sorry! ";
		return 0;
	}

	return numCorrect;
}

/*----------------------*/
int Quiz::getLength()
{
	return questions.size();
}

/*-------------------------*/
string Quiz::getFilename()
{
	return filename;
}

/*--------------------*/
bool Quiz::checkFilename(string filename)
{
	if (filename.compare("quiz3") == 0)
	{
		throw ("Filename is not valid! The file can only contain letters, numbers, and @! ");
	}

	for (unsigned int i = 0; i < filename.size(); i++)
	{
		if ((filename.at(i) < '0' || (filename.at(i) > '9' && filename[i] < '@') || filename[i] > 'z') && filename[i] != '.')
		{
			throw ("Filename is not valid! The file can only contain letters, numbers, and @! ");
		}
	}

	filenameIsValid = true;

	return true;
}

/*-------------------*/
int Quiz::getNumCorrect()
{
	int numCorrect = 0;

	for (unsigned int i = 0; i < questions.size(); i++)
	{
		if (questions.at(i)->getCorrect() == true)
		{
			++numCorrect;
		}
	}

	return numCorrect;
}

/*-------------------*/
int Quiz::getNumIncorrect()
{
	return questions.size() - getNumCorrect();
}

/*-----------------------------------------------*/
bool Quiz::validateLine(string& fileLine, int& lineNumber)
{
	unsigned int i = 0;
	unsigned int blankCount = 0;
	unsigned int seperatorCount = 0;
	bool fieldsRequired3 = false;
	bool fieldsRequired4 = false;
	bool disregardLine = false;

	stringstream lineNumberString;
	lineNumberString << lineNumber;

	disregardLine = checkIfCommentOrBlank(fileLine);
	disregardLine = checkIfInvalidQuestionType(fileLine, lineNumberString, disregardLine);

	if (fileLine.size() > 1 && !disregardLine)
	{
		while (i < fileLine.size() && !disregardLine)
		{
			checkIfBlank(fileLine, blankCount, i);
			checkIfSeperator(fileLine, seperatorCount, i);
			disregardLine = checkLengthOfQuestionType(fileLine, lineNumberString, disregardLine); // if there are spaces before or after the type, those will make the type invalid
			disregardLine = checkIfValidCharacter(fileLine, lineNumberString, i, disregardLine);

			if (fileLine.at(i) == '\0')
			{
				findSeperatorCountForGivenType(fileLine, fieldsRequired3, fieldsRequired4, seperatorCount);
				disregardLine = checkIfSeperatorBlankLine(fileLine, blankCount, seperatorCount, lineNumberString, i, disregardLine);
				disregardLine = checkSeperatorCountGeneral(fileLine, fieldsRequired3, fieldsRequired4, seperatorCount, lineNumberString, i, disregardLine);
			}
			i++;
		}
	}


	cout << endl;

	return disregardLine;
}

/*-------------------------*/
bool Quiz::checkIfCommentOrBlank(string& fileLine)
{
	bool disregardLine = true;
	unsigned int i = 0;

	if (fileLine.at(0) == '#' || fileLine.at(0) == '\0')
	{
		return true;
	}

	while (i < fileLine.size() - 1)
	{
		if ((fileLine.at(i) == ' ' || fileLine.at(i) == '\t') && disregardLine)
		{
			disregardLine = true;
		}
		else
		{
			disregardLine = false;
		}

		i++;
	}
	return disregardLine;
}

/*-------------------------*/
void Quiz::checkIfBlank(string& fileLine, unsigned int& blankCount, int i)
{	
	if (fileLine.at(i) == ' ' || fileLine.at(i) == '\t')
	{
		blankCount++;
	}
}

/*-------------------------------------*/
void Quiz::checkIfSeperator(string& fileLine, unsigned int& seperatorCount, int i)
{
	if (fileLine.at(i) == '|')
	{
		seperatorCount++;
	}
}

/*------------------------------------------*/
bool Quiz::checkIfSeperatorBlankLine(string& fileLine, unsigned int& blankCount, unsigned int& seperatorCount, stringstream& lineNumberString, int i, bool disregardLine)
{
	if (fileLine.at(i) == '\0' && ((blankCount + seperatorCount) == (fileLine.size() - 1)) && !disregardLine && seperatorCount < 0)
	{
		cerr << "ERROR! Line " << lineNumberString.str() << " Too many seperators on blank line! Not going to be processed! ";
		return true;
	}
	else if (disregardLine)
	{
		return true;
	}
	return false;
}

/*------------------------------------------------------*/
bool Quiz::checkIfInvalidQuestionType(string& fileLine, stringstream& lineNumberString, bool disregardLine)
{	
	if (fileLine.at(0) != 'S' && fileLine.at(0) != 's' && fileLine.at(0) != 'M' && fileLine.at(0) != 'm' && fileLine.at(0) != 'T' && fileLine.at(0) != 't' && fileLine.at(0) != ' ' && !disregardLine)
	{
		cerr << "ERROR! Line " << lineNumberString.str() << " has an invalid type! Not going to be processed! ";
		return true;
	}
	else if (disregardLine)
	{
		return true;
	}
	return false;
}

/*-------------------------------------------------------*/
void Quiz::findSeperatorCountForGivenType(string& fileLine, bool& fieldsRequired3, bool& fieldsRequired4, unsigned int& seperatorCount)
{	
	if (seperatorCount == 4 && (fileLine.at(0) == 'S' || fileLine.at(0) == 's' || fileLine.at(0) == 'T' || fileLine.at(0) == 't'))
	{
		fieldsRequired3 = true;
	}
	else if (seperatorCount == 3 && (fileLine.at(0) == 'M' && fileLine.at(0) == 'm'))
	{
		fieldsRequired4 = true;
	}
}

/*----------------------------------------------*/
bool Quiz::checkLengthOfQuestionType(string& fileLine, stringstream& lineNumberString, bool disregardLine)
{
	if (fileLine.size() > 1 && !disregardLine)
	{
		if (fileLine.at(1) != '|') // must have a seperator in second spot in fileLine
		{
			cerr << "ERROR! Line " << lineNumberString.str() << " has to have 1 character for its type! Not going to be processed! ";
			return true;
		}
	}
	else if (disregardLine)
	{
		return true;
	}
	return false;
}

/*----------------------------------------------------------------------------------------------------------*/
bool Quiz::checkIfValidCharacter(string& fileLine, stringstream& lineNumberString, int i, bool disregardLine)
{
	if (fileLine.at(i) < ' ' && fileLine.at(i) != '\0' && fileLine.at(i) != '\t' && !disregardLine)
	{
		cerr << "ERROR! Line " << lineNumberString.str() << " has invalid chars! Not going to be processed! ";
		return true;
	}
	else if (disregardLine)
	{
		return true;
	}
	return false;
}

/*------------------------------------------------*/
bool Quiz::checkSeperatorCountGeneral(string& fileLine, bool& fieldsRequired3, bool& fieldsRequired4, unsigned int& seperatorCount, stringstream& lineNumberString, int i, bool disregardLine)
{
	if (fileLine.at(i) == '\0' && ((fieldsRequired4 || fieldsRequired3) || ((seperatorCount >= 0 && seperatorCount < 3) || seperatorCount > 4)) && !disregardLine)
	{
		cerr << "ERROR! Line " << lineNumberString.str() << " has " << seperatorCount << " seperators! Has to be either 3 or 4! Not going to be processed! ";
		return true;
	}
	else if (disregardLine)
	{
		return true;
	}
	return false;
}


/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
bool Quiz::loadingPartsOfLine(string& fileLine, char& type, string& questionText, string& answerText, char& correctAnswerLetter, int& correctAnswerLengthCount, int& colonCount, int& lineNumber)
{
	bool dropCharacter = false;
	int count = 1;
	bool constructQuestion = true;
	unsigned int i = 0;

	while (i < fileLine.size() - 1 && constructQuestion)
	{
		if (fileLine.at(i) == '|')
		{
			count++;
			dropCharacter = true;
		}
		else
		{
			dropCharacter = false;
		}

		if (count == 1 && !dropCharacter)
		{
			type = fileLine.at(i);
		}
		else if (count == 2 && !dropCharacter && (fileLine.at(i) < '0' || fileLine.at(i) > '9'))
		{
			cerr << "ERROR: line " << lineNumber << " does not have a valid level! A valid level must be between 1 and 9 (inclusive)! Not going to be processed! ";
			constructQuestion = false;
		}
		else if (count == 3 && !dropCharacter)
		{
			questionText = questionText + fileLine.at(i);
		}
		else if (count == 4 && !dropCharacter)
		{
			answerText = answerText + fileLine.at(i);

			if (fileLine.at(i) == ':')
			{
				colonCount++;
			}
		}
		else if (count == 5 && !dropCharacter && (fileLine.at(0) == 'M' || fileLine.at(0) == 'm'))
		{
			if (fileLine.at(i) >= 'a' && fileLine.at(i) <= 'z')
			{
				correctAnswerLetter = fileLine.at(i) & 0xdf;
				correctAnswerLengthCount++;
			}
			else
			{
				correctAnswerLetter = fileLine.at(i);
				correctAnswerLengthCount++;
			}
		}
		i++;

	}
	return constructQuestion;
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
bool Quiz::constructingQuestions(string& fileLine, char& type, string& questionText, string& answerText, char& correctAnswerLetter, int& correctAnswerLengthCount, int& colonCount, int& lineNumber)
{
	bool constructQuestion;

	if (type == 'S' || type == 's' || type == 'T' || type == 't')
	{	
		for (unsigned int i = 0; i < answerText.size(); i++)
		{
			answerText[i] = answerText[i] & 0xdf;
		}
	}

	if ((type == 'S' || type == 's') && colonCount == 0)
	{
		questionText = questionText + "?";

		Question* question = new QuestionSA(questionText, answerText);
		questions.push_back(question);
	}
	else if ((type == 'T' || type == 't') && colonCount == 0)
	{
		questionText = questionText + ". (True or False?)";

		QuestionTF* question = new QuestionTF(questionText, answerText);
		questions.push_back(question);
	}
	else if ((type == 'M' || type == 'm') && correctAnswerLengthCount == 1)
	{
		questionText = questionText + "?";

		QuestionMC* question = new QuestionMC(questionText, answerText, correctAnswerLetter);
		questions.push_back(question);
	}
	else if ((type == 'M' || type == 'm') && correctAnswerLengthCount != 1)
	{
		cerr << "ERROR! Line " << lineNumber << " There should only be 1 correct answer! Not going to be processed! ";
		constructQuestion = false;
	}
	else if (((correctAnswerLetter >> 1) & 0x01) <= colonCount + 1)
	{
		cerr << "ERROR! Line " << lineNumber << " There is no answer of " << correctAnswerLetter << "! Not going to be processed! ";
		constructQuestion = false;
	}

	return constructQuestion;
}

/*--------------*/
Quiz::~Quiz()
{
	for (int i = 0; i < getLength(); i++)
	{	
		delete questions.at(i);
	}
	questions.clear();
}
