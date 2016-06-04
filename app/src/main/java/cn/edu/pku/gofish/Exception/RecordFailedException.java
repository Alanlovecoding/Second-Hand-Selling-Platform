package cn.edu.pku.gofish.Exception;

/**
 * Created by leonardo on 16/6/4.
 */
public class RecordFailedException extends Exception{
    String message; //定义String类型变量
    public RecordFailedException(String ErrorMessagr) {  //父类方法
        message = ErrorMessagr;
    }
    public String getMessage(){   //覆盖getMessage()方法
        return message;
    }
}
