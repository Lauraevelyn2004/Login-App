package br.edu.ifsuldeminas.mch.pdm.login;

import android.app.Activity;
import android.app.ComponentCaller;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;

import br.edu.ifsuldeminas.mch.login.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button buttonTakePicture;
    private ImageView imageViewPicture;

    private Intent resultIntent;

    private static final int REQUEST_PICTURE_CODE = 987654;
    private static final String RESULT_KEY = "resultado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_welcome_id), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intentQueChamou = getIntent();
        String userName = intentQueChamou.getStringExtra("user_name");

        String finalMessage = MessageFormat.format("{0} {1}!",
                getText(R.string.welcome_message), userName);

        View viewLayout = findViewById(R.id.activity_welcome_id);
        Snackbar snackbar = Snackbar.make(viewLayout, finalMessage, Snackbar.LENGTH_LONG);
        snackbar.show();

        buttonTakePicture = findViewById(R.id.buttonTakePictureId);
        imageViewPicture = findViewById(R.id.imageViewPictureId);

        buttonTakePicture.setOnClickListener((View view) -> {
            Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicIntent, REQUEST_PICTURE_CODE);
        });

        resultIntent = new Intent();
        resultIntent.putExtra(RESULT_KEY, "Não bateu a foto.");
        setResult(Activity.RESULT_OK, resultIntent);
    }

    // Dar suporte ao botão Up Navigation (<--)
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data,
                                 @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);

        if (requestCode == REQUEST_PICTURE_CODE) {
            if (data != null) {
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap image = (Bitmap) extras.get("data");
                    imageViewPicture.setImageBitmap(image);

                    resultIntent.putExtra(RESULT_KEY, "Bateu a foto.");
                }
            }
        }
    }
}
