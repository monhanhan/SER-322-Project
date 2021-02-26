CREATE DATABASE IF NOT EXISTS music;
USE music;

CREATE TABLE ARTIST (
    artist_id INT NOT NULL,
    name VARCHAR(30),
    UNIQUE(artist_id),
    PRIMARY KEY(artist_id)
);

CREATE TABLE BAND (
    band_id INT NOT NULL,
    band_name VARCHAR(30),
    UNIQUE(band_id),
    PRIMARY KEY(band_id)
);

CREATE TABLE FORMS (
    band_id int NOT NULL,
    artist_id int NOT NULL,
    PRIMARY KEY(band_id, artist_id),
    UNIQUE(band_id, artist_id),
    FOREIGN KEY(band_id) REFERENCES BAND(band_id),
    FOREIGN KEY(artist_id) REFERENCES ARTIST(artist_id)
);

CREATE TABLE SONG (
    song_id INT NOT NULL,
    release_year int,
    name VARCHAR(30),
    UNIQUE(song_id),
    PRIMARY KEY(song_id)
);

CREATE TABLE PERFORMS (
    artist_id int NOT NULL,
    song_id int NOT NULL,
    Unique(artist_id, song_id),
    PRIMARY KEY(artist_id, song_id),
    FOREIGN KEY(artist_id) REFERENCES ARTIST(artist_id),
    FOREIGN KEY(song_id) REFERENCES SONG(song_id)
);

CREATE TABLE GENRE (
    name VARCHAR(30) NOT NULL,
    UNIQUE(name),
    PRIMARY KEY(name)
);

CREATE TABLE IS_GENRE (
    genre_name VARCHAR(30) NOT NULL,
    song_id INT NOT NULL,
    UNIQUE(genre_name, song_id),
    PRIMARY KEY(genre_name, song_id),
    FOREIGN KEY(genre_name) REFERENCES GENRE(Name),
    FOREIGN KEY(song_id) REFERENCES SONG(song_id)
);

CREATE TABLE ALBUM (
    album_id INT NOT NULL,
    title VARCHAR(30),
    release_year int,
    UNIQUE(album_id),
    PRIMARY KEY(album_id)
);

CREATE TABLE HAS (
    song_id INT NOT NULL,
    album_id INT NOT NULL,
    UNIQUE(song_id, album_id),
    PRIMARY KEY(song_id, album_id),
    FOREIGN KEY(song_id) REFERENCES SONG(song_id),
    FOREIGN KEY(album_id) REFERENCES ALBUM(album_id)
);