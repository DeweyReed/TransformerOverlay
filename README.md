# TransformOverlay

This repo reproduces the
issue [[Transformer] The color of BitmapOverlay is a little off](https://github.com/androidx/media/issues/1050).

1. Clone the repo.
2. Run the app.
3. Tap the "Transform" button.
4. Wait for the toast "onCompleted."
5. From Device Explorer in Android Studio, locate the file "output.mp4" in the app's internal
   storage.
6. Compare the video content with [the original image](app/src/main/res/drawable-nodpi/lut.jpg);
   Their colors are slightly different.
