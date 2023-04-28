package nl.sogyo.javaopdrachten.RayTracer;

public class Line {
    Vector startingPoint;
    Vector endPoint;
    LineParametricRep parametricLine;

    public Line(Vector start, Vector end){
        startingPoint = start;
        endPoint = end;
    }

    public void toParametric(){
        LineParametricRep parametricLineRep = new LineParametricRep(startingPoint, endPoint);
        parametricLine = parametricLineRep;
    }
}
