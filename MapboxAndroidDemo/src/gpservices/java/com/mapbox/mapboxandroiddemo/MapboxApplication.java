package com.mapbox.mapboxandroiddemo;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MapboxApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    FirebaseApp.initializeApp(this, new FirebaseOptions.Builder()
      .setApiKey(getString(R.string.firebase_api_key))
      .setApplicationId(getString(R.string.firebase_app_id))
      .build()
    );


    OkHttpClient picassoClient = new OkHttpClient();
    picassoClient.interceptors().add(new LoggingInterceptor());
    File cache = CacheUtils.createDefaultCacheDir(this);
    picassoClient.setCache(new com.squareup.okhttp.Cache(cache, CacheUtils.calculateDiskCacheSize(cache)));

    Picasso picasso = new Picasso.Builder(this)
      .indicatorsEnabled(true)
      .memoryCache(new LruCache(this))
      .downloader(new OkHttpDownloader(picassoClient))
      .build();


    Picasso.setSingletonInstance(picasso);

  }


}
