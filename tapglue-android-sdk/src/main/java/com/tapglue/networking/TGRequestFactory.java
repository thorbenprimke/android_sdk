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

import com.tapglue.Tapglue;
import com.tapglue.model.TGBaseObject;
import com.tapglue.model.TGConnection;
import com.tapglue.model.TGConnectionUsersList;
import com.tapglue.model.TGEvent;
import com.tapglue.model.TGFeed;
import com.tapglue.model.TGFeedCount;
import com.tapglue.model.TGLoginUser;
import com.tapglue.model.TGPendingConnections;
import com.tapglue.model.TGSearchCriteria;
import com.tapglue.model.TGSocialConnections;
import com.tapglue.model.TGUser;
import com.tapglue.networking.requests.TGRequestCallback;
import com.tapglue.networking.requests.TGRequestErrorType;
import com.tapglue.networking.requests.TGRequestType;

public class TGRequestFactory implements TGNetworkRequests {

    /**
     * Network manager
     */
    private final TGNetworkManager mNetworkManager;

    public TGRequestFactory(TGNetworkManager networkManager) {
        mNetworkManager = networkManager;
    }

    /**
     * Confirm connection
     *
     * @param userId id of the user the connection is confirmed for
     * @param type   the type of connection being confirmed
     * @param output result of the callback
     */
    @Override
    public void confirmConnection(Long userId, TGConnection.TGConnectionType type, TGRequestCallback<TGConnection> output) {
        if (Tapglue.user().getCurrentUser() == null) {
            output.onRequestError(new TGRequestErrorType(TGRequestErrorType.ErrorType.USER_NOT_LOGGED_IN));
            return;
        }
        TGConnection connection = new TGConnection()
            .setUserFromId(Tapglue.user().getCurrentUser().getID())
            .setState(TGConnection.TGConnectionState.CONFIRMED)
            .setUserToId(userId)
            .setType(type);
        createCreateObjectRequest(connection, false, output);
    }

    /**
     * Create connection
     *
     * @param userId user to which connection should be made
     * @param type   type of connection
     * @param state  state of connection
     * @param output return callback
     */
    @Override
    public void createConnection(Long userId, TGConnection.TGConnectionType type, String state, TGRequestCallback<TGConnection> output) {
        if (Tapglue.user().getCurrentUser() == null) {
            output.onRequestError(new TGRequestErrorType(TGRequestErrorType.ErrorType.USER_NOT_LOGGED_IN));
            return;
        }
        TGConnection connection = new TGConnection()
            .setUserFromId(Tapglue.user().getCurrentUser().getID())
            .setState(TGConnection.TGConnectionState.fromString(state))
            .setUserToId(userId)
            .setType(type);
        createCreateObjectRequest(connection, false, output);
    }

    /**
     * Create request for create method
     *
     * @param object            Object of request
     * @param canBeDoneOnlyLive Request should be done only if network is available?
     * @param output            Output callback
     * @param <T>               Type of object of request
     */
    private <T extends TGBaseObject> void createCreateObjectRequest(T object, boolean canBeDoneOnlyLive, TGRequestCallback<T> output) {
        mNetworkManager.performRequest(new TGRequest<>(object, TGRequestType.CREATE, canBeDoneOnlyLive, output));
    }

    /**
     * Create event for current user
     *
     * @param input  event to be created
     * @param output return callback
     */
    @Override
    public void createEvent(TGEvent input, TGRequestCallback<TGEvent> output) {
        createCreateObjectRequest(input, false, output);
    }

    /**
     * Create pending connection with selected type
     *
     * @param returnCallback return callback
     */
    @Override
    public void createPendingConnectionsRequest(TGRequestCallback<TGPendingConnections> returnCallback) {
        mNetworkManager.performRequest(new TGRequest<>(new TGPendingConnections(), TGRequestType.READ, true, returnCallback));
    }

    /**
     * Create request for read method
     *
     * @param object Object of request
     * @param output Output callback
     * @param <T>    Type of object of request
     */
    private <T extends TGBaseObject, TO extends TGBaseObject> void createReadObjectRequest(T object, TGRequestCallback<TO> output) {
        mNetworkManager.performRequest(new TGRequest<>(object, TGRequestType.READ, true, output));
    }

    /**
     * Create request for remove method
     *
     * @param object            Object of request
     * @param canBeDoneOnlyLive Request should be done only if network is available?
     * @param output            Output callback
     */
    private void createRemoveObjectRequest(TGBaseObject object, boolean canBeDoneOnlyLive, TGRequestCallback<Object> output) {
        mNetworkManager.performRequest(new TGRequest(object, TGRequestType.DELETE, canBeDoneOnlyLive, output));
    }

    /**
     * Create request for update method
     *
     * @param object Object of request
     * @param output Output callback
     * @param <T>    Type of object of request
     */
    private <T extends TGBaseObject> void createUpdateObjectRequest(T object, TGRequestCallback<T> output) {
        mNetworkManager.performRequest(new TGRequest<>(object, TGRequestType.UPDATE, false, output));
    }

    /**
     * Create user using all user data
     *
     * @param user   User data
     * @param output return callback
     */
    @Override
    public void createUser(TGUser user, TGRequestCallback<TGUser> output) {
        createCreateObjectRequest(user, true, output);
    }

    /**
     * Get users followed by current user
     *
     * @param output return callback
     */
    @Override
    public void getCurrentUserFollowed(TGRequestCallback<TGConnectionUsersList> output) {
        createReadObjectRequest(new TGConnection().setType(TGConnection.TGConnectionType.FOLLOW).setUserFromId(null), output);
    }

    /**
     * Get followers for current user
     *
     * @param output return callback
     */
    @Override
    public void getCurrentUserFollowers(TGRequestCallback<TGConnectionUsersList> output) {
        createReadObjectRequest(new TGConnection().setType(null).setUserFromId(null), output);
    }

    /**
     * Get friends of current user
     *
     * @param output return callback
     */
    @Override
    public void getCurrentUserFriends(TGRequestCallback<TGConnectionUsersList> output) {
        createReadObjectRequest(new TGConnection().setType(TGConnection.TGConnectionType.FRIEND).setUserFromId(null), output);
    }

    /**
     * Get event by ID
     *
     * @param eventID event ID
     * @param output  return callback
     */
    @Override
    public void getEvent(Long eventID, TGRequestCallback<TGEvent> output) {
        createReadObjectRequest(new TGEvent(null).setReadRequestObjectId(eventID), output);
    }

    /**
     * Get event from selected user
     *
     * @param userId  user ID
     * @param eventId event ID
     * @param output  return callback
     */
    @Override
    public void getEvent(Long userId, Long eventId, TGRequestCallback<TGEvent> output) {
        createReadObjectRequest(new TGEvent(null).setReadRequestUserId(userId).setReadRequestObjectId(eventId), output);
    }

    /**
     * Get all events from current user
     *
     * @param output return callback
     */
    @Override
    public void getEvents(TGRequestCallback<TGFeed> output) {
        createReadObjectRequest(new TGFeed().setIsFeed(false), output);
    }

    /**
     * Get all events from selected user
     *
     * @param userId user ID
     * @param output return callback
     */
    @Override
    public void getEvents(Long userId, TGRequestCallback<TGFeed> output) {
        createReadObjectRequest(new TGFeed().setIsFeed(false).setReadRequestUserId(userId), output);
    }

    /**
     * Get feed of current user
     *
     * @param output return callback
     */
    @Override
    public void getFeed(TGRequestCallback<TGFeed> output) {
        createReadObjectRequest(new TGFeed().setIsFeed(true), output);
    }

    /**
     * Get count on current user feed
     *
     * @param output return callback
     */
    @Override
    public void getFeedCount(TGRequestCallback<TGFeedCount> output) {
        createReadObjectRequest(new TGFeedCount(), output);
    }

    /**
     * Get unread feed of current user
     *
     * @param output return callback
     */
    @Override
    public void getUnreadFeed(TGRequestCallback<TGFeed> output) {
        createReadObjectRequest(new TGFeed().setIsFeed(true).setUnreadCount((long) 1), output);
    }

    /**
     * Get user by user ID
     *
     * @param id     user ID
     * @param output return callback
     */
    @Override
    public void getUserByID(Long id, TGRequestCallback<TGUser> output) {
        createReadObjectRequest(new TGUser().setReadRequestObjectId(id), output);
    }

    /**
     * Get users followed by selected user
     *
     * @param userId id of user
     * @param output return callback
     */
    @Override
    public void getUserFollowed(Long userId, TGRequestCallback<TGConnectionUsersList> output) {
        createReadObjectRequest(new TGConnection().setType(TGConnection.TGConnectionType.FOLLOW).setUserFromId(userId), output);
    }

    /**
     * Get users following current user
     *
     * @param userId id of user
     * @param output return callback
     */
    @Override
    public void getUserFollowers(Long userId, TGRequestCallback<TGConnectionUsersList> output) {
        createReadObjectRequest(new TGConnection().setType(null).setUserFromId(userId), output);
    }

    /**
     * Get friends of selected user
     *
     * @param userId id of user
     * @param output return callback
     */
    @Override
    public void getUserFriends(Long userId, TGRequestCallback<TGConnectionUsersList> output) {
        createReadObjectRequest(new TGConnection().setType(TGConnection.TGConnectionType.FRIEND).setUserFromId(userId), output);
    }

    /**
     * Try to perform login
     *
     * @param user   User basic data
     * @param output return callback
     */
    @Override
    public void login(TGLoginUser user, TGRequestCallback<TGUser> output) {
        mNetworkManager.performRequest(new TGRequest<>(user, TGRequestType.LOGIN, true, output));
    }

    /**
     * Try to perform logout
     *
     * @param output return callback
     */
    @Override
    public void logout(TGRequestCallback<Object> output) {
        mNetworkManager.performRequest(new TGRequest(null, TGRequestType.LOGOUT, true, output));
    }

    /**
     * Reject connection
     *
     * @param userId id of user the connection is confirmed for
     * @param type   the type of connection being confirmed
     * @param output return callback
     */
    @Override
    public void rejectConnection(Long userId, TGConnection.TGConnectionType type, TGRequestCallback<TGConnection> output) {
        if (Tapglue.user().getCurrentUser() == null) {
            output.onRequestError(new TGRequestErrorType(TGRequestErrorType.ErrorType.USER_NOT_LOGGED_IN));
            return;
        }
        TGConnection connection = new TGConnection()
            .setUserFromId(Tapglue.user().getCurrentUser().getID())
            .setType(type)
            .setState(TGConnection.TGConnectionState.REJECTED);
        createCreateObjectRequest(connection, false, output);
    }

    /**
     * Remove(cancel) connection
     *
     * @param userId id of user the connection is made with
     * @param type   type of connection
     * @param output return callback
     */
    @Override
    public void removeConnection(Long userId, TGConnection.TGConnectionType type, TGRequestCallback<Object> output) {
        if (Tapglue.user().getCurrentUser() == null) {
            output.onRequestError(new TGRequestErrorType(TGRequestErrorType.ErrorType.USER_NOT_LOGGED_IN));
            return;
        }
        TGConnection connection = new TGConnection()
            .setUserToId(userId)
            .setType(type)
            .setUserFromId(Tapglue.user().getCurrentUser().getID());
        createRemoveObjectRequest(connection, false, output);
    }

    /**
     * Remove event of current user
     *
     * @param eventID ID of event to be removed
     * @param output  return callback
     */
    @Override
    public void removeEvent(Long eventID, TGRequestCallback<Object> output) {
        createRemoveObjectRequest(new TGEvent(null).setReadRequestObjectId(eventID), false, output);
    }

    /**
     * Remove user from server
     *
     * @param user   User data
     * @param output return callback
     */
    @Override
    public void removeUser(TGUser user, TGRequestCallback<Object> output) {
        createRemoveObjectRequest(user, true, output);
    }

    /**
     * Do a search query for users
     *
     * @param searchCriteria Search phrase
     * @param output         return callback
     */
    @Override
    public void search(String searchCriteria, TGRequestCallback<TGConnectionUsersList> output) {
        mNetworkManager.performRequest(new TGRequest<>(new TGSearchCriteria().setSearchCriteria(searchCriteria), TGRequestType.SEARCH, true, output));
    }

    /**
     * Update social connections
     *
     * @param socialData social connections information
     * @param output     return callback
     */
    @Override
    public void socialConnections(TGSocialConnections socialData, TGRequestCallback<TGConnectionUsersList> output) {
        mNetworkManager.performRequest(new TGRequest<>(socialData, TGRequestType.UPDATE, true, output));
    }

    /**
     * Update event of current user
     *
     * @param input  event to be updated
     * @param output return callback
     */
    @Override
    public void updateEvent(TGEvent input, TGRequestCallback<TGEvent> output) {
        createUpdateObjectRequest(input, output);
    }

    /**
     * Update user data on server
     *
     * @param user   User data
     * @param output return callback
     */
    @Override
    public void updateUser(TGUser user, TGRequestCallback<TGUser> output) {
        createUpdateObjectRequest(user, output);
    }
}
