package music;

import java.util.*;

/**
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists 
 * within one's library.
 * 
 * @author Jeremy Hui
 * @author Vian Miranda
 */
public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary; // contains various playlists

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param songLibrary passes in ArrayList of playlists
     */
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library. 
     */
    public PlaylistLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a 
     * playlist from it.
     * Each song is on a different line.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. Declare a reference that will refer to the last song of the circular linked list.
     * 
     * 3. While there are still lines in the input file:
     *      1. read a song from the file
     *      2. create an object of type Song with the song information
     *      3. Create a SongNode object that holds the Song object from step 3.2.
     *      4. insert the Song object at the END of the circular linked list (use the reference from step 2)
     *      5. increase the count of the number of songs
     * 
     * 4. Create a Playlist object with the reference from step (2) and the number of songs in the playlist
     * 
     * 5. Return the Playlist object
     * 
     * Each line of the input file has the following format:
     *      songName,artist,year,popularity,link
     * 
     * To read a line, use StdIn.readline(), then use .split(",") to extract 
     * fields from the line.
     * 
     * If the playlist is empty, return a Playlist object with null for its last, 
     * and 0 for its size.
     * 
     * The input file has Songs in decreasing popularity order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to end.
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song 
     * in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist createPlaylist(String filename) {

        //Opening file 
        StdIn.setFile(filename);

        //Object that will hold reference to last object in linkedlist
        SongNode lastSong = null;

        //song counter
        int songCount = 0;

        //Transverse through CSV
        while(StdIn.hasNextLine())
        {
            String line = StdIn.readLine(); //Line read 
            String[] songDetails = line.split(","); //Captures entire line of information

            //song object, given the information in CSV
            Song song = new Song(songDetails[0], songDetails[1], Integer.parseInt(songDetails[2]), Integer.parseInt(songDetails[3]), songDetails[4]);

            //songNode object given song
            SongNode songNode = new SongNode(song, null);

            if(lastSong == null)
            {
            songNode.setNext(songNode); //Initally points to itself 
            lastSong = songNode;
            }
            else
            {
            songNode.setNext(lastSong.getNext()); //otherwise, points to the node after last (the first)
            lastSong.setNext(songNode);
            lastSong = songNode;
            }
            songCount++;
        }
        
        Playlist newPlayList = new Playlist(lastSong, songCount);

        return newPlayList; 
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you. 
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * 1. Calls createPlayList() with a file containing song information.
     * 2. Adds the new playlist created by createPlayList() into the songLibrary.
     * 
     * Note: initialize the songLibrary if it is null
     * 
     * @param filename the playlist information input file
     * @param playlistIndex the index of the location where the playlist will 
     * be added 
     */
    public void addPlaylist(String filename, int playlistIndex) {
        
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null ) {
            songLibrary = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= songLibrary.size() ) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null || playlistIndex >= songLibrary.size() ) {
            return false;
        }

        songLibrary.remove(playlistIndex);
            
        return true;
    }
    
    /** 
     * 
     * Adds the playlists from many files into the songLibrary
     * 
     * 1. Initialize the songLibrary
     * 
     * 2. For each of the filenames
     *       add the playlist into songLibrary
     * 
     * The playlist will have the same index in songLibrary as it has in
     * the filenames array. For example if the playlist is being created
     * from the filename[i] it will be added to songLibrary[i]. 
     * Use the addPlaylist() method. 
     * 
     * @param filenames an array of the filenames of playlists that should be 
     * added to the library
     */
    public void addAllPlaylists(String[] filenames) {
        
        songLibrary = new ArrayList<Playlist>();

        for (int i = 0; i < filenames.length ; i++)
        {
            addPlaylist(filenames[i], i);
        }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * The first node of the circular linked list is at position 1, the 
     * second node is at position 2 and so forth.
     * 
     * Return true if the song can be added at the given position within the 
     * specified playlist (and thus has been added to the playlist), false 
     * otherwise (and the song will not be added). 
     * 
     * Increment the size of the playlist if the song has been successfully
     * added to the playlist.
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param position the position inthe playlist to which the song 
     * is to be added 
     * @param song the song to add
     * @return true if the song can be added and therefore has been added, 
     * false otherwise. 
     */
    public boolean insertSong(int playlistIndex, int position, Song song) {
    
        // Validate playlist index and position
        if (playlistIndex < 0 || playlistIndex >= songLibrary.size() || position < 1) {
            return false;
        }
    
        // Catch the playlist specified by the index
        Playlist changePlaylist = songLibrary.get(playlistIndex);
        int sizePlayList = changePlaylist.getSize();
    
        // Check if position is beyond the end of the playlist
        if (position > sizePlayList + 1) {
            return false;
        }
    
        // Create a new node for the song
        SongNode newSong = new SongNode(song, null);
    
        // Case where position is at the end of the list
        if (position == sizePlayList + 1) {
            if (changePlaylist.getLast() == null) 
            {
                // If the playlist is empty, set the new song as the only node
                newSong.setNext(newSong);
            } 
            else 
            {
                // Otherwise, insert at the end
                newSong.setNext(changePlaylist.getLast().getNext());
                changePlaylist.getLast().setNext(newSong);
            }
            changePlaylist.setLast(newSong);
        } 
        else 
        {
            // Find the node before the insertion point
            SongNode findPointer = changePlaylist.getLast();
            for (int i = 0; i < position - 1; i++) {
                findPointer = findPointer.getNext();
            }
    
            // Insert the new song at the specified position
            newSong.setNext(findPointer.getNext());
            findPointer.setNext(newSong);
        }
    
        // Increment the size of the playlist
        changePlaylist.setSize(sizePlayList + 1);
        return true;
    }
    

    /**
     * This method removes a song at a specified playlist, if the song exists. 
     *
     * Use the .equals() method of the Song class to check if an element of 
     * the circular linkedlist matches the specified song.
     * 
     * Return true if the song is found in the playlist (and thus has been 
     * removed), false otherwise (and thus nothing is removed). 
     * 
     * Decrease the playlist size by one if the song has been successfully
     * removed from the playlist.
     * 
     * @param playlistIndex the playlist index within the songLibrary where 
     * the song is to be added.
     * @param song the song to remove.
     * @return true if the song is present in the playlist and therefore has 
     * been removed, false otherwise.
     */
    public boolean removeSong(int playlistIndex, Song song) {
        // Validate the playlist index
        if (playlistIndex < 0 || playlistIndex >= songLibrary.size()) {
            return false;
        }
    
        // Retrieve the specified playlist
        Playlist changePlaylist = songLibrary.get(playlistIndex);
    
        // Check for an empty playlist
        int sizePlayList = changePlaylist.getSize();
        if (sizePlayList == 0) {
            return false;
        }
    
        // Handle the case of a single-song playlist
        SongNode findPointer = changePlaylist.getLast();
        if (sizePlayList == 1) 
        {
            if (findPointer.getSong().equals(song)) {
                changePlaylist.setLast(null); // Remove the only song in the playlist
                changePlaylist.setSize(0);
                return true;
            } 
            else 
            {
                return false; // The only song does not match
            }
        } 
        else 
        {
            // Traverse the circular linked list to find and remove the song
            do 
            {
                // Check if the next node contains the song to remove
                if (findPointer.getNext().getSong().equals(song)) {
                    // Check if we are removing the last node in the list
                    if (findPointer.getNext().equals(changePlaylist.getLast())) {
                        changePlaylist.setLast(findPointer); // Update last node reference
                    }
    
                    // Remove the node by skipping it in the list
                    findPointer.setNext(findPointer.getNext().getNext());
                    changePlaylist.setSize(sizePlayList - 1);
                    return true;
                }
                // Move to the next node
                findPointer = findPointer.getNext();
            } while (findPointer != changePlaylist.getLast());
    
            return false; // Song not found in the playlist
        }
    }
    
    /**
     * This method reverses the playlist located at playlistIndex
     * 
     * Each node in the circular linked list will point to the element that 
     * came before it.
     * 
     * After the list is reversed, the playlist located at playlistIndex will 
     * reference the first SongNode in the original playlist (new last).
     * 
     * @param playlistIndex the playlist to reverse
     */
    public void reversePlaylist(int playlistIndex) {

        //Catches the playlist specified by the index
        Playlist changePlaylist = songLibrary.get(playlistIndex);

        if(changePlaylist.getSize() <= 1) // If there is only one item in the list, return 
        {
            return;
        }

        //have three pointers, one that points to prev, current, and node after
        //stop when current gets to orginal current after while loop

        SongNode origLastNode = changePlaylist.getLast();
        SongNode originalFirstNode = origLastNode.getNext();

        SongNode prevPointer = origLastNode;
        SongNode currentNode = originalFirstNode;
        SongNode nextPointer;
        
        do{
        nextPointer = currentNode.getNext(); //Capture next node 
        currentNode.setNext(prevPointer); //Reverse current node so it points to prev

        //Move pointers ahead one 
        prevPointer = currentNode;
        currentNode = nextPointer;
        }
        //If node has one reference, doesnt do anything -- Stops when current node gets back to where it started 
        while(currentNode != originalFirstNode); 

        originalFirstNode.setNext(origLastNode);
        changePlaylist.setLast(originalFirstNode);
    }

    /**
     * This method merges two playlists.
     * 
     * Both playlists have songs in decreasing popularity order. The resulting 
     * playlist will also be in decreasing popularity order.
     * 
     * You may assume both playlists are already in decreasing popularity 
     * order. If the songs have the same popularity, add the song from the 
     * playlist with the lower playlistIndex first.
     * 
     * After the lists have been merged:
     *  - store the merged playlist at the lower playlistIndex
     *  - remove playlist at the higher playlistIndex 
     * 
     * 
     * @param playlistIndex1 the first playlist to merge into one playlist
     * @param playlistIndex2 the second playlist to merge into one playlist
     */
    public void mergePlaylists(int playlistIndex1, int playlistIndex2) {
 
        // Order playlists
        int minIndex = Math.min(playlistIndex1, playlistIndex2);
        int maxIndex = Math.max(playlistIndex1, playlistIndex2);
    
        Playlist lowerPlaylist = songLibrary.get(minIndex);
        Playlist higherPlaylist = songLibrary.get(maxIndex);
    
        // Empty playlist checks
        if (lowerPlaylist.getSize() == 0) 
        {
            songLibrary.set(minIndex, higherPlaylist);
            removePlaylist(maxIndex);
            return;
        } 
        else if (higherPlaylist.getSize() == 0) 
        {
            removePlaylist(maxIndex);
            return;
        }
    
        // Merging non-empty playlists
        Playlist mergedPlaylist = new Playlist();
        SongNode lastSongInMerged = null;
    
        while(lowerPlaylist.getSize() > 0 && higherPlaylist.getSize() > 0) 
        {
            SongNode higherPopularitySong = getHigherPopularitySong(lowerPlaylist, higherPlaylist, minIndex, maxIndex);
            lastSongInMerged = insertSongIntoMerged(mergedPlaylist, lastSongInMerged, higherPopularitySong);
        }
      
        appendRemainingSongs(lowerPlaylist, mergedPlaylist, lastSongInMerged, minIndex);
        appendRemainingSongs(higherPlaylist, mergedPlaylist, lastSongInMerged, maxIndex);
        
        songLibrary.set(minIndex, mergedPlaylist);
        removePlaylist(maxIndex);
    }
    
    // Helper function to get song with higher popularity
    private SongNode getHigherPopularitySong(Playlist pl1, Playlist pl2, int pl1Index, int pl2Index) 
    {
        SongNode song1 = pl1.getLast().getNext();
        SongNode song2 = pl2.getLast().getNext();
        int popularity1 = song1.getSong().getPopularity();
        int popularity2 = song2.getSong().getPopularity();
        if(popularity1 >= popularity2)
        {
            removeSong(pl1Index, song1.getSong());
            return song1;
        }
        else 
        {
            removeSong(pl2Index, song2.getSong());
            return song2;
        }
    }
    
    // Helper function to append remaining songs of non empty playlist to merged playlist
    private void appendRemainingSongs(Playlist playlist, Playlist mergedPlaylist, SongNode lastSongInMerged, int playlistIndex) 
    {
        while(playlist.getSize() > 0)
        {
            SongNode nextSong = playlist.getLast().getNext();
            removeSong(playlistIndex, nextSong.getSong());
            lastSongInMerged = insertSongIntoMerged(mergedPlaylist, lastSongInMerged, nextSong);
        }
    }
    
    //Helper function to insert song into merged playlist
    private SongNode insertSongIntoMerged(Playlist mergedPlaylist, SongNode lastSongInMerged, SongNode songNode) 
    {
        if(lastSongInMerged == null)
        {
            songNode.setNext(songNode);
            mergedPlaylist.setLast(songNode);
        }
        else
        {
            songNode.setNext(lastSongInMerged.getNext());
            lastSongInMerged.setNext(songNode);
            mergedPlaylist.setLast(songNode);
        }
        mergedPlaylist.setSize(mergedPlaylist.getSize() + 1);
        return songNode;
    }

    /**
     * This method shuffles a specified playlist using the following procedure:
     * 
     * 1. Create a new playlist to store the shuffled playlist in.
     * 
     * 2. While the size of the original playlist is not 0, randomly generate a number 
     * using StdRandom.uniformInt(1, size+1). Size contains the current number
     * of items in the original playlist.
     * 
     * 3. Remove the corresponding node from the original playlist and insert 
     * it into the END of the new playlist (1 being the first node, 2 being the 
     * second, etc). 
     * 
     * 4. Update the old playlist with the new shuffled playlist.
     *    
     * @param index the playlist to shuffle in songLibrary
     */
    public void shufflePlaylist(int playlistIndex) {
        //Catch the playlist to shuffle
        Playlist origPlaylist = songLibrary.get(playlistIndex);
    
        // Check if the playlist is too small to shuffle
        if (origPlaylist.getSize() <= 1) {
            return;
        }
    
        //Begin by creating a new Playlist
        Playlist shuffledPlaylist = new Playlist();
        SongNode lastSongInShuffle = null;

        // To keep track of the size of the shuffled playlist
        int shuffledSize = 0; 
    
        while (origPlaylist.getSize() > 0) {
            // Generate a random index to pick a song from the original playlist
            int randomSongNum = StdRandom.uniformInt(origPlaylist.getSize() + 1);
            SongNode findPointer = origPlaylist.getLast().getNext();
    
            // Navigate to the randomly chosen song node
            for (int i = 0; i < randomSongNum; i++) {
                findPointer = findPointer.getNext();
            }
    
            // Create a new song node for the shuffled playlist
            SongNode songNode = new SongNode(findPointer.getSong(), null);
    
            // If it's the first node, set next to itself; otherwise, append it at the end
            if (lastSongInShuffle == null) {
                songNode.setNext(songNode); // First node points to itself
            } 
            else 
            {
                songNode.setNext(lastSongInShuffle.getNext());
                lastSongInShuffle.setNext(songNode);
            }
    
            lastSongInShuffle = songNode;
            shuffledPlaylist.setLast(lastSongInShuffle);

            // Remove the chosen song from the original playlist
            removeSong(playlistIndex, findPointer.getSong());
            // Increment the size of the shuffled playlist
            shuffledSize++;
        }
    
        // Set the size of the shuffled playlist and update the song library
        shuffledPlaylist.setSize(shuffledSize);
        songLibrary.set(playlistIndex, shuffledPlaylist);
    }
    

    /**
     * N/A
     */
    public void sortPlaylist ( int playlistIndex ) {

        // depreciated
        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Plays playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     * @param repeats number of times to repeat playlist
     * @throws InterruptedException
     */
    public void playPlaylist(int playlistIndex, int repeats) {
        /* DO NOT UPDATE THIS METHOD */

        final String NO_SONG_MSG = " has no link to a song! Playing next...";
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("Nothing to play.");
            return;
        }

        SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext(), first = ptr;

        do {
            StdOut.print("\r" + ptr.getSong().toString());
            if (ptr.getSong().getLink() != null) {
                StdAudio.play(ptr.getSong().getLink());
                for (int ii = 0; ii < ptr.getSong().toString().length(); ii++)
                    StdOut.print("\b \b");
            }
            else {
                StdOut.print(NO_SONG_MSG);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (int ii = 0; ii < NO_SONG_MSG.length(); ii++)
                    StdOut.print("\b \b");
            }

            ptr = ptr.getNext();
            if (ptr == first) repeats--;
        } while (ptr != first || repeats > 0);
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex, songLibrary.get(playlistIndex).getSize());
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        SongNode ptr;
        for (ptr = songLibrary.get(playlistIndex).getLast().getNext(); ptr != songLibrary.get(playlistIndex).getLast(); ptr = ptr.getNext() ) {
            StdOut.print(ptr.getSong().toString() + " -> ");
        }
        if (ptr == songLibrary.get(playlistIndex).getLast()) {
            StdOut.print(songLibrary.get(playlistIndex).getLast().getSong().toString() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (songLibrary.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
                for (int ii = 0; ii < songLibrary.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
     public ArrayList<Playlist> getPlaylists() { return songLibrary; }
     public void setPlaylists(ArrayList<Playlist> p) { songLibrary = p; }
}
