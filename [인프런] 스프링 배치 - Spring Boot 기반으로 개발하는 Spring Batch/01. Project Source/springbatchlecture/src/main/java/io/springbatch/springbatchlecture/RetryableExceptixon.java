package io.springbatch.springbatchlecture;

public class RetryableExceptixon extends RuntimeException {
    public RetryableExceptixon() {
        super();
    }

    public RetryableExceptixon(String message) {
        super(message);
    }
}
