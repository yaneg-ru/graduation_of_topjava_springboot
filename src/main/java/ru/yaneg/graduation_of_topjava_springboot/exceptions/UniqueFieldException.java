package ru.yaneg.graduation_of_topjava_springboot.exceptions;

public class UniqueFieldException extends RuntimeException{

    private static final long serialVersionUID = 1348771109171435607L;

    public UniqueFieldException(String message)
    {
        super(message);
    }
}

