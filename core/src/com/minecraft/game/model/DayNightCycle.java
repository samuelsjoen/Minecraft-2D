package com.minecraft.game.model;

import com.badlogic.gdx.utils.Timer;

/**
 * Represents a day-night cycle mechanism.
 * Provides functionality to simulate day and night cycles,
 * track the number of nights passed, and control cycle timing.
 * Extends {@link com.badlogic.gdx.utils.Timer.Task} to allow for scheduling.
 */
public class DayNightCycle extends Timer.Task {

    private boolean isNight;
    private int numberOfNights;
    private int numberOfRuns;

    /**
     * Constructs a new DayNightCycle with default values.
     * The cycle starts in the day.
     */
    public DayNightCycle() {
        isNight = false;
        numberOfNights = 0;
    }

    @Override
    public void run() {
        this.numberOfRuns++;
        // Change night to day or vice versa
        if (isNight) {
            isNight = false;
            numberOfNights++; // Increment the number when it becomes day
        } else {
            isNight = true;
        }
    }

    /**
     * Get the current time of day
     * @return true if it is night, false if it is day
     */
    public boolean getIsNight() {
        return isNight;
    }

    /**
     * Start the day-night cycle with the specified interval (in seconds)
     * @param interval The interval between day and night cycles
     */
    public void startCycle(float interval) {
        // Stop any existing cycles
        Timer.instance().clear();
        Timer.schedule(this, interval, interval);
    }

    /**
     * Pause the day-night cycle.
     * This method stops the current cycle until it is started again with the {@link #startCycle(float)} method.
     */
    public void pauseCycle() {
        Timer.instance().clear();   
    }

    /**
     * Get the number of nights that have passed
     * @return The number of nights that have passed
     */
    public int getNumberOfNights() {
        return numberOfNights; 
    }

    /**
     * Reset the number of nights that have passed
     */
    public void resetNumberOfNights() {
        numberOfNights = 0;
    }

    /**
     * Get the number of times run() has been called
     * @return An integer representing the number of times run() has been called
     */
    public int numberOfRuns() {
        return this.numberOfRuns;
    }

}
