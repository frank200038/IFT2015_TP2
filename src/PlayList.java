
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Playlist class is responsible for internal operations of playlist such as adding a new song to the current
 * playlist
 *
 * @author Yan Zhuang, Diego Gonzalez
 */
public class PlayList {

    private HeapAdaptablePriorityQueue<Integer,Song> songs = new HeapAdaptablePriorityQueue<>();

    //loads
    public PlayList(int capacity, ArrayList<Song> songs){
        orangiseSongs(capacity,songs);
    }

    /**
     * Helper method that fills the songs to the playlist, while adjusting the priority correspondently
     * @param capacity Allowed capacity for the playlist
     * @param songs Songs to be added to the playlist
     */
    public void orangiseSongs(int capacity, ArrayList<Song> songs){
        Iterator<Song> songIt = songs.iterator();
        int i = 1;
        while(songIt.hasNext() && i <= capacity ){
            Song currentSong = songIt.next();

            // Find if the song already exists in the playlist
            Entry<Integer,Song> entry = findSongEntry(currentSong);

            // If yes, simply adjust the priority. Don't count as a new song
            if (entry != null) {
                this.songs.replaceKey(entry, entry.getValue().higherPriority());
            }
            // Otherwise, simply add the new song to the playlist, with default priority of 0
            // Count as a new song. i++
            else {
                this.songs.insert(currentSong.getPriority(), currentSong);
                i++;
            }

            songIt.remove();
        }
    }

    /**
     * Helper method to verify if current song is already in the playlist.
     * @param song The song that need to be verified
     * @return The entry of the current if exists. Otherwise, return {@code null}
     */
    private Entry<Integer,Song> findSongEntry(Song song){
        Iterator<Entry<Integer,Song>> entry = this.songs.entries();
        while(entry.hasNext()){
            Entry<Integer,Song> currentEntry = entry.next();
            Song currentSong = currentEntry.getValue();
            if (currentSong.equals(song))
                return currentEntry;
        }
        return null;
    }

    /**
     * Helper method to retrieve the next song to be played
     * @return Next song to be played
     */
    public Song play(){
       return songs.removeMin().getValue();
    }

    /**
     * Verify if the playlist is empty
     * @return Whether the playlist is empty or not
     */
    public Boolean isEmpty(){
        return songs.isEmpty();
    }

    /**
     * Return size of the playlist
     * @return Size of the playlist
     */
    public int size(){
        return songs.size();
    }

    @Override
    public String toString() {
        if(songs.isEmpty())
            return "[]";

        StringBuilder sb = new StringBuilder("[");

        Iterator<Entry<Integer,Song>> iterator = songs.heap.iterator();

        while(iterator.hasNext()){
            Entry<Integer,Song> entry = iterator.next();
            sb.append("(").append(entry.getKey()).append(", ").append(entry.getValue()).append("), ");
        }

        // Remove the last comma and the space
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);

        sb.append("]");
        return sb.toString();
    }


}
