package com.example.elasticsearchtest.core.base;

import java.io.Serializable;

public class Version implements Serializable {
    private static final long serialVersionUID = -8094319325751987537L;
    private static final int MAX_VERSION_BITS = 10;
    private static final int MAX_VERSION_VALUE = 1024;
    private static final int MAX_VERSION_VALUE_MASK = 1023;
    private static final int MAJOR = 1;
    private static final int MINOR = 0;
    private static final int REVISION = 0;
    private int major = 1;
    private int minor = 0;
    private int revision = 0;
    private String tag;

    public Version() {
    }
}