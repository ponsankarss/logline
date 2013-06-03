package com.vijayrc.supportguy.domain;

public enum ExitStatus {
    SUCCESS("Done") {
        @Override
        boolean apply(int exitCode) {
            return exitCode == 0;
        }
    }, ERROR("Done with error") {
        @Override
        boolean apply(int exitCode) {
            return exitCode > 0;
        }
    }, NONE("Done with exit status") {
        @Override
        boolean apply(int exitCode) {
            return exitCode < 0;
        }
    };

    private String message;

    ExitStatus(String message) {
        this.message = message;
    }

    abstract boolean apply(int exitCode);

    public static ExitStatus getFor(int exitCode) {
        for (ExitStatus status : ExitStatus.values())
            if (status.apply(exitCode))
                return status;
        return null;
    }

    public String message(){
        return message;
    }
}
