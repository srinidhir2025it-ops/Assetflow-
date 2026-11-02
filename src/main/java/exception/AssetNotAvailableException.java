package com.assetflow.exception;

public class AssetNotAvailableException extends RuntimeException {

    public AssetNotAvailableException(String message) {
        super(message);
    }
}