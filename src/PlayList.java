
import java.util.ArrayList;
import java.util.Iterator;

public class PlayList {
    public static int currentSongId = 0;
    private SortedPriorityQueue<Integer,Song> songs = new SortedPriorityQueue<>();
   // private SortedPriorityQueue<Integer,Song> songs = new SortedPriorityQueue<>();

    //loads

    public PlayList(ArrayList<Song> songs){
        for(Song song: songs){
            this.songs.insert(song.getPriority(),song);
        }
    }
//    public PlayList(int capacity, ArrayList<Song> songs){
//        orangiseSongs(capacity,songs);
//    }

//    public void orangiseSongs(int capacity, ArrayList<Song> songs){
//        Iterator<Song> songIt = songs.iterator();
//        int i = 1;
//        while(songIt.hasNext() && i <= capacity ){
//            Song currentSong = songIt.next();
//            Entry<Integer,Song> entry = findSongEntry(currentSong);
//            if (entry != null) {
//                this.songs.replaceKey(entry, entry.getValue().higherPriority());
//            }
//            else {
//                this.songs.insert(currentSong.getPriority(), currentSong);
//                i++;
//            }
//            songIt.remove();
//        }
//    }

//    private Entry<Integer,Song> findSongEntry(Song song){
//        Iterator<Entry<Integer,Song>> entry = this.songs.entries();
//        while(entry.hasNext()){
//            Entry<Integer,Song> currentEntry = entry.next();
//            Song currentSong = currentEntry.getValue();
//            if (currentSong.equals(song))
//                return currentEntry;
//        }
//        return null;
//    }

    public Song play(){
       return songs.removeMin().getValue();
    }

    public Boolean isEmpty(){
        return songs.isEmpty();
    }

    @Override
    public String toString() {
        if(songs.isEmpty())
            return "[]";

        StringBuilder sb = new StringBuilder("[");

        Iterator<Entry<Integer,Song>> iterator = songs.iterator();

        while(iterator.hasNext()){
            Entry<Integer,Song> entry = iterator.next();
            sb.append("(").append(entry.getKey()).append(", ").append(entry.getValue()).append("), ");
        }

        // Remove the last comma and the space
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);

        sb.append("]");
//        Integer previousPriority = null;
//
//        while(iterator.hasNext()){
//            Entry<Integer,Song> entry = iterator.next();
//
//            int currentPriority = entry.getKey();
//            Song currentSong = entry.getValue();
//
//            if (previousPriority == null){
//                sb.append("[(").append(currentPriority).append(", ").append(currentSong);
//            } else if(previousPriority.equals(currentPriority)){
//                sb.append(", ").append(currentSong);
//            } else{
//                sb.append("), ");
//                sb.append("(").append(currentPriority).append(", ").append(currentSong);
//            }
//            previousPriority = currentPriority;
//        }
//        sb.append(")").append("]");
        return sb.toString();
    }

    public int size(){
        return songs.size();
    }
}
