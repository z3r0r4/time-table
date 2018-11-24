package com.zero.zero.timetable.OVP_Fetcher.receive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import com.zero.zero.timetable.OVP_Fetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.OVP_Fetcher.process.DataExtractor;

public class HTMLFetcher {
	private static boolean fetchOnInitialization = true;
	
	private URL url = null;
	private InputStream istream = null;
	private BufferedReader buffer = null;
	private String wrapper = "center";
	private String username = "";
	private String password = "";
	private String Token = getToken();
	private boolean requiresAuthentication = true;

	public HTMLFetcher(String url) {
		requiresAuthentication = false;
		if(fetchOnInitialization)
			fetch(url);
		else
			try {
				this.url = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	}
	
	public HTMLFetcher(String url, String username, String password) {
		setAuthentication(username, password);
		if(fetchOnInitialization)
			fetch(url);
		else
			try {
				this.url = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	}
	
	public final void fetch() {
		try {
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			
			if (requiresAuthentication) {
				connection.setRequestProperty("Authorization", "Basic "+Token);
			}
			
			connection.connect();
			System.out.println("RESPONSE CODE: " + connection.getResponseCode());
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// everything ok
				istream = connection.getInputStream();
				buffer = new BufferedReader(new InputStreamReader(istream));
				// process stream
			} else {
				// possibly error
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public final void fetch(String url) {
		try {
			this.url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			
			if (requiresAuthentication) {
				connection.setRequestProperty("Authorization", "Basic "+Token);
			}
			
			connection.connect();
			System.out.println("RESPONSE CODE: " + connection.getResponseCode());
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// everything ok
				istream = connection.getInputStream();
				buffer = new BufferedReader(new InputStreamReader(istream));
				// process stream
			} else {
				// possibly error
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setAuthentication(String username, String password) {
		this.username = username;
		this.password = password;
		Token = getToken();
	}
	
	public void setUsername(String username) {
		this.username = username;
		Token = getToken();
	}
	
	public void setPassword(String password) {
		this.password = password;
		Token = getToken();
	}
	
	public void enableAuthentication() {
		requiresAuthentication = true;
	}
	
	public void disableAuthentication() {
		requiresAuthentication = false;
	}

	public void loadHTML() {
		DataExtractor sort = new DataExtractor();
		SubstitutionSchedule plan = sort.extractInformation(buffer, wrapper);
		plan.log();

		try {
			istream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fetch();
	}
	
	public void loadHTML(String Class) {
		DataExtractor sort = new DataExtractor();
		SubstitutionSchedule plan = sort.extractInformation(buffer, wrapper);
		plan.log(Class);

		try {
			istream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fetch();
	}
	
	public SubstitutionSchedule getSubstitutionSchedule() {
		DataExtractor sort = new DataExtractor();
		SubstitutionSchedule schedule = sort.extractInformation(buffer, wrapper);
		fetch();
		return schedule;
	}
	
	private final String getToken() {
		return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}
}
