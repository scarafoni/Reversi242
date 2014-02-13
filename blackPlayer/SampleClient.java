import java.io.*;
import java.net.*;
import java.util.*;

public class SampleClient {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
				ReversiBoard board = new ReversiBoard("nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,white,black,nil,nil,nil,nil,nil,nil,black,white,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,nil,") ;
				TKind myColor;
				myColor =  args[0].equals("black") ? TKind.black : TKind.white;

				PrintWriter files = new PrintWriter(new BufferedWriter(new FileWriter("test.txt",true)));
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer= "";
            String move= "";
						//files.println(board.textBoard());
            while ((fromServer = stdIn.readLine()) != "GAME_OVER") {
							if(!fromServer.split(" ")[0].equals("game"))
							{
								String[] split = fromServer.split(" ");
								int i = Integer.parseInt(split[0]);
								int j = Integer.parseInt(split[1]);

								if(myColor == TKind.white)
									board.move(new Move(i,j),TKind.black);
								else {
									board.move(new Move(i,j),TKind.white);
								}
									//files.println("input- "+fromServer+"\n"+board.textBoard());
							}

							else {
								String[] splitted = fromServer.split(" ");
								if((splitted[1].equals("B") && myColor == TKind.black) ||
									(splitted[1].equals("W") && myColor == TKind.white)) {
									
									//board = new ReversiBoard(splitted[0]);
									long time = System.currentTimeMillis();
									while(System.currentTimeMillis() - time < 1000)
										;
									//files.println("input- "+fromServer);
									move = makeMove(board,myColor,files);
									System.out.println(move);
								}
							}
						}
						files.close();
        } catch (Exception e) {
            e.printStackTrace();
						files.close();
        }
				System.out.println("exiting");
    }

	public static String makeMove(ReversiBoard board,TKind myColor,PrintWriter files) {
	ArrayList<String> moves = new ArrayList<String>();
		for(Integer i = 0; i < 8; i++) {
			for(Integer j =0; j < 8; j++) {
					if ((myColor == TKind.black && (board.get(i,j) == TKind.nil) && (board.move(new Move(i,j),TKind.black) != 0)) || 
						(myColor == TKind.white && (i < 8) && (j < 8) && (board.get(i,j) == TKind.nil) && (board.move(new Move(i,j),TKind.white) != 0))) 
					{
					//moves.add(""+i.toString()+" "+j.toString());
					//int x = board.move(new Move(i,j),myColor);
					//files.println("my move"+i.toString()+" "+j.toString()+"\n"+board.textBoard());
					return ""+i.toString()+" "+j.toString();
					}
			}
		}
		return "pass";
	}
}
