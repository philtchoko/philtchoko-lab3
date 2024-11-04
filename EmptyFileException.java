import java.io.IOException;

public class EmptyFileException extends IOException{
    public EmptyFileException(String text){
        super(text);
    }
}
