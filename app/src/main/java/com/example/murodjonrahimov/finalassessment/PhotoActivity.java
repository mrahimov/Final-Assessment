package com.example.murodjonrahimov.finalassessment;

  import android.content.Intent;
  import android.content.SharedPreferences;
  import android.support.v7.app.AppCompatActivity;
  import android.os.Bundle;
  import android.view.Menu;
  import android.view.MenuInflater;
  import android.view.MenuItem;
  import android.widget.ImageView;
  import com.squareup.picasso.Picasso;
  import conteiner.DogAdapter;

  import static conteiner.DogAdapter.URL_KEY;

public class PhotoActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo);

    ImageView imageView = findViewById(R.id.image);
    String url = getIntent().getStringExtra(URL_KEY);

    Picasso.with(this).load(url).into(imageView);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.USER_NAME_KEY, MODE_PRIVATE);

    switch (item.getItemId()) {
      case R.id.logout:
        sharedPreferences.edit().remove(LoginActivity.PASSWORD_NAME_KEY).apply();
        finish();
        Intent intent = new Intent(PhotoActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
