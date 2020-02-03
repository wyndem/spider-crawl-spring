package cn.wenhaha.spider.exceptions;

public class CrawlConflictException extends RuntimeException {

    public CrawlConflictException() {
        super();
    }

    public CrawlConflictException(String message) {
        super(message);
    }

    public CrawlConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlConflictException(Throwable cause) {
        super(cause);
    }


}
