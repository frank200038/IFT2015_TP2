import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;



/**
* 
* @author      Yan Zhuang, Diego Gonzalez adapted from Francois Major's TSVTrackReader
* @version     %I%, %G%
* @since       1.0
*/
public class SongReader  {

    // attributes
    private String fileName;
    private FileReader reader;
    private BufferedReader bufferReader;
    private String line;

    // getter for the file name
    public String getFileName() { return this.fileName; }


    /**
     * Constructor with file name and trackList arguments
     * Uses the Track constructor with data line.
     * @param  fileName   the file name with complete path
     * @param  songList  an ArrayList of Tracks
     * @see               Song
     */
    public SongReader( String fileName, ArrayList<Song> songList ) {
        this.fileName = fileName;

        try {
            this.reader = new FileReader( this.fileName );
        } catch( FileNotFoundException e ) {
            System.out.println(e.getMessage());
        }

        try {
            this.bufferReader = new BufferedReader( this.reader );

            // read first data line
            this.line = this.bufferReader.readLine();
            // read all tracks in trackList
            while( this.line != null ) {
            // parse line and add to trackList
            Song newSong = new Song(this.line);
            songList.add(newSong);
            // read next line
            this.line = this.bufferReader.readLine();
            }
            this.bufferReader.close();
        } catch( IOException e ) {
                System.out.println(e.getMessage());
            }
    }

}
