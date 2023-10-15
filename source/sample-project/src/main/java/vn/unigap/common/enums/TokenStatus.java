package vn.unigap.common.enums;

public enum TokenStatus {
    INIT(0),
    USED(1);

    private final int status;

    TokenStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
