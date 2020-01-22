/*
Author: Alex Novitchkov
File: mystring.cpp, implementation of class MyString
CS202-01 Assignment 1
Date: 1/18/20

defines the public members of class MyString
implemented in mystring.h
*/

#include <iostream>
#include "mystring.h"

using namespace std;

bool MyString::printAsUppercase = false;

/*--------------------*/
MyString::MyString()
{
  data = 0;
  length = 0;
}

/*----------------------------------*/
MyString::MyString(const char* str)
{
  const char* trav = str;
  length = 0;

  if (trav != 0)
  {
    while (trav[length] != 0)
    {
      length++;
    }
  }

  if (length > 0)
  {
    data = new char[length];

    for (int i = 0; i < length; i++)
    {
      data[i] = str[i];
    }
  }
}

/*-----------------------------------------------*/
MyString::MyString(const MyString& initializer)
{
  if (initializer.length <= 0)
  {
    data = 0;
    length = 0;
  }
  else
  {
    data = new char[initializer.length];
    length = initializer.length;

    for (int i = 0; i < initializer.length; i++)
    {
      data[i] = initializer.data[i];
    }
  }
}

/*-----------------------------------------------------*/
MyString MyString::operator+(const MyString& rObject)
{
  MyString sum;
  
  sum.length = length + rObject.length;
  
  sum.data = new char[sum.length];

  for (int i = 0; i < length; i++)
  {
    sum.data[i] = data[i];
  }

  for (int i = length; i < (sum.length); i++)
  {
    sum.data[i] = rObject.data[i - length];
  }

  return sum;
}

/*----------------------------------------------------*/
MyString& MyString::operator=(const MyString& rValue)
{
   if (this == &rValue) // if this (lValue) = rValue
  {
    return *this;
  }

  delete[] data;

  data = new char[rValue.length];

  length = rValue.length;

  for (int i = 0; i < length; i++)
  {
    data[i] = rValue.data[i];
  }

  return *this; // returns the new object
}

/*----------------------------------------------------------------*/
bool operator==(const MyString& lObject, const MyString& rObject)
{
  if (lObject.length < rObject.length)
  {
    return false;
  }
  else if (lObject.length > rObject.length)
  {
    return false;
  }
  else
  {
    int i = 0;
 
    while ((lObject.data[i] == rObject.data[i]) && i < lObject.length)
    {
      i++;
    }

    if (lObject.data[i] != rObject.data[i])
    {
      return false;
    }
    else
    {
      return true;
    }
  }    
}

/*----------------------------------------------------------------*/
bool operator<(const MyString& lObject, const MyString& rObject)
{
  if (lObject.length == rObject.length)
  {
    int i = 0;
 
    while ((lObject.data[i] == rObject.data[i]) && i < lObject.length)
    {
      i++;
    }

    if (lObject.data[i] >= rObject.data[i])
    {
      return false;
    }
    else
    {
      return true;
    }    
  }
  else if (lObject.length > rObject.length)
  {
    return false;
  }
  else
  {
    return true;
  }    
}

/*----------------------------------------------------------------*/
bool operator>(const MyString& lObject, const MyString& rObject)
{
  if (lObject.length < rObject.length)
  {
    return false;
  }
  else if (lObject.length == rObject.length)
  {
    int i = 0;

    while ((lObject.data[i] == rObject.data[i]) && i < lObject.length)
    {
      i++;
    }

    if (lObject.data[i] <= rObject.data[i])
    {
      return false;
    }
    else
    {
      return true;
    }   
  }
  else
  {
    return true;
  }
}

/*----------------------------------*/
MyString& MyString::operator--()
{
  if (length > 0)
  {
    length--;
  }
  
  return *this;
}

/*----------------------------------*/
MyString MyString::operator--(int)
{
  MyString original(*this);

  operator--();

  return original;
}

/*----------------------------------*/
MyString& MyString::operator++()
{
  if (length > -2) // length cannot end up to be -1 or less
  {
    length++;
  }
  
  return *this;
}

/*----------------------------------*/
MyString MyString::operator++(int)
{
  MyString original(*this);

  operator++();

  return original;
}

/*-----------------------------------------------------*/
ostream& operator<<(ostream& out, const MyString& obj)
{
  if (!MyString::printAsUppercase)
  {
    for (int i = 0; i < obj.length; i++)
    {
      out << obj.data[i];
    }
  }
  else
  {
    for (int i = 0; i < obj.length; i++)
    {
      out << char(obj.data[i] & 0xdf);
    }
  }

  out << "(" << obj.length << ")";

  return out;
}

/*-----------------------------------------------*/
istream& operator>>(istream& in, MyString& obj)
{
  int bufferSize = 50;
  int length = 0;
  bool noMoreUsefulData = false;

  delete[] obj.data; // delete the old

  obj.data = new char[bufferSize]; // construct the new

  cin.getline(obj.data, bufferSize);

  while (length < bufferSize && !noMoreUsefulData)
  {
    if (obj.data[length] == cin.eof())
    {
      noMoreUsefulData = true;
      length--;
    }

    if (obj.data[length] == ' ')
    {
      noMoreUsefulData = true;
      length--;
    }

    length++;
  }

  obj.length = length;
  
  cin.clear();

  return in;
}

/*------------------------------------*/
int MyString::getLength()
{
  return length;
}

/*------------------------------------*/
void MyString::uppercase()
{
  for (int i = 0; i < length; i++)
  {
    data[i] = data[i] & 0xdf; // changes from lowercase version to uppercase
  }
}

/*-------------------------*/
MyString::~MyString()
{
  delete[] data;
  data = 0;
}

/*---------------------------------------------*/
void MyString::setPrintAsUppercase(bool yesNo)
{
  printAsUppercase = yesNo;
}
