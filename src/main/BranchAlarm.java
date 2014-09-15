package main;

import java.io.InputStream;
import java.net.Socket;

public class BranchAlarm extends Thread  {
	int connection;
	int mes;
	Socket s;
	InputStream is;
 
	public BranchAlarm(int i, Socket s){
		System.out.println("new connection" + i);
		this.s = s;
		this.connection = i;
		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}
	public void run() {
		try {

			

			is = s.getInputStream();

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
			s.close();
		}catch (Exception e) {
			System.out.println("Error5: "+e);
		}
	}
}
