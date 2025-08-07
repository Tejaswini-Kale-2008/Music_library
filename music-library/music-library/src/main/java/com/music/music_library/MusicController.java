package com.music.music_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")  // Enable CORS for Postman or frontend
@RestController
@RequestMapping("/api/music")
public class MusicController {

    @Autowired
    private MusicRepository musicRepository;

    // Get all music
    @GetMapping
    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    // Get music by ID
    @GetMapping("/{id}")
    public Optional<Music> getMusicById(@PathVariable Long id) {
        return musicRepository.findById(id);
    }

    // Add new music
    @PostMapping
    public Music addMusic(@RequestBody Music music) {
        return musicRepository.save(music);
    }

    // Update music
    @PutMapping("/{id}")
    public Music updateMusic(@PathVariable Long id, @RequestBody Music updatedMusic) {
        return musicRepository.findById(id).map(music -> {
            music.setTitle(updatedMusic.getTitle());
            music.setArtist(updatedMusic.getArtist());
            music.setAlbum(updatedMusic.getAlbum());
            music.setYear(updatedMusic.getYear());
            return musicRepository.save(music);
        }).orElseGet(() -> {
            updatedMusic.setId(id);
            return musicRepository.save(updatedMusic);
        });
    }

    // Delete music
    @DeleteMapping("/{id}")
    public void deleteMusic(@PathVariable Long id) {
        musicRepository.deleteById(id);
    }
}
