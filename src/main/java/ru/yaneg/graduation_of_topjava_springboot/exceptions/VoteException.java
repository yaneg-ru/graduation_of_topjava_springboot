package ru.yaneg.graduation_of_topjava_springboot.exceptions;

public class VoteException extends RuntimeException{

    private static final long serialVersionUID = 1440358151649795169L;

    public VoteException(String message)
    {
        super(message);
    }
}

