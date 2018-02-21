package cmu.research.mycompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


/**
 * Servlet implementation class gitSearch
 */
@WebServlet(name = "gitSearch", urlPatterns = {"/gitSearch"})
public class gitSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String requestURL = "https://api.github.com/search/issues?q=getActivity()+label:bug+language:java+state:open&sort=created&order=asc";
        
        String myresponse="";
        int status;
       
       try {  
               // Make call to a particular URL
		URL url = new URL(requestURL);
               //Calling the HTTP URL Connection for the Rest call
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               //Setting the request header in the rest call
              // conn.setRequestProperty("user-key", userkey);
               //Defining the request method
               conn.setRequestMethod("GET");
               //Defining the accepted request and response types
               conn.setRequestProperty("Accept", "application/json");
               //Initializing the connection Status
               status = conn.getResponseCode();
               System.out.println(status);
               //Defining a string to recieve the output               
               String output;
               //Buffered reader object to read the InputStream 
               BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()),"UTF-8"));
		//Reading the input stream till the end of the stream
               while ((output = br.readLine()) != null) {
               myresponse +=output;
               }
       //System.out.println("The response is:"+myresponse);
       PrintWriter out = response.getWriter();
       
       JSONParser parser = new JSONParser();
       Object jsonObject = parser.parse(myresponse);
       JSONObject myjsonObject = (JSONObject) jsonObject;
       JSONArray itemsArray = (JSONArray) myjsonObject.get("items");
       @SuppressWarnings("unchecked")
       Iterator<JSONObject> it = itemsArray.iterator();
       int count = 0;
       while (it.hasNext())
       { 
    	   count++;
    	   JSONObject generalInfoObject = it.next();
       String URL = (String)generalInfoObject.get("url");
       String title = (String)generalInfoObject.get("title");
       //Timestamp createdAt = (Timestamp) generalInfoObject.get("created_at");
       String body = (String)generalInfoObject.get("body");
    	   out.println("Repository "+count);
    	   out.println("URL "+URL);
    	   out.println("Title "+title);
    	   out.println("Body "+body);
    	   out.flush();
       }
       }
    catch(Exception e)
    {
     e.printStackTrace();
    }
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
