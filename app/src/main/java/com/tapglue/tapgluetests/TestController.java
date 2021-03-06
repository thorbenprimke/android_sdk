/*
 * Copyright (c) 2015 Tapglue (https://www.tapglue.com/). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tapglue.tapgluetests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestController {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:MM:ss", Locale.getDefault());
    @NonNull
    private final TestAdapter mAdapter;
    private final List<String> mItems;
    private final ListView mTestLayout;
    private int linesCounter = 0;

    public TestController(ListView testLayout, Context ctx) {
        mTestLayout = testLayout;
        mItems = new ArrayList<>();
        mAdapter = new TestAdapter(ctx, android.R.layout.simple_list_item_1, mItems);
        mTestLayout.setAdapter(mAdapter);
    }

    public void log(@Nullable final String text) {
        log(text, true, true);
    }

    public void log(@Nullable final String text, final boolean withTimestamp) {
        log(text, true, withTimestamp);
    }

    public void log(@Nullable final String text, final boolean withLineNumber, final boolean withTimestamp) {
        if (text == null) return;
        final String line =
            (withLineNumber ? "[" + linesCounter++ + "] " : " ") +
                (withTimestamp ? "[" + dateFormat.format(new Date()) + "] " : " ") +
                text;
        mTestLayout.post(new Runnable() {
            @Override
            public void run() {
                mItems.add(line);
                mAdapter.notifyDataSetChanged();
                mTestLayout.invalidate();
            }
        });

        Log.d("TapglueTests", text);
    }

    private class TestAdapter extends ArrayAdapter<String> {

        @SuppressWarnings("SameParameterValue")
        public TestAdapter(Context context, int textViewResourceId,
                           @NonNull List<String> objects) {
            super(context, textViewResourceId, objects);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
