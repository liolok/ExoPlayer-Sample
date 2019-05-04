# 安卓应用 - 鲁迅经典语录鉴赏

## 整体预览

![整体预览](docs/images/Sample.gif "整体预览")

## 首屏 | Splash Activity

展示鲁迅签名及其名言一句，延时两秒自动淡出至列表页面。

横屏布局（[`res/layout-land/activity_splash.xml`](app/src/main/res/layout-land/activity_splash.xml)）：

### 核心代码

```kotlin
Handler().postDelayed({
    startActivity(Intent(this, ListActivity::class.java))
    finish(); overridePendingTransition(anim.fade_in, anim.fade_out)  // use fade animation
}, DELAY_MILLIS)  // jump to list activity after delay
```

![横屏布局](docs/images/Screenshot_Splash_Landscape.png "横屏布局")

## 列表 | List Activity

列表使用 `RecyclerView` 实现，条目使用 `CardView` 实现。

### 核心代码

```kotlin
// Provide a reference to the views for each item
class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Item) = with(itemView) {
        title.text = item.title
        thumb.setImageResource(
            resources.getIdentifier("cover_${item.book}", "drawable", context.packageName)
        )  // get drawable resource id by item's book name value, set thumb image
        setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            val options = ActivityOptions  // use shared element transition
                .makeSceneTransitionAnimation(context as Activity, thumb as View, thumb.transitionName)
            context.startActivity(intent.putExtra(Item.EXTRA_KEY, item), options.toBundle())
        }  // on item view clicked, start detail activity and pass the item object as extra
    }  // bind an item object to its view
}
```

## 详情 | Detail Activity

- 工具栏内显示书籍封面图：使用 `CollapsingToolbarLayout` 实现，详见 [`activity_detail.xml`](app/src/main/res/layout/activity_detail.xml)；
- 与列表页面间的过渡动画：使用 `Shared Element Transition` 实现，详见上一节核心代码；
- 保存评分条分值：使用 `SharedPreference` 实现，详见下面核心代码。

### 核心代码

```kotlin
// Need only one preference file to store rating values
val ratingPref = getPreferences(Context.MODE_PRIVATE)
ratingBar.apply {
    rating = ratingPref.getFloat(item.title, DEFAULT_RATING)  // read rating value from preference file
    setOnRatingBarChangeListener { _, rating, fromUser ->
        if (fromUser) with(ratingPref.edit()) { putFloat(item.title, rating); apply() }
    }  // write value into preference file on user changes rating
}
```

## 数据字典

```kotlin
@Parcelize  // for intent extra, see more in RecyclerAdapter.ItemViewHolder.onClick() and DetailActivity.onCreate()
@Serializable  // for reading list from json resource file, see more in ListActivity.onCreate()
data class Item(val book: String, val title: String, val content: String) : Parcelable {
    companion object {
        const val EXTRA_KEY = "${BuildConfig.APPLICATION_ID}.Item"
    }  // intent extra key, use app's package name as prefix to be unique
}
```

初始数据存储在 [`res/raw/items.json`](app/src/main/res/raw/items.json)，在 `List Activity` 中使用 `Kotlinx Serialization` 解析并传递给 `RecyclerAdapter`。

## 已知问题

列表页进入详情页的过渡动画会遮盖虚拟导航栏，疑似为安卓官方上游 Bug。

## 心得体会

Kotlin 真香，Android 开发真难。

## 参考资料

- `RecyclerView`：[Create a List with RecyclerView  |  Android Developers](https://developer.android.com/guide/topics/ui/layout/recyclerview "Create a List with RecyclerView  |  Android Developers")
- `Activity`：
    - [Start another activity  |  Android Developers](https://developer.android.com/training/basics/firstapp/starting-activity "Start another activity  |  Android Developers")
    - [Start an activity using an animation  |  Android Developers](https://developer.android.com/training/transitions/start-activity "Start an activity using an animation  |  Android Developers")
    - [Add an up action  |  Android Developers](https://developer.android.com/training/appbar/up-action.html "Add an up action  |  Android Developers")
- `Resource`：[Resource types overview  |  Android Developers](https://developer.android.com/guide/topics/resources/available-resources.html "Resource types overview  |  Android Developers")
- `SharedPreference`：[Save key-value data  |  Android Developers](https://developer.android.com/training/data-storage/shared-preferences "Save key-value data  |  Android Developers")
