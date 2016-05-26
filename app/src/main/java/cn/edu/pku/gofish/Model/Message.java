package cn.edu.pku.gofish.Model;

/**
 * Created by leonardo on 16/5/13.
 */
public class Message {
    public static final int TYPE_RECEVIED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;
    private String time;
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


}
