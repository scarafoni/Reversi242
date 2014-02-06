import java.net.*;
import java.io.*;

public class Sockets {
	int portNumber = 4444;//Integer.parseInt(args[0]);
	ServerSocket serverSocket;
	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	String inputLine, outputLine;

	public Sockets(int portNum) {
		try {
			portNumber = portNum;
			serverSocket = new ServerSocket(portNumber);
			System.out.println("waiting for client to accept...");
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
			//out.println("test connection");
			System.out.println("connection successful");

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	public String getInput() throws IOException 
		{return in.readLine();}
	public void sendBoard(String board)
		{out.println(board);}
}
