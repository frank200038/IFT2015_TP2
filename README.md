# IFT2015 Devoir 2
<div>
<b> Course </b>: IFT2015 - Data Structure <br>
<b> Professor </b>: François Major  <br>
<b> Language used:</b> Java <br>
<b> Main Data Structure Used </b>: Adaptable Priority Queue in Heap  
</div>

# Description

<div>
You are developing a music streaming application, <code>`MuzStream`</code>. One of the features is a play
queue (referred to later as the playlist), much like Spotify's, where the songs that have been
queued will automatically play. The application must have the following behavior:
<ul>
<li> The songs are considered as processes and are being played in an order determined by
their priorities; i.e. the song with the highest priority is played out first, then the second
highest priority, and so on; </li>
<li> The priorities will be determined by integer values, where the lower the value the higher
the priority; </li>
<li> Songs with equal priorities are played out in any order; </li>
<li> The playlist is initially filled with playlistCapacity (given to the program through one
of its arguments) songs taken sequentially from the listeners’ requests, which will be
provided in an ASCII file (whose name is also given as an argument); </li>
<li> When placed in the playlist, the priority of a song is 0; </li>
<li> If the next song in the requests is already in the playlist, its priority increases by one;
i.e. the integer value representing its priority is decremented by one; </li>
<li> Songs are removed from the playlist as they are played. When the size of the playlist
reaches playlistLimit, an integer given as an argument representing a percentage
of the full capacity (e.g. 20 for 20%), it is refilled by songs from the requests file; following
the above priority policy; </li>
<li> The application will stop when the playlist is empty or after the playlist is filled
numberOFillings, also an integer given as an argument, times; </li>
</ul>

<hr>
Each time the playlist is filled, the application releases the TOP-k, that is the k songs played the
most often since the application was launched, from number 1 the song
<ul>
<li> that played the most, then the second most, and so on, to the kth; k is also given as an
argument to the program. </li>
<li> For each song in the TOP-k, you must report the average time interval between two
plays of the song. </li>
</ul>
</div>
