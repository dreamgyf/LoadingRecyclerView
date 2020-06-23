# LoadingRecyclerView

[![License](https://img.shields.io/badge/license-Apache%202-green)](https://github.com/dreamgyf/LoadingRecyclerView/blob/master/LICENSE)
[![Forks](https://img.shields.io/github/forks/dreamgyf/LoadingRecyclerView)](https://github.com/dreamgyf/LoadingRecyclerView/network/members)
[![Starts](https://img.shields.io/github/stars/dreamgyf/LoadingRecyclerView)](https://github.com/dreamgyf/LoadingRecyclerView/stargazers)
[![Issues](https://img.shields.io/github/issues/dreamgyf/LoadingRecyclerView)](https://github.com/dreamgyf/LoadingRecyclerView/issues)

[English](https://github.com/dreamgyf/LoadingRecyclerView/blob/master/README.md)
|
[中文](https://github.com/dreamgyf/LoadingRecyclerView/blob/master/README-zh.md)

---

### LoadingRecyclerView是一种当以任意方向滑动到边缘时，可以自动加载更多数据的RecyclerView

---

<div align=center><img src="https://dreamgyf-image.oss-cn-shanghai.aliyuncs.com/LoadingRecyclerView/demo-1.gif"/></div>

#### 快速使用

* 确保你的仓库包含`jcenter`:

```groovy
repositories {
  jcenter()
}
```

* 在模块的`build.gradle`文件中，将下列`implementation`添加到`dependencies`中：

```groovy
dependencies {
  implementation 'androidx.recyclerview:recyclerview:1.1.0'
  implementation 'com.dreamgyf.android.view:loading-recycler-view:0.1.0'
}
```

