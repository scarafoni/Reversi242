#!/bin/bash
#run the server
java Reversi 100 d d d &
#white player
java $1 4444 &
#black
java $2 5555 &
