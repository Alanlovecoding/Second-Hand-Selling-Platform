package cn.edu.pku.gofish.Model;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Iris on 16/5/13.
 */

//消息界面中的每一个栏目

public class Message1 {
    String time;
    String usrname;
    private String briefmessage;
    String url;
    AsyncHttpClient client = new AsyncHttpClient();
    private int ID;
    private int user_id;
    private int item_id;
    private String status;
    public Message1(int id)
    {
        ID = id;
    }
    public Message1(String _time, String _usrname, String _briefmessage)
    {
        time = _time;
        usrname = _usrname;
        briefmessage = _briefmessage;
    }
    public Message1(int user_id, int item_id,String _briefmessage)
    {
        this.user_id = user_id;
        this.item_id = item_id;
        this.briefmessage = _briefmessage;
    }

    public String TimeLine()
    {
        return time;
    }
    public String UsrnameLine()
    {
        return usrname;
    }
    public String BriefMessageLine()
    {
        return briefmessage;
    }

    public void uploadFile(String url) throws Exception {

        RequestParams params = new RequestParams();

        params.put("user_id", user_id);
        params.put("item_id", item_id);
        params.put("message", briefmessage);
        params.put("status",status);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable){

            }
        });
    }

    public void downloadFile()
    {
        client.get(url + "/"+ID, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user_id = Integer.parseInt(response.getString("user_id"));
                    item_id = Integer.parseInt(response.getString("item_id"));
                    briefmessage = response.getString("message");
                    status= response.getString("status");

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

    public void update(String url)
    {
        RequestParams params = new RequestParams();

        params.put("id", ID);
        params.put("status",status);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable){

            }
        });
    }
}
