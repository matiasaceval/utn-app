package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public final class ErrorDialog {

    private static Dialog dialog;

    public static void hideCancelButton(boolean hideCancel) {
        ImageView cancel = dialog.findViewById(R.id.cancelDialog);
        cancel.setOnClickListener(view -> dialog.dismiss());
        cancel.setVisibility(hideCancel ? View.GONE : View.VISIBLE);
    }

    public static void setLogo(int logo) {
        ImageView logoImage = dialog.findViewById(R.id.logo);
        logoImage.setImageResource(logo);
    }

    public static void setTitle(String title) {
        TextView titleText = dialog.findViewById(R.id.dialogErrorTitle);
        titleText.setText(title);
    }

    public static void setMessage(String message) {
        TextView messageText = dialog.findViewById(R.id.dialogErrorMessage);
        messageText.setText(message);
    }

    public static void setButtonText(String text) {
        Button button = dialog.findViewById(R.id.button);
        button.setText(text);
    }

    public static void setOnClickListener(View.OnClickListener listener) {
        Button ok = dialog.findViewById(R.id.button);
        ok.setOnClickListener(listener);
    }

    public static void setDefaultButton() {
        Button ok = dialog.findViewById(R.id.button);
        ok.setOnClickListener(view -> dialog.dismiss());
    }

    public static void show(Context context, String title, String message, String buttonText, boolean hideCancel, int logo, View.OnClickListener listener) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_error);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        setDefaultButton();

        if (logo != 0) setLogo(logo);
        if (title != null) setTitle(title);
        if (message != null) setMessage(message);
        if (buttonText != null) setButtonText(buttonText);
        if (listener != null) setOnClickListener(listener);
        hideCancelButton(hideCancel);

        dialog.show();
    }

    public static void show(Context context, String title, String message, String buttonText, boolean hideCancel, int logo) {
        show(context, title, message, buttonText, hideCancel, logo, null);
    }

    public static void show(Context context, String title, String message, String buttonText, boolean hideCancel) {
        show(context, title, message, buttonText, hideCancel, 0, null);
    }

    public static void show(Context context, String title, String message, boolean hideCancel) {
        show(context, title, message, null, hideCancel, 0, null);
    }

    public static void show(Context context, String title, String message, int logo) {
        show(context, title, message, null, false, logo, null);
    }

    public static void show(Context context, String title, String message, String buttonText) {
        show(context, title, message, buttonText, false, 0, null);
    }

    public static void show(Context context, String title, String message, View.OnClickListener listener) {
        show(context, title, message, null, false, 0, listener);
    }

    public static void show(Context context, String title, String message) {
        show(context, title, message, null, false, 0, null);
    }

}
