package ar.edu.utn.mdp.utnapp.fetch.callbacks;

public interface ICallBackRequest<T> {
    void onSuccess(T response);
    void onError(int statusCode);
}
