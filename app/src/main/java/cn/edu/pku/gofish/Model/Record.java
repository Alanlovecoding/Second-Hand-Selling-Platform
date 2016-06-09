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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.edu.pku.gofish.Exception.MyException;
import cn.edu.pku.gofish.USR;
import cz.msebera.android.httpclient.Header;


/**
 * Created by leonardo on 16/5/11.
 */
public class Record {

    private String ID;         //record unique number
    private String user_id;
    private String number;      //item number
    private String title;
    private String describetext;
    private String price;
    private ArrayList<String> imagePaths;
    private int imgcnt;
    private String usrname;

    private String status;
    private int picnum=0;


    private Bitmap[] file;

    static SyncHttpClient client = new SyncHttpClient();       //used for communicate with the server
    static AsyncHttpClient client2 = new AsyncHttpClient();

    private String url = "http://gofish.hackpku.com:8003/api/items";       //the server

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

    public Record(String id)
    {
        ID = id;
    }
    public void setID(String ID)
    {
        this.ID = ID;
    }
    String impath;       //impath is the path of the upload photo
    public Record(String user_id, String _title, String _describetext, String _pricetext, String _number,ArrayList<String> _imgPaths,String status) {
        title = _title;
        describetext = _describetext;
        price = _pricetext;

        this.user_id = user_id;
        number = _number;
        imagePaths = _imgPaths;
        picnum=imagePaths.size();
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

        params.put("title", title);          //put info in a params
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

        client2.post(url, params, new AsyncHttpResponseHandler() {       //send the params to url,for now upload one photo

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                //Log.d("NET", "Record post done"+new String(bytes));
                mListener.onDialogPositiveClick();
                for(int j=0;j<picnum;j++) {
                    try {
                        postPhoto(j,new String(bytes));      //upload one photo
                    } catch (MyException e) {
                        e.printStackTrace();
                    }
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
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {   //what to do if fail
                Log.d("NET", "Record post failed" + bytes);
                mListener.onDialogNegativeClick();
            }
        });
    }

    public void downloadFile()        //download a file
    {

        client2.get(url + "/"+ID, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user_id = response.getString("user_id");
                    number = response.getString("number");
                    price = response.getString("price");
                    String description = response.getString("description");
                    JSONObject user = response.getJSONObject("user");
                    user_id = user.getString("name");
                    describetext = description;
                    title=response.getString("title");
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
    public String getID(){return ID;}
    public String getTitle(){return title;}
    public String getDescribetext(){return describetext;}
    public Bitmap[] getFile(){return file;}
    public int getImgcnt(){return imgcnt;}
    public String getPrice(){return price;}
    public String getNumber(){return number;}

    public void postPhoto(int i,String item_id) throws MyException{     //upload a photo,the argument is the number of the record
        Log.d("NET", "Record uploadfile "+i+" begin");
        int count = 0;
        FileInputStream fis = null;
        RequestParams params = new RequestParams();

        File file = null;
        try {
            file = new File(imagePaths.get(i));       //open the upload picture to file
        }catch(Exception e){

        }
        params.put("item_id", item_id);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        if(file.exists()&& file.length()>0) {      //if file exist and length>0
            try {
                //params.put("", file);
                //Log.d("NET", "Record image_file " + file.toString());
                int length = (int) file.length();

                byte[] bytes = new byte[length];

                FileInputStream in = new FileInputStream(file);
                BufferedInputStream buf = new BufferedInputStream(in);
                Bitmap bitmap = BitmapFactory.decodeStream(buf);      //change it to a bitmap
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
                File dir = new File(sdCard.getAbsolutePath() + "/dir1/dir2");    //put the upload picture to a new path
                dir.mkdirs();
                String t = format.format(new Date());
                String tmpname="tmpfileup" + t + ".jpg";
                File file2 = new File(dir, tmpname);    //give it a new name

                FileOutputStream f = new FileOutputStream(file2, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, f);
                f.flush();
                f.close();
                Log.d("NET", "write done ");

                params.put("image_file", file2);      //put the file2 to params

                //Log.d("NET", "content " + contents);
            } catch (FileNotFoundException e) {
                Log.d("NET", "file not exist");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("NET", "Record post begin " + params.toString());

        client2.post("http://gofish.hackpku.com:8003/api/images", params, new AsyncHttpResponseHandler() {    //post them

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
