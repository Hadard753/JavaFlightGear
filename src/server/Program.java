package server;

public class Program {

	public static void main(String[] args) {
		ClientHandler clientHandler = new MyClientHandler();
		Server serialServer = new MySerialServer(5200, clientHandler);
		serialServer.start();

		System.out.println("Server is running on port 5200.");
	}
}
