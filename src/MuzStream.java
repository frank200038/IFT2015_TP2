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

        ArrayList<Song> songs = new ArrayList<>();
        organiseSongs(playlistCap,songs,allSongs);

        PlayList playList = new PlayList(songs);


        while(noFilling >= 0){
            System.out.println("PlayList: "+playList);

            Song played = playList.play();

            addToTopK(played);
            addPlayTime(played);
            songs.remove(played);

            System.out.println("Playing " + played.presentSong());

            double currentCapacityPercent = (double) songs.size() / playlistCap;
            if(currentCapacityPercent <= ((double) playlistLimit)/100 && !playList.isEmpty()){

                playList = refill(playlistCap,playList,songs,allSongs);
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
     * Helper method to organise all songs from the ArrayList according to the full capacity of the {@code playList}
     * @param capacity full capacity that the playlist can hold
     * @param songs Organised songs to be added to the ArrayList (To be added later into {@code playList})
     * @param allSongs All songs obtained from the request music file
     */
    private static void organiseSongs(int capacity, ArrayList<Song> songs, ArrayList<Song> allSongs){
        Iterator<Song> iterator = allSongs.iterator();
        int i = 1;
        while (iterator.hasNext() && i <= capacity){
            Song song = iterator.next();

            if (songs.contains(song)){
                int index = songs.indexOf(song);
                Song toChange = songs.get(index);
                toChange.higherPriority();
                i--;
            } else{
                songs.add(song);
            }

            iterator.remove();
            i++;
        }

    }

    /**
     * Helper method to refill the playlist
     * @param fullCapacity Full capacity that the playlist can hold
     * @param playList The current playlist
     * @param songs organised songs to be added into playlist
     * @param allSongs Leftover songs to be potentially added to playList
     * @return A new playlist refilled back to the full capacity
     */
    private static PlayList refill(int fullCapacity,PlayList playList,ArrayList<Song> songs,ArrayList<Song> allSongs){
        System.out.println("Refill");
        organiseSongs(fullCapacity-playList.size(),songs,allSongs);


        presentTopK(topK.size());
        return new PlayList(songs);
      //  playList.orangiseSongs(fullCapacity- playList.size(),allSongs);
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
