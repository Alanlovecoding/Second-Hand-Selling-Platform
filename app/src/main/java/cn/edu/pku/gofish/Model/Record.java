package cn.edu.pku.gofish.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.edu.pku.gofish.Exception.MyException;
import cn.edu.pku.gofish.USR;
import cz.msebera.android.httpclient.Header;


/**
 * Created by leonardo on 16/5/11.
 */
public class Record {

    private int ID;
    private int user_id;
    int number;
    private String title;
    private String describetext;
    private float price;
    private ArrayList<String> imagePaths;
    private int imgcnt;
    private String usrname;

    private String status;


    private Bitmap[] file;

    static SyncHttpClient client = new SyncHttpClient();
    static AsyncHttpClient client2 = new AsyncHttpClient();

    private String url = "http://gofish.hackpku.com:8003/api/items";

    NoticeDialogListener mListener = null;
    public interface NoticeDialogListener {
        public void onDialogPositiveClick();

        public void onDialogNegativeClick();
    }
    public void setInterface(NoticeDialogListener _Listener)
    {
        mListener = _Listener;
        Log.d("Register","go");
    }

    public Record(int id)
    {
        ID = id;
    }
    public void setID(int ID)
    {
        this.ID = ID;
    }
    String impath;
    public Record(int user_id, String _title, String _describetext, float _pricetext, int _number,String _imagePaths,String status) {
        title = _title;
        describetext = _describetext;
        price = _pricetext;

        this.user_id = user_id;
        number = _number;
        impath = _imagePaths;
        //file=new File[9];
        /*if(imagePaths!=null) {
            imagePaths = _imagePaths;
            imgcnt = imagePaths.size();
        }
        else {
            imgcnt = 0;
            imagePaths = new ArrayList<String>();

        }*/
        this.status =  status;
    }

    public void uploadFile() throws MyException{
        Log.d("NET", "Record uploadfile begin");
        int count = 0;
        FileInputStream fis=null;
        RequestParams params = new RequestParams();
        /*for (String tmp : imagePaths) {
            try {
                fis=new FileInputStream(tmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            file[count] = bitmap;
            if (file[count]!=null) {
                count++;
            } else {
                throw new MyException("FileNotFoundException");
            }
        }*/

        params.put("title", title);
        params.put("number", number);
        params.put("user_id", USR.usr_id);
        params.put("price", price);
        params.put("description", describetext);
        /*if(file.exists()&& file.length()>0)
            try {
                params.put("image_file", file);
             Log.d("NET", "Record image_file    " + file.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
        params.put("status",status);
        Log.d("NET", "Record post begin " + params.toString());

        client2.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("NET", "Record post done"+new String(bytes));
                mListener.onDialogPositiveClick();
                try {
                    postPhoto(new String(bytes));
                } catch (MyException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NET", "Record post failed" + " "+i+" "+new String(bytes));
                mListener.onDialogNegativeClick();
            }
        });
    }

    public void changeFile() throws MyException {
        int count = 0;
        FileInputStream fis=null;
        RequestParams params = new RequestParams();
        /*for (String tmp : imagePaths) {
            try {
                fis=new FileInputStream(tmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            file[count] = bitmap;
            if (file[count]!=null) {
                count++;
            } else {
                throw new MyException("FileNotFoundException");
            }
        }*/


        File file = null;
        try {
            file = new File(impath);
        }catch(Exception e){

        }
        params.put("title", title);
        params.put("number", number);
        params.put("user_id", USR.usr_id);
        params.put("price", price);
        params.put("description", describetext);
        try {
            params.put("image_file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("status",status);
        Log.d("NET", "Record post begin" + params.toString());

        client2.post(url+"/"+ID, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("NET", "Record post done");
                mListener.onDialogPositiveClick();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NET", "Record post failed" + bytes);
                mListener.onDialogNegativeClick();
            }
        });
    }

    public void downloadFile()
    {

        client2.get(url + "/"+ID, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user_id = Integer.parseInt(response.getString("user_id"));
                    number = Integer.parseInt(response.getString("number"));
                    price = Float.parseFloat(response.getString("price"));
                    String description = response.getString("description");
                    describetext = description;
                    Log.d("NET","Record "+user_id+" "+number+" "+price+" "+describetext);
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

    public void deleteFile()
    {

        client2.get(url + "/"+ID+"/delete", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("NET","Record delete success"+user_id+" "+number+" "+price+" "+describetext);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("NET", "Record delete failed" + user_id + " " + number + " " + price + " " + describetext);
            }
        });

    }

    public String getName()
    {
        return usrname;
    }
    public int getID(){return ID;}
    public String getTitle(){return title;}
    public String getDescribetext(){return describetext;}
    public Bitmap[] getFile(){return file;}
    public int getImgcnt(){return imgcnt;}

    public void postPhoto(String item_id) throws MyException{
        Log.d("NET", "Record uploadfile begin");
        int count = 0;
        FileInputStream fis = null;
        RequestParams params = new RequestParams();

        File file = null;
        try {
            file = new File(impath);
        }catch(Exception e){

        }
        params.put("item_id", item_id);

        if(file.exists()&& file.length()>0) {
            try {
                //params.put("", file);
                Log.d("NET", "Record image_file " + file.toString());
                int length = (int) file.length();

                byte[] bytes = new byte[length];

                FileInputStream in = new FileInputStream(file);
                BufferedInputStream buf = new BufferedInputStream(in);
                Bitmap bitmap = BitmapFactory.decodeStream(buf);
                try {
                    in.read(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String contents = new String(bytes);
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/dir1/dir2");
                dir.mkdirs();
                File file2 = new File(dir, "mytextfile.jpg");

                FileOutputStream f = new FileOutputStream(file2, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, f);
                f.flush();
                f.close();
                Log.d("NET", "write done ");

                params.put("image_file", file2);

                Log.d("NET", "content " + contents);
            } catch (FileNotFoundException e) {
                Log.d("NET", "file not exist");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("NET", "Record post begin " + params.toString());

        client2.post("http://gofish.hackpku.com:8003/api/images", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("NET", "Record post photo done" + new String(bytes));

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NET", "Record post photo failed" + " "+i );
                if(bytes!=null)
                {
                    Log.d("NET", "Record post photo byte" + " "+new String(bytes) );
                }

            }
        });
    }

}
