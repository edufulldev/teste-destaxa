package destaxa.autorizador;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String request = in.readLine();
            System.out.println("Recebido: " + request);

            double valor = extrairValorSimulado(request);

            if (valor > 1000) {
                System.out.println("Timeout simulado. Não responder.");
                return;
            }

            String resposta;
            if (((int) valor) % 2 == 0) {
                resposta = "0210|000|Autorizado"; // Simulando bit 39 = 000
            } else {
                resposta = "0210|051|Negado";     // Simulando bit 39 = 051
            }

            out.write(resposta);
            out.newLine();
            out.flush();

            System.out.println("Resposta enviada: " + resposta);

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    private double extrairValorSimulado(String request) {
        try {
            String[] partes = request.split("\\|");
            return Double.parseDouble(partes[1]); // exemplo: "0200|238.00|..."
        } catch (Exception e) {
            return new Random().nextInt(2000); // fallback
        }
    }
}
