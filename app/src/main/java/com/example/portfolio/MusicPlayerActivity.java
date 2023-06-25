package com.example.portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.provider.MediaStore;
import android.database.Cursor;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.portfolio.adapters.SongAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

// USING SONGADAPTER'S ITEMCLICKLISTENER (ONITEMCLICK() BELOW)
public class MusicPlayerActivity extends AppCompatActivity implements SongAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Song> songList;
    private MediaPlayer mediaPlayer;
    private LinearLayout bottomMusicView;
    private ImageView albumImage;
    private TextView titleTextView, artistTextView, totalCountTextView;
    private ImageButton pauseButton, previousButton, nextButton;
    private EditText searchEditText;
    private boolean isPlaying = false;
    private int currentSongPosition = -1, newSongTiming;
    private SeekBar seekbar;
    private int songDuration;
    // ANY INTEGER VALUE CAN BE USED (UNIQUE)
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        ImageView sortButton = findViewById(R.id.sort_imageview);
        recyclerView = findViewById(R.id.recyclerView);
        bottomMusicView = findViewById(R.id.custom_bottom_view);
        albumImage = findViewById(R.id.album_image);
        titleTextView = findViewById(R.id.song_title);
        artistTextView = findViewById(R.id.artist_name);
        pauseButton = findViewById(R.id.button_play_pause);
        previousButton = findViewById(R.id.button_previous);
        nextButton = findViewById(R.id.button_next);
        searchEditText = findViewById(R.id.search_input);
        totalCountTextView = findViewById(R.id.totalCountTextView);
        seekbar = findViewById(R.id.seek_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // REQUEST PERMISSION TO STORAGE
        requestStoragePermission();

        // SORT MENU
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // POPUP MENU
                PopupMenu popupMenu = new PopupMenu(MusicPlayerActivity.this, sortButton);
                // INFLATE THE MENU LAYOUT
                popupMenu.getMenuInflater().inflate(R.menu.music_sort_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item clicks here
                        if (item.getItemId() == R.id.sort_by_artist) {
                            return true;
                        } else if (item.getItemId() == R.id.sort_by_date) {
                            return true;
                        } else if (item.getItemId() == R.id.sort_by_date) {
                            return true;
                        }
                        return false;
                    }
                });

                // Show the PopupMenu
                popupMenu.show();
            }
        });

        // BUTTONS IN THE BOTTOM VIEW
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    // PAUSE MUSIC
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    isPlaying = false;
                    pauseButton.setImageResource(R.drawable.ic_play);
                } else {
                    // PLAY MUSIC
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                    isPlaying = true;
                    pauseButton.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSongPosition > 0) {
                    currentSongPosition--;
                    playSongAtIndex(currentSongPosition, songDuration);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSongPosition < songList.size() - 1) {
                    currentSongPosition++;
                    playSongAtIndex(currentSongPosition, songDuration);
                }
            }
        });
    }

    // ༼ つ ◕_◕ ༽つ PERMISSIONS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void requestStoragePermission() {
        // CHECK IF PERMISSION HAS BEEN GRANTED
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // REQUEST FOR PERMISSION
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            // RETRIEVE MUSIC FILES
            songList = getMusicItemsFromMediaStore();
            songAdapter = new SongAdapter(songList);
            recyclerView.setAdapter(songAdapter);
            // PLAY THE SELECTED MUSIC
            songAdapter.setItemClickListener(this);
        }
    }

    // Override the onRequestPermissionsResult() method to handle the permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your logic to retrieve music files
                songList = getMusicItemsFromMediaStore();
                songAdapter = new SongAdapter(songList);
                recyclerView.setAdapter(songAdapter);
            } else {
                // Permission denied, handle accordingly (e.g., show a message or disable the functionality)
            }
        }
    }

    // ༼ つ ◕_◕ ༽つ RETRIEVE MP3 FILES' COLUMN VALUES (ID, TITLE, ETC) FROM THE CURSOR TO CREATE SONG OBJECTS
    private List<Song> getMusicItemsFromMediaStore() {
        List<Song> songList = new ArrayList<>();
        // ADD MORE IF NEEDED
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST
        , MediaStore.Audio.Media.DURATION};
        // RETRIEVE ONLY MP3 FILES
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        // SET ORDER
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        // PERFORM QUERY TO RETRIEVE SONGS
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, sortOrder);

        // CURSOR IS THE RESULT SET OF A DATABASE QUERY ↑ (IN THIS CASE, ALL THE MP3 FILES WITH THOSE DECLARED COLUMNS)
        if (cursor != null) {
            // getColumnIndexOrThrow() RETRIEVES THE INDEX OF A COLUMN IN THE CURSOR, ASSIGN THEM TO VARIABLES
            int filePathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            int albumIdColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int titleColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int artistColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            int durationColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            // ITERATE OVER EACH ROW IN THE CURSOR RESULT SET
            while (cursor.moveToNext()) {
                // RETRIEVE THE VALUES FROM THE COLUMNS
                String filePath = cursor.getString(filePathColumnIndex);
                long albumId = cursor.getLong(albumIdColumnIndex);
                String title = cursor.getString(titleColumnIndex);
                String artist = cursor.getString(artistColumnIndex);
                int duration = cursor.getInt(durationColumnIndex);

                // RETRIEVE ALBUM ART USING ALBUM ID
                // content://media/external/audio/albumart/ : THIS IS THE LOCATION OF THE ALBUM ART
                Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + albumId);
                Bitmap albumArt = null;
                try {
                    // LOAD THE ALBUM ART INTO A BITMAP OBJECT BY USING openInputStream()
                    InputStream inputStream = getContentResolver().openInputStream(albumArtUri);
                    // DECODE IT USING decodeStream()
                    albumArt = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    // TOTAL COUNT OF SONGS FOUND
                    totalCountTextView.setText("Total songs found (" + (songList.size() + 1) + ")");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Song song = new Song(filePath, albumArt, title, artist, duration);
                songList.add(song);
            }
            // CLOSE THE CURSOR TO RELEASE THE ASSOCIATED RESOURCES
            cursor.close();
        }
        // SONGLIST NOW CONTAINS ALL THE SONGS
        return songList;
    }

    // PASSED FROM SONGADAPTER.JAVA'S INTERFACE
    @Override
    public void onItemClick(int position, String fileName, ImageView imageView, TextView title, TextView artist, int duration) {
        // SET SELECTED SONG'S POSITION TO CURRENTSONGPOSITION
        currentSongPosition = position;

        bottomMusicView.setVisibility(View.VISIBLE);
        albumImage.setImageDrawable(imageView.getDrawable());
        titleTextView.setText(title.getText().toString());
        artistTextView.setText(artist.getText().toString());

        // PLAY SELECTED SONG
        playSongAtIndex(currentSongPosition, duration);

        updateUIWithCurrentSong();

        // SEEKBAR
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the current position of the song if the change is from the user
                if (fromUser) {
                    newSongTiming = seekbar.getProgress();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
                pauseButton.setImageResource(R.drawable.ic_play);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(newSongTiming);
                mediaPlayer.start();
                pauseButton.setImageResource(R.drawable.ic_pause);
            }
        });
    }

    private void playSongAtIndex(int position, int duration) {
        // CHECK IF THERE IS AN AUDIO ALREADY PLAYING
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        currentSongPosition = position;
        Song song = songList.get(position);
        String fileName = song.getFilePath();

        // CREATE A NEW INSTANCE OF MEDIAPLAYER TO PLAY NEW SONG
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            // PLAY MUSIC
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();

            pauseButton.setImageResource(R.drawable.ic_pause);
            isPlaying = true;
            updateUIWithCurrentSong();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ༼ つ ◕_◕ ༽つ SEEKBAR
        // SET SELECTED SONG'S DURATION TO SONGDURATION
        songDuration = duration;
        seekbar.setMax(songDuration);

        Handler handler = new Handler();
        Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                int currentTimePosition = mediaPlayer.getCurrentPosition();
                seekbar.setProgress(currentTimePosition);
                handler.postDelayed(this, 100);  // Update every 100 milliseconds
            }
        };

        // Start updating the SeekBar when the song starts playing
        handler.postDelayed(updateSeekBar, 100);

        // WHEN MUSIC ENDS
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                isPlaying = false;
                pauseButton.setImageResource(R.drawable.ic_play);
            }
        });
    }



    private void updateUIWithCurrentSong() {
        Song song = songList.get(currentSongPosition);
        bottomMusicView.setVisibility(View.VISIBLE);
        albumImage.setImageBitmap(song.getAlbumImage());
        titleTextView.setText(song.getTitle());
        artistTextView.setText(song.getArtist());
    }


    // ༼ つ ◕_◕ ༽つ OPTIONS MENU >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // PASSED FROM SONGADAPTER.JAVA'S INTERFACE
    @Override
    public void onOptionsIconClick(int position, String fileName) {
        // Show the bottom popup view for editing the MP3 file
        showBottomPopupView(songList.get(position));
    }

    // BOTTOM POPUP VIEW
    private void showBottomPopupView(Song song) {
        // Create a BottomSheetDialog with your custom layout
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_music_popup);

        // Initialize views in the bottom popup view
        ImageView albumImageView = bottomSheetDialog.findViewById(R.id.album_image);
        TextView titleTextView = bottomSheetDialog.findViewById(R.id.song_title);
        TextView artistTextView = bottomSheetDialog.findViewById(R.id.artist_name);
        ImageButton addToPlaylistButton = bottomSheetDialog.findViewById(R.id.addToPlaylistImageButton);
        TextView addToPlaylistText = bottomSheetDialog.findViewById(R.id.addToPlaylistTextView);
        ImageButton deleteButton = bottomSheetDialog.findViewById(R.id.deleteImageButton);
        TextView deleteTextView = bottomSheetDialog.findViewById(R.id.deleteTextView);

        // Set the song details in the views
        albumImageView.setImageBitmap(song.getAlbumImage());
        titleTextView.setText(song.getTitle());
        artistTextView.setText(song.getArtist());

        bottomSheetDialog.show();
    }
}