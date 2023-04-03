package scrapper.Exceptions;

import scrapper.ScrapperApplication;

public class ScrapperBadRequestException extends IllegalArgumentException{
    public ScrapperBadRequestException(String mes){
        super(mes);
    }
}
