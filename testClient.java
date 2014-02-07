import java.util.*;
import java.io.*;
public class testClient {
	public static void main(String[] args) {
			System.out.println("test output");

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
				bw.write("some stuff");
				bw.close();
			} catch(IOException e){e.printStackTrace();}
	}
}
