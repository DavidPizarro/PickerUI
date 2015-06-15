package com.dpizarro.uipicker.library.picker;

import com.dpizarro.uipicker.library.R;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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
class PickerUIAdapter extends ArrayAdapter<String> {

    private static final String EMPTY_STRING = "";

    private static final int ROTATION_CENTER        = 0;
    private static final int ROTATION_TWICE_ABOVE   = -25;
    private static final int ROTATION_FIRST         = -50;
    private static final int ROTATION_BELOW         = 25;
    private static final int ROTATION_TWICE_BELOW   = 50;
    private static final int ROTATION_ABOVE_FAR     = -55;
    private static final int ROTATION_BELOW_FAR     = 55;

    private Context mContext;
    private List<String> items;
    private int centerPosition;
    private boolean itemsClickables = true;
    private SparseIntArray positionsNoClickables;
    private int mColorTextCenter = -1;
    private int mColorTextNoCenter = -1;
    private boolean isInEditMode = false;

    /**
     * Constructor to use the adapter.
     *
     * @param context         The current context.
     * @param resource        The resource ID for a layout file containing a layout to use when
     *                        instantiating views.
     * @param items           The objects to represent in the ListView.
     * @param position        position to set in the center of the list. By default, is the half of
     *                        items.
     * @param itemsClickables to set if items can be clicked.
     * @param isInEditMode    to avoid to set styles.
     */
    public PickerUIAdapter(Context context, int resource, List<String> items, int position,
            boolean itemsClickables,
            boolean isInEditMode) {
        super(context, resource, items);
        this.mContext = context;
        this.itemsClickables = itemsClickables;
        this.isInEditMode = isInEditMode;
        positionsNoClickables = new SparseIntArray(items.size());
        setItems(items, position);
        setPositonsNoClickables();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.pickerui_item, parent, false);
        }

        TextView textItem = ViewHolder.get(convertView, R.id.tv_item);
        String option = items.get(position);
        textItem.setText(option);

        /**
         * If isInEditMode active, don't set styles
         */
        if (!isInEditMode) {
            setTextItemStyle(textItem, position);
        }

        return convertView;
    }

    /**
     * This method sets the appropriate style to each of the components to get a carousel effect.
     *
     * @param textItem the TextView of the current position of the actual item
     * @param position the current position of the actual item
     */
    private void setTextItemStyle(TextView textItem, int position) {

        if (position == centerPosition) {
            textItem.setTextAppearance(mContext, R.style.PickerUI_Center_Item);
            setTextCenterColor(textItem);
            textItem.setRotationX(ROTATION_CENTER);
            textItem.setAlpha((float) 1.0);
        } else if (position - 1 == centerPosition) {
            textItem.setTextAppearance(mContext, R.style.PickerUI_Near_Center_Item);
            setTextNoCenterColor(textItem);
            textItem.setRotationX(ROTATION_TWICE_ABOVE);
            textItem.setAlpha((float) 1.0);
        } else if (position - 2 == centerPosition) {
            textItem.setTextAppearance(mContext, R.style.PickerUI_Far_Center_Item);
            setTextNoCenterColor(textItem);
            textItem.setRotationX(ROTATION_FIRST);
            textItem.setAlpha((float) 0.7);
        } else if (position + 1 == centerPosition) {
            textItem.setTextAppearance(mContext, R.style.PickerUI_Near_Center_Item);
            setTextNoCenterColor(textItem);
            textItem.setRotationX(ROTATION_BELOW);
            textItem.setAlpha((float) 1.0);
        } else if (position + 2 == centerPosition) {
            textItem.setTextAppearance(mContext, R.style.PickerUI_Far_Center_Item);
            setTextNoCenterColor(textItem);
            textItem.setRotationX(ROTATION_TWICE_BELOW);
            textItem.setAlpha((float) 0.7);
        } else {

            if (position < centerPosition) {
                textItem.setRotationX(ROTATION_BELOW_FAR);
            } else {
                textItem.setRotationX(ROTATION_ABOVE_FAR);
            }

            textItem.setTextAppearance(mContext, R.style.PickerUI_Small_Item);
        }
    }

    private void setTextCenterColor(TextView textItem) {
        if (mColorTextCenter != -1) {
            textItem.setTextColor(mColorTextCenter);
        }
    }

    private void setTextNoCenterColor(TextView textItem) {
        if (mColorTextNoCenter != -1) {
            textItem.setTextColor(mColorTextNoCenter);
        }
    }

    /**
     * Sets the text color for the item of the center.
     *
     * @param color the color of the text
     */
    public void setColorTextCenter(int color) {
        mColorTextCenter = color;
    }

    /**
     * Sets the text color for the items which aren't in the center.
     *
     * @param color the color of the text
     */
    public void setColorTextNoCenter(int color) {
        mColorTextNoCenter = color;
    }

    /**
     * This method is used to set the items to display in the panel and the empty rows int the
     * beginning and in the
     * end.
     *
     * @param rawItems elements to show in panel
     * @param position position to set in the center of the list. By default, is the half of items.
     */
    void setItems(List<String> rawItems, int position) {

        addEmptyRows(rawItems);

        if (position == -1) {
            centerPosition = 2;
        } else {
            centerPosition = position + 2;
        }

    }

    /**
     * It saves in {@link PickerUIAdapter#centerPosition} and notify adapter. Then, the adapter in
     * {@link
     * PickerUIAdapter#setTextItemStyle(TextView, int)} set the appropriate style.
     *
     * @param position this is the position in the center of the list
     */
    public void handleSelectEvent(int position) {
        this.centerPosition = position;
        this.notifyDataSetChanged();
    }

    /**
     * This method set if the elements can be clicked by the user.
     *
     * @param itemsClickables indicates whether the items are clickable or not.
     */
    public void setItemsClickables(boolean itemsClickables) {
        this.itemsClickables = itemsClickables;
    }

    /**
     * The first two positions must be empty.
     * The last 2 positions must be empty too.
     */
    private void setPositonsNoClickables() {

        positionsNoClickables.put(0, 0);
        positionsNoClickables.put(1, 1);
        positionsNoClickables.put(items.size() - 2, items.size() - 2);
        positionsNoClickables.put(items.size() - 1, items.size() - 1);
    }

    /**
     * The first two positions must be empty.
     * The last 2 positions must be empty too.
     *
     * @param rawItems the items to show in panel
     */
    private void addEmptyRows(List<String> rawItems) {

        List<String> emptyRows = Arrays.asList(EMPTY_STRING, EMPTY_STRING);
        List<String> items = new ArrayList<String>();
        items.addAll(emptyRows);
        items.addAll(rawItems);
        items.addAll(emptyRows);

        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * This method indicates whether items can be clicked.
     *
     * if it is allowed that elements can be clicked, the first two positions and the last 2
     * positions can not be
     * pressed.
     *
     * @param position the current position of the actual item
     * @return if this item is clickable
     */
    @Override
    public boolean isEnabled(int position) {
        if (!itemsClickables) {
            return false;
        } else {

            boolean isClickable = positionsNoClickables.get(position, -1) == -1;
            return isClickable && super.isEnabled(position);
        }
    }

    /**
     * Generic ViewHolde static class
     */
    public static class ViewHolder {

        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
