import java.util.ArrayList;
import java.util.Iterator;

public class MuzStream {
    static ArrayList<Song> allSongs = new ArrayList<>();
    static FavoritesList<Song> topK = new FavoritesList<>();

    public static void main(String[] args){

        //String fileName = args[0];
        //int playlistCap = Integer.parseInt(args[1]);
        int playlistCap = 4;
        int playlistLimit = 25;
//        int playlistLimit = Integer.parseInt(args[2]);
        int noFilling = 2;
//        int noFilling = Integer.parseInt(args[3]);

        SongReader reader = new SongReader("src/test.txt",allSongs);


        PlayList playList = new PlayList(playlistCap,allSongs);


        while(noFilling >= 0 || ! playList.isEmpty()){
            System.out.println("PlayList: "+playList);

            Song played = playList.play();

            addToTopK(played);
            addPlayTime(played);

            System.out.println("Playing " + played.presentSong());

            double currentCapacityPercent = (double) playList.size() / playlistCap;
            if(currentCapacityPercent <= ((double) playlistLimit)/100 && !playList.isEmpty()){

                refill(playlistCap,playList,allSongs);
                noFilling--;
            }

            if(playList.isEmpty()){
                System.out.println("Playlist: " + playList);
                presentTopK(topK.size());
                break;
            }
        }

    }

    /**
     * Helper method to refill the playlist
     * @param fullCapacity Full capacity that the playlist can hold
     * @param playList The current playlist
     * @param allSongs Leftover songs to be potentially added to playList
     * @return A new playlist refilled back to the full capacity
     */
    private static void refill(int fullCapacity,PlayList playList,ArrayList<Song> allSongs){
        System.out.println("Refill");

        presentTopK(topK.size());

        playList.orangiseSongs(fullCapacity- playList.size(),allSongs);
    }

    /**
     * Add the song to the topK list. Add only if the song has not been added to the list
     * @param song The song to be added to the topK list
     */
    private static void addToTopK(Song song){
        boolean found = false;

        if(! topK.isEmpty()){
            Iterable<Song> it = topK.getFavorites(topK.size());
            for (Song current: it){
                if(current.equals(song)) {
                    found = true;
                    break;
                }
            }
        }

        if(!found){
            topK.access(song);
        }
    }

    private static void presentTopK(int k){
        System.out.println("Top-"+k);
        Iterable<Song> it = topK.getFavorites(k);
        for(Song song : it){
            System.out.println(song.presentTopSong());
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
