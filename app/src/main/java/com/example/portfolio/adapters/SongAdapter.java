package com.example.portfolio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio.R;
import com.example.portfolio.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> songList;
    private ItemClickListener itemClickListener;

    public SongAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songList.get(position);

        // SET ALBUM IMAGE
        if (song.getAlbumImage() != null) {
            holder.albumImageView.setImageBitmap(song.getAlbumImage());
        } else {
            // Set a default image if album image is not available
            holder.albumImageView.setImageResource(R.drawable.placeholder_album);
        }

        // SET TITLE & ARTIST
        holder.titleTextView.setText(song.getTitle());
        holder.artistTextView.setText(song.getArtist());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    // WHEN USER PRESSES THE SONG
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumImageView;
        public TextView titleTextView;
        public TextView artistTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImageView = itemView.findViewById(R.id.album_image);
            titleTextView = itemView.findViewById(R.id.song_title);
            artistTextView = itemView.findViewById(R.id.artist_name);

            // Set click listener for the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        // GET POSITION OF SELECTED MUSIC
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                            // Pass the position and filename to the item click listener
                            Song song = songList.get(position);
                            String filename = song.getFilePath(); // Replace with the appropriate method to get the file path from the Song object
                            itemClickListener.onItemClick(position, filename);
                        }
                    }
                }
            });
        }
    }

    // Item click listener interface
    public interface ItemClickListener {
        void onItemClick(int position, String filename);
    }
}
