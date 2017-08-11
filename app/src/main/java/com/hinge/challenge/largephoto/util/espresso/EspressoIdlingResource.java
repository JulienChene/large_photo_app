package com.hinge.challenge.largephoto.util.espresso;

import android.support.test.espresso.IdlingResource;

/**
 * Fetched from Google's samples Android Architecture repo:
 * https://github.com/googlesamples/android-architecture/blob/todo-mvp-rxjava/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/util/EspressoIdlingResource.java
 */
public class EspressoIdlingResource
{

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}
