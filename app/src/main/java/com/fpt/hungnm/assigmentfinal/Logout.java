package com.fpt.hungnm.assigmentfinal.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.fpt.hungnm.assigmentfinal.Login;

public class Logout {
    private static final String TAG = "LogoutHelper";

    public static void performLogout(Context context) {
        try {
            // Xóa dữ liệu đăng nhập
            SharedPreferences preferences = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(context, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

            Toast.makeText(context, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e(TAG, "performLogout - " + ex.getMessage());
        }
    }
}
