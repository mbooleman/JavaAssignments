import java.util.*;  

public class Robot{
    enum direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    int[] position = new int[2];
    direction facing = direction.NORTH;
    ArrayList<Runnable> executeOrder = new ArrayList<Runnable>();
    


    public Robot(int x,int y, direction direction){
        position = new int[]{x,y};
        facing = direction;
    }

    public void printState(){
        System.out.println("The robot is located at ("+position[0]+","+position[1]+") and facing "+facing);
    }

    public void turnLeft(){
        executeOrder.add(new Runnable(){
            @Override
            public void run(){
                facing = facing.turnCounterClockwise();
                if (facing.ordinal() == 0){
                    facing = direction.values()[direction.values().length-1];
                } else {
                    facing = direction.values()[facing.ordinal()-1];
                } 

                
            }
        }
        );
        
    }
    
    public void turnRight(){
        executeOrder.add(new Runnable(){
            @Override
            public void run(){
                if (facing.ordinal() < direction.values().length-1){
                    facing = direction.values()[facing.ordinal()+1];
                } else {
                    facing = direction.values()[0];
                } 
            }
        }
        );
        
    }

    public void forward(int distance){
        if ( distance > 3 || distance < -1){
            System.out.println("Distance not within allowed bounds, please try again.");
            return;
        }
        executeOrder.add(new Runnable(){
            @Override
            public void run(){
                switch (facing.ordinal()){
                case 0:
                    position[1] += distance;
                    break;
                case 1:
                    position[0] += distance;
                    break;
                case 2:
                    position[1] -= distance;
                    break;
                case 3:
                    position[0] -= distance;
                    break;
                }              
            }
        }
        );
        
    } 

    public void forward(){
        forward(1);        
    } 

    public void backward(){
        forward(-1);
        /*executeOrder.add(new Runnable(){
            @Override
            public void run(){
                if (facing.equals("North")){
                    position[1]--;
                } else if (facing.equals("West")){
                    position[0]++ ;
                } else if (facing.equals("South")){
                    position[1]++;
                } else {
                    position[0]--;
                }
            }
        }
        );*/
    } 

    public void execute(){
        executeOrder.forEach(Runnable::run);
        //position[0] = 
        printState();
    }

    public static void main(String[] args){

        //Robot myFirstRobot = new Robot(0, 1, "East");
        Robot mySecondRobot = new Robot(1, 0, direction.WEST);
        /*myFirstRobot.turnLeft();
        myFirstRobot.forward(2);
        mySecondRobot.backward();
        //myFirstRobot.printState(); // Now facing "North"
        //mySecondRobot.printState(); // Still facing "West"
        myFirstRobot.execute();
        mySecondRobot.execute();*/

        Robot myFirstRobot = new Robot(1, 0, direction.NORTH);
        myFirstRobot.printState();
        myFirstRobot.turnLeft();
        myFirstRobot.forward(2);
        myFirstRobot.backward();
        /*myFirstRobot.turnRight();
        myFirstRobot.forward();
        myFirstRobot.backward();
        myFirstRobot.turnRight();
        myFirstRobot.forward(3);
        myFirstRobot.backward();*/
        myFirstRobot.execute();

    }
}