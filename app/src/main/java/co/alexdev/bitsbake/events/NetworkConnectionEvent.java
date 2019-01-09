package co.alexdev.bitsbake.events;


/*
  Event which triggers when a recipe is clicked
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
