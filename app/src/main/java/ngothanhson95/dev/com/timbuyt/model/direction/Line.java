package ngothanhson95.dev.com.timbuyt.model.direction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Line  implements Serializable{

    public List<Agency> agencies = null;
    public String color;
    public String name;
    public String textColor;
    public Vehicle vehicle;

}
