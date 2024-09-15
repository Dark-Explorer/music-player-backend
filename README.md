# Project: My Music

### Simple Backend Music Web App
This is backend repository of a music app, providing a new experience for users to enjoy their favourite
genres, songs, artists.

Technology: Spring Boot v3.3.2, Spring Data JPA, Spring Security,
Lombok, Mapstruct, JWT, REST API

Database: MySQL

Other: Maven

Backend: https://github.com/Dark-Explorer/music-player-frontend

## Project Setup
Note: This project uses Java 22. You need to install from [Oracle](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)
1. After cloning process, install necessary dependencies using Maven (dependencies are shown in pom.xml)

2. Config your database management system (MySQL) information in application.yaml

3. Run the project at http://localhost:1208/

## Endpoints:

### Collection: Security

#### Login (Get token):

Method: POST

>```
>http://localhost:1208/music/auth/token
>```

Request body:
```json
{
  "username": "",
  "password": ""
}
```

#### Introspect (Validate token):

Method: POST

>```
>http://localhost:1208/music/auth/introspect
>```

Request body:
```json
{
  "token": ""
}
```

#### Log out:

Method: POST

>```
>http://localhost:1208/music/auth/logout
>```

Request body:
```json
{
  "token": ""
}
```

### Collection: User

#### Create new user (Sign up):

Method: POST

>```
>http://localhost:1208/music/users
>```

Request body:
```json
{
  "name": "",
  "username": "",
  "password": "",
  "email": ""
}
```

#### Get current user info

Method: GET

>```
>http://localhost:1208/music/users/myInfo
>```

#### Update user info

Method: PUT

>```
>http://localhost:1208/music/users/{userId}
>```

Request body:
```json
{
  "password": "",
  "email": ""
}
```

### Collection: Song

#### Create a song:

Method: POST

>```
>http://localhost:1208/music/songs
>```

Request body:
```json
{
  "title": "",
  "image": "",
  "sound_link": "",
  "artists": []
}
```

#### Get all songs:

Method: GET

>```
>http://localhost:1208/music/songs
>```

#### Get a song by ID:

Method: GET

>```
>http://localhost:1208/music/songs/{songId}
>```

#### Edit a song:

Method: PUT

>```
>http://localhost:1208/music/songs/{songId}
>```

Request body:
```json
{
  "title": "",
  "image": "",
  "sound_link": "",
  "artists": []
}
```

#### Delete a song:

Method: DELETE

>```
>http://localhost:1208/music/songs/{songId}
>```

#### Search for songs by title:

Method: GET

>```
>http://localhost:1208/music/songs/search
>```

|Param|Type|
|---|---|
|title|String|

#### Get songs by artist:

Method: GET

>```
>http://localhost:1208/music/songs/{artistId}/songs
>```

### Collection: Artist

#### Create an artist:

Method: POST

>```
>http://localhost:1208/music/artists
>```

Request body:
```json
{
  "name": "",
  "gender": "",
  "yob": 0,
  "image": ""
}
```

#### Get all artists:

Method: GET

>```
>http://localhost:1208/music/artists
>```

#### Edit an artist:

Method: PUT

>```
>http://localhost:1208/music/artists/{artistId}
>```

Request body:
```json
{
  "name": "",
  "gender": "",
  "yob": 0,
  "image": ""
}
```

#### Delete an artist:

Method: DELETE

>```
>http://localhost:1208/music/artists/{artistId}
>```

#### Get an artist's info

Method: GET

>```
>http://localhost:1208/music/artists/info/{artistId}
>```

#### Get artists by name:

Method: GET

>```
>http://localhost:1208/music/artists/{artistName}
>```

### Collection: Playlist

#### Create a playlist:

Method: POST

>```
>http://localhost:1208/music/playlists
>```

Request body:
```json
{
  "name": ""
}
```

#### Get playlists by name:

Method: GET

>```
>http://localhost:1208/music/playlists
>```

| Param |Type|
|----|---|
|name|String|

#### Get current user's playlists:

Method: GET

>```
>http://localhost:1208/music/playlists/myPlaylists
>```

#### Get songs of a playlist:

Method: GET

>```
>http://localhost:1208/music/playlists/{playlistId}
>```

#### Edit a playlist:

Method: PUT

>```
>http://localhost:1208/music/playlists/{playlistId}
>```

Request body:
```json
{
  "name": ""
}
```

#### Delete a playlist:

Method: DELETE

>```
>http://localhost:1208/music/playlists/{playlistId}
>```

#### Add a song to a playlist

Method: POST

>```
>http://localhost:1208/music/playlists/{playlistId}/songs/{songId}
>```

#### Remove a song from a playlist

Method: DELETE

>```
>http://localhost:1208/music/playlists/{playlistId}
>```

### Collection: Search

#### Search songs and playlists by keyword:

Method: GET

>```
>http://localhost:1208/music/search
>```

| Param   |Type|
|---------|---|
| keyword |String|