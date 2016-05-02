# LabelView
Efficient and lightweight TextView

Inspired by https://engineering.instagram.com/posts/1633609290216367/improving-comment-rendering-on-android/


## How to use

### Gradle
```groovy
compile "ds.labelview:library:0.2.0"
```

### Java
```java
LabelView view = new LabelView(this);
view.setText("Hello World");
```

### XML
```xml
 <ds.labelview.LabelView
        android:id="@+id/label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="12dp"
        android:gravity="center_horizontal"
        app:text="LabelView"
        app:textColor="#009b00"
        app:textSize="12sp"
        app:textStyle="bold|italic"
        app:shadowColor="#f00"
        app:shadowDx="1dp"
        app:shadowDy="1dp"
        app:shadowRadius="2dp"
        />
```

```
Copyright 2016 Deviant Studio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
