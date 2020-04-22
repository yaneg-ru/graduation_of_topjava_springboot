package ru.yaneg.graduation_of_topjava_springboot.exceptions;

public class ErrorMessage {
    private final String url;
    private final ErrorType type;
    private final String detail;

    public ErrorMessage(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public ErrorType getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }
}

