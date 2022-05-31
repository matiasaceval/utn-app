package ar.edu.utn.mdp.utnapp.fetch.callback_request;

public class CallBackRequest<T>  implements ICallBack <T>{

    @Override
    public void onSuccess(T response) {}
    @Override
    public void onError(int statusCode) {}
}
