/*
Author: Alex Novtichkov
File: main.cpp, tests different operations on a MyString object
CS202-01 Assignment 1
Date: 1/18/20

This file contains the controller (main) of the program
*/

#include <iostream>
#include "mystring.h"

using namespace std;

int main(int argc, char* argv[])
{
  int numArgs = argc - 1;

  if (numArgs == 0)
  {
    cout << "ERROR: No parameters given" << endl;

    return 0;
  }
  else
  {
    MyString* arrayOfObjects = new MyString[numArgs];
  
    for (int i = 0; i < numArgs; i++)
    {
      arrayOfObjects[i] = (argv[i + 1]);
  
      cout << arrayOfObjects[i] << " ";
    }

    cout << endl;

    if (arrayOfObjects[0] == arrayOfObjects[numArgs - 1])
    {
      cout << "The first string (" << arrayOfObjects[0] 
           << ") is equal to the last string ("
           << arrayOfObjects[numArgs - 1] << ")" << endl;
    }
    else if (arrayOfObjects[0] > arrayOfObjects[numArgs - 1])
    {  
      cout << "The first string (" << arrayOfObjects[0] 
           << ") is greater than the last string ("
           << arrayOfObjects[numArgs - 1] << ")" << endl;
    }
    else
    { 
      cout << "The first string (" << arrayOfObjects[0] 
           << ") is less than the last string (" 
           << arrayOfObjects[numArgs - 1] << ")" << endl;
    }

    for (int i = 0; i < numArgs; i++)
    {
      arrayOfObjects[i]--;
      cout << arrayOfObjects[i] << " ";
    }

    cout << endl;
    
    MyString combined;

    /*
       if 1 element, arrayOfObjects[numArgs - 1] will already be modified
       so arrayOfObjects[0] will be modified from its already modified state
    */
    if (numArgs != 1) 
    {
      combined = arrayOfObjects[0]-- + --arrayOfObjects[numArgs - 1];
    }
    else
    {
      MyString original = arrayOfObjects[0];

      combined = arrayOfObjects[0]-- + --original;
    }
    /*--------------------------------------------------------------------*/

    cout << combined << endl;

    combined.uppercase();

    cout << combined << endl;

    MyString::setPrintAsUppercase(true);

    for (int i = 0; i < numArgs; i++)
    {
      cout << arrayOfObjects[i] << " ";
    }

    cout << endl;

    return 1;
  }
}
