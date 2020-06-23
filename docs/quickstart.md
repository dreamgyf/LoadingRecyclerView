### Dependency

* make sure that the repositories section include `jcenter`:

```groovy
repositories {
  jcenter()
}
```

* On your module's `build.gradle` file add this implementation statement to the `dependencies` section:

```groovy
dependencies {
  implementation 'androidx.recyclerview:recyclerview:1.1.0'
  implementation 'com.dreamgyf.android.view:loading-recycler-view:0.1.0'
}
```

### Add Layout

* Add in Layout file:

  ```xml
  <com.dreamgyf.loadingrecyclerview.LoadingRecyclerView
  		android:id="@+id/loading_recycler_view"
  		android:layout_width="match_parent"
  		android:layout_height="match_parent"/>
  ```

### Config code

* Configure in Activity:

  ```java
  LoadingRecyclerView loadingRecyclerView = findViewById(R.id.loading_recycler_view);
  loadingRecyclerView.setLayoutManager(new LinearLayoutManager());
  loadingRecyclerView.setAdapter(new RecyclerView.Adapter() {...});
  loadingRecyclerView.setLoadingListener(new LoadingRecyclerView.LoadingListener() {
  		@Override
  		public void onLoadMore(final int direction, int offset) {
  				//Todo something such as load more data.
  				loadingRecyclerView.loadFinish(direction);
  			}
  		});
  ```