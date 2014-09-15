package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OkoAvto {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, IOException {
		String root = null;
		String url = null;
		String table = null;
		String pass = null;

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("config.txt"));
			JSONObject jsonObject = (JSONObject) obj;

			root = (String) jsonObject.get("root");
			url = (String) jsonObject.get("url");
			table = (String) jsonObject.get("table");
			pass = (String) jsonObject.get("pass");
			// System.out.println(root);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Bd bd = new Bd(root, pass, url, table);
		// Bd bd = new Bd("atlas","astalavista","localhost", "atlas");
		bd.connect();
		// new ServiceServers();
		//new AlarmServer(8888);
		new Server(10002, bd);
	}

}
