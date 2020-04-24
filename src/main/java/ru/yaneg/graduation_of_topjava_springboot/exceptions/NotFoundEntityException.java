package ru.yaneg.graduation_of_topjava_springboot.exceptions;

public class NotFoundEntityException extends RuntimeException{

    private static final long serialVersionUID = -4529460788784928201L;

    public NotFoundEntityException(String message)
    {
        super(message);
    }
}

