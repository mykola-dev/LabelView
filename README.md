# LabelView
Efficient and lightweight TextView

Inspired by http://instagram-engineering.tumblr.com/post/114508858967/improving-comment-rendering-on-android


## How to use

### Gradle
```groovy
compile "ds.labelview:library:0.4.1"
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
        android:text="Hello World"
        android:textColor="#009b00"
        android:textSize="12sp"
        android:textStyle="bold|italic"
        android:shadowColor="#f00"
        android:shadowDx="1"
        android:shadowDy="1"
        android:shadowRadius="2"
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
