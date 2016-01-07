package com.test.bufalo;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;


public class CollectTweets {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws TwitterException, IOException, ParseException {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("RGBcGHEhSQvzXgZ7IDMT1IyIm");
        cb.setOAuthConsumerSecret("IA8lKFfIBuTHUrXm7o7s7MH2Hg9loX41gc709WsuBJoociElaj");
        cb.setOAuthAccessToken("3305908472-9h3CfAVNeKzMYlsV8qN7TlvSSenu9soVhrnFDK9");
        cb.setOAuthAccessTokenSecret("ILUYWIM6gZUJKqpRZqFcnOIFFVB1C5SGoCQKEWyh4ttwa");
        cb.setDebugEnabled(true);
        cb.setJSONStoreEnabled(true);
        
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        
		  Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		  //Query query = new Query("(us open) OR (cricket) OR (football) OR (hockey) OR (sport) OR (racing)");
		  //Query query = new Query("(us open) OR (Kricket) OR (Fußball) OR (hockey) OR (sport) OR (Rennsport)");
		  Query query = new Query("(us open) OR (крикет) OR (футбол) OR (хоккей) OR (спортивный) OR (гоночный)");
		  query.setLang("ru");
		  query.setCount(100);

		  try {
			  //fos = new FileOutputStream("C:/Users/dell/Desktop/tweets/EnglishTweets.json", true);
			  //fos = new FileOutputStream("C:/Users/dell/Desktop/tweets/GermanTweets.json", true);
			  fos = new FileOutputStream("C:/Users/dell/Desktop/tweets/RussianTweetsKapil.json", true);
	          osw = new OutputStreamWriter(fos, "UTF-8");
	          bw = new BufferedWriter(osw);
			  QueryResult result = twitter.search(query);
		      List<Status> tweets = result.getTweets();

		      JSONArray ja = new JSONArray();
		      for (Status tweet : tweets) {
		    	  JSONObject obj = new JSONObject();
		    	  
		    	  SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		    	  TimeZone utc = TimeZone.getTimeZone("UTC");
		    	  date.setTimeZone(utc);
		    	  String tweetCreatedAt = date.format(tweet.getCreatedAt());
		    	  
		    	  ArrayList<String> hashList = new ArrayList<String>();
		    	  ArrayList<String> urlList = new ArrayList<String>();
		    	  
		    	  for(HashtagEntity hashTag : tweet.getHashtagEntities()){
		    		  hashList.add(hashTag.getText());
		    	  }
		    	  
		    	  for(URLEntity urls : tweet.getURLEntities()){
		    		  urlList.add(urls.getDisplayURL());
		    	  }
		    	  
		    	  obj.put("id", tweet.getId());
		    	  obj.put("text_ru", tweet.getText());
		    	  obj.put("lang", tweet.getLang());
		    	  obj.put("created_at", tweetCreatedAt);
		    	  obj.put("tweet_hashtags", hashList);
		    	  obj.put("tweet_urls", urlList);
		    	  ja.add(obj);
		    	  System.out.println(tweet.getId() + ":--- " + tweet.getText() + "----- " + tweet.getLang() + "----" + tweetCreatedAt);
		      }
		      	bw.write(ja.toJSONString());
				bw.flush();
				bw.close();

		      System.exit(0);
	}
		
		  	catch (TwitterException te) {
			  System.out.println("Couldn't connect: " + te);
			  }
		  finally {
	            if (bw != null) {
	                try {
	                    bw.close();
	                } catch (IOException ignore) {
	                }
	            }
	            if (osw != null) {
	                try {
	                    osw.close();
	                } catch (IOException ignore) {
	                }
	            }
	            if (fos != null) {
	                try {
	                    fos.close();
	                } catch (IOException ignore) {
	            }
	            }
	        }
}
}
