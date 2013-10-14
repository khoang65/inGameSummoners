package com.example.proj1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetInfo {
	public String getInternetData(String summonerSearch) throws IOException, URISyntaxException {
		BufferedReader in = null;
		String data = null;
		try{
			HttpClient client = new DefaultHttpClient();
			URI website = new URI("http://lolnexus.com/NA/search?name="+ summonerSearch + "&server=NA");
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse response = null;
			try {
				response = client.execute(request);
			} catch (IOException e) {
				System.err.println("IOexception called");
			} finally {
				System.out.println("response returned");
			}
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			while ((l=in.readLine()) !=null){
				sb.append(l + nl);
			}
			in.close();
			data = sb.toString();
			return data;
		} finally{
			if (in != null){
				try{
					in.close();
					return data;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
