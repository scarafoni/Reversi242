import java.io.*;
import java.net.*;
import java.util.*;

public class SampleClient {
		static enum color{white,black};
		static color myColor;
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
				ReversiBoard board;
        int portNumber = Integer.parseInt(args[0]);
				myColor = portNumber == 4444 ? color.black : color.white;

        try {
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("GAME_OVER"))
                    break;
                
								board = new ReversiBoard(fromServer);
								long time = System.currentTimeMillis();
								while(System.currentTimeMillis() - time < 1000)
									;
                fromUser = makeMove(board);//stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
								//fromServer = in.readLine();
          }  
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
	System.err.println(e.getMessage());
            System.exit(1);
        }
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
