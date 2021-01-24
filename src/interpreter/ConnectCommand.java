package interpreter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectCommand implements Command {

	static Socket server;
	// volatile static Runnable closeClient;
	static PrintWriter out;
	
	@Override
	public void execute(String[] line) {
		if (line.length != 2) 
			try {
				throw new Exception("Connect: Parameters are not correct");
			} catch (Exception e) { }
		String ip = line[0];
		int port = Integer.parseInt(line[1]);
		if (port > 9999 || port < 0)
			try {
				throw new Exception("Connect: Port is not correct");
			} catch (Exception e) {}
		try {
			server = new Socket(ip, port);
			out = new PrintWriter(server.getOutputStream());
			System.out.println("Client is connected to a remote Server.");
//			closeClient = () -> {
//				try {
//					out.close();
//					server.close();
//					System.out.println("Closing Client.");
//				} catch (IOException e) {}
//			};
		} catch (UnknownHostException e) { }
			catch (IOException e) {
		}
	}

}
