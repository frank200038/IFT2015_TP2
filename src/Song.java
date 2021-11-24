import java.util.ArrayList;

public class Song  {
    private String artist,song;
    private int duration,priority;

    // Playtime between each play of the song
    private ArrayList<Integer> playTime = new ArrayList<>();
    private int currentPlayTimePointer = -1; // Current index of playtime after playing the song

    public Song(String line){
        String[] data = line.split("\t");
        this.artist = data[0];
        this.song = data[1];
        this.duration = Integer.parseInt(data[2]);
        this.priority = 0;
    }

    public int getDuration(){
        return duration;
    }
    public int getPriority() {
        return priority;
    }

    /**
     * Helper method to increase the priority.
     * @return Newly increased priority
     */
    public int higherPriority(){
        this.priority -= 1;
        return this.priority;
    }

    /**
     * Add the playtime after the song is being played. If the {@code currentPlayTimePointer} is -1 means
     * the song has never played
     * @param newTime the time to add upon the current play time
     */
    public void addPlayTime(int newTime){
        if(currentPlayTimePointer != -1) {
            int oldTime = playTime.get(currentPlayTimePointer);
            playTime.set(currentPlayTimePointer, oldTime + newTime);
        }
    }

    /**
     * The song is being played again. Add the {@code currentPlayTimePointer} by 1 to start at index 0.
     */
    public void addPlayTimeNew(){
        currentPlayTimePointer++;
        playTime.add(0);
    }

    @Override
    public String toString() {
        return "(" + artist + ", " +
                song + ", " +
                duration +
                ")";
    }

    /**
     * Helper method that prints out the song in the format for the {@link PlayList}
     * @return String of the song in the correspondent format {@link PlayList}
     */
    public String presentSong(){
        return artist+"\t"+song+"\t"+duration;
    }

    /**
     * Helpter method that prints out the in the format for the {@code topKlist}
     * @return String of the song in the correspondent format for {@code topKlist}
     */
    public String presentTopSong(){
        return artist+"\t"+song+"\t"+averagePlayTime();
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (! (o instanceof Song))
            return false;
        Song compare = (Song) o;
        return (this.artist.equals(compare.artist)) && (this.song.equals(compare.song)) &&
                (this.duration == compare.duration);
    }

    /**
     * Helper method that returns the average play time of the song
     * @return Average play time
     */
    private double averagePlayTime(){
        if (playTime.isEmpty())
            return 0;
        else{
            double sum = playTime.stream().mapToDouble(Integer::doubleValue).sum();
            return sum / playTime.size();
        }
    }
}
