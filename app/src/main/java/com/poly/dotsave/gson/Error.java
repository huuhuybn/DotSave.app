
package com.poly.dotsave.gson;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Error {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
