package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
}
