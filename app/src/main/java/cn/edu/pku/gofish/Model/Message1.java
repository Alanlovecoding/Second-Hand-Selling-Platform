package cn.edu.pku.gofish.Model;

/**
 * Created by Iris on 16/5/13.
 */

//消息界面中的每一个栏目

public class Message1 {
    String time;
    String usrname;
    String briefmessage;
    //String picurl;

    public Message1(String _time, String _usrname, String _briefmessage)
    {
        time = _time;
        usrname = _usrname;
        briefmessage = _briefmessage;
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
}
