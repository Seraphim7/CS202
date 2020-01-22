COMPILER = g++
OPTIONS = -c -g -Wall

stringTest: main.o mystring.o 
	$(COMPILER) main.o mystring.o -o stringTest

main.o: main.cpp mystring.h 
	$(COMPILER) $(OPTIONS) main.cpp

mystring.o: mystring.cpp mystring.h
	$(COMPILER) $(OPTIONS) mystring.cpp

clean:
	rm *.o 
