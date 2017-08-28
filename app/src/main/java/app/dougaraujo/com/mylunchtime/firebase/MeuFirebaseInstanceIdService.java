package app.dougaraujo.com.mylunchtime.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dux-Douglas2 on 28/08/2017.
 */

public class MeuFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String resefhedToken = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(resefhedToken);
    }

    private void sendRegistrationToServer(String resefhedToken) {
        Log.d("TOKEN", resefhedToken);
    }
}
