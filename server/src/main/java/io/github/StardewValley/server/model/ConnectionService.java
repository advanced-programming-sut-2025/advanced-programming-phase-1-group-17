package io.github.StardewValley.server.model;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConnectionService {
    private final Map<String, Long> lastSeenMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT_MS = 60_000;
    private static final long QuitGameTimer = 120_000;
    private final HashMap<String, Long> disConnectionTime = new HashMap<>();

    public void updateHeartbeat(String username) {
        lastSeenMap.put(username, System.currentTimeMillis());
    }

    public boolean isConnected(String username) {
        Long lastSeen = lastSeenMap.get(username);
        return lastSeen != null && (System.currentTimeMillis() - lastSeen) <= TIMEOUT_MS;
    }

    public Map<String, Long> getLastSeenMap() {
        return lastSeenMap;
    }

    public void markDisconnected(String username) {
        if (disConnectionTime.get(username) == null)
            disConnectionTime.put(username, System.currentTimeMillis());
    }

    public boolean shouldSaveAndQuit(String username) {
        return System.currentTimeMillis() - disConnectionTime.get(username) > QuitGameTimer;
    }

    public void removeDisconnectionTime(String username) {
        disConnectionTime.remove(username);
    }
}
