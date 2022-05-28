package ar.edu.utn.mdp.utnapp.fetch.request;

import org.json.JSONArray;

public interface IRequestCallBack {
    void onSuccess(JSONArray response);

    void onError(int statusCode);
}
