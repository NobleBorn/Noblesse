package com.example.noblesse;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class Timer {

    public void timer(ITimer timers){
        int timeDuration = 2000; // Time duration in milliseconds

        Task<Void> timerTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(timeDuration); // Sleep for the specified duration
                } catch (InterruptedException e) {
                    System.out.println("Timer interrupted!");
                }
                return null;
            }
        };

        timerTask.setOnSucceeded(event -> {
            Platform.runLater(timers::afterTimer); // Execute the afterTimer method on the JavaFX Application Thread
        });

        timers.duringTimer(); // Execute the duringTimer method

        // Start a new thread to execute the timer task
        new Thread(timerTask).start();
    }
}
