# PickerUI

Android library to display a list of items for pick one with blur effect (if you wish).
Support for Android 3.0 and up. It supports portrait and landscape mode, saving the state.

Try out the sample application on [Google Play][1].

<a href="https://play.google.com/store/apps/details?id=com.dpizarro.libraries.uipickerlibrary/">
  <img alt="PickerUI Sample on Google Play"
         src="http://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

Including in Your Project
=========================

Last version is 1.0

Just add the following statement in your build.gradle

    compile 'com.github.dpizarro.libraries.pickerui:library:VERSION'
    
You may also add the library as an Android Library to your project. All the library files live in ```library```.

Usage
=====

To add the PickerUI to your layout add this to your xml
```xml
<com.dpizarro.uipicker.library.picker.PickerUI
        android:id="@+id/picker_ui_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```  

You can add custom attributes in your xml to customize: background, colors, behaviors, elements, blur, blur effects...
```xml
<com.dpizarro.uipicker.library.picker.PickerUI
        android:id="@+id/picker_ui_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        pickerui:backgroundColor="#4cffa5"
        pickerui:linesCenterColor="#3941ff"
        pickerui:autoDismiss="true"
        pickerui:itemsClickables="true"
        pickerui:entries="@array/months"
        pickerui:textCenterColor="#000000"
        pickerui:textNoCenterColor="#737373"
        pickerui:blur="true"
        pickerui:blur_downScaleFactor="@integer/blur_downscale_min"
        pickerui:blur_FilterColor="#ff5e48"
        pickerui:blur_radius="@integer/blur_radius_min"
        pickerui:blur_use_renderscript="true"/>
```

You can use PickerUI programatically, using the Builder class to set the settings and the desired functionalities of panel to make easy:
```java
PickerUI mPickerUI = (PickerUI) findViewById(R.id.picker_ui_view);

List<String> options = Arrays.asList(getResources().getStringArray(R.array.months));

PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                                                      .withItems(options)
                                                      .withBackgroundColor(getRandomColor())
                                                      .withAutoDismiss(true)
                                                      .withItemsClickables(false)
                                                      .withUseBlur(false)
                                                      .build();

mPickerUI.setSettings(pickerUISettings);
```

You can set/get values programatically:
```java
mPickerUI.setItems(this, options);
mPickerUI.setColorTextCenter(R.color.background_picker);
mPickerUI.setColorTextNoCenter(R.color.background_picker);
mPickerUI.setBackgroundColorPanel(R.color.background_picker);
mPickerUI.setLinesColor(R.color.background_picker);
mPickerUI.setItemsClickables(false);
mPickerUI.setAutoDismiss(false);
```

To slide (show/hide) PickerUI, you only have to use **`slide()`** method, selecting none (by default, center) or some position.
```java
mPickerUI.slide();
//or
mPickerUI.slide(8);
```

In order to receive the value selected in the picker, you will need to implement the `onItemClickPickerUI` interface.
```java
mPickerUI.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
                    @Override
                    public void onItemClickPickerUI(int which, int position, String valueResult) {
                        Toast.makeText(MainActivity.this, valueResult, Toast.LENGTH_SHORT).show();
                    }
                });
```

Or browse the [source code of the sample application][2] for a complete example of use.

Blur effect
=====

XXXXXXX


Contribution
============

#### Pull requests are welcome!

Feel free to contribute to PickerUI.

If you've fixed a bug or have a feature you've added, just create a pull request. Issues can be reported on the github issue tracker.

Author
-------

David Pizarro (dpizarro89@gmail.com)

<a href="https://plus.google.com/u/0/110797503395500685158">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/DavidPizarro/android-live-templates/master/art/google.png" />
</a>
<a href="https://twitter.com/DavidPizarro89">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/DavidPizarro/android-live-templates/master/art/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/davidpizarrodejesus">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/DavidPizarro/android-live-templates/master/art/linkedin.png" />
</a>


License
-------

    Copyright 2015 David Pizarro

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
---

[1]: https://play.google.com/store/apps/details?id=com.dpizarro.libraries.uipickerlibrary
[2]: https://github.com/DavidPizarro/PickerUI/tree/master/UIPickerLibrary
