# In The Game Android SDK

This SDK allows you to easily integrate the In The Game engagement platform in a video player in your Android app.
The repository includes an example app that shows how to use the framework.


## Installation

In your Android project, choose **File > New > New module > Import from .aar file**. 

Select the **itgframework.aar** file (included in this repo).

Add the following imports to your app's build.gradle:

```
implementation project(":itgframework")
implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
```

## Usage

To quickly show a full-screen activity with your interactive video channel (examples are in Kotlin):

```
val videoURL = "<your video url>"

val intent = Intent(this, ITGPlayerActivity::class.java)
var bundle = Bundle()
bundle.putString(ITGPlayerActivity.URL_PARAM, videoURL)
intent.putExtras(bundle)
startActivity(intent)
```

To load the video channel in a view in your custom layout file: 
(add constraints or layout parameters as needed)

```
<com.tiagolira.itgframework.ITGPlayerView
  android:id="@+id/playerView"
  android:layout_width="match_parent"
  android:layout_height="100dp"
  android:background="#CCCCCC"/>
```

And load it in your activity/fragment (we recommend doing it in `onResume`):

```
playerView.load("<your video url>")
```

There are two additional parameters for further configuration: `language` and `allowsFullScreen`:

```
playerView.load("<your video url>", "en", false)
```

## Notes

If you run into an Android AppCompat bug that causes webviews to crash, go to the app's build.gradle file and replace this line:
```
implementation 'androidx.appcompat:appcompat:1.1.0'
```

With:
```
implementation 'androidx.appcompat:appcompat:1.1.0-rc01'
```
