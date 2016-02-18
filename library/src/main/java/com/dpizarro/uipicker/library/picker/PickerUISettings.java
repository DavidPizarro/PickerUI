package com.dpizarro.uipicker.library.picker;

import com.dpizarro.uipicker.library.R;
import com.dpizarro.uipicker.library.blur.PickerUIBlur;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/*
 * Copyright (C) 2015 David Pizarro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class PickerUISettings implements Parcelable {

    public static final Parcelable.Creator<PickerUISettings> CREATOR
            = new Parcelable.Creator<PickerUISettings>() {
        public PickerUISettings createFromParcel(Parcel source) {
            return new PickerUISettings(source);
        }

        public PickerUISettings[] newArray(int size) {
            return new PickerUISettings[size];
        }
    };
    /**
     * Default behaviour of PickerUi when an item is selected
     */
    public static boolean DEFAULT_AUTO_DISMISS = true;
    /**
     * Default behaviour of items
     */
    public static boolean DEFAULT_ITEMS_CLICKABLES = true;
    private List<String> mItems;
    private int mColorTextCenter;
    private int mColorTextNoCenter;
    private int mBackgroundColor;
    private int mLinesColor;
    private boolean mAutoDismiss;
    private boolean mItemsClickables;
    private float mBlurDownScaleFactor;
    private int mBlurRadius;
    private int mBlurFilterColor;
    private boolean mUseBlur;
    private boolean mUseBlurRenderscript;
    private int mPopupLocation;
    public static int POPUP_AT_BOTTOM = 1;
    public static int POPUP_AT_TOP = 2;
    public static int POPUP_AT_UNSPECIFIED = 0;

    private PickerUISettings(Builder builder) {
        setItems(builder.mItems);
        setPopupLocation(builder.mPopupLocation);
        setColorTextCenter(builder.mColorTextCenter);
        setColorTextNoCenter(builder.mColorTextNoCenter);
        setBackgroundColor(builder.mBackgroundColor);
        setLinesColor(builder.mLinesColor);
        setItemsClickables(builder.mItemsClickables);
        setAutoDismiss(builder.mAutoDismiss);
        setUseBlur(builder.mUseBlur);
        setUseBlurRenderscript(builder.mUseBlurRenderscript);
        setBlurDownScaleFactor(builder.mDownScaleFactor);
        setBlurRadius(builder.mRadius);
        setBlurFilterColor(builder.mFilterColor);
    }

    private PickerUISettings(Parcel in) {
        in.readStringList(this.mItems);
        this.mColorTextCenter = in.readInt();
        this.mColorTextNoCenter = in.readInt();
        this.mBackgroundColor = in.readInt();
        this.mLinesColor = in.readInt();
        this.mAutoDismiss = in.readByte() != 0;
        this.mItemsClickables = in.readByte() != 0;
        this.mBlurDownScaleFactor = in.readFloat();
        this.mBlurRadius = in.readInt();
        this.mBlurFilterColor = in.readInt();
        this.mUseBlur = in.readByte() != 0;
        this.mUseBlurRenderscript = in.readByte() != 0;
    }

    public List<String> getItems() {
        return mItems;
    }

    void setItems(List<String> items) {
        mItems = items;
    }

    public int getPopupLocation() {
        return mPopupLocation;
    }

    public void setPopupLocation(int mPopupLocation) {
        this.mPopupLocation = mPopupLocation;
    }

    public int getColorTextCenter() {
        return mColorTextCenter;
    }

    void setColorTextCenter(int colorTextCenter) {
        mColorTextCenter = colorTextCenter;
    }

    public int getColorTextNoCenter() {
        return mColorTextNoCenter;
    }

    void setColorTextNoCenter(int colorTextNoCenter) {
        mColorTextNoCenter = colorTextNoCenter;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public int getLinesColor() {
        return mLinesColor;
    }

    void setLinesColor(int linesColor) {
        mLinesColor = linesColor;
    }

    public boolean areItemsClickables() {
        return mItemsClickables;
    }

    void setItemsClickables(boolean itemsClickables) {
        mItemsClickables = itemsClickables;
    }

    public boolean isAutoDismiss() {
        return mAutoDismiss;
    }

    void setAutoDismiss(boolean autoDismiss) {
        mAutoDismiss = autoDismiss;
    }

    public boolean isUseBlur() {
        return mUseBlur;
    }

    void setUseBlur(boolean useBlur) {
        mUseBlur = useBlur;
    }

    public boolean isUseBlurRenderscript() {
        return mUseBlurRenderscript;
    }

    void setUseBlurRenderscript(boolean useBlurRenderscript) {
        mUseBlurRenderscript = useBlurRenderscript;
    }

    public float getBlurDownScaleFactor() {
        return mBlurDownScaleFactor;
    }

    void setBlurDownScaleFactor(float blurDownScaleFactor) {
        mBlurDownScaleFactor = blurDownScaleFactor;
    }

    public int getBlurRadius() {
        return mBlurRadius;
    }

    void setBlurRadius(int blurRadius) {
        mBlurRadius = blurRadius;
    }

    public int getBlurFilterColor() {
        return mBlurFilterColor;
    }

    void setBlurFilterColor(int blurFilterColor) {
        mBlurFilterColor = blurFilterColor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mItems);
        dest.writeInt(this.mColorTextCenter);
        dest.writeInt(this.mColorTextNoCenter);
        dest.writeInt(this.mBackgroundColor);
        dest.writeInt(this.mLinesColor);
        dest.writeByte(mAutoDismiss ? (byte) 1 : (byte) 0);
        dest.writeByte(mItemsClickables ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.mBlurDownScaleFactor);
        dest.writeInt(this.mBlurRadius);
        dest.writeInt(this.mBlurFilterColor);
        dest.writeByte(mUseBlur ? (byte) 1 : (byte) 0);
        dest.writeByte(mUseBlurRenderscript ? (byte) 1 : (byte) 0);
    }

    public static final class Builder {

        private List<String> mItems;
        private int mColorTextCenter = R.color.text_center_pickerui;
        private int mColorTextNoCenter = R.color.text_no_center_pickerui;
        private int mBackgroundColor = R.color.background_panel_pickerui;
        private int mLinesColor = R.color.lines_panel_pickerui;
        private boolean mUseBlur = PickerUIBlur.DEFAULT_USE_BLUR;
        private boolean mUseBlurRenderscript = PickerUIBlur.DEFAULT_USE_BLUR_RENDERSCRIPT;
        private boolean mItemsClickables = DEFAULT_ITEMS_CLICKABLES;
        private float mDownScaleFactor = PickerUIBlur.DEFAULT_DOWNSCALE_FACTOR;
        private int mRadius = PickerUIBlur.DEFAULT_BLUR_RADIUS;
        private boolean mAutoDismiss = DEFAULT_AUTO_DISMISS;
        private int mFilterColor = -1;
        private int mPopupLocation = 0;

        public Builder() {
        }

        private Builder(Builder builder) {
            mUseBlurRenderscript = builder.mUseBlurRenderscript;
            mUseBlur = builder.mUseBlur;
        }

        public Builder withItems(List<String> mItems) {
            this.mItems = mItems;
            return this;
        }

        public Builder withPopupLocation(int mPopupLocation){
            this.mPopupLocation = mPopupLocation;
            return this;
        }

        public Builder withColorTextCenter(int mColorTextCenter) {
            this.mColorTextCenter = mColorTextCenter;
            return this;
        }

        public Builder withColorTextNoCenter(int mColorTextNoCenter) {
            this.mColorTextNoCenter = mColorTextNoCenter;
            return this;
        }

        public Builder withBackgroundColor(int mBackgroundColor) {
            this.mBackgroundColor = mBackgroundColor;
            return this;
        }

        public Builder withLinesColor(int mLinesColor) {
            this.mLinesColor = mLinesColor;
            return this;
        }

        public Builder withItemsClickables(boolean mItemsClickables) {
            this.mItemsClickables = mItemsClickables;
            return this;
        }

        public Builder withAutoDismiss(boolean mAutoDismiss) {
            this.mAutoDismiss = mAutoDismiss;
            return this;
        }

        public Builder withBlurDownScaleFactor(float mDownScaleFactor) {
            this.mDownScaleFactor = mDownScaleFactor;
            return this;
        }

        public Builder withBlurRadius(int mRadius) {
            this.mRadius = mRadius;
            return this;
        }

        public Builder withBlurFilterColor(int mFilterColor) {
            this.mFilterColor = mFilterColor;
            return this;
        }

        public Builder withUseBlurRenderscript(boolean mUseBlurRenderscript) {
            this.mUseBlurRenderscript = mUseBlurRenderscript;
            return this;
        }

        public Builder withUseBlur(boolean mUseBlur) {
            this.mUseBlur = mUseBlur;
            return this;
        }

        public PickerUISettings build() {
            return new PickerUISettings(this);
        }

    }
}
