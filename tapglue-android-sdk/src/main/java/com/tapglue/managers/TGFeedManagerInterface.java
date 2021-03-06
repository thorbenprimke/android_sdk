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

package com.tapglue.managers;

import com.tapglue.model.TGConnectionUsersList;
import com.tapglue.model.TGFeed;
import com.tapglue.model.TGFeedCount;
import com.tapglue.networking.requests.TGRequestCallback;

public interface TGFeedManagerInterface {
    void cachedFeedForCurrentUser(TGRequestCallback<TGFeed> returnMethod);

    void getCachedFeedIfAvailable(TGRequestCallback<TGFeed> returnMethod);

    void retrieveEventsForCurrentUser(TGRequestCallback<TGFeed> returnMethod);

    void retrieveEventsForUser(Long userId, TGRequestCallback<TGFeed> returnMethod);

    void retrieveFeedForCurrentUser(TGRequestCallback<TGFeed> returnMethod);

    void retrieveFollowersForCurrentUser(TGRequestCallback<TGConnectionUsersList> returnMethod);

    void retrieveFollowersForUser(Long userId, TGRequestCallback<TGConnectionUsersList> returnMethod);

    void retrieveFollowsForCurrentUser(TGRequestCallback<TGConnectionUsersList> returnMethod);

    void retrieveFollowsForUser(Long userId, TGRequestCallback<TGConnectionUsersList> returnMethod);

    void retrieveFriendsForCurrentUser(TGRequestCallback<TGConnectionUsersList> returnMethod);

    void retrieveFriendsForUser(Long userId, TGRequestCallback<TGConnectionUsersList> returnMethod);

    void retrieveUnreadCountForCurrentUser(TGRequestCallback<TGFeedCount> returnMethod);

    void retrieveUnreadFeedForCurrentUser(TGRequestCallback<TGFeed> returnMethod);
}
