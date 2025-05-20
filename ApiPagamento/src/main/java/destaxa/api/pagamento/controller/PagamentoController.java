package destaxa.api.pagamento.controller;

import destaxa.api.pagamento.dto.PagamentoRequest;
import destaxa.api.pagamento.dto.PagamentoResponse;
import destaxa.api.pagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public PagamentoResponse authorize(@RequestBody PagamentoRequest request) {
        return pagamentoService.processarPagamento(request);
    }
}
