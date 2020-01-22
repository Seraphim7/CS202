/*
Author: Alex Novitchkov
File: mystring.h, the definition of the MyString class 
CS202-01 Assignment 1
Date: 1/18/20

Contains a MyString class whch contains a char*
and some operator overload functions
*/

#include <iostream>

using namespace std;

/* Contains a copy of the string operated on */
class MyString
{
  public:
    /*
      Default constructor for default purposes
    */
    MyString();

    /*
      constructor accespting 1 argument, a literal cString

      @param str, a cString being fed in to variable data
    */
    MyString(const char* str);

    /*
      initializes a MyString object from another myString object, copies

      @param myString, a MyString object
    */
    MyString(const MyString& myString);

    /*
      defining operator + to be able to add 2 MyString objects,
      based on cStrings

      @param rObject, right hand MyString object being added
      @return MyString, returns the resulting MyString object, the sum
    */
    MyString operator+(const MyString& rObject);
   

    /*
      defining operator = to be able to copy a
      MyString object to an already initialized one

      @param rValue, a MyString object that is being to copy data
      @return MyString, a copied MyString object from variable rValue
    */
    MyString& operator=(const MyString& rValue);    
   
    /*
      overloads prefix operator -- to decrement the variable, length, by 1

      @return MyString&, object being operated on
    */
    MyString& operator--();

    /*
      overloads postfix operator -- to decrement the variable, length, by 1
      
      @return MyString&, object being operated on
    */
    MyString operator--(int);

    /*
      overloads prefix operator ++ to increment the variable, length, by 1
      
      @return MyString&, object being operated on
    */
    MyString& operator++();

    /*
      overloads postfix operator ++ to increment the variable, length, by 1
      
      @return MyString&, object being operated on
    */
    MyString operator++(int);
   
   /*
      defining operator == to be able to tell whether
      lObject is greater than rObject based on their cStrings

      @param lObject, left hand MyString object being compared
      @param rObject, right hand MyString object being compared against
      @return bool, true or false depending on if equal or not
    */
    friend bool operator==(const MyString& lObject, const MyString& rObject);

    /*
      defining operator > to be able to tell whether
      lObject is greater than rObject based on their cStrings

      @param lObject, left hand MyString object being compared
      @param rObject, right hand MyString object being compared against
      @return bool, true or false depending on if greater or not
    */
    friend bool operator>(const MyString& lObject, const MyString& rObject);
  
    /*
      defining operator > to be able to tell whether
      lObject is less than rObject based on their cStrings
      
      @param lObject, left hand MyString object being compared
      @param rObject, right hand MyString object being compared against
      @return bool, true or false depending on if less or not
    */
    friend bool operator<(const MyString& lObject, const MyString& rObject);

    /*
      defines operator << to be able to write out a MyString object
      
      @param out, ostream object to output myString
      @param myString, MyString object to be written to by variable out
      @return out to be able to continue outputing data
    */
    friend ostream& operator<<(ostream& out, const MyString& myString);

    /*
      defines operator >> to be able to read into a MyString object

      @param in, istream object for input into myString
      @param myString, MyString object to be read into by varable in
      @return in to be able to continue reading in data
    */
    friend istream& operator>>(istream& in, MyString& myString);

    /*
      turns the member cString to uppercase
    */
    void uppercase();

    /*
      returns the length of the member cString
      
      @return int containing the length
    */
    int getLength();

    /*
      deletes dynamically alocatted memory used by char* data
    */
    ~MyString();

    /*
      sets static variable printAsUppercase to yes or no

      @param yesNo, bool to set static variable printAsUppercase
    */
    static void setPrintAsUppercase(bool yesNo);
    
  private:
    char* data;
    int length;
    static bool printAsUppercase;
};
