package co.alexdev.bitsbake.networking;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.utils.Constants;

/**Network Response class used to catch the connection status
 * @param status current status for the network
 * @param data data that we need to receive
 * @param error error thrown */
public class NetworkResponse {

    @Constants.NetworkStatus
    public final int status;

    @Nullable
    public List<Recipe> data;

    @Nullable
    public final Throwable error;

    public NetworkResponse(int status, @Nullable List<Recipe> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static NetworkResponse loading() {
        return new NetworkResponse(Constants.RESPONSE_LOADING, null, null);
    }

    public static NetworkResponse success(@NonNull List<Recipe> recipes) {
        return new NetworkResponse(Constants.RESPONSE_SUCCES, recipes, null);
    }

    public static NetworkResponse error(@NonNull Throwable error) {
        return new NetworkResponse(Constants.RESPONSE_ERROR, null, error);
    }
}
