package destaxa.api.pagamento.service;

import destaxa.api.pagamento.dto.PagamentoRequest;
import destaxa.api.pagamento.dto.PagamentoResponse;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PagamentoService {

    public PagamentoResponse processarPagamento(PagamentoRequest request) {
        PagamentoResponse response = new PagamentoResponse();
        response.payment_id = UUID.randomUUID().toString();
        response.value = request.value;

        try (Socket socket = new Socket("localhost", 8583);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String isoMessage = montarMensagemSimulada(request); // Ex: 0200|238.00|...
            out.write(isoMessage);
            out.newLine();
            out.flush();

            String resposta = in.readLine(); // Ex: 0210|000|Autorizado
            String[] partes = resposta.split("\\|");

            response.response_code = partes[1];
            response.authorization_code = "A12345";

        } catch (IOException e) {
            response.response_code = "TIMEOUT";
            response.authorization_code = null;
        }

        LocalDateTime agora = LocalDateTime.now();
        response.transaction_date = agora.format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        response.transaction_hour = agora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return response;
    }

    private String montarMensagemSimulada(PagamentoRequest request) {
        return String.format("0200|%.2f|%s", request.value, request.external_id);
    }
}
