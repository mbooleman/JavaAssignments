package nl.sogyo.javaopdrachten.RayTracer;

final public class AngleUtility {
    
    private AngleUtility(){

    }
    
    public static double angleCalculator(LineParametricRep lineOne, LineParametricRep lineTwo){
        Vector directionOne = lineOne.tParameters;
        Vector directionTwo = lineTwo.tParameters;

        double dotProduct = 0;
        double distanceOneSquared = 0;
        double distanceTwoSquared = 0;
       
        dotProduct = directionOne.xCoordinate*directionTwo.xCoordinate+directionOne.yCoordinate*directionTwo.yCoordinate+directionOne.zCoordinate*directionTwo.zCoordinate;
           
           
        distanceOneSquared =  directionOne.xCoordinate*directionOne.xCoordinate+directionOne.yCoordinate*directionOne.yCoordinate+directionOne.zCoordinate*directionOne.zCoordinate;
        distanceTwoSquared = directionTwo.xCoordinate*directionTwo.xCoordinate+directionTwo.yCoordinate*directionTwo.yCoordinate+directionTwo.zCoordinate*directionTwo.zCoordinate;
        
        double distanceOne = Math.sqrt(distanceOneSquared);
        double distanceTwo = Math.sqrt(distanceTwoSquared);
        
        double angle = Math.acos(Math.abs(dotProduct)/(distanceOne*distanceTwo));
        angle = angle * (180/Math.PI);
        return angle;
    }

}
