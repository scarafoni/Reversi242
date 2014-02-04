import java.io.*;
import java.net.*;

public class SampleClient {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = Integer.parseInt(args[0]);

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
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
          }  
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
	System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
