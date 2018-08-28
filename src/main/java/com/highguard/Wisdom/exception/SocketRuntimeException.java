package com.highguard.Wisdom.exception;

/**
 * Created by ws on 2017/7/2.
 */
public class SocketRuntimeException extends RuntimeException{
    private int mErrorCode = -1;

    public final static int DEFAULT_ERROR_CODE = 500;
    public final static int DEVICEID_NOTFOUND = 404;
    public final static int DEVICEID_ISUSED = 403;
    public final static int CARD_NOTEXISTS = 405;
    public final static int CARD_EXISTS = 406;
    public final static int INVALID_ARGUMENTS = 407;
    public final static int INVALID_USER = 408;
    public final static int LASTORDER_NOTPAY = 409;
    public final static int LASTORDER_NOTGIVEBACK = 410;
    public final static int INVALID_USER_MOBILE = 411;



    public SocketRuntimeException(int errorCode, String message){
        super(message);
        mErrorCode = errorCode;
    }

    public int getErrorCode(){
        return mErrorCode;
    }

}
