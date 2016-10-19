package restapi.test.narudore.personal.testrest;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ray on 10/19/2016.
 */

public class MyApp extends Application
{
    private Retrofit retrofit;

    @Override
    public void onCreate ()
    {
        super.onCreate();

        retrofit = new Retrofit.Builder()
            .baseUrl("http://10.1.11.87/testci3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public <T> T createService (Class<T> service)
    {
        return retrofit.create(service);
    }
}
