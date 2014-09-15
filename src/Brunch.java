import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Brunch extends Thread {
	//Server server;
	int num = 0;
	Socket s;

	public Brunch(int i, Socket s) {
		// this.num = num;
		this.s = s;
		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try {
			while (true) {
			 	InputStream is = s.getInputStream();
				OutputStream os = s.getOutputStream();
				byte buf[] = new byte[64 * 1024];

				int r = is.read(buf);

				if (r == (-1)) {
					s.close();
					System.out.println("Sosket close");
					return;
				}

				String data = new String(buf, 0, r);

				System.out.println(data.toString());
			}

		} catch (Exception e) {
			System.out.println("init error 2: " + e);

		}

	}
}
