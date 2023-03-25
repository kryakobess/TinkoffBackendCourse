package scrapper.Exceptions;

public class ScrapperNotFoundException extends IllegalStateException{
    public ScrapperNotFoundException(String mes){
        super(mes);
    }
}
