package com.fpt.hungnm.assigmentfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Logout {
    private static final String TAG = "LogoutHelper";

    public static void performLogout(Context context) {
        try {
            Log.d(TAG, "Logout started!");

            // Đăng xuất Firebase
            FirebaseAuth.getInstance().signOut();

            // Xóa dữ liệu SharedPreferences
            SharedPreferences preferences = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Log.d(TAG, "SharedPreferences cleared!");

            // Quay về Login
            Intent intent = new Intent(context, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

            Toast.makeText(context, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Log.e(TAG, "performLogout - " + ex.getMessage());
        }
    }
}
