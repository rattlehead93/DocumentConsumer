package com.document.document.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class DocumentRequest {
    @NotEmpty(message = "file content can not be empty")
    private String fileContent;

    @Max(value = 255, message = "fileName cannot be greater than 255 characters")
    @NotEmpty(message = "file name can not be empty")
    private String fileName;

    @NotEmpty(message = "file extension can not be empty")
    @Max(value = 4, message = "extension can not be greater than 4 characters.")
    private String extension;

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
