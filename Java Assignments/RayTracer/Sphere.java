package nl.sogyo.javaopdrachten.RayTracer;
import java.util.*;



public class Sphere implements Shape{
    Vector center;
    double radius;
    double diffuseCoefficient = 0.8;
    
    public Sphere(Vector origin,double radius){
        center = origin;
        this.radius = radius;
    }

    public Vector pointOnSphere(double degreesXY,double degreesZ){
        //source: https://en.wikipedia.org/wiki/Spherical_coordinate_system#Coordinate_system_conversions
        Vector pointOnSphere = new Vector(0,0,0);
        double radiansXY = degreesXY*Math.PI/180;
        double radiansZ = degreesZ*Math.PI/180;
        pointOnSphere.xCoordinate =center.xCoordinate + radius*Math.cos(radiansXY)*Math.sin(radiansZ);
        pointOnSphere.yCoordinate =center.yCoordinate + radius*Math.sin(radiansXY)*Math.sin(radiansZ);
        pointOnSphere.zCoordinate =center.zCoordinate + radius*Math.cos(radiansZ);

        return pointOnSphere;
    }

    // @Override
    //     public Vector[] intersect(Line line){
    //         ArrayList<Intersection> allIntersectionObjects = new ArrayList<Intersection>();
    //         ArrayList<Vector> pointsOnSphere = new ArrayList<Vector>();
    //         ArrayList<Line> linesInSphere = new ArrayList<Line>();
    //         ;

    //         for(int i = 0; i < 181;i++){
    //             for(int j = 0; j < 361;j++){
    //                 pointsOnSphere.add(pointOnSphere(i, j));
    //                 linesInSphere.add(new Line(center,pointsOnSphere.get(i*360+j)));
    //                 allIntersectionObjects.add(Intersection.findIntersection(line,linesInSphere.get(i*360+j)));
    //             }
    //         }
            
    //         List<Intersection> allIntersectionsList = allIntersectionObjects.stream().filter(p -> p.doesIntersect == true).toList();
    //         Vector[] allIntersections = new Vector[allIntersectionsList.size()];
    //         for(int i = 0; i < allIntersectionsList.size();i++){
    //             allIntersections[i] = allIntersectionsList.get(i).nearestPointLineOne;
    //         }
    //         return allIntersections;
    //     }
    
        public ArrayList<Vector> intersect2(Line line){
            //source https://www.codeproject.com/Articles/19799/Simple-Ray-Tracing-in-C-Part-II-Triangles-Intersec
            ArrayList<Vector> allIntersections = new ArrayList<Vector>();
            double vx = (line.endPoint.xCoordinate-line.startingPoint.xCoordinate);
            double vy = (line.endPoint.yCoordinate-line.startingPoint.yCoordinate);
            double vz = (line.endPoint.zCoordinate-line.startingPoint.zCoordinate);
            double px = line.startingPoint.xCoordinate;
            double py = line.startingPoint.yCoordinate;
            double pz = line.startingPoint.zCoordinate;
            double cx = center.xCoordinate;
            double cy = center.yCoordinate;
            double cz = center.zCoordinate;
            double t1 = 0;
            double t2 = 0;
            double A = (vx*vx+vy*vy+vz*vz);
            double B = 2.0*(px*vx+py*vy+pz*vz-vx*cx-vy*cy-vz*cz);
            double C = px*px-2*px*cx+cx*cx+py*py-2*py*cy+cy*cy+ pz*pz - 2 * pz*cz + cz*cz - radius*radius;
            double D = B*B - 4*A*C;
            double x1 = 0;
            double x2 = 0;
            double y1 = 0;
            double y2 = 0;
            double z1 = 0;
            double z2 = 0;
            if(D>= 0){
                t1 = (-B - Math.sqrt(D))/(2.0*A);
                t2 = (-B + Math.sqrt(D))/(2.0*A);
                x1 = px + t1*vx;
                y1 = py + t1*vy;
                z1 = pz + t1*vz;
                x2 = px + t2*vx;
                y2 = py + t2*vy;
                z2 = pz + t2*vz;
                allIntersections.add(new Vector(x1,y1,z1));
                allIntersections.add(new Vector(x2,y2,z2));
            } else if (D == 0){
                t1 = (-B - Math.sqrt(D))/(2.0*A);
                x1 = px + t1*vx;
                y1 = py + t1*vy;
                z1 = pz + t1*vz;
                allIntersections.add(new Vector(x1,y1,z1));
            }
            return allIntersections;
        }
        @Override
        public Vector intersect(Line line){
            //source https://www.codeproject.com/Articles/19799/Simple-Ray-Tracing-in-C-Part-II-Triangles-Intersec
            Vector allIntersections = new Vector(-1,-1,-1);
            double vx = (line.endPoint.xCoordinate-line.startingPoint.xCoordinate);
            double vy = (line.endPoint.yCoordinate-line.startingPoint.yCoordinate);
            double vz = (line.endPoint.zCoordinate-line.startingPoint.zCoordinate);
            double px = line.startingPoint.xCoordinate;
            double py = line.startingPoint.yCoordinate;
            double pz = line.startingPoint.zCoordinate;
            double cx = center.xCoordinate;
            double cy = center.yCoordinate;
            double cz = center.zCoordinate;
            double t1 = 0;
            double t2 = 0;
            double A = (vx*vx+vy*vy+vz*vz);
            double B = 2.0*(px*vx+py*vy+pz*vz-vx*cx-vy*cy-vz*cz);
            double C = px*px-2*px*cx+cx*cx+py*py-2*py*cy+cy*cy+ pz*pz - 2 * pz*cz + cz*cz - radius*radius;
            double D = B*B - 4*A*C;
            double x1 = 0;
            double y1 = 0;
            double z1 = 0;
            double t;
            if(D>= 0){
                t1 = (-B - Math.sqrt(D))/(2.0*A);
                t2 = (-B + Math.sqrt(D))/(2.0*A);
                if(t1>t2){
                    t=t1;
                } else {
                    t=t2;
                }
                x1 = px + t*vx;
                y1 = py + t*vy;
                z1 = pz + t*vz;
                
                allIntersections.xCoordinate = x1;
                allIntersections.yCoordinate = y1;
                allIntersections.zCoordinate = z1;
                
            } else if (D == 0){
                t1 = (-B - Math.sqrt(D))/(2.0*A);
                x1 = px + t1*vx;
                y1 = py + t1*vy;
                z1 = pz + t1*vz;
                allIntersections.xCoordinate = x1;
                allIntersections.yCoordinate = y1;
                allIntersections.zCoordinate = z1;
            }
            return allIntersections;
        }

    @Override
        public double getDiffuseCoefficient(Vector pointImpact, LightSource lightSource){
            // Vector perpendicular = PerpendicularVector(pointImpact);
            LineParametricRep centerPerp = new LineParametricRep(center, pointImpact);
            
            Vector centerPerpVector = centerPerp.tParameters;
            // System.out.println(centerPerpVector.xCoordinate);
            // System.out.println(centerPerpVector.yCoordinate);
            // System.out.println(centerPerpVector.zCoordinate);

            double distanceCenterPerp = Math.sqrt(Math.pow(centerPerpVector.xCoordinate,2)+Math.pow(centerPerpVector.yCoordinate,2)+Math.pow(centerPerpVector.zCoordinate,2));
            //System.out.println(distanceCenterPerp);
            Vector centerPerpNormal = new Vector(centerPerpVector.xCoordinate/distanceCenterPerp,centerPerpVector.yCoordinate/distanceCenterPerp,centerPerpVector.zCoordinate/distanceCenterPerp);
            LineParametricRep lightToPoint = new LineParametricRep(lightSource.position, pointImpact);
            Vector direction = lightToPoint.tParameters;
            double distanceDirection = Math.sqrt(Math.pow(direction.xCoordinate,2)+Math.pow(direction.yCoordinate,2)+Math.pow(direction.zCoordinate,2));;
            Vector directionNormal = new Vector(direction.xCoordinate/distanceDirection,direction.yCoordinate/distanceDirection,direction.zCoordinate/distanceDirection);

            //System.out.println(centerPerp.tParameters.xCoordinate);
            //System.out.println(centerPerp.tParameters.yCoordinate);
            //System.out.println(centerPerp.tParameters.zCoordinate);
            //double brightness = diffuseCoefficient * lightSource.brightness * Math.max(0,(perpendicular.xCoordinate*direction.xCoordinate+perpendicular.yCoordinate*direction.yCoordinate+perpendicular.zCoordinate*direction.zCoordinate));
            //double brightness = diffuseCoefficient * lightSource.brightness * Math.max(0,(centerPerp.tParameters.xCoordinate*direction.xCoordinate+centerPerp.tParameters.yCoordinate*direction.yCoordinate+centerPerp.tParameters.zCoordinate*direction.zCoordinate));
            double brightness = diffuseCoefficient * lightSource.brightness * Math.max(0,(centerPerpNormal.xCoordinate*directionNormal.xCoordinate+centerPerpNormal.yCoordinate*directionNormal.yCoordinate+centerPerpNormal.zCoordinate*directionNormal.zCoordinate));

            //System.out.println(Math.max(0,(centerPerp.tParameters.xCoordinate*direction.xCoordinate+centerPerp.tParameters.yCoordinate*direction.yCoordinate+centerPerp.tParameters.zCoordinate*direction.zCoordinate)));
             if(distanceCenterPerp==0 || distanceDirection==0){
                 return lightSource.brightness;
            }
            return brightness;
        }

    public Vector PerpendicularVector(Vector pointImpact){
        double xCoordinate = center.yCoordinate*pointImpact.zCoordinate-center.zCoordinate*pointImpact.yCoordinate;
        double yCoordinate = center.xCoordinate*pointImpact.zCoordinate-center.zCoordinate*pointImpact.xCoordinate;
        double zCoordinate = center.xCoordinate*pointImpact.yCoordinate-center.yCoordinate*pointImpact.xCoordinate;
        Vector perpendicular = new Vector(xCoordinate,yCoordinate,zCoordinate);
        return perpendicular;
    }
}
