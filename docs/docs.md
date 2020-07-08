### Layout properties

|       Properties       |  Type   |  Default  |                         Description                          |
| :--------------------: | :-----: | :-------: | :----------------------------------------------------------: |
|       direction        |  flags  |    end    | Choose whether to slide to the first or last load, and there are two optional values :`start`,`end`, you can also input `start\|end` to select both |
|       enableLoad       | boolean |   true    |             Whether to turn on the load function             |
| loadingBackgroundColor |  color  | #FFFAFAFA |            Background color of loading animation             |
|    loadingArcColor     |  color  | #FF000000 |                Arc color of loading animation                |

### Documentation

#### public `LoadingRecyclerView` extends RecyclerView

---

* ##### Constants

| Type |       Value       |          Description           |
| :--: | :---------------: | :----------------------------: |
| int  | `Direction.START` | Load when sliding to the start |
| int  |  `Direction.END`  |  Load when sliding to the end  |

* ##### Public interface

| Interface name    |                 Method                  |                          Parameters                          |                    Description                     |
| ----------------- | :-------------------------------------: | :----------------------------------------------------------: | :------------------------------------------------: |
| `LoadingListener` | `onLoadMore(int direction, int offset)` | The direction is one of the above constants, and the offset is the offset at the beginning of loading the next set of data (actually the total number of items in the current list) | Method to call back when a load event is generated |

* ##### Public constructors

| Public constructors                                          |
| ------------------------------------------------------------ |
| `public LoadingRecyclerView(Context context)`                |
| `public LoadingRecyclerView(Context context, AttributeSet attrs)` |

* ##### Public methods

| Return   value |                        Method                         |                 Parameters                 |                    Description                    |
| :------------: | :---------------------------------------------------: | :----------------------------------------: | :-----------------------------------------------: |
|      void      |             `setDirection(int direction)`             | One of the above constants or `START\|END` | Set direction of loading when sliding to the edge |
|      void      |    `setLoadingListener(LoadingListener listener)`     |      See above interface description       |   Set a callback when a load event is generated   |
|      void      |                    `enableLoad()`                     |                    None                    |                  Turn on loading                  |
|      void      |              `enableLoad(int direction)`              |         One of the above constants         |  Turn on loading function in a certain direction  |
|      void      |                    `disableLoad()`                    |                    None                    |                 Turn off loading                  |
|      void      |             `disableLoad(int direction)`              |         One of the above constants         | Turn off loading function in a certain direction  |
|     float      |                 `getLoadingRadius()`                  |                    None                    |        Get radius of the loading animation        |
|      void      |           `setLoadingRadius(float radius)`            |                Radius value                |        Set radius of the loading animation        |
|      void      | `setLoadingPosition(int direction, float x, float y)` |    direction,x-coordinate,y-coordinate     |       Set position of the loading animation       |
|      int       |             `getLoadingBackgroundColor()`             |                    None                    |   Get background color of the loading animation   |
|      void      |            `setLoadingArcColor(int color)`            |          Hexadecimal color value           |   Set background color of the loading animation   |
|      int       |                `getLoadingArcColor()`                 |                    None                    |      Get Arc color of the loading animation       |
|      void      |            `setLoadingArcColor(int color)`            |          Hexadecimal color value           |      Set Arc color of the loading animation       |

