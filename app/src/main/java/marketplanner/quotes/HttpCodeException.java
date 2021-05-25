package marketplanner.quotes;

import java.io.IOException;

public class HttpCodeException extends IOException
{
    private int code_;

    public HttpCodeException(int code) {
        super("HTTP returned response code " + code);
        code_ = code;
    }

    public int code() {
        return code_;
    }
}
