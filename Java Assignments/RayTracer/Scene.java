package nl.sogyo.javaopdrachten.RayTracer;
import java.util.*;

public class Scene {
    Vector viewPoint;
    Viewport viewPort;
    ArrayList<LightSource> lightSourceList;
    ArrayList<Sphere> shapeList;
    
    public Scene(Vector view, Viewport screen, ArrayList<LightSource> lightSources, ArrayList<Sphere> shapes){
        viewPoint = view;
        viewPort = screen;
        lightSourceList = lightSources;
        shapeList = shapes;
    }

    public double getMaxBrightness(){
        double maxValue = -1;
        
        for(int i = 0; i<lightSourceList.size();i++){
            if(lightSourceList.get(i).brightness > maxValue){
                maxValue = lightSourceList.get(i).brightness;
            }
        }
    return maxValue;
    }
}
