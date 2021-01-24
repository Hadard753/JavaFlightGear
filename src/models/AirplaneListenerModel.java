package models;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;

import server.Server;

public class AirplaneListenerModel extends Observable implements Server {

    int port;
    volatile boolean stop;
    volatile Point airplanePosition;
    public double startX;
    public double startY;

    public AirplaneListenerModel(int port) {
        this.port = port;
        stop = false;
    }

    @Override
    public void stop() {
        this.stop = true;
    }

    public Point getAirplanePosition() {
        return airplanePosition;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                runServer();
            } catch (Exception e) {
				System.out.println("have error:"+e+" and stop server");

                stop = true;
            }
        }).start();
    }


    private void runServer() throws Exception {
        System.out.println("monitoring location - waiting for flight simulator to connect on port " + port);
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(500000);

        Socket aClient = server.accept();
        System.out.println("monitoring location - flight simulator connected");

        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
            while (!stop) {
                String[] cooInput = userInput.readLine().split(",");
                airplanePosition = new Point();

                airplanePosition.setLocation(Double.parseDouble(cooInput[0]), Double.parseDouble(cooInput[1]));
                setChanged();
                notifyObservers();
            }
        } catch (IOException e) {
            System.out.println("Error on read location code - check now");
            e.printStackTrace();
        } finally {
            System.out.println("SERVER STOPPED - CHECK THIS NOW!");
            server.close();
        }
    }
}
