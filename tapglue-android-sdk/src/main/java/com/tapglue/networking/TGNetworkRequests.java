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

package com.tapglue.networking;

import com.tapglue.model.TGConnection;
import com.tapglue.model.TGConnectionUsersList;
import com.tapglue.model.TGEvent;
import com.tapglue.model.TGFeed;
import com.tapglue.model.TGFeedCount;
import com.tapglue.model.TGLoginUser;
import com.tapglue.model.TGPendingConnections;
import com.tapglue.model.TGSocialConnections;
import com.tapglue.model.TGUser;
import com.tapglue.networking.requests.TGRequestCallback;

public interface TGNetworkRequests {

    /**
     * Confirm connection
     *
     * @param userId id of the user the connection is confirmed for
     * @param type   the type of connection being confirmed
     * @param output result of the callback
     */
    void confirmConnection(Long userId, TGConnection.TGConnectionType type, TGRequestCallback<TGConnection> output);

    /**
     * Create connection
     *
     * @param userId user to which connection should be made
     * @param type   type of connection
     * @param state  state of connection
     * @param output return callback
     */
    void createConnection(Long userId, TGConnection.TGConnectionType type, String state, TGRequestCallback<TGConnection> output);

    /**
     * Create event for current user
     *
     * @param input  event to be created
     * @param output return callback
     */
    void createEvent(TGEvent input, TGRequestCallback<TGEvent> output);

    /**
     * Create pending connection with selected type
     *
     * @param returnCallback return callback
     */
    void createPendingConnectionsRequest(TGRequestCallback<TGPendingConnections> returnCallback);

    /**
     * Create user using all user data
     *
     * @param user   User data
     * @param output return callback
     */
    void createUser(TGUser user, TGRequestCallback<TGUser> output);

    /**
     * Get users followed by current user
     *
     * @param output return callback
     */
    void getCurrentUserFollowed(TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Get followers for current user
     *
     * @param output return callback
     */
    void getCurrentUserFollowers(TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Get friends of current user
     *
     * @param output return callback
     */
    void getCurrentUserFriends(TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Get event by ID
     *
     * @param eventID event ID
     * @param output  return callback
     */
    void getEvent(Long eventID, TGRequestCallback<TGEvent> output);

    /**
     * Get event from selected user
     *
     * @param userId  user ID
     * @param eventId event ID
     * @param output  return callback
     */
    void getEvent(Long userId, Long eventId, TGRequestCallback<TGEvent> output);

    /**
     * Get all events from current user
     *
     * @param output return callback
     */
    void getEvents(TGRequestCallback<TGFeed> output);

    /**
     * Get all events from selected user
     *
     * @param userId user ID
     * @param output return callback
     */
    void getEvents(Long userId, TGRequestCallback<TGFeed> output);

    /**
     * Get feed of current user
     *
     * @param output return callback
     */
    void getFeed(TGRequestCallback<TGFeed> output);

    /**
     * Get count on current user feed
     *
     * @param output return callback
     */
    void getFeedCount(TGRequestCallback<TGFeedCount> output);

    /**
     * Get unread feed of current user
     *
     * @param output return callback
     */
    void getUnreadFeed(TGRequestCallback<TGFeed> output);

    /**
     * Get user by user ID
     *
     * @param id     user ID
     * @param output return callback
     */
    void getUserByID(Long id, TGRequestCallback<TGUser> output);

    /**
     * Get users followed by selected user
     *
     * @param userId id of user
     * @param output return callback
     */
    void getUserFollowed(Long userId, TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Get users following current user
     *
     * @param userId id of user
     * @param output return callback
     */
    void getUserFollowers(Long userId, TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Get friends of selected user
     *
     * @param userId id of user
     * @param output return callback
     */
    void getUserFriends(Long userId, TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Try to perform login
     *
     * @param user   User basic data
     * @param output return callback
     */
    void login(TGLoginUser user, TGRequestCallback<TGUser> output);

    /**
     * Try to perform logout
     *
     * @param output return callback
     */
    void logout(TGRequestCallback<Object> output);

    /**
     * Reject connection
     *
     * @param userId id of user the connection is confirmed for
     * @param type   the type of connection being confirmed
     * @param output return callback
     */
    void rejectConnection(Long userId, TGConnection.TGConnectionType type, TGRequestCallback<TGConnection> output);

    /**
     * Remove(cancel) connection
     *
     * @param userId id of user the connection is made with
     * @param type   type of connection
     * @param output return callback
     */
    void removeConnection(Long userId, TGConnection.TGConnectionType type, TGRequestCallback<Object> output);

    /**
     * Remove event of current user
     *
     * @param eventID ID of event to be removed
     * @param output  return callback
     */
    void removeEvent(Long eventID, TGRequestCallback<Object> output);

    /**
     * Remove user from server
     *
     * @param user   User data
     * @param output return callback
     */
    void removeUser(TGUser user, TGRequestCallback<Object> output);

    /**
     * Do a search query for users
     *
     * @param searchCriteria Search phrase
     * @param output         return callback
     */
    void search(String searchCriteria, TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Update social connections
     *
     * @param socialData social connections information
     * @param output     return callback
     */
    void socialConnections(TGSocialConnections socialData, TGRequestCallback<TGConnectionUsersList> output);

    /**
     * Update event of current user
     *
     * @param input  event to be updated
     * @param output return callback
     */
    void updateEvent(TGEvent input, TGRequestCallback<TGEvent> output);

    /**
     * Update user data on server
     *
     * @param user   User data
     * @param output return callback
     */
    void updateUser(TGUser user, TGRequestCallback<TGUser> output);
}
