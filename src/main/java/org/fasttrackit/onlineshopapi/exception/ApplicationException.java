package org.fasttrackit.onlineshopapi.exception;

//  Nu poti crea o clasa ApplicationException pentru ca este o clasa abstracta
public abstract class ApplicationException extends Exception {

    private String code;

    public ApplicationException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "code='" + code + '\'' +
                "} " + super.toString();
    }
}