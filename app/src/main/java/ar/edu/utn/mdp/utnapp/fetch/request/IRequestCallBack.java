package ar.edu.utn.mdp.utnapp.fetch.request;

import org.json.JSONObject;

public interface IRequestCallBack {
    void onSuccess(JSONObject response);
    void onError(int statusCode);
}
