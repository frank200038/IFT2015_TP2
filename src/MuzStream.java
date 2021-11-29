import java.util.ArrayList;
import java.util.Iterator;

/**
 * The main class of the MuzStream which is responsible of filling and refilling of playlist, and
 * manipulation of the playlist (Such as presenting topK)
 *
 * @author Yan Zhuang, Diego Gonzalez
 */
public class MuzStream {
    static ArrayList<Song> allSongs = new ArrayList<>();
    static FavoritesList<Song> topK = new FavoritesList<>();

    public static void main(String[] args){

        String fileName = args[0];
        int playlistCap = Integer.parseInt(args[1]);
        int playlistLimit = Integer.parseInt(args[2]);
        int noFilling = Integer.parseInt(args[3]);
        int topKSize = Integer.parseInt(args[4]);
        SongReader song = new SongReader(fileName,allSongs);
        PlayList playList = new PlayList(playlistCap,allSongs);

        while(!playList.isEmpty() && noFilling > 0){

            Song played = playList.play(); // Retrieve the song that is being played

            topK.access(played);
            addPlayTime(played); // Update the average play time of the songs

            // Calculate the current capacity and the allowed capacity of the playlist (Including rounding)
            Double currentCapacityPercent = Math.round((double) playList.size() / playlistCap * 100.0) / 100.0;
            Double allowedCapacity = (double) playlistLimit / 100;

            // Don't need to add more songs if there are no more songs in request file
            if(currentCapacityPercent.compareTo(allowedCapacity) <= 0  && !allSongs.isEmpty() ){
                refill(playlistCap, playList, allSongs, topKSize);
                noFilling--;
            }

            if(playList.isEmpty())
                presentTopK(topK.size());
        }
    }

    /**
     * Helper method to refill the playlist
     * @param fullCapacity Full capacity that the playlist can hold
     * @param playList The current playlist
     * @param allSongs Leftover songs to be potentially added to playList
     * @return A new playlist refilled back to the full capacity
     */
    private static void refill(int fullCapacity,PlayList playList,ArrayList<Song> allSongs, int topKSize){
        presentTopK(topKSize);
        playList.orangiseSongs(fullCapacity- playList.size(),allSongs);
    }

    /**
     * Helper method to generate the Top-K list,
     * @param k the number of top songs to display
     */
    private static void presentTopK(int k){
        System.out.println("Top-"+k);

        // Make sure that user don't request to retrieve top-0 (invalid)
        if (k != 0) {
            Iterable<Song> it = topK.getFavorites(k);
            for (Song song : it) {
                System.out.println(song.presentTopSong());
            }
        }
    }

    /**
     * Method that add play time to each songs in the top K list.
     * @param song the current song that is being played
     */
    private static void addPlayTime(Song song){
        Iterable<Song> it = topK.getFavorites(topK.size());
        for (Song current:it){

            // If current song correspondents with the song means the song is being played again.
            if(current.equals(song)){
                current.addPlayTimeNew();
            } else {
                // Add to the current play time of the song.
                current.addPlayTime(song.getDuration());
            }
        }
    }

}
