package co.alexdev.bitsbake.events;

public class NetworkConnectionEvent {

    private boolean networkState;

    public NetworkConnectionEvent(boolean networkState) {
        this.networkState = networkState;
    }

    public boolean getNetworkState() {
        return networkState;
    }
}
