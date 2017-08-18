package cn.edu.pku.gofish.Model;

import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by leonardo on 16/5/13.
 */
public class Message {
    public static final int TYPE_RECEVIED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;
    private String time;
    public int ID;
    private int user_id;
    private int item_id;
    public Message(String content, int type)
    {
        this.content=content;
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public int getType()
    {
        return type;
    }

    private String url = "/api/items";
    SyncHttpClient client = new SyncHttpClient();
    public void uploadFile(String url) throws Exception {
        RequestParams params = new RequestParams();


        params.put("user_id", user_id);
        params.put("item_id", item_id);
        params.put("message", content);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable){

            }
        });
    }

    public void downloadFile(String url)
    {
        client.get(url + ID, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user_id = Integer.parseInt(response.getString("user_id"));
                    item_id = Integer.parseInt(response.getString("item_id"));
                    content = response.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
