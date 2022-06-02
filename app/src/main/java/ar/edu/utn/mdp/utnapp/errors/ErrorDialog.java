package ar.edu.utn.mdp.utnapp.errors;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;

import ar.edu.utn.mdp.utnapp.R;

public final class ErrorDialog extends Dialog {

    public ErrorDialog(Context context, String title, String message, String buttonText, boolean hideCancel, int logo, View.OnClickListener listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);

        findViewById(R.id.cancelDialog).setVisibility(hideCancel ? View.GONE : View.VISIBLE);
        findViewById(R.id.cancelDialog).setOnClickListener(v -> dismiss());
        findViewById(R.id.error_button).setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onClick(v);
        });

        ((ImageView) findViewById(R.id.logo)).setImageResource(logo);
        ((TextView) findViewById(R.id.dialogErrorTitle)).setText(title);
        ((TextView) findViewById(R.id.dialogErrorMessage)).setText(message);
        ((Button) findViewById(R.id.error_button)).setText(buttonText);

        show();
    }

    public ErrorDialog(Context context, String title, String message, int logo, View.OnClickListener listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);

        findViewById(R.id.cancelDialog).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelDialog).setOnClickListener(v -> dismiss());
        findViewById(R.id.error_button).setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onClick(v);
        });

        ((ImageView) findViewById(R.id.logo)).setImageResource(logo);
        ((TextView) findViewById(R.id.dialogErrorTitle)).setText(title);
        ((TextView) findViewById(R.id.dialogErrorMessage)).setText(message);

        show();
    }

    public ErrorDialog(Context context, String title, String message, int logo) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);

        findViewById(R.id.cancelDialog).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelDialog).setOnClickListener(v -> dismiss());
        findViewById(R.id.error_button).setOnClickListener(v -> dismiss());

        ((ImageView) findViewById(R.id.logo)).setImageResource(logo);
        ((TextView) findViewById(R.id.dialogErrorTitle)).setText(title);
        ((TextView) findViewById(R.id.dialogErrorMessage)).setText(message);

        show();
    }

    public ErrorDialog(Context context, String title, String message, View.OnClickListener listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);

        findViewById(R.id.cancelDialog).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelDialog).setOnClickListener(v -> dismiss());
        findViewById(R.id.error_button).setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onClick(v);
        });

        ((TextView) findViewById(R.id.dialogErrorTitle)).setText(title);
        ((TextView) findViewById(R.id.dialogErrorMessage)).setText(message);

        show();
    }

    public static void handler(int statusCode, Context ctx) {
        handler(statusCode, ctx, null);
    }

    public static void handler(int statusCode, Context ctx, View.OnClickListener onClickListener) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                new ErrorDialog(ctx, "Invalid credentials", "Email or password is wrong. Please try again.", R.drawable.ic_alert, onClickListener);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                new ErrorDialog(ctx, "Error", "Internal server error", R.drawable.ic_cloud_offline, onClickListener);
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                new ErrorDialog(ctx, "Error", "Service unavailable. Please try again later.", R.drawable.ic_cloud_offline, onClickListener);
                break;
            default:
                new ErrorDialog(ctx, "Error", "Unknown error. Please report it to the support.", R.drawable.ic_warning, onClickListener);
                break;
        }
    }
}
