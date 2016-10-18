package restapi.test.narudore.personal.testrest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 10/17/2016.
 */

class News
{
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;


    public int getId ()
    {
        return id;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getContent ()
    {
        return content;
    }

    @Override
    public String toString ()
    {
        return title;
    }
}
