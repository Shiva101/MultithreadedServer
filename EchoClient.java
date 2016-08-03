package lab_207;

import java.io.*;
import java.net.*;

public class EchoClient {

	public void go() {
		try {
			Socket echoSocket = new Socket("127.0.0.1", 8888);

			System.out
					.println("Ready... you can start sending the data to the server\n");

			System.out
					.println("Press 'q' or 'QUIT' or 'quit' to close the connection.\n");

			DataOutputStream os = new DataOutputStream(
					echoSocket.getOutputStream());

			BufferedReader is = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));

			String userInput;

			while ((userInput = stdIn.readLine()) != null) {
					os.writeBytes(userInput);
					os.writeByte('\n');
					System.out.println("echo: " + is.readLine());

					if ((userInput.equals("q") || (userInput.equals("QUIT")) || (userInput
							.equals("quit")))) {
						System.out.println("Disconnecting from the Server...\n");
						break;
					}
			}
			os.close();
			is.close();
			echoSocket.close();

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + e);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection " + e);
		}

	}

	public static void main(String[] args) {
		EchoClient e = new EchoClient();
		e.go();
	}

}
