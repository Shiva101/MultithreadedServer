package lab_207;

import java.io.*;
import java.net.*;
import java.lang.Thread;

public class ServerRunnable {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8888);
			int count = 0;
			while (true) {
				Socket clientSocket = server.accept();
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				// Setting the MAX_PRIORITY for the 3rd Client
				if (count == 2) {
					System.out
							.println("***************************************");
					System.out.println("Entering MAX priority client...");
					t.setPriority(t.MAX_PRIORITY);
					System.out.println("PRIORITY:  " + t.getPriority());
					System.out
							.println("***************************************");
				}
				if (count == 3) {
					System.out
							.println("***************************************");
					System.out.println("Entering MIN priority client...");
					t.setPriority(t.MIN_PRIORITY);
					System.out.println("PRIORITY:  " + t.getPriority());
					System.out
							.println("***************************************");
				}
				count = count + 1;
			}
		} catch (Exception e) {
			System.err.println("Exception caught:" + e);
		}
	}
}

class ClientHandler implements Runnable {
	Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			System.out.println("------ Thread info "
					+ Thread.currentThread().toString() + "------");
			System.out.println("Connected to the server on port "
					+ clientSocket.getLocalPort());
			System.out.println("Connected from Client: "
					+ clientSocket.getInetAddress());
			System.out
					.println("Connected from Client with Client Port Number: "
							+ clientSocket.getPort());
			System.out
					.println("-----------------------------------------------\n");
			BufferedReader is = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			PrintWriter os = new PrintWriter(clientSocket.getOutputStream(),
					true);
			String inputLine;

			while ((inputLine = is.readLine()) != null) {

				if (inputLine.equals("QUIT") || inputLine.equals("q")) {
					String th = Thread.currentThread().toString();

					System.out.println("Closing the Connection for..." + th
							+ "\n");
					System.out
							.println("---------------------------------------\n");
					break;
				}
				System.out.println("Input [" + inputLine + "] come in" + "\n");
				os.println(inputLine);
				os.flush();
			}
			os.close();
			is.close();
			clientSocket.close();

		} catch (Exception e) {
			System.err.println("Exception caught: clientSocket disconnected.");
		}
	}
}