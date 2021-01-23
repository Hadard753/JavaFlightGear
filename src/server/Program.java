package server;

public class Program {

	public static void main(String[] args) {
		ClientHandler clientHandler = new MyClientHandler();
		Server serialServer = new MySerialServer(8000, clientHandler);
		serialServer.start();

		System.out.println("Server is running on port 8000.");
	}
}
