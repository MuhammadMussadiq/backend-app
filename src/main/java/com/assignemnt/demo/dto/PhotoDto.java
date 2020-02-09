package com.assignemnt.demo.dto;

public class PhotoDto {

    private Integer id;
    private String mimeType;
    private String name;
    private byte[] fileBytes;

    public PhotoDto() {
    }

    public PhotoDto(Integer id, String mimeType, String name, byte[] fileBytes) {
        this.id = id;
        this.mimeType = mimeType;
        this.name = name;
        this.fileBytes = fileBytes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
