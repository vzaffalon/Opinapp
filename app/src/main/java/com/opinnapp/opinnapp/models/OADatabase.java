package com.opinnapp.opinnapp.models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cayke on 26/05/17.
 */

public class OADatabase {
    //region USER
    public static boolean createUser (OAUser user) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference newUserRef = usersRef.push();

        user.setId(newUserRef.getKey());
        newUserRef.setValue(user.firebaseRepresentation());
        return true;
    }

    //for login
    public static void getUserWithFacebookID(String id, final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        Query queryRef = usersRef.orderByChild("facebookID").equalTo(id);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OAUser user = null;
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    user = userSnap.getValue(OAUser.class);
                    callback.onSuccess(user);
                }
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    public static void getUserWithID(String id, final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user/" + id);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OAUser user = dataSnapshot.getValue(OAUser.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    public static void getAllUsers(final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                OAUser user = dataSnapshot.getValue(OAUser.class);
                callback.onSuccess(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }
    //endregion

    //region Story
    public static boolean createStory(OAStory story) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        DatabaseReference newStoryRef = storyRef.push();

        story.setId(newStoryRef.getKey());
        newStoryRef.setValue(story.firebaseRepresentation());

        if (story instanceof OAStoryMultiChoiceImages) {
            OAStoryMultiChoiceImages storyMultiChoiceImages = (OAStoryMultiChoiceImages) story;
            for (int i = 0; i < storyMultiChoiceImages.getImages().size(); i++)
                uploadImage(storyMultiChoiceImages.getImages().get(i), storyMultiChoiceImages, i);
        }
        return true;
    }

    public static void getAllStories(final OAFirebaseCallback callback) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OAStory> stories = new ArrayList<OAStory>();

                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    String type = (String) userSnap.child("type").getValue();

                    if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                        OAStoryMultiChoiceImages story = userSnap.getValue(OAStoryMultiChoiceImages.class);
                        story.setObjectsValuesWithFirebaseIds();
                        stories.add(story);
                    }
                    else if (type != null && type.equals("OAStoryTextOnly")) {
                        OAStoryTextOnly story = userSnap.getValue(OAStoryTextOnly.class);
                        story.setObjectsValuesWithFirebaseIds();
                        stories.add(story);
                    }
                    else {
                        OAStory story = userSnap.getValue(OAStory.class);
                        story.setObjectsValuesWithFirebaseIds();
                        stories.add(story);
                    }
                }

                callback.onSuccess(stories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    public static void getStoriesFromUser(OAUser user, final OAFirebaseCallback callback) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story");
        Query queryRef = storyRef.orderByChild("ownerID").equalTo(user.getId());

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String type = (String) dataSnapshot.child("type").getValue();

                if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                    OAStoryMultiChoiceImages story = dataSnapshot.getValue(OAStoryMultiChoiceImages.class);
                    story.setObjectsValuesWithFirebaseIds();
                    callback.onSuccess(story);
                }
                else if (type != null && type.equals("OAStoryTextOnly")) {
                    OAStoryTextOnly story = dataSnapshot.getValue(OAStoryTextOnly.class);
                    story.setObjectsValuesWithFirebaseIds();
                    callback.onSuccess(story);
                }
                else {
                    OAStory story = dataSnapshot.getValue(OAStory.class);
                    story.setObjectsValuesWithFirebaseIds();
                    callback.onSuccess(story);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    private static void getStoryWithID(String id, final OAFirebaseCallback callback) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story/" + id);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = (String) dataSnapshot.child("type").getValue();

                if (type != null && type.equals("OAStoryMultiChoiceImages")) {
                    OAStoryMultiChoiceImages story = dataSnapshot.getValue(OAStoryMultiChoiceImages.class);
                    story.setObjectsValuesWithFirebaseIds();
                    callback.onSuccess(story);
                }
                else if (type != null && type.equals("OAStoryTextOnly")) {
                    OAStoryTextOnly story = dataSnapshot.getValue(OAStoryTextOnly.class);
                    story.setObjectsValuesWithFirebaseIds();
                    callback.onSuccess(story);
                }
                else {
                    OAStory story = dataSnapshot.getValue(OAStory.class);
                    story.setObjectsValuesWithFirebaseIds();
                    callback.onSuccess(story);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    private static void updateStory(OAStory story) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("story/" + story.getId());
        storyRef.setValue(story.firebaseRepresentation());
    }
    //endregion

    //region Comment
    public static boolean createComment(OAComment comment) {
        DatabaseReference storyRef = FirebaseDatabase.getInstance().getReference("comment");
        DatabaseReference newStoryRef = storyRef.push();

        comment.setId(newStoryRef.getKey());
        newStoryRef.setValue(comment.firebaseRepresentation());

        return true;
    }

    public static void getCommentsWithStoryID(String id, final OAFirebaseCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("comment");
        Query queryRef = usersRef.orderByChild("storyID").equalTo(id);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OAComment> comments = new ArrayList<OAComment>();
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    OAComment comment  = userSnap.getValue(OAComment.class);
                    comment.setObjectsValuesWithFirebaseIds();
                    comments.add(comment);
                }
                callback.onSuccess(comments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }
    //endregion

    //region ImageOption
    public static void getImageOptionWithID(String id, final OAFirebaseCallback callback) {
        DatabaseReference imagesRef = FirebaseDatabase.getInstance().getReference("imageOption/" + id);

        imagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OAImageOption image =  dataSnapshot.getValue(OAImageOption.class);
                image.setObjectsValuesWithFirebaseIds();
                callback.onSuccess(image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }


    private static void addImageOptionToStory(String imagePath, OAStoryMultiChoiceImages story) {
        String imageOptionID = createImageOption(imagePath, story.getId());

        if (story.getImagesIds() == null)
            story.setImagesIds(new ArrayList<String>());

        story.getImagesIds().add(imageOptionID);

        updateStory(story);
    }

    private static String createImageOption(String imagePath, String storyID) {
        OAImageOption imageOption = new OAImageOption();
        imageOption.setImagePath(imagePath);
        imageOption.setStoryID(storyID);

        DatabaseReference imageOptionRef = FirebaseDatabase.getInstance().getReference("imageOption");
        DatabaseReference newImageOptionRef = imageOptionRef.push();

        imageOption.setId(newImageOptionRef.getKey());
        newImageOptionRef.setValue(imageOption.firebaseRepresentation());

        return imageOption.getId();
    }

    public static void likeOption(boolean like, OAImageOption imageOption, OAUser user) {
        if (like)
            addLike(imageOption, user);
        else
            removeLike(imageOption, user);
    }

    private static void removeLike(OAImageOption imageOption, OAUser user) {
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("likeImage/" + imageOption.getId());
        likeRef.child(user.getId()).removeValue();
        imageOption.isLiked = false;
    }

    private static void addLike(OAImageOption imageOption, OAUser user) {
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("likeImage/" + imageOption.getId());
        likeRef.child(user.getId()).setValue(true);
        imageOption.isLiked = true;
    }

    public static void getLikesForImageOption(OAImageOption imageOption, final OAFirebaseCallback callback) {
        final DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("likeImage/" + imageOption.getId());
        likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> likes = new ArrayList<String>();
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    likes.add(userSnap.getKey());
                }
                callback.onSuccess(likes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    //endregion

    //region Images
    public static void uploadImage(OAImageOption imageOption, final OAStoryMultiChoiceImages story, int index) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://opinapp-c6381.appspot.com");
        StorageReference imagesRef = storageRef.child("OAImageOption/" + story.getId() + "/" + index);

        Uri uri = Uri.fromFile(new File(imageOption.getImagePath()));
        UploadTask uploadTask = imagesRef.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.print("erro");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                addImageOptionToStory(downloadUrl.toString(), story);
            }
        });
    }


    //endregion

    //region Bookmarks
    public static void favoriteStory(boolean favorite, OAStory story, OAUser user) {
        if (favorite)
            addStoryToBookmark(story, user);
        else
            removeStoryFromBookmark(story, user);
    }

    private static void removeStoryFromBookmark(OAStory story, OAUser user) {
        DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmark/" + user.getId());
        bookmarkRef.child(story.getId()).removeValue();
        story.isBookmarked = false;
    }

    private static void addStoryToBookmark(OAStory story, OAUser user) {
        DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmark/" + user.getId());
        bookmarkRef.child(story.getId()).setValue(true);
        story.isBookmarked = true;
    }

    public static void getIfStoryIsBookmarked(final OAStory story, OAUser user) {
        DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmark/" + user.getId());
        DatabaseReference specificBookmarkRef = bookmarkRef.child(story.getId());
        specificBookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                story.isBookmarked = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                story.isBookmarked = false;
            }
        });
    }

    public static void getBookmarksFromUser(OAUser user, final OAFirebaseCallback callback) {
        DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmark/" + user.getId());
        bookmarkRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String storyID = dataSnapshot.getKey();
                getStoryWithID(storyID, new OAFirebaseCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        callback.onSuccess(object);
                    }

                    @Override
                    public void onFailure(DatabaseError databaseError) {
                        callback.onFailure(databaseError);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    //endregion

    //region Likes
    public static void likeStory(boolean like, OAStory story, OAUser user) {
        if (like)
            addLike(story, user);
        else
            removeLike(story, user);
    }

    private static void removeLike(OAStory story, OAUser user) {
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("like/" + story.getId());
        likeRef.child(user.getId()).removeValue();
        story.isLiked = false;
    }

    private static void addLike(OAStory story, OAUser user) {
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("like/" + story.getId());
        likeRef.child(user.getId()).setValue(true);
        story.isLiked = true;
    }

    public static void getLikesForStory(OAStory story, final OAFirebaseCallback callback) {
        final DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("like/" + story.getId());
        likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> likes = new ArrayList<String>();
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    likes.add(userSnap.getKey());
                }
                callback.onSuccess(likes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    //endregion

    //region Dislikes
    public static void dislikeStory(boolean like, OAStory story, OAUser user) {
        if (like)
            addDisLike(story, user);
        else
            removeDisLike(story, user);
    }

    private static void removeDisLike(OAStory story, OAUser user) {
        DatabaseReference dislikeRef = FirebaseDatabase.getInstance().getReference("dislike/" + story.getId());
        dislikeRef.child(user.getId()).removeValue();
        story.isDisliked = false;
    }

    private static void addDisLike(OAStory story, OAUser user) {
        DatabaseReference dislikeRef = FirebaseDatabase.getInstance().getReference("dislike/" + story.getId());
        dislikeRef.child(user.getId()).setValue(true);
        story.isDisliked = true;
    }

    public static void getDislikesForStory(OAStory story, final OAFirebaseCallback callback) {
        final DatabaseReference dislikeRef = FirebaseDatabase.getInstance().getReference("dislike/" + story.getId());
        dislikeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> likes = new ArrayList<String>();
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    likes.add(userSnap.getKey());
                }
                callback.onSuccess(likes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError);
            }
        });
    }

    //endregion

}