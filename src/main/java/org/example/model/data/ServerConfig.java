package org.example.model.data;

public enum ServerConfig {
    ACCESS("https://accounts.spotify.com"), // connect server path
    REDIRECT_URI("http://localhost:8080/callback"), // local server address
    CLIENT_ID("770eec8dcb0e4bd7ab0c33a2f215cf20"), // user id
    CLIENT_SECRET("07c254298e094f038be8d49e3ac58339"), // test secret code of spotify app
    AUTH_CODE(""), // permission code
    API("https://api.spotify.com"), // API server path
    ACCESS_TOKEN(""); // token works as a permission for resources

    private String actual;

    ServerConfig(String data) {
        this.actual = data;
    }

    public static String buildAuthenticateLink() {
        return ACCESS.actual + "/authorize" + "?client_id=" + CLIENT_ID.actual +
                "&redirect_uri=" + REDIRECT_URI.actual + "&response_type=code";
    }

    public String get() {
        return actual;
    }

    public void set(String newValue) {
        this.actual = newValue;
    }

    @Override
    public String toString() {
        return actual;
    }
}
