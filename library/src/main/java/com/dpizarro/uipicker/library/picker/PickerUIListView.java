package com.dpizarro.uipicker.library.picker;

import com.dpizarro.uipicker.library.R;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

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
class PickerUIListView extends ListView {

    private final static int ROW_HEIGHT = 40;
    private PickerUIItemClickListener mItemClickListenerPickerUI;
    private PickerUIAdapter mPickerUIAdapter;
    private boolean scrollEnabled = false;
    private int lastPositionNotified;
    private int firstItem, scrollTop;
    private List<String> items;
    private int which;

    /**
     * Default constructor
     */
    public PickerUIListView(Context context) {
        super(context);
        if (isInEditMode()) {
            createEditModeView(context);
        } else {
            init(items);
        }
    }

    /**
     * Default constructor
     */
    public PickerUIListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            createEditModeView(context);
        } else {
            init(items);
        }
    }

    /**
     * Default constructor
     */
    public PickerUIListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            createEditModeView(context);
        } else {
            init(items);
        }
    }

    /**
     * Constructor with items to show in the panel
     *
     * @param items elements to show in panel
     */
    public PickerUIListView(Activity context, List<String> items) {
        super(context);
        if (isInEditMode()) {
            createEditModeView(context);
        } else {
            init(items);
        }
    }

    /**
     * This method inflates the ListView to be visible from Preview Layout.
     * It use a mock list (item0, item1, item2...) and set the center in the middle of the list with
     * 'setSelection(...)'
     * It only applies in Preview Layout.
     *
     * @param context it's necessary to use in {@link PickerUIAdapter}
     */
    private void createEditModeView(Context context) {
        String[] entries = new String[10];
        for (int i = 0; i < 10; i++) {
            entries[i] = "item " + i;
        }
        List<String> entriesList = Arrays.asList(entries);
        mPickerUIAdapter = new PickerUIAdapter(context, R.layout.pickerui_item, entriesList,
                entriesList.size() / 2, true, true);
        setAdapter(mPickerUIAdapter);
        setSelection(entriesList.size() / 2);
    }

    private void init(List<String> items) {
        this.items = items;

        ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //This will be called as the layout is finished, prior to displaying.
                scrollEnabled = true;

                if (PickerUIListView.this.items != null) {
                    selectListItem(PickerUIListView.this.items.size() / 2, false);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    //noinspection deprecation
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    getItemInListCenter();
                    if (scrollTop < -ROW_HEIGHT) {
                        mPickerUIAdapter.handleSelectEvent(firstItem + 1 + 2);
                        selectListItem(firstItem + 1);
                    } else {
                        selectListItem(firstItem);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                // save index and top position
                View v = getChildAt(0);

                //Required to select the closest item when finger releases scroll
                scrollTop = (v == null) ? 0 : v.getTop();
                firstItem = firstVisibleItem;

                if (scrollEnabled) {
                    getItemInListCenter();
                }
            }
        });

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setNewPositionCenter(position);
            }
        });
    }

    /**
     * This method is used by {@link PickerUI} to indicate to {@link PickerUIListView} the items to
     * display in the panel and a number of configurations.
     *
     * @param context           {@link PickerUIAdapter} needs a context to inflate the layout
     * @param items             elements to show in panel
     * @param idRequestPickerUI id of the element
     * @param position          position to set in the center of the list
     * @param itemsClickables   indicates whether the items are clickable or not.
     */
    public void setItems(Context context, List<String> items, int idRequestPickerUI, int position,
            boolean itemsClickables) {
        this.items = items;
        this.which = idRequestPickerUI;
        mPickerUIAdapter = new PickerUIAdapter(context, R.layout.pickerui_item, items, position,
                itemsClickables, false);
        setAdapter(mPickerUIAdapter);
    }

    /**
     * Method to select an item from the list and notifies {@link PickerUI} if necessary.
     * Not notify {@link PickerUI} the 1st time , because it will be because they just set the items
     * and not by
     * voluntary user selection.
     *
     * @param position the position to select in the list and to set in the center
     * @param notify   indicates whether to notify the selection of an item
     */
    private void selectListItem(final int position, final boolean notify) {
        setSelection(position);

        if (notify) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //We need to give the adapter time to draw the views
                    if (mItemClickListenerPickerUI == null) {
                        throw new IllegalStateException(
                                "You must assign a valid PickerUIListView.PickerUIItemClickListener first!");
                    }
                    mItemClickListenerPickerUI
                            .onItemClickItemPickerUI(which, position, items.get(position));

                }
            }, 200);

        }
    }

    /**
     * Method to select an item from the list and notifies {@link PickerUI}.
     *
     * @param position the position to select in the list and to set in the center
     */
    private void selectListItem(final int position) {
        selectListItem(position, true);
    }

    /**
     * Method to select an item and notify to {@link PickerUIAdapter} to set the style.
     *
     * @param position the position to select in the list and to set in the center
     */
    private void setNewPositionCenter(int position) {
        mPickerUIAdapter.handleSelectEvent(position);
        selectListItem(position - 2);
    }

    /**
     * When the user is scrolling and stops, we need to get the item in the center of the list, save
     * this position and
     * notify to adapter.
     */
    public int getItemInListCenter() {

        int position = pointToPosition(getWidth() / 2, getHeight() / 2);
        if (position != -1) {

            if (position != lastPositionNotified) {

                //Only refresh adapter on different positions
                lastPositionNotified = position;
                mPickerUIAdapter.handleSelectEvent(position);
            }
        }
        return position - 2;
    }

    PickerUIAdapter getPickerUIAdapter() {
        return mPickerUIAdapter;
    }

    /**
     * Set a callback listener for the item click.
     *
     * @param listener Callback instance.
     */
    void setOnClickItemPickerUIListener(PickerUIItemClickListener listener) {
        this.mItemClickListenerPickerUI = listener;
    }

    /**
     * Interface for a callback when the item has been clicked.
     */
    interface PickerUIItemClickListener {

        /**
         * Callback when the item has been clicked.
         *
         * @param which       id of the element has been clicked
         * @param position    Position of the current item.
         * @param valueResult Value of text of the current item.
         */
        void onItemClickItemPickerUI(int which, int position, String valueResult);
    }
}
