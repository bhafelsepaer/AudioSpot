package com.epam.audiospot.builder;

import com.epam.audiospot.entity.Artist;
import com.epam.audiospot.entity.AudioTrack;
import com.epam.audiospot.entity.Genre;
import com.epam.audiospot.exception.IllegalBuildStateException;
import com.epam.audiospot.exception.ServiceException;
import com.epam.audiospot.service.ArtistService;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AudioBuilder implements Builder<AudioTrack> {

    @Override
    public AudioTrack build(ResultSet resultSet) throws SQLException, ServiceException {
        Long id = resultSet.getLong(AudioTrack.ID_LABEL);
        String title = resultSet.getString(AudioTrack.TITLE_LABEL);
        BigDecimal price = resultSet.getBigDecimal(AudioTrack.PRICE_LABEL);
        Long authorId = resultSet.getLong(AudioTrack.AUTHOR_ID_LABEL);

        ArtistService artistService = new ArtistService();
        Optional<Artist> artistOptional = artistService.getArtist(authorId);
        if(!artistOptional.isPresent()){
            throw new IllegalBuildStateException("Missing artist");
        }

        Long albumId = resultSet.getLong(AudioTrack.ALBUM_ID_LABEL);
        String genreContent = resultSet.getString(AudioTrack.GENRE_LABEL);
        Genre genre = Genre.valueOf(genreContent.toUpperCase());
        return new AudioTrack(id,albumId,artistOptional.get(),title,price,genre);
    }
}
