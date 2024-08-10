package com.thesis.mainapp.config;

public class TimeUnitConverter {

    public static int hoursToMinutes(int hours) {
        return hours * 60;
    }

    public static double minutesToHours(int minutes) {
        return minutes / 60.0;
    }

    public static int minutesToSeconds(int minutes) {
        return minutes * 60;
    }

    public static double secondsToMinutes(int seconds) {
        return seconds / 60.0;
    }

    public static int hoursToSeconds(int hours) {
        return hours * 3600;
    }

    public static double secondsToHours(int seconds) {
        return seconds / 3600.0;
    }

    /**
     * Alters the value by considering both time units
     * (time unit of resource, and time unit of usage) to seconds
     * @param fromTime
     * @param toTime
     * @param value
     * @return
     */
    public static double convert(String fromTime, String toTime, double value) {
        if (fromTime.equals(toTime)) {
            return value;
        }
        double valueInSeconds;
        switch (fromTime) {
            case "hours" , "hour":
                valueInSeconds = value * 3600;
                break;
            case "minutes","minute":
                valueInSeconds = value * 60;
                break;
            case "seconds","second":
                valueInSeconds = value;
                break;
            default:
                throw new IllegalArgumentException("Unknown fromUnit: " + fromTime);
        }
        switch (toTime) {
            case "hours" , "hour":
                return valueInSeconds / 3600;
            case "minutes","minute":
                return valueInSeconds / 60;
            case "seconds","second":
                return valueInSeconds;
            default:
                throw new IllegalArgumentException("Unknown toUnit: " + toTime);
        }
    }
}

