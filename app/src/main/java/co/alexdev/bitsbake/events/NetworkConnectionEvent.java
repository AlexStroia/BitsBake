package co.alexdev.bitsbake.events;

/**
 * Event which triggers when a recipe is clicked
 *
 * @param  networkState  boolean which shows if the network is on or off
 */

public class NetworkConnectionEvent {

    private boolean networkState;

    public NetworkConnectionEvent(boolean networkState) {
        this.networkState = networkState;
    }

    public boolean getNetworkState() {
        return networkState;
    }
}
