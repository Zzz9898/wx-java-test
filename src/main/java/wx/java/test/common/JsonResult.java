package wx.java.test.common;

public class JsonResult<T> {
    private int code;
    private String message;
    private T data;

    public JsonResult() {
        this.code = 0;
        this.message = "";
        this.data = null;
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> JsonResult<T> success(String message) {
        return new JsonResult(0, message);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(0, "success", data);
    }

    public static <T> JsonResult<T> success(String message, T data) {
        return new JsonResult(0, message, data);
    }

    public static <T> JsonResult<T> error(String message) {
        return new JsonResult(-1, message);
    }

    public static <T> JsonResult<T> error(String message, T data) {
        return new JsonResult(-1, message, data);
    }

    public static <T> JsonResult<T> error(int code, String message) {
        return new JsonResult(code, message);
    }

    public static <T> JsonResult<T> error(int code, String message, T data) {
        return new JsonResult(code, message, data);
    }
}
