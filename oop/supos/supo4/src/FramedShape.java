public class FramedShape extends Entity {
    private Shape mShape;
    public FramedShape(Shape s){
        mShape = s;
    }
    public Shape unFramed(){
        return mShape;
    }

    public void draw() {
        mShape.draw();
        // Draw frame around shape
    }
    public static void main(String[] args){
        FramedShape fs = new FramedShape(new Circle());
//        FramedShape fs2 = new FramedShape(fs); //error
    }
}
