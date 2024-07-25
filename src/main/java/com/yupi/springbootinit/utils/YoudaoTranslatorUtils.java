package com.yupi.springbootinit.utils;



import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class YoudaoTranslatorUtils {
    private static final String APP_KEY = "7bbd37afe24afae0"; // 替换为你的有道翻译 APP Key
    private static final String APP_SECRET = "htq4nzpWh3olOQV5B5oTBIWID1Ub4tc5"; // 替换为你的有道翻译 APP Secret

    public static String translateChartType(String chartType) {
        try {
            return translate(chartType, "zh-CHS", "en");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String translate(String query, String from, String to) throws Exception {
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = md5(APP_KEY + query + salt + APP_SECRET);
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appKey", APP_KEY);
        params.put("salt", salt);
        params.put("sign", sign);

        String response = sendPost("https://openapi.youdao.com/api", params);
        return parseTranslation(response);
    }

    private static String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String sendPost(String url, Map<String, String> params) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(postDataBytes);
            os.flush();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static String parseTranslation(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("translation")) {
            return jsonObject.getJSONArray("translation").getString(0);
        }
        return "Translation error!";
    }
}