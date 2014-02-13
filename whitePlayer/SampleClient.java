import java.io.*;
import java.net.*;
import java.util.*;

public class SampleClient {
		static enum color{white,black};
		static color myColor;
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
				ReversiBoard board;
				myColor =  args[0].equals("black") ? color.black : color.white;

        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer= "";
            String fromUser= "";
						PrintWriter files = new PrintWriter("test.txt","UTF-8");
						//System.out.println("outside the loop");
            while ((fromServer = stdIn.readLine()) != "GAME_OVER") {
							String boardRead  = stdIn.readLine();
							if(!boardRead.split(" ")[0].equals("game"))
								files.append(boardRead);
							files.close();
							System.out.println("2 4");
							String[] splitted = fromServer.split(" ");
							if((splitted[1].equals("B") && myColor == color.black) ||
								(splitted[1].equals("W") && myColor == color.white)) {
								
								//board = new ReversiBoard(splitted[0]);
								long time = System.currentTimeMillis();
								while(System.currentTimeMillis() - time < 1000)
									;
								//fromUser = makeMove(board);
								//System.out.println("2 4");
							}
						}
        } catch (Exception e) {
            e.printStackTrace();
        }
				System.out.println("exiting");
    }

	public static String makeMove(ReversiBoard board) {
	ArrayList<String> moves = new ArrayList<String>();
		for(Integer i = 0; i < 8; i++) {
			for(Integer j =0; j < 8; j++) {
					if ((myColor == color.black && (board.get(i,j) == TKind.nil) && (board.checkNoMove(new Move(i,j),TKind.black) != 0)) || 
						(myColor == color.white && (i < 8) && (j < 8) && (board.get(i,j) == TKind.nil) && (board.checkNoMove(new Move(i,j),TKind.white) != 0))) 
					{
					//moves.add(""+i.toString()+" "+j.toString());
					return ""+i.toString()+" "+j.toString();
					}
			}
		}
		Random  rg = new Random();
		return moves.get(rg.nextInt(moves.size()));
	}
}
