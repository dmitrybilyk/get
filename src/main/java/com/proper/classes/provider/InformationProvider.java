package com.proper.classes.provider;

public class InformationProvider {

    public static InformationProvider getInstance() {
        return SingletonHolder.getInstance();
    }

    private static class SingletonHolder {

        private static final InformationProvider INSTANCE = new InformationProvider("Some name");
        public static InformationProvider getInstance() {
            return INSTANCE;
        }
    }

    private String name;

    private InformationProvider(String name) {}

    public String getName() {
        return name;
    }

}
