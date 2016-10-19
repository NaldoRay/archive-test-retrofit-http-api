package restapi.test.narudore.personal.testrest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostNewsActivity extends AppCompatActivity
{
    private EditText titleText;
    private EditText contentText;
    private TextView errorText;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);

        titleText = (EditText) findViewById(R.id.titleText);
        contentText = (EditText) findViewById(R.id.contentText);
        errorText = (TextView) findViewById(R.id.errorText);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_post_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.postMenu:
                post();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void post ()
    {
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();

        MyApp app = (MyApp) getApplication();
        NewsService newsService = app.createService(NewsService.class);
        newsService.postNews(title, content).enqueue(new Callback<ServiceResponse<Void>>()
        {
            @Override
            public void onResponse (Call<ServiceResponse<Void>> call, Response<ServiceResponse<Void>> response)
            {
                ServiceResponse<Void> serviceResponse = response.body();
                if (serviceResponse.isSuccess())
                {
                    hideError();

                    setResult(RESULT_OK);
                    finish();
                }
                else
                {
                    showError(serviceResponse.getErrors());
                }
            }

            @Override
            public void onFailure (Call<ServiceResponse<Void>> call, Throwable t)
            {
                showError(new String[]{t.getMessage()});
            }
        });
    }

    private void showError (String[] errors)
    {
        String errorMessage = "";
        for (String error : errors)
            errorMessage += error + '\n';

        errorText.setText(errorMessage);
        errorText.setVisibility(View.VISIBLE);
    }

    private void hideError ()
    {
        errorText.setVisibility(View.GONE);
    }
}
