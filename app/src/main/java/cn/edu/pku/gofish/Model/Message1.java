package cn.edu.pku.gofish.Model;

import android.util.Log;

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
    private String time;
    String usrname;
    private String briefmessage;
    String url = "http://gofish.hackpku.com:8003/api/trade_requests";
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
        return ""+user_id;
    }
    public String BriefMessageLine()
    {
        return briefmessage;
    }

    NoticeDialogListener mListener = null;
    public interface NoticeDialogListener {
        public void onDialogPositiveClick();

        public void onDialogNegativeClick();
    }
    public void setInterface(NoticeDialogListener _Listener)
    {
        mListener = _Listener;
        Log.d("Register", "go");
    }

    public void uploadFile() throws Exception {

        RequestParams params = new RequestParams();
        //user_id,item_id,number,status
        params.put("user_id", user_id);
        params.put("item_id", item_id);
        //params.put("message", briefmessage);
        params.put("number",0);
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
                    time = response.getString("created_at");
                    if(mListener!=null)
                    {
                        mListener.onDialogPositiveClick();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                if(mListener!=null)
                {
                    mListener.onDialogPositiveClick();
                }
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
