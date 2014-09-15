package main;






import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

//Создаем сервер
public class Server {
	int port;
	int i=0;
	LogTxt log;
	public Server(int port, Bd bd) throws IOException {
		
		log = new LogTxt();
		try{
			ServerSocket server = new ServerSocket(port);
			InetAddress me = InetAddress.getLocalHost();
			String dottedQuad = me.getHostAddress();
			System.out.println("server is started IP:" + dottedQuad + " port: "	+ port);
			
			
			
			/*ServerSocket server = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
			
			InetAddress me = InetAddress.getLocalHost();
			String ip = me.getHostAddress();
			System.out.println("server is started  IP: "+ip+" port:"+port);*/
			
			while(true){
				i++;
				if(1000<i){
					i=1;
				}
				new Branch(i, server.accept(), bd, log);
			}
			
		}catch(Exception e){
			System.out.println("Error create server " +  e);
			
		}
		
	}
}
