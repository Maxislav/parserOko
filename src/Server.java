import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	Socket s;
	static int port;
	int i = 0;

	Server(int port) throws UnknownHostException, IOException {
		int i = 1;
		try {
			ServerSocket server = new ServerSocket(port, 0,
					InetAddress.getByName("localhost"));
			InetAddress me = InetAddress.getLocalHost();
			String dottedQuad = me.getHostAddress();
			System.out.println("server is started IP:" + dottedQuad + " port: "
					+ port);

			while (true) {
				Brunch bruncn = new Brunch(i, server.accept());
				// new Brunch().start();
			}
		} catch (Exception e) {
			System.out.println("init error1: " + e);
		}

	}

	
}
