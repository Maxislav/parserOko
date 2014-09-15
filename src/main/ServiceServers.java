package main;

public class ServiceServers implements Runnable {
	Thread t;
	
	public ServiceServers(){
		// new Server(10002, bd)
		t = new Thread();
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
