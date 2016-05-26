package cn.edu.pku.gofish.Model;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by leonardo on 16/5/11.
 */
public class Record {
    private String title;
    private String describetext;
    private String price;
    private String type;
    private ArrayList<String> imagePaths;
    private int imgcnt;
    private String usrname;
    private File[] file;
    AsyncHttpClient client = new AsyncHttpClient();

    public Record(String _usrname, String _title, String _describetext, String _pricetext, String _type, ArrayList<String> _imagePaths) {
        title = _title;
        describetext = _describetext;
        price = _pricetext;
        type = _type;
        imagePaths = _imagePaths;
        usrname = _usrname;
        //file=new File[9];
        imgcnt = imagePaths.size();
    }

    public void uploadFile(String url) throws Exception {
        int count = 0;
        RequestParams params = new RequestParams();
        for (String tmp : imagePaths) {
            file[count] = new File(tmp);
            if (file[count].exists() && file[count].length() > 0) {
                count++;
            } else {
                throw new Exception("FileNotFoundException");
            }
        }
        params.put("uploadfiles", file);
        params.put("price", price);
        params.put("title", title);
        params.put("type", type);
        params.put("usrname", usrname);
        params.put("describetext", describetext);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable){

            }
        });
    }

    public String TextLine()
    {
        return usrname;
    }
}