package ar.edu.utn.mdp.utnapp.fetch.callbacks;

public interface ICallbackRequest<T> {
    void onSuccess(T response);

    void onError(int statusCode);
}
