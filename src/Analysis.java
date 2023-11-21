import java.util.ArrayList;
import java.util.List;

public class Analysis {
    private List<Integer> waitDurations;

    public Analysis() {
        waitDurations = new ArrayList<>();
    }

    public void addWaitDuration(int waitDuration) {
        waitDurations.add(waitDuration);
    }

    public double calculateAverageWaitDuration() {
        if (waitDurations.isEmpty()) {
            return 0.0;
        }

        int totalWaitTime = 0;
        for (int waitDuration : waitDurations) {
            totalWaitTime += waitDuration;
        }

        return (double) totalWaitTime / waitDurations.size();
    }

    public int findLongestWaitDuration() {
        if (waitDurations.isEmpty()) {
            return 0;
        }

        int longestWait = Integer.MIN_VALUE;
        for (int waitDuration : waitDurations) {
            longestWait = Math.max(longestWait, waitDuration);
        }

        return longestWait;
    }

    public int findShortestWaitDuration() {
        if (waitDurations.isEmpty()) {
            return 0;
        }

        int shortestWait = Integer.MAX_VALUE;
        for (int waitDuration : waitDurations) {
            shortestWait = Math.min(shortestWait, waitDuration);
        }

        return shortestWait;
    }
}
