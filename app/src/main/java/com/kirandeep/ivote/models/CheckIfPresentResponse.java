package com.kirandeep.ivote.models;

/**
 * Created by abc on 07-05-2018.
 */

public class CheckIfPresentResponse {

        private Integer errorCode;
        private Object errDesc;
        private EntryAadharData data;

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public Object getErrDesc() {
            return errDesc;
        }

        public void setErrDesc(Object errDesc) {
            this.errDesc = errDesc;
        }

    public EntryAadharData getData() {
        return data;
    }

    public void setData(EntryAadharData data) {
        this.data = data;
    }
}

