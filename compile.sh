#!/bin/bash

rm *.class
cp *.java whitePlayer/
cd whitePlayer
javac SampleClient.java
cd ..
cp *.java blackPlayer/
cd blackPlayer
javac SampleClient.java
cd ..
javac Reversi.java

