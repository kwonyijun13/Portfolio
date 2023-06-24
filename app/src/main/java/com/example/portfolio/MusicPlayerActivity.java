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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.provider.MediaStore;
import android.database.Cursor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.portfolio.adapters.SongAdapter;

public class MusicPlayerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Song> songList;

    // ANY INTEGER VALUE CAN BE USED (UNIQUE)
    private static final int PERMISSION_REQUEST_CODE = 123;
    private static final int REQUEST_CODE_OPEN_DOCUMENT_TREE = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        ImageView sortButton = findViewById(R.id.sort_imageview);
        recyclerView = findViewById(R.id.recyclerView);

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

        if (cursor != null) {
            int filePathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            int albumIdColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int titleColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int artistColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

            while (cursor.moveToNext()) {
                String filePath = cursor.getString(filePathColumnIndex);
                long albumId = cursor.getLong(albumIdColumnIndex);
                String title = cursor.getString(titleColumnIndex);
                String artist = cursor.getString(artistColumnIndex);

                // Retrieve the album art using the album ID
                Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + albumId);
                Bitmap albumArt = null;
                try {
                    InputStream inputStream = getContentResolver().openInputStream(albumArtUri);
                    albumArt = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Song song = new Song(filePath, albumArt, title, artist);
                songList.add(song);
            }

            cursor.close();
        }

        return songList;
    }
}