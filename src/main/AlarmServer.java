package main;

import java.net.InetAddress;
import java.net.ServerSocket;

public class AlarmServer extends Thread {
	int port;
	int i=0;
	public AlarmServer(int port){
		this.port = port;
		start();
	}
	public void run(){
		try{
			ServerSocket server = new ServerSocket(port);
			InetAddress me = InetAddress.getLocalHost();
			String dottedQuad = me.getHostAddress();
			System.out.println("Alarrm is started IP:" + dottedQuad + " port: "	+ port);
			while(true){
				i++;
				if(1000<i){
					i=1;
				}
				new BranchAlarm(i, server.accept());
			}
		}catch(Exception e){
			System.out.println("Error create alarm server " +  e);
			
		}
	}

}
