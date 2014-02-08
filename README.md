Reversi242
==========
Welcome to the readme for Reversi RG (really good) edition. 

<yourMom> 
	We spent a week slaving over this game just so you damb ungrateful kids could complain about how hard it is.
	But no, it's fine, you're under no obligation to thank your TA's or anything.
</yourMom>

manifest:
=========
	README.md: this file.	
	Reversi.jar: the main executable for the game
	SampleClient.java: a sample program that plays the othello game

Instructions for running:
=========================
	to start, type the folowing
		$java -jar Reversi.jar timeout visual playerOne playerTwo
		where:
			-timeout is an integer that specifies how long each player has to make a move (in ms)
			-visual is either the string "gui" or "text" depending on which you want to use. There's no api for human players to use the text-based version, so you'll have to use the gui. THANKS OBAMA.
			-playerOne and playerTwo are strings that specify the players, if you want to use human players, just type in the -human flag. To use one of your programs, just pass the running instructions for your program as the variable (surround it with quotes).

	for example, if I wanted to run the game with a 5 second timeout, a guid, player one as a human and player two as the SampleClient AI, I'd enter the following:
		$java -jar Reversi.jar 5000 gui -human "SampleClient white"
		but you can do whatever you want, no pressure
		
		the client file only takes on argument, a string, either "black" or "white" specifying which color the player is. Black is player 1, white is player 2

What can I do to make my own othello-player?
============================================
	-It's always about you, isn't it? *sigh*
	-the game will send a string to your program via stdio which contains the game board. 
	-Your program then prints out a row and column as a single string to stdio (standar print) and the programwill receive it.
	-if you're program wanted to place a piece on spot 2, 3, it would output "2 3"
