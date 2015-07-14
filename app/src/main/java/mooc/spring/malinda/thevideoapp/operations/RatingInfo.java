package mooc.spring.malinda.thevideoapp.operations;

public class RatingInfo {
    private long serverVideoId;
    private float newRatings;

    public long getServerVideoId() {
        return serverVideoId;
    }

    public void setServerVideoId(long serverVideoId) {
        this.serverVideoId = serverVideoId;
    }

    public float getNewRatings() {
        return newRatings;
    }

    public void setNewRatings(float newRatings) {
        this.newRatings = newRatings;
    }
}
