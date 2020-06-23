# LoadingRecyclerView

[![License](https://img.shields.io/badge/license-Apache%202-green)](https://github.com/dreamgyf/LoadingRecyclerView/blob/master/LICENSE)
[![Forks](https://img.shields.io/github/forks/dreamgyf/LoadingRecyclerView)](https://github.com/dreamgyf/LoadingRecyclerView/network/members)
[![Starts](https://img.shields.io/github/stars/dreamgyf/LoadingRecyclerView)](https://github.com/dreamgyf/LoadingRecyclerView/stargazers)
[![Issues](https://img.shields.io/github/issues/dreamgyf/LoadingRecyclerView)](https://github.com/dreamgyf/LoadingRecyclerView/issues)

[English](https://github.com/dreamgyf/LoadingRecyclerView/blob/master/README.md)
|
[中文](https://github.com/dreamgyf/LoadingRecyclerView/blob/master/README-zh.md)

---

### LoadingRecyclerView is a RecyclerView that can automatically load more data when sliding to the edge in any direction.

---

![Demo](https://dreamgyf-image.oss-cn-shanghai.aliyuncs.com/LoadingRecyclerView/demo-1.gif)

#### Quick Start

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

