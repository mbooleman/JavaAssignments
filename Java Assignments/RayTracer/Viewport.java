package nl.sogyo.javaopdrachten.RayTracer;

public class Viewport {
    Vector cornerOne;
    Vector cornerTwo;
    Vector cornerThree;
    Vector cornerFour;
    Line verticeOne;
    Line verticeTwo;
    Line verticeThree;
    Line verticeFour;
    Line xAxis;
    Line yAxis;
    

    public Viewport(Line verticesOne,Line verticesTwo,Line verticesThree){
        this.verticeOne = verticesOne;
        this.verticeTwo = verticesTwo;
        this.verticeThree = verticesThree;
        cornerOne = verticesOne.startingPoint;
        cornerTwo = verticesOne.endPoint;
        if(cornerOne != verticesTwo.startingPoint && cornerTwo != verticesTwo.startingPoint){
            cornerThree = verticesTwo.startingPoint;
        } else if(cornerOne != verticesTwo.endPoint && cornerTwo != verticesTwo.endPoint){
            cornerThree = verticesTwo.endPoint;
        }
        if(cornerOne != verticesThree.startingPoint && cornerTwo != verticesThree.startingPoint && cornerThree != verticesThree.startingPoint){
            cornerFour = verticesThree.startingPoint;
        } else if(cornerOne != verticesThree.endPoint && cornerTwo != verticesThree.endPoint && cornerThree != verticesThree.endPoint){
            cornerFour = verticesThree.endPoint;
        }
        Line checkLine23 = new Line(cornerTwo,cornerThree);
        Line checkLine34 = new Line(cornerThree,cornerFour);
        if(verticesTwo != checkLine23 && verticesThree != checkLine23){
            this.verticeFour = checkLine23;
        } else if (verticesTwo != checkLine34 && verticesThree != checkLine34){
            this.verticeFour = checkLine34;
        }
        this.xAxis = verticesOne;
        Intersection intersection12 = Intersection.findIntersection(verticesOne,verticesTwo);
        Intersection intersection13 = Intersection.findIntersection(verticesOne,verticesThree);
        if(intersection12.intersectionAngle==90){
            this.yAxis = verticesTwo;
        } else if(intersection13.intersectionAngle==90){
            this.yAxis = verticesThree;
        }
    }

    public Vector getVector(Coordinate coord){
        Vector pixelVector = new Vector(0,0,0);
        Vector planeVector = getPlaneVector();
        double zAngle = getZAngle(planeVector);
        double xAngle = getXAngle(planeVector);
        double yAngle = getYAngle(planeVector);
        double sqrtXY = Math.sqrt(Math.pow(coord.xCoordinate,2)+Math.pow(coord.yCoordinate,2));
        double zChange = Math.sin(zAngle)*sqrtXY;
        double xChange = Math.sin(yAngle)*Math.sqrt(Math.pow(sqrtXY,2.0)-Math.pow(zChange,2.0));
        double yChange = Math.sin(xAngle)*Math.sqrt(Math.pow(sqrtXY,2.0)-Math.pow(zChange,2.0));
        pixelVector.xCoordinate = verticeOne.startingPoint.xCoordinate+xChange; 
        pixelVector.yCoordinate = verticeOne.startingPoint.yCoordinate+yChange; 
        pixelVector.zCoordinate = verticeOne.startingPoint.zCoordinate+zChange; 
        return pixelVector;
    }

    public Vector getPlaneVector() {
        Vector planeOne = new Vector(verticeOne.startingPoint.xCoordinate-verticeOne.endPoint.xCoordinate,verticeOne.startingPoint.yCoordinate-verticeOne.endPoint.yCoordinate,verticeOne.startingPoint.zCoordinate-verticeOne.endPoint.zCoordinate);
        Vector planeTwo = new Vector(verticeTwo.startingPoint.xCoordinate-verticeTwo.endPoint.xCoordinate,verticeTwo.startingPoint.yCoordinate-verticeTwo.endPoint.yCoordinate,verticeTwo.startingPoint.zCoordinate-verticeTwo.endPoint.zCoordinate);
        double xCoordinate = planeOne.yCoordinate*planeTwo.zCoordinate-planeOne.zCoordinate*planeTwo.yCoordinate;
        double yCoordinate = planeOne.xCoordinate*planeTwo.zCoordinate-planeOne.zCoordinate*planeTwo.xCoordinate;
        double zCoordinate = planeOne.xCoordinate*planeTwo.yCoordinate-planeOne.yCoordinate*planeTwo.xCoordinate;
        Vector crossProduct = new Vector(xCoordinate,yCoordinate,zCoordinate);
        
        double lengthCrossProduct = Math.sqrt(Math.pow(crossProduct.xCoordinate,2)+Math.pow(crossProduct.yCoordinate,2)+Math.pow(crossProduct.zCoordinate,2));
        
        Vector normalVector = new Vector(crossProduct.xCoordinate/lengthCrossProduct,crossProduct.yCoordinate/lengthCrossProduct,crossProduct.zCoordinate/lengthCrossProduct);
        
        return normalVector;
    }

    public Vector getPlaneNormalVector() {
        Vector planeOne = new Vector(verticeOne.startingPoint.xCoordinate-verticeOne.endPoint.xCoordinate,verticeOne.startingPoint.yCoordinate-verticeOne.endPoint.yCoordinate,verticeOne.startingPoint.zCoordinate-verticeOne.endPoint.zCoordinate);
        Vector planeTwo = new Vector(verticeTwo.startingPoint.xCoordinate-verticeTwo.endPoint.xCoordinate,verticeTwo.startingPoint.yCoordinate-verticeTwo.endPoint.yCoordinate,verticeTwo.startingPoint.zCoordinate-verticeTwo.endPoint.zCoordinate);
        double xCoordinate = planeOne.yCoordinate*planeTwo.zCoordinate-planeOne.zCoordinate*planeTwo.yCoordinate;
        double yCoordinate = planeOne.xCoordinate*planeTwo.zCoordinate-planeOne.zCoordinate*planeTwo.xCoordinate;
        double zCoordinate = planeOne.xCoordinate*planeTwo.yCoordinate-planeOne.yCoordinate*planeTwo.xCoordinate;
        Vector crossProduct = new Vector(xCoordinate,yCoordinate,zCoordinate);
        
        return crossProduct;
    }

    public double getNormalK(Vector normalVector){
        double normalK = verticeOne.startingPoint.xCoordinate*normalVector.xCoordinate+verticeOne.startingPoint.yCoordinate*normalVector.yCoordinate+verticeOne.startingPoint.zCoordinate*normalVector.zCoordinate;
        return normalK;
    }

    public boolean isInDirectionOfPlane(Vector normalVector, double kNormal, LineParametricRep line){
        boolean isInDirection = true;
        double t = (kNormal-(normalVector.xCoordinate*line.parameters.xCoordinate+normalVector.yCoordinate*line.parameters.yCoordinate+normalVector.zCoordinate*line.parameters.zCoordinate))/(normalVector.xCoordinate*line.tParameters.xCoordinate+normalVector.yCoordinate*line.tParameters.yCoordinate+normalVector.zCoordinate*line.tParameters.zCoordinate);
        Vector tLine = new Vector(line.parameters.xCoordinate+line.tParameters.xCoordinate*t,line.parameters.yCoordinate+line.tParameters.yCoordinate*t,line.parameters.zCoordinate+line.tParameters.zCoordinate*t);
        double tPlane = tLine.xCoordinate*normalVector.xCoordinate+tLine.yCoordinate*normalVector.yCoordinate+tLine.zCoordinate*normalVector.zCoordinate;
        // System.out.println(tPlane);
        // System.out.println(kNormal);
        if(tPlane - kNormal > 1){
            isInDirection = false;
        }
        return isInDirection;
    }

    private double getYAngle(Vector planeVector) {
        double cosine = planeVector.yCoordinate/(Math.pow(planeVector.xCoordinate,2)+Math.pow(planeVector.yCoordinate,2)+Math.pow(planeVector.zCoordinate,2));
        double yAngle = Math.acos(cosine);
        return yAngle;
    }


    private double getXAngle(Vector planeVector) {
        double cosine = planeVector.xCoordinate/(Math.pow(planeVector.xCoordinate,2)+Math.pow(planeVector.yCoordinate,2)+Math.pow(planeVector.zCoordinate,2));
        double xAngle = Math.acos(cosine);
        return xAngle;
        
    }

    private double getZAngle(Vector planeVector) {
        double cosine = planeVector.zCoordinate/(Math.pow(planeVector.xCoordinate,2)+Math.pow(planeVector.yCoordinate,2)+Math.pow(planeVector.zCoordinate,2));
        double zAngle = Math.acos(cosine);
        return zAngle;
    }

    public double[] getCoordinateLengths() {
        double[] XY = new double[2];
        double distanceVerticeOne = Intersection.distancePoints(verticeOne.startingPoint,verticeOne.endPoint);
        double distanceVerticeTwo = Intersection.distancePoints(verticeTwo.startingPoint,verticeTwo.endPoint);
        double distanceVerticeThree = Intersection.distancePoints(verticeThree.startingPoint,verticeThree.endPoint);
        double distanceVerticeFour = Intersection.distancePoints(verticeFour.startingPoint,verticeFour.endPoint);
        XY[0]=distanceVerticeOne;
        if(distanceVerticeOne == distanceVerticeTwo){
            XY[1]=distanceVerticeThree;
        } else if(distanceVerticeOne == distanceVerticeThree){
            XY[1]=distanceVerticeTwo;
        } else if(distanceVerticeOne == distanceVerticeFour){
            XY[1]=distanceVerticeThree;
        }
        return XY;
    }
}
