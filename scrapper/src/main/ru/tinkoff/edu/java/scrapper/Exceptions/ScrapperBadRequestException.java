package scrapper.Exceptions;

public class ScrapperBadRequestException extends IllegalArgumentException {
    public ScrapperBadRequestException(String mes) {
        super(mes);
    }
}
