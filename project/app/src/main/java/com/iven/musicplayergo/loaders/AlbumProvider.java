package com.iven.musicplayergo.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.iven.musicplayergo.models.Album;
import com.iven.musicplayergo.models.Artist;
import com.iven.musicplayergo.models.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumProvider {

    public static final int ALBUMS_LOADER = 1;

    @NonNull
    static List<Album> retrieveAlbums(@Nullable final List<Song> songs) {
        List<Album> albums = new ArrayList<>();
        if (songs != null) {
            for (Song song : songs) {
                getAlbum(albums, song.albumName).songs.add(song);
            }
        }
        return albums;
    }

    private static Album getAlbum(List<Album> albums, String albumName) {
        for (Album album : albums) {
            if (!album.songs.isEmpty() && album.songs.get(0).albumName.equals(albumName)) {
                return album;
            }
        }
        Album album = new Album();
        albums.add(album);
        return album;
    }

    public static class AsyncAlbumsForArtistLoader extends WrappedAsyncTaskLoader<Pair<Artist, List<Album>>> {

        private String mSelectedArtist;

        public AsyncAlbumsForArtistLoader(Context context, String selectedArtist) {
            super(context);
            mSelectedArtist = selectedArtist;
        }

        @Override
        public Pair<Artist, List<Album>> loadInBackground() {
            Artist artist = ArtistProvider.getArtist(getContext(), mSelectedArtist);
            List<Album> albums = artist.albums;
            return new Pair<>(artist, albums);
        }
    }
}
