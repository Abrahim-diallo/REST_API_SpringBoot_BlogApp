package net.sencodester.springboot.payload;

public class JWTAuthResponse{
    private String accessToken;
    private String tokenType;

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    // getter and setter methods
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


}
