package ar.edu.utn.mdp.utnapp.fetch.request;

public interface LoginCallBack {
    void onSuccess();

    void onError(int statusCode);
}
