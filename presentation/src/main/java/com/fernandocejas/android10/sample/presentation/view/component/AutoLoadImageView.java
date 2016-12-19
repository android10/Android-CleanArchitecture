/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Simple implementation of {@link android.widget.ImageView} with extended features like setting an
 * image from an url and an internal file cache using the application cache directory.
 */
public class AutoLoadImageView extends ImageView {

  private static final String BASE_IMAGE_NAME_CACHED = "image_";

  private String imageUrl = null;
  private int imagePlaceHolderResId = -1;
  private DiskCache cache = new DiskCache(getContext().getCacheDir());

  public AutoLoadImageView(Context context) {
    super(context);
  }

  public AutoLoadImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AutoLoadImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override protected Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    SavedState savedState = new SavedState(superState);
    savedState.imagePlaceHolderResId = this.imagePlaceHolderResId;
    savedState.imageUrl = this.imageUrl;
    return savedState;
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if(!(state instanceof SavedState)) {
      super.onRestoreInstanceState(state);
      return;
    }
    SavedState savedState = (SavedState)state;
    super.onRestoreInstanceState(savedState.getSuperState());
    this.imagePlaceHolderResId = savedState.imagePlaceHolderResId;
    this.imageUrl = savedState.imageUrl;
    this.setImageUrl(this.imageUrl);
  }

  /**
   * Set an image from a remote url.
   *
   * @param imageUrl The url of the resource to load.
   */
  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
    AutoLoadImageView.this.loadImagePlaceHolder();
    if (this.imageUrl != null) {
      this.loadImageFromUrl(this.imageUrl);
    } else {
      this.loadImagePlaceHolder();
    }
  }

  /**
   * Loads and image from the internet (and cache it) or from the internal cache.
   *
   * @param imageUrl The remote image url to load.
   */
  private void loadImageFromUrl(final String imageUrl) {
    new Thread() {
      @Override public void run() {
        final Bitmap bitmap = AutoLoadImageView.this.getFromCache(getFileNameFromUrl(imageUrl));
        if (bitmap != null) {
          AutoLoadImageView.this.loadBitmap(bitmap);
        } else {
          if (isThereInternetConnection()) {
            final ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.download(imageUrl, new ImageDownloader.Callback() {
              @Override public void onImageDownloaded(Bitmap bitmap) {
                AutoLoadImageView.this.cacheBitmap(bitmap, getFileNameFromUrl(imageUrl));
                AutoLoadImageView.this.loadBitmap(bitmap);
              }

              @Override public void onError() {
                AutoLoadImageView.this.loadImagePlaceHolder();
              }
            });
          } else {
            AutoLoadImageView.this.loadImagePlaceHolder();
          }
        }
      }
    }.start();
  }

  /**
   * Run the operation of loading a bitmap on the UI thread.
   *
   * @param bitmap The image to load.
   */
  private void loadBitmap(final Bitmap bitmap) {
    ((Activity) getContext()).runOnUiThread(new Runnable() {
      @Override public void run() {
        AutoLoadImageView.this.setImageBitmap(bitmap);
      }
    });
  }

  /**
   * Loads the image place holder if any has been assigned.
   */
  private void loadImagePlaceHolder() {
    if (this.imagePlaceHolderResId != -1) {
      ((Activity) getContext()).runOnUiThread(new Runnable() {
        @Override public void run() {
          AutoLoadImageView.this.setImageResource(
              AutoLoadImageView.this.imagePlaceHolderResId);
        }
      });
    }
  }

  /**
   * Get a {@link android.graphics.Bitmap} from the internal cache or null if it does not exist.
   *
   * @param fileName The name of the file to look for in the cache.
   * @return A valid cached bitmap, otherwise null.
   */
  private Bitmap getFromCache(String fileName) {
    Bitmap bitmap = null;
    if (this.cache != null) {
      bitmap = this.cache.get(fileName);
    }
    return bitmap;
  }

  /**
   * Cache an image using the internal cache.
   *
   * @param bitmap The bitmap to cache.
   * @param fileName The file name used for caching the bitmap.
   */
  private void cacheBitmap(Bitmap bitmap, String fileName) {
    if (this.cache != null) {
      this.cache.put(bitmap, fileName);
    }
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    final ConnectivityManager connectivityManager =
        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }

  /**
   * Creates a file name from an image url
   *
   * @param imageUrl The image url used to build the file name.
   * @return An String representing a unique file name.
   */
  private String getFileNameFromUrl(String imageUrl) {
    //we could generate an unique MD5/SHA-1 here
    String hash = String.valueOf(imageUrl.hashCode());
    if (hash.startsWith("-")) {
      hash = hash.substring(1);
    }
    return BASE_IMAGE_NAME_CACHED + hash;
  }

  /**
   * Class used to download images from the internet
   */
  private static class ImageDownloader {
    interface Callback {
      void onImageDownloaded(Bitmap bitmap);

      void onError();
    }

    ImageDownloader() {}

    /**
     * Download an image from an url.
     *
     * @param imageUrl The url of the image to download.
     * @param callback A callback used to be reported when the task is finished.
     */
    void download(String imageUrl, Callback callback) {
      try {
        final URLConnection conn = new URL(imageUrl).openConnection();
        conn.connect();
        final Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        if (callback != null) {
          callback.onImageDownloaded(bitmap);
        }
      } catch (IOException e) {
        reportError(callback);
      }
    }

    /**
     * Report an error to the caller
     *
     * @param callback Caller implementing {@link Callback}
     */
    private void reportError(Callback callback) {
      if (callback != null) {
        callback.onError();
      }
    }
  }

  /**
   * A simple disk cache implementation
   */
  private static class DiskCache {

    private static final String TAG = "DiskCache";

    private final File cacheDir;

    DiskCache(File cacheDir) {
      this.cacheDir = cacheDir;
    }

    /**
     * Get an element from the cache.
     *
     * @param fileName The name of the file to look for.
     * @return A valid element, otherwise false.
     */
    synchronized Bitmap get(String fileName) {
      Bitmap bitmap = null;
      File file = buildFileFromFilename(fileName);
      if (file.exists()) {
        bitmap = BitmapFactory.decodeFile(file.getPath());
      }
      return bitmap;
    }

    /**
     * Cache an element.
     *
     * @param bitmap The bitmap to be put in the cache.
     * @param fileName A string representing the name of the file to be cached.
     */
    synchronized void put(Bitmap bitmap, String fileName) {
      final File file = buildFileFromFilename(fileName);
      if (!file.exists()) {
        try {
          final FileOutputStream fileOutputStream = new FileOutputStream(file);
          bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
          fileOutputStream.flush();
          fileOutputStream.close();
        } catch (IOException e) {
          Log.e(TAG, e.getMessage());
        }
      }
    }

    /**
     * Creates a file name from an image url
     *
     * @param fileName The image url used to build the file name.
     * @return A {@link java.io.File} representing a unique element.
     */
    private File buildFileFromFilename(String fileName) {
      String fullPath = this.cacheDir.getPath() + File.separator + fileName;
      return new File(fullPath);
    }
  }

  private static class SavedState extends BaseSavedState {
    int imagePlaceHolderResId;
    String imageUrl;

    SavedState(Parcelable superState) {
      super(superState);
    }

    private SavedState(Parcel in) {
      super(in);
      this.imagePlaceHolderResId = in.readInt();
      this.imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeInt(this.imagePlaceHolderResId);
      out.writeString(this.imageUrl);
    }

    public static final Parcelable.Creator<SavedState> CREATOR =
        new Parcelable.Creator<SavedState>() {
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
  }
}
