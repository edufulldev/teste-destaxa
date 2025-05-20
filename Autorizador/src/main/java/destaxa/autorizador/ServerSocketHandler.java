package destaxa.autorizador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketHandler {
    private final int port;
    private final ExecutorService executor;

    public ServerSocketHandler(int port) {
        this.port = port;
        this.executor = Executors.newFixedThreadPool(10); // aceita até 10 conexões simultâneas
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Autorizador escutando na porta " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

