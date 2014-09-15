package main;

//import java.net.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Branch extends Thread {
	static Bd bd;
	int connection;
	int mes;
	Socket s;
	InputStream is;
	OutputStream os;// s.getOutputStream();
	Parser parser;
	 LogTxt log;

	public Branch(int i, Socket s, Bd bd, LogTxt log) {
		Branch.bd = bd;
		this.parser = new Parser(bd);
		this.s = s;
		this.log = log;
		try {
			s.setSoTimeout(15*60*1000);
		} catch (SocketException e) {
			System.out.println("Error s timeout: "+e);
			return;
		}
		
		System.out.println("new connection" + i);
		this.connection = i;
		this.s = s;
		try {
			is = s.getInputStream();
			os = s.getOutputStream();
		} catch (IOException e) {
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Socket close");
				return;
				
			}
			e.printStackTrace();
		}
		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}

	public void run() {
		while (true) {
			try {

				

				

				byte buf[] = new byte[64 * 1024];

				int r = is.read(buf);

				if (r == (-1)) {
					s.close();
					System.out.println("Sosket close");
					return;
				}

				String data = new String(buf, 0, r);
				mes++;
				System.out.println("");
				System.out.println("connection:" + connection + " mes: " + mes+ " Data: " + data); 
				//log.save(""+data); cохранение в текстовый файл
				String resp = "J";
				os.write(resp.getBytes());
				
				if (mes == 1) {
					parser.first(data);
				} else {
					if (mes > 100) {
						mes = 2;
					}
					parser.next(data);

				}

			} catch (Exception e) {
				System.out.println("Error1: "+e);
				try {
					s.close();
		
					System.out.println("s close ");
					return;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

}
