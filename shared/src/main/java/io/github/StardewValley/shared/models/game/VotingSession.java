package io.github.StardewValley.shared.models.game;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VotingSession {
    VotingType type;
    Player targetPlayer; // only for KICK_PLAYER
    Map<Player, Boolean> votes = new HashMap<>(); // username -> vote
    Set<Player> players;
    boolean active;
    Game game;

    public void start(Set<Player> players, VotingType type, Player target, Game game) {
        this.players = players;
        this.type = type;
        this.targetPlayer = target;
        this.active = true;
        this.game = game;
    }

    public Result submitVote(Player player, boolean vote) {
        if (!active || votes.containsKey(player)) return new Result(false, "You have already voted.");
        votes.put(player, vote);
        if (votes.size() == players.size()) {
            return finish();
        }
        return new Result(false, "");
    }

    private Result finish() {
        active = false;
        long yesCount = votes.values().stream().filter(v -> v).count();
        if (yesCount > players.size() / 2) {
            if (type == VotingType.FORCE_TERMINATE) {
                return new Result(true, "Game Terminated successfully");
            }
            else if (type == VotingType.KICK_PLAYER) {
                return new Result(true, "Player %s kicked out of the game successfully."
                    .formatted(targetPlayer));
            }
        }
        return new Result(true, "Game resuming...");
    }


    public enum VotingType {
        FORCE_TERMINATE, KICK_PLAYER;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public VotingType getType() {
        return type;
    }

    public void setType(VotingType type) {
        this.type = type;
    }

    public Map<Player, Boolean> getVotes() {
        return votes;
    }

    public void setVotes(Map<Player, Boolean> votes) {
        this.votes = votes;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}


