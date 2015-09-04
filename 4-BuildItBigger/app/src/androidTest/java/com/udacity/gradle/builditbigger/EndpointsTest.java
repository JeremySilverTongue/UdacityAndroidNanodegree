package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class EndpointsTest extends ApplicationTestCase<Application> implements JokeReceiver {

//    public void testFail(){
//        fail("This is supposed to fail");
//    }

    public EndpointsTest() {
        super(Application.class);
    }

    CountDownLatch latch = null;
    String joke = null;

    public void testJokeEndpoint() {
        try {
            GetJokeTask jokeTask = new GetJokeTask();
            latch = new CountDownLatch(1);
            jokeTask.execute(this);
            latch.await(30, TimeUnit.SECONDS);
            assertNotNull("Joke was null",joke);
            assertFalse("Joke had zero length", joke.length() == 0);

        } catch (Exception e){
            fail("Timed out");
        }

    }

    @Override
    public void receiveJoke(String joke) {
        this.joke = joke;
        latch.countDown();

    }
}