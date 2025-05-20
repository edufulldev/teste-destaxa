package destaxa.api.pagamento.dto;

public class PagamentoRequest {

    public String external_id;
    public double value;
    public String card_number;
    public int installments;
    public String cvv;
    public int exp_month;
    public int exp_year;
    public String holder_name;
}
