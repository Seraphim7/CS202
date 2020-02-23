#ifndef QUIZ_H
#define QUIZ_H

/*
Author: Alex Novitchkov
File: quiz.h, contains the function prototypes of the class Quiz
CS202-01 asgn3
Special compiler options: -c -o
Date modified: 2/13/20

This file contains the function prototypes and the instance variable of the class Quiz
 */

#include <string>
#include <vector>
#include "question.h"

using namespace std;

class Quiz
{
	public:
		/*
		   Sets the filename variable with the input parameter

		   @param filename, data filename to open
		 */
		Quiz(string filename);

		/*
		   Loads all question in the file to private member questions

		   @param dataFileName, fileName string to parse data from (letters or numbers only)
		   @return bool whether file was opened successfully (letters or numbers only)
		 */
		bool loadQuestions(string dataFileName);

		/*
		   Dumps questions onto the console
		 */
		void dumpQuestions();

		/*
		   Delivers the quiz to the console for user interaction

		@param incorrectOnly, flag to show only question that the user got incorrect

		   @return number of questions answer correctly (int numbers only)
		 */
		int deliverQuiz(bool incorrectOnly);

		/*
		   Gets the length of the questions container

		   @return length of questions container (int numbers only)
		 */
		int getLength();

		/*
		   Sets the private member filename with the data filename

		   @param filename, data filename
		 */
		void setFilename(string filename);

		/*
		   Returns the filename

		   @return string, filename
		 */
		string getFilename();

		/*
		   Checks to see whether filename is valid and sets filenameIsValid

		   @param filename, checks whether valid
		   @return bool whether valid or not
		 */
		bool checkFilename(string filename);

		/*
		   Returns the number of answers answered correctly

		   @return int, number of answers correct
		 */
		int getNumCorrect();

		/*
		   Returns the number of answers answered incorrectly

		   @return int, number of answers incorrect
		 */
		int getNumIncorrect();

/*
Returns whether the line is valid based on different criteria

@param fileLine, a line in the file
@param the file line number

@return bool, whether valid or not
*/
		bool validateLine(string& fileLine, int& lineNumber);

/*
Checks if the given file line is a comment or a blank

@param fileLine, a line in the file

@return true or false whether the criteria is met
*/
		bool checkIfCommentOrBlank(string& fileLine);

/*
Increments blankCount

@param fileLine, a line in the file
@param blankCount, number of blanks
*/
		void checkIfBlank(string& fileLine, unsigned int& blankCount, int i);

/*
Increments seperatorCount

@param fileLine, a line in the file
@param seperatorCount, number of seperators
@param i, loop iterator
*/
		void checkIfSeperator(string& fileLine, unsigned int& seperatorCount, int i);

/*
Checks if it is a line with seperators and/or lines

@param fileLine, a line in the file
@param blankCount, number of blanks
@param seperatorCount, number of seperators
@param lineNumberString, line number
@param i, iterator
@param disregardLine, whether to disregard line

@return bool, whether the line needs to be disregarded
*/
		bool checkIfSeperatorBlankLine(string& fileLine, unsigned int& blankCount, unsigned int& seperatorCount, stringstream& lineNumberString, int i, bool disregardLine);

/*
Checks if invalid question type

@param fileLine, a line in the file
@param fieldsRequired3, question requirement of 3 fields
@param fieldsRequired4, question requirement of 4 fields
@param disregardLine, whether to disregard line

@return disregardLine, whether the line needs to be disregarded
*/
		bool checkIfInvalidQuestionType(string& fileLine, stringstream& lineNumberString, bool disregardLine);


/*
Finds specific seperator counts (3 and 4)

@param fileLine, a line in the file
@param fieldsRequired3, question requirement of 3 fields
@param fieldsRequired4, question requirement of 4 fields
@param seperatorCount, number of seperators
*/
		void findSeperatorCountForGivenType(string& fileLine, bool& fieldsRequired3, bool& fieldsRequired4, unsigned int& seperatorCount);

/*
Checks the length of a question type

@param fileLine, a line in the file
@param lineNumberString, line number
@param disregardLine, whether to disregard line

@return disregardLine, whether the line needs to be disregarded
*/		
		bool checkLengthOfQuestionType(string& fileLine, stringstream& lineNumberString, bool disregardLine);

/*
Checks if valid character in a line

@param fileLine, a line in the file
@param lineNumberString, line number
@param i, iterator
@param disregardLine, whether to disregard line

@return disregardLine, whether the line needs to be disregarded
*/
		bool checkIfValidCharacter(string& fileLine, stringstream& lineNumberString, int i, bool disregardLine);

/*
Checks the seperator count of a line

@param fileLine, a line in the file
@param fieldsRequired3, question requirement of 3 fields
@param fieldsRequired4, question requirements of 4 fields
@param seperatorCount, number of seperators
@param lineNumberString, line number
@param i, iterator
@param disregardLine, whether to disregard line

@return disregardLine, whether the line needs to be disregarded
*/
		bool checkSeperatorCountGeneral(string& fileLine, bool& fieldsRequired3, bool& fieldsRequired4, unsigned int& seperatorCount, stringstream& lineNumbeString, int i, bool disregardLine);

/*
Load the parts of a line into seperate containers

@param fileLine, a line in the file
@param type, the type of question
@param questionText, the question
@param answerText, the text of answer(s)
@param correctAnswerLetter, the correct answer letter (if applicable)
@param colonCount, how many colons in the answer line
@param lienNumber, line number

@return whether construction of question types can be done
*/
		bool loadingPartsOfLine(string& fileLine, char& type, string& questionText, string& answerText, char& correctAnswerLetter, int& correctAnswerLengthCount, int& colonCount, int& lineNumber);

/*
Constructing the questions types

@param fileLine, a line in the file
@param type, the type of question
@param questionText, the question
@param answerText, the text of answer(s)
@param correctAnswerLetter, the correct answer (if applicable)
@param colonCount, how many colons in the answer line

@return whether construction of question types was done
*/
		bool constructingQuestions(string& fileLine, char& type, string& questionText, string& answerText, char& correctAnswerLetter, int& correctAnswerLengthCount, int& colonCount, int& lineNumber);

		~Quiz();

	private:
		vector<Question*> questions;
		string filename;
		bool filenameIsValid;
};

#endif
