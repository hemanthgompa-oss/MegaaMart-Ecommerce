package com.megamart.megamart.Entity;

public class CustomerResponse {

    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String address;

    public CustomerResponse(Long id, String fullName, String email,
                            String mobileNumber, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }
}