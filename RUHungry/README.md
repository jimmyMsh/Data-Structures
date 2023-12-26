## Project Overview

The Music Playlist project was designed to build a music streaming application in Java, which simulates functionalities similar to those found in apps like Spotify or Apple Music. It involved the implementation of circular linked lists, object-oriented programming (OOP) principles, file reading from CSV format, and the use of arrays to manage collections of data.

### Skills Demonstrated

- Circular Linked List Manipulation
- Object Referencing
- CSV File Input/Output
- Object-Oriented Programming
- ArrayList Usage

### Assignment Structure

The project is structured into the following main components:

1. **Song Class**: This class encapsulates the metadata for a single song, such as title, artist, year, popularity, and a link to the audio file.
   
2. **PlaylistLibrary Class**: This is the central class of the application. It stores an ArrayList of `Playlist` objects, which represent users' music playlists. It provides methods for playlist management such as creating, adding, removing, reversing, merging, and shuffling playlists.

3. **Driver**: A utility class that facilitates interactive testing of the `PlaylistLibrary` implementation. It is not part of the graded submission but is essential for development and debugging.

4. **StdRandom and StdAudio**: Utility classes provided for shuffling playlists and playing songs, respectively.

5. **StdIn and StdOut**: Classes for handling file input and console output – are not to be modified.

### Key Methods in PlaylistLibrary

- `createPlayList(String filename)`: Parses a CSV file to create a single playlist.

- `addPlaylist(String filename, int playlistIndex)`: Adds a playlist to the song library at a specified index—an implementation provided for this method.

- `addAllPlaylists(String[] filenames)`: Accepts an array of CSV filenames and adds the corresponding playlists to the library.

- `insertSong(int playlistIndex, int position, Song song)`: Inserts a song into a specified playlist at a given position.

- `removeSong(int playlistIndex, Song song)`: Removes a song from a specified playlist if it exists.

- `reversePlaylist(int playlistIndex)`: Reverses the order of songs in a specified playlist.

- `mergePlaylists(int playlistIndex1, int playlistIndex2)`: Merges two playlists, maintaining songs in decreasing order of popularity.

- `shufflePlaylist(int playlistIndex)`: Shuffles the songs in a specified playlist randomly.
