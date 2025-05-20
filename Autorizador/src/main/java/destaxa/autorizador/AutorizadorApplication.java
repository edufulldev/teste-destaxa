package destaxa.autorizador;


public class AutorizadorApplication {

	public static void main(String[] args) {
		ServerSocketHandler server = new ServerSocketHandler(8583);
		server.start();
	}

}
