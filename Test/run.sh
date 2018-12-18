#!/bin/bash

clear
rm -rf *.class

javac *.java -Xlint:unchecked
java Simulation

rm -rf *.class
