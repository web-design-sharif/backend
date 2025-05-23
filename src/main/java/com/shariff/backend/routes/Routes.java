package com.shariff.backend.routes;

public final class Routes {
    public static final String API = "/api";
    public static final String ROOT = "/";
    public static final String ERROR = "/error";


    public static final class Auth {
        public static final String BASE = API + "/auth";
        public static final String SIGN_UP = "/signup";
        public static final String SIGN_IN = "/signin";
        
        private Auth() {}
    }

    public static final class Users {
        public static final String BASE = API + "/users";
        public static final String ME = "/me";
        
        private Users() {}
    }

    private Routes() {} 
} 