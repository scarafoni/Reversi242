#!/bin/bash 
# Use: run-example player1 player2

# Clean out the FIFOS and processes upon exit
trap '/bin/rm player1in player1out player2in player2out ; kill $(jobs -p)' EXIT

# Create FIFOs for player input and output.
# A FIFO looks like a file to other programs.
mkfifo player1in
mkfifo player1out

# Start the player processes
#"$1" <player1in >player1out &
#"$2" <player2in >player2out &
"java testClient" <player1in >player1out &

# The arguments to the gui program are file (FIFO) names.
# It opens the "out" files for input and the "in" files for output.
# Before reading from a FIFO, set a timer so that if the read waits
# for too long, the read times out and the game is over.
# When the game is over, the gui program exits, ending this script and
# cleaning up the player processes and FIFOs.
java testServer player1in
