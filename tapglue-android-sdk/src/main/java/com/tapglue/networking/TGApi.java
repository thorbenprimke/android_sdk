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

import android.support.annotation.NonNull;

import com.tapglue.Tapglue;
import com.tapglue.model.TGConnection;
import com.tapglue.model.TGConnectionUsersList;
import com.tapglue.model.TGEvent;
import com.tapglue.model.TGFeed;
import com.tapglue.model.TGFeedCount;
import com.tapglue.model.TGLoginUser;
import com.tapglue.model.TGPendingConnections;
import com.tapglue.model.TGSocialConnections;
import com.tapglue.model.TGUser;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

interface TGApi {

    @NonNull
    @PUT("me/connections")
    Call<TGConnection> createConnection(@Body TGConnection userAndType);

    @NonNull
    @POST("me/events")
    Call<TGEvent> createEvent(@Body TGEvent event);

    @NonNull
    @POST("users")
    Call<TGUser> createUser(@Body TGUser user);

    @NonNull
    @DELETE("me")
    Call<Object> deleteUser();

    @NonNull
    @GET("me/events/{id}")
    Call<TGEvent> getEvent(@Path("id") Long id);

    @NonNull
    @GET("users/{userId}/events/{id}")
    Call<TGEvent> getEvent(@Path("userId") Long userId, @Path("id") Long id);

    @NonNull
    @GET("me/events")
    Call<TGFeed> getEvents();

    @NonNull
    @GET("users/{userId}/events")
    Call<TGFeed> getEvents(@Path("userId") Long userId);

    @NonNull
    @GET("me/feed")
    Call<TGFeed> getFeed();

    @NonNull
    @GET("me/followers")
    Call<TGConnectionUsersList> getFollowed();

    @NonNull
    @GET("users/{id}/followed")
    Call<TGConnectionUsersList> getFollowedForUser(@Path("id") Long userId);

    @NonNull
    @GET("me/follows")
    Call<TGConnectionUsersList> getFollows();

    @NonNull
    @GET("users/{id}/follows")
    Call<TGConnectionUsersList> getFollowsForUser(@Path("id") Long userId);

    @NonNull
    @GET("me/friends")
    Call<TGConnectionUsersList> getFriends();

    @NonNull
    @GET("users/{id}/friends")
    Call<TGConnectionUsersList> getFriendsForUser(@Path("id") Long userId);

    @NonNull
    @GET("me/connections/pending")
    Call<TGPendingConnections> getPendingConnections();

    @NonNull
    @GET("me/feed/unread")
    Call<TGFeed> getUnreadFeed();

    @NonNull
    @GET("me/feed/unread/count")
    Call<TGFeedCount> getUnreadFeedCount();

    @NonNull
    @GET("users/{id}")
    Call<TGUser> getUser(@Path("id") Long id);

    @NonNull
    @POST("me/login")
    Call<TGUser> login(@Body TGLoginUser user);

    @NonNull
    @DELETE("me/logout")
    Call<Object> logout();

    @NonNull
    @DELETE("me/connections/{type}/{id}")
    Call<Object> removeConnection(@Path("id") Long userTo, @Path("type") String type);

    @NonNull
    @DELETE("me/events/{id}")
    Call<Object> removeEvent(@Path("id") Long id);

    @NonNull
    @GET("users/search")
    Call<TGConnectionUsersList> search(@Query("q") String criteria);

    @NonNull
    @POST("analytics")
    Call<Object> sendAnalytics();

    @NonNull
    @POST("me/connections/social")
    Call<TGConnectionUsersList> socialConnections(@Body TGSocialConnections connections);

    @NonNull
    @PUT("me/events/{id}")
    Call<TGEvent> updateEvent(@Path("id") Long id, @Body TGEvent event);

    @NonNull
    @PUT("me")
    Call<TGUser> updateUser(@Body TGUser user);
}
