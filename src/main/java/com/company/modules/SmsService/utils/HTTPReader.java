package com.company.modules.SmsService.utils;

import com.company.modules.SmsService.iface.CommInterface;
import com.company.modules.SmsService.model.PDU;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oracle.javafx.jmx.json.JSONException;
import okhttp3.*;


import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by fim on 17.07.17.
 */
public class HTTPReader implements CommInterface {

    String URL = "http://37.221.248.104:8080/rest/SMS/";

    @Override
    public boolean openPort() {
        return true;
    }

    @Override
    public boolean closePort() {
        return true;
    }

    @Override
    public List<PDU> getSMS(int index) {
        List<PDU> list = new ArrayList<>();
        String json = null;
        try {
            json = getfromHTTP(URL + "get/" + index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(json);
        return list;
    }

    @Override
    public List<PDU> getAllSMS() {
        List<PDU> list = new ArrayList<>();
        String json = null;
        try {
            json = getfromHTTP(URL + "getAll");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(json);
        return list;
    }

    @Override
    public boolean sendSMS(String number, String message) {
        sendPost(URL + "send", number, message);
        return true;
    }

    @Override
    public String getSignalStrenght() {
        String json = null;
        try {
            json = getfromHTTP(URL + "signal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(json);
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String getfromHTTP(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            System.out.println(jsonText);
        }
        finally {
            is.close();
        }
        return "";
    }

    public static String sendHTTPPost(String url){
        //String url = "http://localhost:8888/_ah/api/langapi/v1/createLanguage";
        String param = "{\"language\":\"russian\", \"description\":\"dsfsdfsdfsdfsd\"}";

        String charset = "UTF-8";
        URLConnection connection = null;
        try {
            connection = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(param.getBytes(charset));
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            InputStream response = connection.getInputStream();
            System.out.println("Response" + response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendPost(String URL, String number, String message) {
        try {
            URL url = new URL(URL);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            Map<String, String> arguments = new HashMap<>();
            arguments.put("number", number);
            arguments.put("message", message);
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
        }
        catch (IOException e ){
            e.printStackTrace();
        }
// Do something with http.getInputStream()
        return "";
    }


}
