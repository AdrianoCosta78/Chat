package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient implements Runnable {
	
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private ClientSocket clientSocket;
	private Scanner sc;
	
	public ChatClient() {
		 sc = new Scanner(System.in);
	}
	
	
	public void start() throws IOException {
		try {
		clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, ChatServer.PORT));

		System.out.println("Cliente conectado ao servidor em: "+SERVER_ADDRESS+": "+ChatServer.PORT);
		new Thread(this).start();
		messageLoop();
		}finally {
			clientSocket.close();
		}
	}
	
	@Override
	public void run() {
		String msg;
		while((msg = clientSocket.getMessage()) != null) {
			System.out.printf("Mensagem recebida do servidor: %s", msg);
		}
		
	}
	
	private void messageLoop() throws IOException {
		String msg;
		do {
			System.out.println("Digite uma mensagem ou SAIR para finalizar: ");
			msg = sc.nextLine();
			clientSocket.sendMsg(msg);
			
			}while(!msg.equalsIgnoreCase("sair"));
	}

	public static void main(String[] args) {
		
		try {
			ChatClient  client = new ChatClient();
			client.start();
		} catch (IOException e) {
			System.out.println("Erro ao iniciar o servidor"+ e.getMessage());
		}
		System.out.println("Cliente finalizado");
	}

}
