#!/bin/bash
#run the server
java Reversi 5000 text computer computer &

sleep 1s

#white player
java SampleClient 4444 &

sleep 1s

#black player
java SampleClient 5555 &

