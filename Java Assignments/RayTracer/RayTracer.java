package nl.sogyo.javaopdrachten.RayTracer;
import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import nl.sogyo.javaopdrachten.RayTracer.Vector;


interface Shape {
    Vector intersect(Line line);

    double getDiffuseCoefficient(Vector pointImpact, LightSource lightSource);
}

public class RayTracer {
    
    public static void main(String[] args){

        Vector viewPointExample = new Vector(0,0,0);
        
        Line verticeOneExample = new Line(new Vector(400.0,300.0,50.0),new Vector(-400.0,300.0,50.0));
        Line verticeTwoExample = new Line(new Vector(400.0,300.0,50.0),new Vector(400.0,-300.0,50.0));
        Line verticeThreeExample = new Line(new Vector(-400.0,-300.0,50.0),new Vector(-400.0,300.0,50.0));
        Viewport viewportExample = new Viewport(verticeOneExample, verticeTwoExample, verticeThreeExample);
       
        Vector centroidOneExample = new Vector(0,0,100.0);
        Vector centroidTwoExample = new Vector(100.0,150.0,130.0);
        Sphere sphereOneExample = new Sphere(centroidOneExample,200.0);
        Sphere sphereTwoExample = new Sphere(centroidTwoExample,50.0);
        ArrayList<Sphere> Shapes = new ArrayList<Sphere>();
        //
        Shapes.add(sphereTwoExample);
        Shapes.add(sphereOneExample);
        Vector lightSourceOneExample = new Vector(500.0,500.0,155);
        Vector lightSourceTwoExample = new Vector(500.0,-100.0,75.0);
        LightSource lightSourceOne = new LightSource(lightSourceOneExample,100.0);
        LightSource lightSourceTwo = new LightSource(lightSourceTwoExample,50.0);
        ArrayList<LightSource> lightSourceList = new ArrayList<LightSource>();
        lightSourceList.add(lightSourceOne);
        lightSourceList.add(lightSourceTwo);

        Scene sceneExample = new Scene(viewPointExample,viewportExample,lightSourceList,Shapes);
        
        double[] coordinateRange = sceneExample.viewPort.getCoordinateLengths();
        
        
        ArrayList<ArrayList<Vector>> coordinatesVectors = new ArrayList<ArrayList<Vector>>();
        ArrayList<ArrayList<Line>> viewLineArray = new ArrayList<ArrayList<Line>>();
        ArrayList<ArrayList<ArrayList<Vector>>> allViewIntersection = new ArrayList<ArrayList<ArrayList<Vector>>>();
        ArrayList<ArrayList<ArrayList<Vector>>> onlyIntersectionsBetweenScreenAndViewpoint = new ArrayList<ArrayList<ArrayList<Vector>>>();
        ArrayList<ArrayList<Vector>> nearestIntersection = new ArrayList<ArrayList<Vector>>();
        ArrayList<ArrayList<ArrayList<Vector>>> allObjectLightIntersection = new  ArrayList<ArrayList<ArrayList<Vector>>>();
        ArrayList<ArrayList<ArrayList<Line>>> objectLightLineArray = new ArrayList<ArrayList<ArrayList<Line>>>();
        ArrayList<ArrayList<ArrayList<Double>>> brightnessViewportArray = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<ArrayList<Double>> brightnessCoordinate = new ArrayList<ArrayList<Double>>();
        int counter =0;
        for(int i = 0;i<coordinateRange[0];i++){
            coordinatesVectors.add(new ArrayList<Vector>());
            viewLineArray.add(new ArrayList<Line>());
            allViewIntersection.add(new ArrayList<ArrayList<Vector>>());
            onlyIntersectionsBetweenScreenAndViewpoint.add(new ArrayList<ArrayList<Vector>>());
            nearestIntersection.add(new ArrayList<Vector>());
            allObjectLightIntersection.add(new ArrayList<ArrayList<Vector>>());
            objectLightLineArray.add(new ArrayList<ArrayList<Line>>());
            brightnessViewportArray.add(new ArrayList<ArrayList<Double>>());
            brightnessCoordinate.add(new ArrayList<Double>());

            for(int j = 0; j < coordinateRange[1];j++){
                allViewIntersection.get(i).add(new ArrayList<Vector>());
                coordinatesVectors.get(i).add(sceneExample.viewPort.getVector(new Coordinate(i, j)));
                
                viewLineArray.get(i).add(new Line(sceneExample.viewPoint,coordinatesVectors.get(i).get(j)));
                objectLightLineArray.get(i).add(new ArrayList<Line>());
                allObjectLightIntersection.get(i).add(new ArrayList<Vector>());
                brightnessViewportArray.get(i).add(new ArrayList<Double>());
                for(int k = 0; k<sceneExample.shapeList.size();k++){
                    allViewIntersection.get(i).get(j).add(sceneExample.shapeList.get(k).intersect(viewLineArray.get(i).get(j)));
                }

                onlyIntersectionsBetweenScreenAndViewpoint.get(i).add(getForwardIntersections(allViewIntersection.get(i).get(j),sceneExample,coordinatesVectors.get(i).get(j)));

                nearestIntersection.get(i).add(findShortestDistanceVector(onlyIntersectionsBetweenScreenAndViewpoint.get(i).get(j),sceneExample.viewPoint,coordinatesVectors.get(i).get(j)));

                for(int l = 0; l < sceneExample.lightSourceList.size();l++){
                    objectLightLineArray.get(i).get(j).add(new Line(sceneExample.lightSourceList.get(l).position,nearestIntersection.get(i).get(j)));
                
                }

                for(int k = 0; k<sceneExample.shapeList.size();k++){
                    for(int l = 0; l < sceneExample.lightSourceList.size();l++){
                        allObjectLightIntersection.get(i).get(j).add(sceneExample.shapeList.get(k).intersect(objectLightLineArray.get(i).get(j).get(l)));


                        if(allObjectLightIntersection.get(i).get(j).get(k*sceneExample.lightSourceList.size()+l).xCoordinate==-1&&allObjectLightIntersection.get(i).get(j).get(k*sceneExample.lightSourceList.size()+l).yCoordinate==-1&&allObjectLightIntersection.get(i).get(j).get(k*sceneExample.lightSourceList.size()+l).zCoordinate==-1){
                            if(nearestIntersection.get(i).get(j).xCoordinate==coordinatesVectors.get(i).get(j).xCoordinate && nearestIntersection.get(i).get(j).yCoordinate==coordinatesVectors.get(i).get(j).yCoordinate && nearestIntersection.get(i).get(j).zCoordinate==coordinatesVectors.get(i).get(j).zCoordinate){
                                brightnessViewportArray.get(i).get(j).add(sceneExample.lightSourceList.get(l).brightness);
                                System.out.println("print dit?");
                            } else {
                                brightnessViewportArray.get(i).get(j).add(sceneExample.shapeList.get(k).getDiffuseCoefficient(nearestIntersection.get(i).get(j),sceneExample.lightSourceList.get(l)));
                             
                            }
                        } else {
                            brightnessViewportArray.get(i).get(j).add(0.0);
                        }
                    }
                }

                brightnessCoordinate.get(i).add(getMax(brightnessViewportArray.get(i).get(j)));
            }
        }
        System.out.println(counter++);
        System.out.println(viewLineArray.get(700).get(500).endPoint.xCoordinate);
        int[][] colourArray = brightnessToRGBA(brightnessCoordinate);
        
        
        twoDToImage(colourArray, "./image.jpg");


    }

    private static double sum(ArrayList<Double> arrayList) {
        double sum = 0;
        for(int i = 0; i < arrayList.size();i++){
            sum += arrayList.get(i);
        }
        return sum;
    }

    private static Double getMax(ArrayList<Double> arrayList) {
        double max = -1;
        for(int i = 0; i<arrayList.size();i++){
            if(max<arrayList.get(i)){
                max = arrayList.get(i);
            }
        }
        return max;
    }

    public static ArrayList<Vector> getForwardIntersections(ArrayList<Vector> arrayList,Scene scene,Vector coordinateIJ) {
        ArrayList<Vector> forwardVectors = new ArrayList<Vector>();
        ArrayList<LineParametricRep> parametricVectors= new ArrayList<LineParametricRep>();
        for(int i =0; i< arrayList.size();i++){
            parametricVectors.add(new LineParametricRep(new Vector(0,0,0), arrayList.get(i)));
            if(arrayList.get(i).xCoordinate==-1 && arrayList.get(i).yCoordinate==-1 && arrayList.get(i).zCoordinate==-1){
                forwardVectors.add(coordinateIJ);
            } else {
                if(scene.viewPort.isInDirectionOfPlane(scene.viewPort.getPlaneNormalVector(), scene.viewPort.getNormalK(scene.viewPort.getPlaneNormalVector()), parametricVectors.get(i))){
                    forwardVectors.add(arrayList.get(i));
                } else {
                    forwardVectors.add(new Vector(-1,-1,-1));
                }
            }
        }

        return forwardVectors;
    }

    public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}

    private static int[][] brightnessToRGBA(ArrayList<ArrayList<Double>> brightnessCoordinate) {
        int[][] RGBAArray = new int[brightnessCoordinate.size()][brightnessCoordinate.get(0).size()];
        int[] RGBA = new int[4];
        for(int i = 0; i < brightnessCoordinate.size();i++){
            for(int j = 0; j < brightnessCoordinate.get(0).size();j++){
                RGBA[0]= (int) (255*brightnessCoordinate.get(i).get(j)/100);
                RGBA[1]= (int) (255*brightnessCoordinate.get(i).get(j)/100);
                RGBA[2]= (int) (255*brightnessCoordinate.get(i).get(j)/100);
                RGBA[3]= 255;
                RGBAArray[i][j] = getColorIntValFromRGBA(RGBA);
            }
        }
        return RGBAArray;
    }

    private static Vector findShortestDistanceVector(ArrayList<Vector> allIntersection,
            Vector viewPoint,Vector coordinatesVector) {
        ArrayList<Double> distances = getAllDistances(allIntersection, viewPoint,coordinatesVector);
        int index = getIndexOfMinimum(distances);
        if(Collections.min(distances)==10*10*10*10.0*10*10*10*10*10*10 || index == distances.size()-1){
            return coordinatesVector;
        }
        return allIntersection.get(index);
    }

    private static int getIndexOfMinimum(ArrayList<Double> distances) {
        int indexI = 0;
        double min =10*10*10*10.0*10*10*10*10*10*10*10;
        for(int i = 0;i<distances.size();i++){
                 if(distances.get(i)< min){
                    min = distances.get(i);
                    indexI = i;
                }
            }
        return indexI;
    }

    private static ArrayList<Double> getAllDistances(ArrayList<Vector> allViewIntersection, Vector viewPoint,Vector coordinatesVector) {
        ArrayList<Double> distances = new ArrayList<Double>();
        for(int i = 0; i<allViewIntersection.size();i++){
            if(allViewIntersection.get(i).xCoordinate==-1 && allViewIntersection.get(i).yCoordinate==-1 && allViewIntersection.get(i).zCoordinate==-1){
                distances.add(10*10*10*10.0*10*10*10*10*10*10);
                
            } else {
                distances.add(Math.sqrt(Math.pow(viewPoint.xCoordinate-allViewIntersection.get(i).xCoordinate,2)+Math.pow(viewPoint.yCoordinate-allViewIntersection.get(i).xCoordinate,2)+Math.pow(viewPoint.zCoordinate-allViewIntersection.get(i).xCoordinate,2)));
            }
        }
        distances.add(Math.sqrt(Math.pow(viewPoint.xCoordinate-coordinatesVector.xCoordinate,2)+Math.pow(viewPoint.yCoordinate-coordinatesVector.yCoordinate,2)+Math.pow(viewPoint.zCoordinate-coordinatesVector.zCoordinate,2)));
        return distances;
    }   

    public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}

}
