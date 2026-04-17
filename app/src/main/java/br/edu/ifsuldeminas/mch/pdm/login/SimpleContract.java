package br.edu.ifsuldeminas.mch.pdm.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimpleContract extends ActivityResultContract<String, String> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, String userName) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.putExtra("user_name", userName);
        return intent;
    }

    @Override
    public String parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null)
            return "";

        return intent.getStringExtra("resultado");
    }
}
