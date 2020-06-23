### 布局属性

|    属性    |  类型   | 默认值 |                             描述                             |
| :--------: | :-----: | :----: | :----------------------------------------------------------: |
| direction  |  flags  |  end   | 选择滑动到最前加载还是最后加载，有两种可选值:`start`,`end`，或使用`start\|end`选择两者 |
| enableLoad | boolean |  true  |                       是否开启加载功能                       |

### 接口文档

#### public `LoadingRecyclerView` extends RecyclerView

---

* ##### 常量

| 类型 |        值         |       描述       |
| :--: | :---------------: | :--------------: |
| int  | `Direction.START` | 滑动到最前时加载 |
| int  |  `Direction.END`  | 滑动到最后时加载 |

* ##### 公有接口

| 接口名            |                  方法                   |                             参数                             |            描述            |
| ----------------- | :-------------------------------------: | :----------------------------------------------------------: | :------------------------: |
| `LoadingListener` | `onLoadMore(int direction, int offset)` | direction为上述常量中的其一，offset为加载下一组数据开始时的偏移量(实为当前列表Item的总数) | 当产生加载事件时回调的方法 |

* ##### 构造方法

| 公有构造方法                                                 |
| ------------------------------------------------------------ |
| `public LoadingRecyclerView(Context context)`                |
| `public LoadingRecyclerView(Context context, AttributeSet attrs)` |

* ##### 公有方法

| 返回值 |                      方法                      |              参数               |              描述              |
| :----: | :--------------------------------------------: | :-----------------------------: | :----------------------------: |
|  void  |         `setDirection(int direction)`          | 为上述常量中的其一或`START\END` | 设置滑动到哪个方向的边缘时加载 |
|  void  | `setLoadingListener(LoadingListener listener)` |         见上述接口说明          |   设置当产生加载事件时的回调   |
|  void  |                 `enableLoad()`                 |               无                |          开启加载功能          |
|  void  |          `enableLoad(int direction)`           |       为上述常量中的其一        |     开启某个方向的价值功能     |
|  void  |                `disableLoad()`                 |               无                |          关闭加载功能          |
|  void  |          `disableLoad(int direction)`          |       为上述常量中的其一        |     关闭某个方向的加载功能     |
|  void  |        `setLoadingRadius(float radius)`        |             半径值              |       设置加载动画的大小       |