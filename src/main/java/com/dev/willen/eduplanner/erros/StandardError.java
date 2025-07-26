package com.dev.willen.eduplanner.erros;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class StandardError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime timestemp;
    private Integer Status;
    private String error;
    private String path;

    public StandardError() {
    }

    public StandardError(LocalDateTime timestemp, Integer status, String error, String path) {
        this.timestemp = timestemp;
        Status = status;
        this.error = error;
        this.path = path;
    }

    public LocalDateTime getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(LocalDateTime timestemp) {
        this.timestemp = timestemp;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
