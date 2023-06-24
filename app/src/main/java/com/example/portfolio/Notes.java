package com.example.portfolio;
public class Notes { }

/* GIT
Initialize Git: VCS - Create Git Repos - Select Root dir of project
Add and Commit: Git - Add
Connect your local repository to the remote repository on GitHub:

In Android Studio, go to the menu bar and select VCS -> Git -> Remotes -> Configure...
Click on the "+" sign in the top left corner to add a new remote repository.
Provide a name for the remote repository (e.g., "origin").
Enter the URL of your GitHub repository (e.g., "https://github.com/your-username/your-repository").
Click on the "OK" button to save the remote repository configuration.
Push your local commits to GitHub:

In Android Studio, go to the menu bar and select VCS -> Git -> Push.
Select the remote repository you configured in the previous step.
Click on the "Push" button to push your local commits to GitHub.

Place to get git token: https://github.com/settings/tokens
 */

/* TextInputLayout
Add dependency: implementation 'com.google.android.material:material:1.4.0'

Take Note:
When using app:errorEnabled="true" it will create an empty space below to show the error text
app:hintAnimationEnabled="true" is true by default, so don't have to add

XML: EditText, TextInputEditText
android:inputType="textEmailAddress"
- Hints the system to display a keyboard optimized for entering email address
- Still requires a custom method to validate the email
app:endIconMode="password_toggle"
- built in function to hide or show, no code required

XML: Constraint Layout
app:layout_constraintWidth_percent="0.5"
- Width should be set to 0dp for this to take effect

FIREBASE
In build.gradle(:app)
    // Firebase BoM (Bill of Materials)
    implementation platform('com.google.firebase:firebase-bom:32.1.0')
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-auth:21.0.3'

plugins {
    id 'com.google.gms.google-services'
}

In build.gradle(Project)
buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }
}
plugins {
    id 'com.google.gms.google-services' version '4.3.15' apply false
}
 */

/* MOTIONLAYOUT
https://developer.android.com/develop/ui/views/animations/motionlayout
Example:
In .xml
<androidx.constraintlayout.motion.widget.MotionLayout
            android:id="@+id/motionLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layoutDescription="@xml/scene_01"
            tools:showPaths="true">

            <ImageView
                android:id="@+id/firstImageView"
                android:layout_height="match_parent"
                android:layout_width="match_parent"/>

            <ImageView
                android:id="@+id/secondImageView"
                android:layout_height="match_parent"
                android:layout_width="match_parent"/>

            <androidx.constraintlayout.helper.widget.Carousel
                android:id="@+id/carousel"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:carousel_forwardTransition="@+id/forward"
                app:carousel_backwardTransition="@+id/backward"
                app:carousel_previousState="@+id/previous"
                app:carousel_nextState="@+id/next"
                app:carousel_infinite="true"
                app:carousel_firstView="@+id/firstImageView"
                app:constraint_referenced_ids="firstImageView,secondImageView" />

        </androidx.constraintlayout.motion.widget.MotionLayout>
In scene_01.xml
<MotionScene xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:motion="http://schemas.android.com/apk/res-auto">

    <Transition
        motion:constraintSetStart="@id/start"
        motion:constraintSetEnd="@+id/next"
        motion:duration="1000"
        android:id="@+id/forward">
        <OnSwipe
            motion:dragDirection="dragLeft"
            motion:touchAnchorSide="left" />
    </Transition>

    <Transition
        motion:constraintSetStart="@+id/start"
        motion:constraintSetEnd="@+id/previous"
        android:id="@+id/backward">
        <OnSwipe
            motion:dragDirection="dragRight"
            motion:touchAnchorSide="right" />
    </Transition>

    <ConstraintSet android:id="@+id/start">
        <Constraint
            android:id="@+id/button"
            android:layout_width="64dp"
            android:layout_height="64dp"
            android:layout_marginStart="8dp"
            motion:layout_constraintBottom_toBottomOf="parent"
            motion:layout_constraintStart_toStartOf="parent"
            motion:layout_constraintTop_toTopOf="parent" />
    </ConstraintSet>

    <ConstraintSet android:id="@+id/end">
        <Constraint
            android:id="@+id/button"
            android:layout_width="64dp"
            android:layout_height="64dp"
            android:layout_marginEnd="8dp"
            motion:layout_constraintBottom_toBottomOf="parent"
            motion:layout_constraintEnd_toEndOf="parent"
            motion:layout_constraintTop_toTopOf="parent" />
    </ConstraintSet>

</MotionScene>

In .java


List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.cat);
        imageList.add(R.drawable.blackcat);

        carousel.setAdapter(new Carousel.Adapter() {
            @Override
            public int count() {
                // need to return the number of items we have in the carousel
                return imageList.size();
            }

            @Override
            public void populate(View view, int index) {
                // need to implement this to populate the view at the given index
                int imageResId = imageList.get(index);

                imageView1.setImageResource(imageResId);
                imageView2.setImageResource(imageResId);
            }

            @Override
            public void onNewItem(int index) {
                // called when an item is set
            }
        });
 */

/* MATERIALBUTTON
Material Design styling: MaterialButton implements the Material Design guidelines for buttons, providing a modern and visually appealing appearance out of the box. It supports elevation, ripple effects, and other Material Design attributes.

Theming and customization: MaterialButton supports various theming options, allowing you to easily change the appearance of the button to match your app's theme. You can customize the background, text color, icon, shape, and other attributes using XML attributes or programmatically.

Icon support: MaterialButton allows you to add icons to the button alongside the text. You can specify icons using vector drawables, image resources, or font icons. This is particularly useful when you need to display actions or visual indicators within the button itself.

Toggleable behavior: MaterialButton supports toggle functionality, allowing the button to be in either a selected or unselected state. You can use it as a checkbox or radio button replacement by enabling the toggleable mode.

Accessibility enhancements: MaterialButton includes built-in accessibility features, making it easier for users with disabilities to interact with the button. It supports accessibility labels, hints, and other accessibility attributes.

Extended button styles: MaterialButton provides additional button styles beyond the standard rectangular shape, such as outlined, text, and filled variants. These styles allow you to achieve different visual effects and fit various design requirements.

Themable states and animations: MaterialButton supports different states like pressed, focused, and disabled, with corresponding animations and visual feedback. This helps create an interactive and engaging user experience.
 */

/* FACEBOOK LOG IN
Follow this https://developers.facebook.com/docs/facebook-login/android

Run this to generate a key hash for a release build (65):
keytool -exportcert -alias androiddebugkey -keystore "C:\Users\yijun\.android\debug.keystore" -storepass android -keypass android | "C:\Users\yijun\openssl-0.9.8k_X64\bin\openssl.exe" base64

Run this for development environment (28+):
keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
If not working, add OpenSSL to system's PATH:
Press Win + X and select "System".
    Click on "Advanced system settings".
    In the "System Properties" window, click on the "Environment Variables" button.
    In the "System Variables" section, select the "Path" variable and click on "Edit".
    Add a new entry with the path to the OpenSSL bin directory (e.g., C:\path\to\openssl\bin).
    Click "OK" to save the changes.
    Restart your command prompt or any open terminal windows for the changes to take effect.


https://developers.facebook.com/apps/2476993995809883/dashboard/
Place to get App ID and Secret: https://developers.facebook.com/apps/2476993995809883/settings/basic/
Add App ID and App Secret into https://console.firebase.google.com/u/0/project/fir-registration-app-49110/authentication/providers\

If Fragment is used, must also update your activity to use your fragment. You can customize the properties of
Login button and register a callback in your onCreate() or onCreateView() method. Properties you can customize
includes LoginBehavior, DefaultAudience, ToolTipPopup.Style and permissions on the LoginButton.

 */

/* GOOGLE LOG IN
Add Google Play Services
Settings - Appearance & Behavior - System Settings - Android SDK - SDK Tools - Check: Google Play Services

Link: https://developers.google.com/identity/sign-in/android/start-integrating
 */

/* onCreate() vs onStart()
onCreate():
1. called when activity is first created
2. used for initialization tasks that should only be performed ONCE
3. UI, binding of data to views and other setup operations should be done here
4. called only ONCE

onStart():
1. called when activity becomes visible to user, but not yet in foreground
2. followed by the onResume(), which is called when activity comes to the foreground and becomes interactive
3. Components that I want to start and resume, e.g. registering broadcast receivers or initiating network requests should be done here
4. called everytime the activity becomes visible
 */

/* GMAIL API (NOT USED)
https://console.cloud.google.com/
https://developers.google.com/gmail/api/guides/sending

ENABLE GMAIL API
1. Go to the API Library (APIs & Services -> Library) and search for "Gmail API".
2. Select the Gmail API from the results.
3. Click the "Enable" button.

Set up OAuth 2.0 credentials
1. In the Google Cloud Console, go to APIs & Services -> Credentials.
2. Click the "Create Credentials" button and select "OAuth client ID".
3. Configure the OAuth consent screen with the necessary details.
4. Select "Android" as the application type.
5. Provide the package name of your Android application.
6. Enter the SHA-1 fingerprint of your signing key.
7. Click the "Create" button.
8. Once the credentials are created, download the JSON file containing the client ID and client secret.

Obtain SHA-1 Fingerprint (Secure Hash Algorithm 1)
1. Open 'Gradle' on the right
2. Click the first button under the word 'Gradle'
3. Search 'signingReport' and hit enter
4. Now it appears in console
5. Remember to remove after

Add in build.gradle
implementation 'com.google.api-client:google-api-client-android:1.31.5'
implementation 'com.google.apis:google-api-services-gmail:v1-rev20210630-1.31.5'
 */

/* CUSTOM POPUP (See Firebase Registration App)
Also includes popup_layout.xml and popup_background.xml
Many imports above (import android.widget.PopupWindow;)

popupWindowEmail = new PopupWindow(); ...
- creating a new PopupWindow() for each validation instead of creating a new one each time in the showPopupWindow()
 */

/* FRAGMENTS
- Reusable UI Components

1. Create a new directory under 'res' called 'menu'
2. Create the items you want:
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/navigation_home"
        android:title="Home"
        android:icon="@drawable/ic_home" />

    <item
        android:id="@+id/navigation_dashboard"
        android:title="Dashboard"
        android:icon="@drawable/ic_dashboard" />

    <item
        android:id="@+id/navigation_notifications"
        android:title="Notifications"
        android:icon="@drawable/ic_notifications" />
</menu>
3. Add a BottomNavigationView widget in the activity.xml
<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        app:menu="@menu/bottom_navigation_menu" />
4. Now create separate fragment classes for each item (good to create a new directory called 'fragments')

*/

/* ScrollView vs NestedScrollView

ScrollView is a basic scrolling container that allows a single child view to be scrolled vertically.
It's typically used when you have a small amount of content that needs to be scrolled.
ScrollView is not optimized for nested scrolling scenarios and may not handle nested scrolling views (such as RecyclerView or ListView) efficiently.
It does not support nested scrolling by default, meaning if you have a ScrollView inside another scrolling container, only the outer scrolling container will scroll.

NestedScrollView is an extended version of ScrollView with added support for handling nested scrolling views.
It's specifically designed to handle scenarios where you have multiple scrolling views nested inside each other, such as a ScrollView inside another ScrollView or a RecyclerView inside a ScrollView.
NestedScrollView enables smooth scrolling and better performance in situations where there are nested scrolling views.
It provides more advanced features for handling nested scrolling, including nested scrolling events and better coordination between scrolling views.
In summary, if you have a simple scrolling container with a single child view, ScrollView is sufficient. However, if you have nested scrolling views or need more advanced features for handling nested scrolling scenarios, such as nested scrolling events and better coordination, NestedScrollView is the preferred choice.
 */

/* IMPORTING FONTS
1. Create new dir named 'assets' in the 'app' module (main)
2. inside the 'assets', create another dir called 'fonts'
3. copy font files in
4. under the res/font (create), create a new XML file
5. Inside the .java
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/coffee_healing.ttf");
        qrButton.setTypeface(customFont);

FOR CUSTOM FONTS (REMEMBER IMPORT INTO FONT FOLDER)
Example:
<font-family xmlns:android="http://schemas.android.com/apk/res/android">
    <font
        android:fontStyle="normal"
        android:fontWeight="400"
        android:font="@font/coffee_healing" />
</font-family>
 */

/* FRAMELAYOUT
Used when I want to overlay multiple views
single child layout
managing fragments
 */

/* SEARCHVIEW AND CUSTOM ONE
Searchview: user MUST press the search icon before being able to type in...

Custom:
<LinearLayout
    android:id="@+id/search_bar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/grey"
    android:padding="10dp">

    <ImageView
        android:id="@+id/search_icon"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ic_search" />

    <EditText
        android:id="@+id/search_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Search..."
        android:background="@null" />

</LinearLayout>

 */

/* GETTING PERMISSIONS
AndroidManifest.xml
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

Activity.java
import android.Manifest;

// Define a constant for the permission request code
private static final int PERMISSION_REQUEST_CODE = 123;

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
 */

/* Custom RecyclerView with Adapter (Music Player)
1. activity_music_player.xml (holds the recyclerview)
2. item_song.xml (what each item in the recyclerview should look like, e.g. has image, text, etc...)
3. Song.java (CUSTOM CLASS TO HOLD THE METADATA INFO)
4. SongAdapter.java (sets the images, text)

ONLY FETCHING AND DISPLAYING (NO ONCLICKLISTENER YET)
SongAdapter.java
package com.example.portfolio.adapters;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio.R;
import com.example.portfolio.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> songList;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumImageView;
        public TextView titleTextView;
        public TextView artistTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImageView = itemView.findViewById(R.id.album_image);
            titleTextView = itemView.findViewById(R.id.song_title);
            artistTextView = itemView.findViewById(R.id.artist_name);
        }
    }
}


 */

/* POPUP MENU
.xml
<ImageView
            android:id="@+id/sort_imageview"
            android:layout_width="100dp"
            android:layout_height="match_parent"
            android:background="@color/white"
            android:scaleType="center"
            android:layout_weight="1"
            android:src="@drawable/ic_sort"/>

Activity.java
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
 */