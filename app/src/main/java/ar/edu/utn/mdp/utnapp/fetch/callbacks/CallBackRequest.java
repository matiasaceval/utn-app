package ar.edu.utn.mdp.utnapp.fetch.callbacks;

public class CallBackRequest<T>  implements ICallBackRequest<T> {

    public CallBackRequest() {
    }

    @Override
    public void onSuccess(T response) {}
    @Override
    public void onError(int statusCode) {}
}
