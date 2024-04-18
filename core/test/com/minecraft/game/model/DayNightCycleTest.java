package com.minecraft.game.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.minecraft.game.LibgdxUnitTest;

// # TODO Gå over at antall sekunder passer med antall ganger run() blir kalt
public class DayNightCycleTest extends LibgdxUnitTest {

    DayNightCycle dayNightCycle = new DayNightCycle();

    @Test
    public void testRun() {
        // In the beginning, it should be day, aka. isNight = false
        assertTrue(dayNightCycle.getIsNight() == false);
        // Run the cycle 10 times
        for (int i = 0; i < 10; i++) {
            dayNightCycle.run();
            // If i is even, it should be night, if i is odd, it should be day
            if (i % 2 == 0) {
                assertTrue(dayNightCycle.getIsNight() == true);
            } else {
                assertTrue(dayNightCycle.getIsNight() == false);
            }
        }
    }

    @Test
    public void testStartCycle() {
        // Start the cycle with an interval of 1 second
        dayNightCycle.startCycle(1);
        // Wait for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*dag, natt, dag, natt, dag, natt */
        // The cycle should have run 5 times, so the number of entire nights survived should be 2??
        System.out.println(dayNightCycle.getNumberOfNights());
        assertTrue(dayNightCycle.getNumberOfNights() == 2);
    }

    @Test 
    public void testPauseCycle() {
        dayNightCycle.startCycle(1);
        // Wait for 2,5 seconds
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(dayNightCycle.numberOfRuns() == 2);
        // Pause the cycle
        dayNightCycle.pauseCycle();
        // Wait for 1 seconds
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // The cycle should not have run any more times
        assertTrue(dayNightCycle.numberOfRuns() == 2);
    }

    @Test
    public void testResetNumberOfNights() {
        dayNightCycle.startCycle(1);
        // Wait for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // The cycle should have run 3 times, so the number of entire nights survived should be 2??
        assertTrue(dayNightCycle.getNumberOfNights() == 1);
        // Reset the number of nights
        dayNightCycle.resetNumberOfNights();
        // The number of nights should now be 0
        assertTrue(dayNightCycle.getNumberOfNights() == 0);
    }
}