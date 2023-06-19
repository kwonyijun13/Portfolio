package com.example.portfolio;
public class Notes { }

/* GIT
Initialize Git: VCS - Create Git Repos - Select Root dir of project
Add and Commit: Git - Add

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
 */