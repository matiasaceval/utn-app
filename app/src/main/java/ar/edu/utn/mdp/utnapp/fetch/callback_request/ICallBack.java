package ar.edu.utn.mdp.utnapp.fetch.callback_request;

public interface ICallBack<T> {
    void onSuccess(T response);
    void onError(int statusCode);
}
