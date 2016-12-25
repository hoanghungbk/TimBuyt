package ngothanhson95.dev.com.timbuyt.model.direction;

import java.io.Serializable;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Distance implements Serializable{

    public String text;
    public int value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
