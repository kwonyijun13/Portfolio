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
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.portfolio.adapters.SongAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

// USE SONGADAPTER'S ITEMCLICKLISTENER (ONITEMCLICK() BELOW)
public class MusicPlayerActivity extends AppCompatActivity implements SongAdapter.ItemClickListener{

    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Song> songList;
    private MediaPlayer mediaPlayer;
    private Bitmap albumArt;
    private LinearLayout bottomMusicView;
    private ImageView albumImage;
    private TextView titleTextView, artistTextView;
    private ImageButton pauseButton, previousButton, nextButton;
    private EditText searchEditText;
    private boolean isPlaying = false;
    private int currentSongPosition = -1;

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // REQUEST PERMISSION TO STORAGE
        requestStoragePermission();

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
                    playSongAtIndex(currentSongPosition);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSongPosition < songList.size() - 1) {
                    currentSongPosition++;
                    playSongAtIndex(currentSongPosition);
                }
            }
        });
    }

    private void requestStoragePermission() {
        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted
            // Proceed with your logic to retrieve music files
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

    // RETRIEVE MP3 FILES' COLUMN VALUES (ID, TITLE, ARTIST, ETC) FROM THE CURSOR TO CREATE SONG OBJECTS
    private List<Song> getMusicItemsFromMediaStore() {
        List<Song> songList = new ArrayList<>();
        // ADD MORE IF NEEDED
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST};
        // RETRIEVE ONLY MP3 FILES
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        // Perform the query to retrieve the music items
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder
        );

        // CURSOR IS THE RESULT SET OF A DATABASE QUERY ↑ (IN THIS CASE, ALL THE MP3 FILES)
        if (cursor != null) {
            // getColumnIndexOrThrow() RETRIEVES THE INDEX OF A COLUMN IN THE CURSOR
            int filePathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            int albumIdColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int titleColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int artistColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            // ITERATE OVER EACH ROW IN THE CURSOR RESULT SET
            while (cursor.moveToNext()) {
                // RETRIEVE THE VALUES FROM THE COLUMNS
                String filePath = cursor.getString(filePathColumnIndex);
                long albumId = cursor.getLong(albumIdColumnIndex);
                String title = cursor.getString(titleColumnIndex);
                String artist = cursor.getString(artistColumnIndex);

                // RETRIEVE ALBUM ART USING ALBUM ID
                // content://media/external/audio/albumart/ : THIS IS THE LOCATION OF THE ALBUM ART
                Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + albumId);
                albumArt = null;
                try {
                    // LOAD THE ALBUM ART INTO A BITMAP OBJECT BY USING openInputStream()
                    InputStream inputStream = getContentResolver().openInputStream(albumArtUri);
                    // DECODE IT USING decodeStream()
                    albumArt = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Song song = new Song(filePath, albumArt, title, artist);
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
    public void onItemClick(int position, String fileName, ImageView imageView, TextView title, TextView artist) {
        // SET CURRENT SONG'S POSITION TO CURRENTSONGPOSITION
        currentSongPosition = position;

        bottomMusicView.setVisibility(View.VISIBLE);
        albumImage.setImageDrawable(imageView.getDrawable());
        titleTextView.setText(title.getText().toString());
        artistTextView.setText(artist.getText().toString());

        // Call playSongAtIndex() to play the selected song
        playSongAtIndex(currentSongPosition);

        updateUIWithCurrentSong();
    }

    private void playSongAtIndex(int position) {
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
    }

    private void updateUIWithCurrentSong() {
        Song song = songList.get(currentSongPosition);
        bottomMusicView.setVisibility(View.VISIBLE);
        albumImage.setImageBitmap(song.getAlbumImage());
        titleTextView.setText(song.getTitle());
        artistTextView.setText(song.getArtist());
    }

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