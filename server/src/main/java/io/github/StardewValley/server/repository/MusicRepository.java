package io.github.StardewValley.server.repository;

import io.github.StardewValley.shared.dto.MusicDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MusicRepository {
    private static final ArrayList<MusicDTO> musicList = new ArrayList<>();

    public MusicRepository() {
        MusicDTO sample = new MusicDTO();
        sample.setName("Wind Sound");
        sample.setUrl("C:\\Users\\omidr\\Desktop\\sound effects\\Wind.mp3");
        musicList.add(sample);
    }

    public static ArrayList<MusicDTO> getAll() {
        return musicList;
    }

    public static void addMusic(MusicDTO music) {
        for(MusicDTO m : musicList) {
            if(m.getUrl().equals(music.getUrl())) {
                return;
            }
        }
        musicList.add(music);
    }

    public static void removeMusic(String name) {
        musicList.removeIf(m -> m.getName().equalsIgnoreCase(name));
    }
}
