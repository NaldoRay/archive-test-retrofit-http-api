package personal.narudore.test.android.testretrofit;

/**
 * Created by Ray on 10/19/2016.
 */

public class ServiceResponse<T>
{
    private boolean success;
    private T[] data;
    private String[] errors;

    public boolean isSuccess ()
    {
        return success;
    }

    public T[] getData ()
    {
        return data;
    }

    public String[] getErrors ()
    {
        return errors;
    }
}
