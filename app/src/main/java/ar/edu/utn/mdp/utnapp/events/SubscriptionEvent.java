package ar.edu.utn.mdp.utnapp.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.List;
import ar.edu.utn.mdp.utnapp.MainActivity;
import ar.edu.utn.mdp.utnapp.ProgressDialog;
import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.commission.Subject;
import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.UserModel;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Network;

public class SubscriptionEvent {

    public static View.OnClickListener clickOnSubscription(Context ctx, List<Subject> subscriptionList) {
        return view -> {
            if (!Network.isNetworkConnected(ctx, true)) return;

            if (subscriptionList.size() < 1) {
                new ErrorDialog(ctx, "😱 Whoops!", "Parece que te has olvidado de seleccionar al menos una materia...", R.drawable.ic_alert);
                return;
            }

            HashSet<String> subscriptionSet = subcriptionListToHashSet(subscriptionList);

            User user = UserContext.getUser(ctx);
            user.setSubscription(subscriptionSet);

            Dialog progress = new ProgressDialog(ctx);

            UserModel.updateUser(ctx, user, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    progress.dismiss();
                    Intent intent = new Intent(ctx, MainActivity.class);
                    ctx.startActivity(intent);
                    ((Activity) ctx).finish();

                }

                @Override
                public void onError(int error) {
                    progress.dismiss();
                    ErrorDialog.handler(error, ctx);
                }
            });

        };
    }

    private static HashSet<String> subcriptionListToHashSet(List<Subject> subscriptionList) {
        HashSet<String> subscriptionSet = new HashSet<>(subscriptionList.size());
        for (Subject subject : subscriptionList) {
            subscriptionSet.add(subject.toString());
        }
        return subscriptionSet;
    }
}
