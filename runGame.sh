#!/bin/bash 
# Use: run-example player1 player2

# Clean out the FIFOS and processes upon exit
trap '/bin/rm player1in player1out player2in player2out ; kill $(jobs -p)' EXIT
p1="java SampleClient 4444"
p2="java SampleClient 5555"
# Create FIFOs for player input and output.
# A FIFO looks like a file to other programs.
#mkfifo player1in
#mkfifo player1out
#mkfifo player2in
#mkfifo player2out

#touch player1in
#touch player1out
#touch player2in
#touch player2out

# Start the player processes
#($p1) < player1in & #> player1out &
#($p2) < player2in > player2out &

# The arguments to the gui program are file (FIFO) names.
# It opens the "out" files for input and the "in" files for output.
# Before reading from a FIFO, set a timer so that if the read waits
# for too long, the read times out and the game is over.
# When the game is over, the gui program exits, ending this script and
# cleaning up the player processes and FIFOs.
java Reversi 50000 text computer computer player1in player1out player2in player2out
