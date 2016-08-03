package lab_207;

import java.io.*;
import java.net.*;

public class EchoServer {

	public void go() {

		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		try {
			serverSocket = new ServerSocket(8888);

			clientSocket = serverSocket.accept();

			System.out.println("Connected to the server on port "
					+ clientSocket.getLocalPort() + "\n");

			BufferedReader is = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			PrintWriter os = new PrintWriter(new BufferedOutputStream(
					clientSocket.getOutputStream(), 1024), false);

			String inputLine;

			while ((inputLine = is.readLine()) != null) {
				// Echo the line out
				if (inputLine.equals("QUIT") || inputLine.equals("q")
						|| inputLine.equals("quit")) {
					System.out.println("Closing the connection...");
					break;
				}
				System.out.println("Input [" + inputLine + "] come in");
				os.println(inputLine);
				os.flush();
			}
			os.close();
			is.close();
			clientSocket.close();
			

		} catch (IOException e) {
			System.out.println("Could not listen on port: " + 8888 + ", " + e);
		}
	}

	public static void main(String[] args) {
		EchoServer es = new EchoServer();
		es.go();
	}

}
