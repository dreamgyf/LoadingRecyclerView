### 导入依赖

* 确保你的仓库包含`jcenter`:

```groovy
repositories {
  jcenter()
}
```

* 在模块的`build.gradle`文件中，将下列`implementation`添加到`dependencies`中：

```groovy
implementation 'com.dreamgyf.android.view:loading-recycler-view:0.2.0'
```

### 添加布局

* 在Layout文件中添加:

  ```xml
  <com.dreamgyf.loadingrecyclerview.LoadingRecyclerView
  		android:id="@+id/loading_recycler_view"
  		android:layout_width="match_parent"
  		android:layout_height="match_parent"/>
  ```

### 代码配置

* 在Activity中配置:

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