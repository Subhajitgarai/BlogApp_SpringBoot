package com.blog.security;

import lombok.Builder;

@Builder
public class JWTResponse {
    private String jwtToken;
    private String useername;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUseername() {
        return useername;
    }

    public void setUseername(String useername) {
        this.useername = useername;
    }

    @Override
    public String toString() {
        return "JWTResponse [jwtToken=" + jwtToken + ", useername=" + useername + "]";
    }

    public JWTResponse(String jwtToken, String useername) {
        super();
        this.jwtToken = jwtToken;
        this.useername = useername;
    }

    public JWTResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

}
