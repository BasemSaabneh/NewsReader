package com.devschema.sh4d0w.newsreader;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getName();

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final String HTTP_METHOD = "GET";
    private static final String KEY_TITLE = "webTitle";
    private static final String KEY_SECTION = "sectionName";
    private static final String KEY_PUB_DATE = "webPublicationDate";
    private static final String KEY_WEB_URL = "webUrl";
    private static final String KEY_TAGS = "tags";
    private static final String JSON_ROOT = "response";
    private static final String JSON_ROOT_ARRAY = "results";
    private Context mContext;
    private QueryUtils(){}
    private QueryUtils(Context context) {
        this.mContext=context;
    }

    public static List<News> fetchData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        List<News> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(HTTP_METHOD);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> news = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject baseJsonResponseResult = baseJsonResponse.getJSONObject(JSON_ROOT);
            JSONArray newsArray = baseJsonResponseResult.getJSONArray(JSON_ROOT_ARRAY);

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String title = currentNews.getString(KEY_TITLE);
                String section = currentNews.getString(KEY_SECTION);
                String date = formatDate(currentNews.getString(KEY_PUB_DATE));
                String weburl = currentNews.getString(KEY_WEB_URL);
                String author = "";
                if (currentNews.getJSONArray(KEY_TAGS).length() > 0) {
                    author = currentNews
                            .getJSONArray(KEY_TAGS)
                            .getJSONObject(0)
                            .getString(KEY_TITLE);
                }

                News newsData = new News(title, section, date, weburl, author);
                news.add(newsData);
            }

        } catch (JSONException e) {
        }
        return news;
    }

    private static String formatDate(String date) {
        return date.substring(0, date.indexOf("T"));
    }
}
