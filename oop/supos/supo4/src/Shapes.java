import java.util.ArrayList;

public class Shapes extends Shape{
    private ArrayList<Shape> mShapes = new ArrayList<>();

    public void addShape(Shape s){
        mShapes.add(s);
    }

    @Override
    public void draw() {
        for(Shape s : mShapes){
            s.draw();
        }
    }
}
