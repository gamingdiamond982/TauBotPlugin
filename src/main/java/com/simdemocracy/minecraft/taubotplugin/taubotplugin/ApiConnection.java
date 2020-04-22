package com.simdemocracy.minecraft.taubotplugin.taubotplugin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
//exception
class ApiException extends Exception {
    public ApiException(String errorMessage) {
        super(errorMessage);
    }
}

public class ApiConnection {

    JSONParser configParser = new JSONParser();
    String uname;
    String psswd;
    String ip;

    public ApiConnection(){
        //Builds connection from config
        //should probably be moved to its own function
        System.out.println("Building config");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try(FileReader fileReader = new FileReader("config.json")) {
            Object obj = new JSONParser().parse(new FileReader("./plugins/config.json"));
            JSONObject jo = (JSONObject) obj;
            this.uname = (String) jo.get("uname");
            this.psswd = (String) jo.get("passwd");
            this.ip = (String) jo.get("ip");
            System.out.println("-------------------------------");
            System.out.println("-Uname: " + this.uname);
            System.out.println("-Psswd:" + this.psswd);
            System.out.println("-ip: " + this.ip);
            System.out.println("-------------------------------");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }



    private String Get(String toUrl) throws ApiException {
        //Simple connection to taubot api
        try {
            URL url = new URL (toUrl);
            String encoding = Base64.getEncoder().encodeToString((this.uname + ":" + this.psswd).getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                return line;
            }
        } catch(Exception e) {
            
            throw new ApiException("Failed to connect");
        }
        throw new ApiException("wtf have you done you magical cow");

    }
    public int getUserBalance(String ign) throws ApiException {
        //simple balance getter
        String balance = Get("http://"+ this.ip +"/Minecraft/account/" + ign + "/balance");
        String[] content = balance.split(" ");
        if (content.length > 1) {
            //sloppy code I know but it works
            throw new ApiException("Failed with error code: " + content[0]);
        }
        return Integer.parseInt(balance);
    }

}
