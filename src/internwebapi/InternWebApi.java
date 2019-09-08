/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internwebapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author BIBEK
 */
public class InternWebApi {
    
    public static String key; // key is made public so can be used across all methods throughout the program

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            InternWebApi.getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            InternWebApi.postRequest();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void getRequest() throws MalformedURLException, IOException, JSONException{
        
        String uri ="https://interns.bcgdvsydney.com/api/v1/key"; //uri for the get method to retrieve the apiKey
        URL address = new URL(uri);
        HttpsURLConnection con = (HttpsURLConnection) address.openConnection();
        con.setRequestMethod("GET");   //HTTP GET method
        con.setRequestProperty("User-Agent","User_Agent");
        
        int responseCode=con.getResponseCode();
        System.out.println(responseCode);
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) { //used to capture the response and required data from the server
            String inputLine;
            response = new StringBuilder();
            while((inputLine = in.readLine())!=null){
                response.append(inputLine);
            }
        }
        System.out.println(response.toString());
        
        JSONObject baseResponse = new JSONObject(response.toString());
        
        key = baseResponse.getString("key");  //key retrived from the server
        System.out.println(key);
      
    }
    
    public static void postRequest() throws IOException, JSONException{
        
      
        String uri = ("https://interns.bcgdvsydney.com/api/v1/submit?apiKey="+key);
        
        JSONObject personalInfo = new JSONObject(); 
        personalInfo.put("email","npne.bibek@gmail.com");    // parameters posted as JSONObject to the server
        personalInfo.put("name", "Bibek Neupane");
        
        URL address = new URL(uri);
        HttpsURLConnection con = (HttpsURLConnection) address.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(personalInfo.toString().getBytes());
        Integer responseCode =con.getResponseCode();
        
        try (BufferedReader br = new BufferedReader( new InputStreamReader(con.getInputStream()))) { //recieves the response from the server
             StringBuilder response = new StringBuilder();
             String responseLine;
             while ((responseLine = br.readLine()) != null) {
             response.append(responseLine.trim());
    }
        System.out.println(response.toString()); //Prints the response from the server
}        
}   
}
