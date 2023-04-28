package nl.sogyo.javaopdrachten.RayTracer;

public class LineParametricRep{
    Vector parameters;
    Vector tParameters=new Vector(0,0,0);

    public LineParametricRep(Vector startPoint, Vector endPoint){
        parameters = startPoint;
        tParameters.xCoordinate = endPoint.xCoordinate - startPoint.xCoordinate;
        tParameters.yCoordinate = endPoint.yCoordinate - startPoint.yCoordinate;
        tParameters.zCoordinate = endPoint.zCoordinate - startPoint.zCoordinate;

    }
}
    
    
