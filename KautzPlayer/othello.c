#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>
#define FALSE 0
#define TRUE 1

typedef signed char board[8][8];

int me;
int depthlimit, timelimit1, timelimit2;
board gamestate;

int debug = FALSE;
int turn;

void error(char * msg)
{
    fprintf(stderr,"%s\n", msg);
    exit(-1);
}


void printboard(board state, int player, int turn, int X, int Y)
{
    int i, j, num;
    num = 64;

    fprintf(stderr, "%2d. %c plays (%2d, %2d)\n", turn, (player==1 ? 'B' : 'W'), X, Y);
    for(i = 0; i < 8; i ++) {
 	for(j = 0; j < 8; j++){
	    fprintf(stderr, "%4d", state[i][j]);
	    if(state[i][j]) num --;
 	}
 	printf("\n");
    }
    printf("number of blanks = %d\n", num);
}


void NewGame(void)
{
    int X,Y;
    for (X=0; X<8; X++)
	for (Y=0; Y<8; Y++)
	    gamestate[X][Y] = 0;
    gamestate[3][3] = -1;
    gamestate[4][4] = -1;
    gamestate[3][4] = 1;
    gamestate[4][3] = 1;
}





int CanFlip(board state, int player, int X, int Y, int dirX, int dirY) 
{
    int capture = FALSE;
    while (X+dirX < 8 && X+dirX >= 0 && Y+dirY < 8 && Y+dirY >= 0 && state[X+dirX][Y+dirY]==-player) {
        X = X+dirX; Y = Y+dirY;
        capture = TRUE;
    }
    if (capture == FALSE) return FALSE;
    if (X+dirX < 8 && X+dirX >= 0 && Y+dirY < 8 && Y+dirY >= 0 && state[X+dirX][Y+dirY]==player)
        return TRUE;
    else return FALSE;
}


int Legal(board state, int player, int X, int Y)
{
    int i,j, captures;
    captures = 0;
    if (state[X][Y]!=0) return FALSE;
    for (i=-1; i<=1; i++)
	for (j=-1; j<=1; j++)
	    if ((i!=0 || j!=0) && CanFlip(state, player, X, Y, i, j))
		return TRUE;
    return FALSE;
}


void DoFlip(board state, int player, int X, int Y, int dirX, int dirY)
{
    while (X+dirX < 8 && X+dirX >= 0 && Y+dirY < 8 && Y+dirY >= 0 && state[X+dirX][Y+dirY]==-player) {
        X = X+dirX; Y = Y+dirY;
        state[X][Y] = player;
    }
}

void Update(board state, int player, int X, int Y)
{
    int i,j;

    if (X<0) return; /* pass move */
    if (state[X][Y] != 0) {
	printboard(state, player, turn, X, Y);
	error("Illegal move");
    }
    state[X][Y] = player;
    for (i=-1; i<=1; i++)
	for (j=-1; j<=1; j++)
	    if ((i!=0 || j!=0) && CanFlip(state, player, X, Y, i, j))
		DoFlip(state, player, X, Y, i, j);
}

    

void Result(board oldstate, board newstate, int player, int X, int Y)
{
    int i, j;
    for (i=0; i<8; i++)
	for (j=0; j<8; j++)
	    newstate[i][j] = oldstate[i][j];
    Update(newstate, player, X, Y);
}

int GameOver(board state)
{
    int X,Y;

    for (X=0; X<8; X++)
	for (Y=0; Y<8; Y++) {
	    if (Legal(state, 1, X, Y)) return FALSE;
	    if (Legal(state, -1, X, Y)) return FALSE;
	}
    return TRUE;
}

int CutoffTest(board state, int depth)
{
    if (GameOver(state) || (depthlimit > 0 && depth > depthlimit))
        return 1;
    return 0;
}

int Evaluate(board state)
{
    int X,Y,score;
    score = 0;

    if (GameOver(state)){
	for (X=0; X<8; X++)
	    for (Y=0; Y<8; Y++)
		score += state[X][Y];
	if (score==0) return 0;
	else if (score>0) return 1000000;
	else return -1000000;
    }
    for (X=0; X<8; X++)
	for (Y=0; Y<8; Y++){
	    score += state[X][Y];
	    if ((X==0 || X==7) && (Y==0 || Y==7)) score += state[X][Y] * 4;
	}
    return score;
}


int Actions(board state, int player, int Xacts[], int Yacts[])
{
    int X, Y, n;
    n = 0;
    for (X=0; X<8; X++)
        for (Y=0; Y<8; Y++)
	    if (Legal(state, player, X, Y)){
		Xacts[n] = X; Yacts[n] = Y;
		n++;
	    }
    if (n==0){
	Xacts[n] = -1; Yacts[n] = -1;
	n=1;
    }
    return n;
}



int AlphaBeta(board state, int depth, int alpha, int beta, int player, int * bestX, int * bestY)
{
    int X,Y, numacts, i, value;
    int Xacts[64], Yacts[64];
    board child;
    
    if (CutoffTest(state, depth)) return Evaluate(state);
    numacts = Actions(state, player, Xacts, Yacts);

    if (player == 1){
	for (i=0; i<numacts; i++) {
	    Result(state, child, player, Xacts[i], Yacts[i]);
            value = AlphaBeta(child, depth+1, alpha, beta, -player, &X, &Y);
            if (value > alpha) {
                alpha = value;
		*bestX = Xacts[i]; *bestY = Yacts[i];
	    }
            if (beta <= alpha)
                break;
	}
	return alpha;
    } else {
	for (i=0; i<numacts; i++) {
	    Result(state, child, player, Xacts[i], Yacts[i]);
            value = AlphaBeta(child, depth+1, alpha, beta, -player, &X, &Y);
            if (value < beta) {
                beta = value;
		*bestX = Xacts[i]; *bestY = Yacts[i];
	    }
            if (beta <= alpha)
                break;
	}
	return beta;
    }
}



void MakeMove(void)
{
    int X,Y;
    (void) AlphaBeta(gamestate, 0, -10000000, 10000000, me, &X, &Y);
    if (X>=0) {
	Update(gamestate, me, X, Y);
	printf("%d %d\n", X, Y);
	fflush(stdout);
    } else {
	printf("pass\n");
	fflush(stdout);
    }
    if (debug) printboard(gamestate, me, ++turn, X, Y);
}


int main(int argc, char** argv)
{
    char inbuf[256];
    char playerstring[1];
    int X,Y;

    if (argc >= 2 && strncmp(argv[1],"-d",2)==0) debug = TRUE;
    turn = 0;
    fgets(inbuf, 256, stdin);
    if (sscanf(inbuf, "game %1s %d %d %d", playerstring, &depthlimit, &timelimit1, &timelimit2) != 4) error("Bad initial input");
    if (playerstring[0] == 'B') me = 1; else me = -1;
    NewGame();
    if (me == 1) MakeMove();
    while (fgets(inbuf, 256, stdin)!=NULL){
	if (strncmp(inbuf,"pass",4)!=0) {
	    if (sscanf(inbuf, "%d %d", &X, &Y) != 2) return 0;
	    Update(gamestate, -me, X, Y);
	    if (debug) printboard(gamestate, -me, ++turn, X, Y);
	}
	MakeMove();
    }
    return 0;
}

    



