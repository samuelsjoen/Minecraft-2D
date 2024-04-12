package com.minecraft.game.model;

import com.badlogic.gdx.utils.Timer;

public class DayNightCycle extends Timer.Task {

    private boolean isNight;
    private int numberOfNights;

    public DayNightCycle() {
        isNight = false;
        numberOfNights = 0;
    }

    @Override
    public void run() {
        // Change the time of day
        if (isNight) {
            isNight = false;
            numberOfNights++; // Increment the number when it becomes day
            //System.out.println("It is now day, number of survived nights: " + numberOfNights);
        } else {
            isNight = true;
        }

        //System.out.println("DayNightCycle run, it is now " + (isNight ? "night" : "day"));
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

}
