package io.github.StardewValley.shared.dto;

import java.util.ArrayList;

public class GetVoteCandidatesResponse {
    private ArrayList<String> playerUsernames;

    public GetVoteCandidatesResponse() {
    }

    public GetVoteCandidatesResponse(ArrayList<String> playerUsernames) {
        this.playerUsernames = playerUsernames;
    }

    public ArrayList<String> getPlayerUsernames() {
        return playerUsernames;
    }

    public void setPlayerUsernames(ArrayList<String> playerUsernames) {
        this.playerUsernames = playerUsernames;
    }
}
