package co.alexdev.bitsbake.networking;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import co.alexdev.bitsbake.model.response.Recipe;
import co.alexdev.bitsbake.utils.Constants;

public class NetworkResponse {

    @Constants.NetworkStatus
    final int mNetworkStatus;

    @Nullable
    public List<Recipe> data;

    @Nullable
    public final Throwable error;

    public NetworkResponse(int mNetworkStatus, @Nullable List<Recipe> data, @Nullable Throwable error) {
        this.mNetworkStatus = mNetworkStatus;
        this.data = data;
        this.error = error;
    }

    public static NetworkResponse loading() {
        return new NetworkResponse(Constants.ERROR, null, null);
    }

    public static NetworkResponse success(@NonNull List<Recipe> recipes) {
        return new NetworkResponse(Constants.SUCCESS, recipes, null);
    }

    public static NetworkResponse error(@NonNull Throwable error) {
        return new NetworkResponse(Constants.ERROR, null, error);
    }
}
