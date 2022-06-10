package App;

public class Records_element
{

    public final String user_id;
    public final String user_id_f;
    public final String message;

    Records_element(String user_id,String user_id_f,String message)
    {
        this.user_id = user_id;
        this.user_id_f = user_id_f;
        this.message = message;
    }

    public String getUser_id()
    {
        return this.user_id;
    }
    public String getUser_id_f()
    {
        return this.user_id_f;
    }
    public String getMessage()
    {
        return this.message;
    }
}