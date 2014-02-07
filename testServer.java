import java.util.*;
import java.io.*;

public class testServer {
	public static void main(String[] args) {

		try {
		InputStream input = new FileInputStream(args[0]);
		String in = input.read();
		System.out.println(in);
		}catch(IOException e){e.printStackTrace();};
		

	}
}
