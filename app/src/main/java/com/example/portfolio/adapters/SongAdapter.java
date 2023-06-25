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

import org.w3c.dom.Text;

import java.util.List;

// ADAPTERS ARE USED TO MANAGE THE DATA & PROVIDE A VISUAL REPRESENTATION OF THAT DATA IN A VIEW COMPONENT
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
        public ImageView albumImageView, sortImageView;
        public TextView titleTextView;
        public TextView artistTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImageView = itemView.findViewById(R.id.album_image);
            titleTextView = itemView.findViewById(R.id.song_title);
            artistTextView = itemView.findViewById(R.id.artist_name);
            sortImageView = itemView.findViewById(R.id.sort_imageview);

            sortImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Song song = songList.get(position);
                            String filename = song.getFilePath();
                            itemClickListener.onOptionsIconClick(position, filename);
                        }
                    }
                }
            });

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
                            String filename = song.getFilePath();
                            itemClickListener.onItemClick(position, filename, albumImageView, titleTextView, artistTextView);
                        }
                    }
                }
            });
        }
    }

    // Item click listener interface
    public interface ItemClickListener {
        void onItemClick(int position, String filename, ImageView imageView, TextView title, TextView artist);
        void onOptionsIconClick(int position, String filename);
    }
}
/*
Data Management: An adapter acts as a bridge between the data set and the RecyclerView. It holds the data and manages its access, retrieval, and manipulation. The adapter is responsible for organizing the data and providing it to the RecyclerView as individual items.

View Creation: The adapter is responsible for creating the views that represent the individual items in the data set. When the RecyclerView needs a new view for an item, it asks the adapter to create it. The adapter inflates the item layout and creates a view holder to hold the references to the views within that layout.

View Binding: After creating a view, the adapter binds the data from the corresponding item in the data set to the views within the view holder. This process ensures that the data is correctly displayed in each item view.

Recycling Views: The RecyclerView reuses item views as the user scrolls through the list. When an item view scrolls off the screen, it is detached from the RecyclerView and passed back to the adapter to be recycled. The adapter can then reuse that view for another item that needs to be displayed, instead of creating a new view from scratch. This recycling mechanism improves performance and reduces memory usage.

Notifying Data Changes: When the underlying data set changes (e.g., new items are added, items are removed, or data is updated), the adapter is responsible for notifying the RecyclerView about these changes. The adapter informs the RecyclerView which items were inserted, removed, or modified, allowing the RecyclerView to update its layout and animations accordingly.
 */