package nl.sogyo.javaopdrachten.RayTracer;

final public class Intersection {
    Vector nearestPointLineOne;
    Vector nearestPointLineTwo;
    double intersectionAngle;
    boolean doesIntersect;

    private Intersection(Vector pointOne,Vector pointTwo, double angle,boolean doesIntersect){
        nearestPointLineOne = pointOne;
        nearestPointLineTwo = pointTwo;
        intersectionAngle = angle;
        this.doesIntersect = doesIntersect;
    }

    public static Intersection findIntersection(Line lineOne,Line lineTwo){
        lineOne.toParametric();
        lineTwo.toParametric();;
        double angle = AngleUtility.angleCalculator(lineOne.parametricLine,lineTwo.parametricLine);
        
        Intersection intersection = calculateNearestPoint(lineOne, lineTwo, angle);
        return intersection;
    }

    private static Intersection calculateNearestPoint(Line lineOne, Line lineTwo, double angle) {
        //source: http://paulbourke.net/geometry/pointlineplane/
        double d1343 = d1343(lineOne, lineTwo);
        double d4321 = d4321(lineOne, lineTwo);
        double d1321 = d1321(lineOne, lineTwo);
        double d4343 = d4343(lineTwo);
        double d2121 = d2121(lineOne);
        double mu_a = optimalMuA(d1343, d4321, d1321, d4343, d2121);
        double mu_b = OptimalMuB(d1343, d4321, d4343, mu_a);
        
        Vector P_a =  setNearestPoint(lineOne, mu_a);
       
        Vector P_b = setNearestPoint(lineTwo, mu_b);
        
        boolean hasIntersection = false;
        
        double distanceNearestPoint = distancePoints(P_a, P_b);
        if(distanceNearestPoint==0){
            hasIntersection=true;
        }
        Intersection intersection = new Intersection(P_a,P_b,angle,hasIntersection);
        return intersection;
    }

    private static double OptimalMuB(double d1343, double d4321, double d4343, double mu_a) {
        double mu_b = (d1343+mu_a*d4321)/d4343;
        return mu_b;
    }

    private static double optimalMuA(double d1343, double d4321, double d1321, double d4343, double d2121) {
        double mu_a = (d1343*d4321-d1321*d4343)/(d2121*d4343-d4321*d4321);
        return mu_a;
    }

    private static double d1343(Line lineOne, Line lineTwo) {
        double d1343 = (lineOne.startingPoint.xCoordinate-lineTwo.startingPoint.xCoordinate)*(lineTwo.endPoint.xCoordinate-lineTwo.startingPoint.xCoordinate) +
                        (lineOne.startingPoint.yCoordinate-lineTwo.startingPoint.yCoordinate)*(lineTwo.endPoint.yCoordinate-lineTwo.startingPoint.yCoordinate) +
                        (lineOne.startingPoint.zCoordinate-lineTwo.startingPoint.zCoordinate)*(lineTwo.endPoint.zCoordinate-lineTwo.startingPoint.zCoordinate);
        return d1343;
    }

    private static double d4321(Line lineOne, Line lineTwo) {
        double d4321 = (lineTwo.endPoint.xCoordinate-lineTwo.startingPoint.xCoordinate)*(lineOne.endPoint.xCoordinate-lineOne.startingPoint.xCoordinate)+
                        (lineTwo.endPoint.yCoordinate-lineTwo.startingPoint.yCoordinate)*(lineOne.endPoint.yCoordinate-lineOne.startingPoint.yCoordinate)+
                        (lineTwo.endPoint.zCoordinate-lineTwo.startingPoint.zCoordinate)*(lineOne.endPoint.zCoordinate-lineOne.startingPoint.zCoordinate);
        return d4321;
    }

    private static double d1321(Line lineOne, Line lineTwo) {
        double d1321 = (lineOne.startingPoint.xCoordinate-lineTwo.startingPoint.xCoordinate)*(lineOne.endPoint.xCoordinate-lineOne.startingPoint.xCoordinate)+
                        (lineOne.startingPoint.yCoordinate-lineTwo.startingPoint.yCoordinate)*(lineOne.endPoint.yCoordinate-lineOne.startingPoint.yCoordinate)+
                        (lineOne.startingPoint.zCoordinate-lineTwo.startingPoint.zCoordinate)*(lineOne.endPoint.zCoordinate-lineOne.startingPoint.zCoordinate);
        return d1321;
    }

    private static double d4343(Line lineTwo) {
        double d4343 = (lineTwo.endPoint.xCoordinate-lineTwo.startingPoint.xCoordinate)*(lineTwo.endPoint.xCoordinate-lineTwo.startingPoint.xCoordinate)+
                        (lineTwo.endPoint.yCoordinate-lineTwo.startingPoint.yCoordinate)*(lineTwo.endPoint.yCoordinate-lineTwo.startingPoint.yCoordinate)+
                        (lineTwo.endPoint.zCoordinate-lineTwo.startingPoint.zCoordinate)*(lineTwo.endPoint.zCoordinate-lineTwo.startingPoint.zCoordinate);
        return d4343;
    }

    private static double d2121(Line lineOne) {
        double d2121 = (lineOne.endPoint.xCoordinate-lineOne.startingPoint.xCoordinate)*(lineOne.endPoint.xCoordinate-lineOne.startingPoint.xCoordinate)+
                        (lineOne.endPoint.yCoordinate-lineOne.startingPoint.yCoordinate)*(lineOne.endPoint.yCoordinate-lineOne.startingPoint.yCoordinate)+
                        (lineOne.endPoint.zCoordinate-lineOne.startingPoint.zCoordinate)*(lineOne.endPoint.zCoordinate-lineOne.startingPoint.zCoordinate);
        return d2121;
    }

    public static double distancePoints(Vector P_a, Vector P_b) {
        
        double distance = Math.sqrt(Math.pow(P_a.xCoordinate-P_b.xCoordinate,2) + Math.pow(P_a.yCoordinate-P_b.yCoordinate,2) + Math.pow(P_a.zCoordinate-P_b.zCoordinate,2));
        return distance;
    }

    private static Vector setNearestPoint(Line lineTwo, double mu_b) {
        Vector P = new Vector(0,0,0);
        P.xCoordinate = lineTwo.startingPoint.xCoordinate+mu_b*(lineTwo.endPoint.xCoordinate-lineTwo.startingPoint.xCoordinate);
        P.yCoordinate = lineTwo.startingPoint.yCoordinate+mu_b*(lineTwo.endPoint.yCoordinate-lineTwo.startingPoint.yCoordinate);
        P.zCoordinate = lineTwo.startingPoint.zCoordinate+mu_b*(lineTwo.endPoint.zCoordinate-lineTwo.startingPoint.zCoordinate);
        return P;
    }

}
