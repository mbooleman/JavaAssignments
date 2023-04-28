import java.util.*;  
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileNotFoundException;

public class decisionTree {
    
    public static void scannerToNodesAndEdges(Scanner scan, ArrayList<Node> nodes,ArrayList<Edge> edges){
        String[] entryArray;
        String entryString = "";
        while (scan.hasNextLine()){
            entryString = scan.nextLine();
            entryArray = entryString.split(", ");
            if (entryArray.length == 2 && (entryString.contains("?"))){
                nodes.add(new Node(entryArray[0],entryArray[1],false));
            } else if (entryArray.length == 2){
                nodes.add(new Node(entryArray[0],entryArray[1],true));
            } else if (entryArray.length == 3){
                edges.add(new Edge(entryArray[0],entryArray[1],entryArray[2]));
            }
        }
    }

    public static Node getFirstNode(ArrayList<Node> nodes, ArrayList<Edge> edges){
        
        ArrayList<String> endNodes = new ArrayList<String>();
        String firstNode = "";
        
        for (int i = 0; i < edges.size();i++){
            endNodes.add(edges.get(i).endNode);
        }
        for (int i = 0 ; i < edges.size();i++){
            if(!(endNodes.contains(edges.get(i).startNode))){
                firstNode = edges.get(i).startNode;
                break;
            }
        }
        
        Node startingNode = getNode(nodes,firstNode);
        
        return startingNode;
    }

    public static Node getNode(ArrayList<Node> nodes,String nodeName){
        Node returnNode = new Node("","",false);
        for(int i =0; i < nodes.size();i++){
            if(nodes.get(i).name.equals(nodeName)){
               returnNode = nodes.get(i);
            }
        }
        return returnNode;
    }

    public static ArrayList<Edge> getAnswerEdges(Node parentNode,ArrayList<Edge> edges){
        ArrayList<Edge> answerEdges = new ArrayList<Edge>();
        
        for(int i =0; i < edges.size();i++){
            if(parentNode.name.equals(edges.get(i).startNode)){
                if(answerEdges.size()!=0){
                    answerEdges.add(edges.get(i));
                    break;
                }
                answerEdges.add(edges.get(i));
            }
        }

        return answerEdges;
    }

    public static String inputScanner(){
        Scanner inputScan = new Scanner(System.in);
        String answerInput = inputScan.nextLine();
        return answerInput;
    }

    public static Node matchAnswertToNextNode(String answer,ArrayList<Edge> answerEdges,ArrayList<Node> nodes){
        String answerMatch = "";
        for(int i =0; i < answerEdges.size();i++){
            if(answerEdges.get(i).answer.equals(answer)){
                answerMatch = answerEdges.get(i).endNode;
            }
        }
        Node returnNode = getNode(nodes,answerMatch);
        return returnNode;
    }

    public static void main(String[] args) throws IOException{

        FileReader file = new FileReader(args[0]);
                
        ArrayList<Node> nodesArray = new ArrayList<Node>();
        ArrayList<Edge> edgeArray = new ArrayList<Edge>();

        scannerToNodesAndEdges(file.reader,nodesArray,edgeArray);
        
        int i = 0;
        boolean exitWhile = false;
        String answer = "";
        ArrayList<ArrayList<Edge>> possibleAnswers = new ArrayList<ArrayList<Edge>>();
        ArrayList<Node> questionNode =new ArrayList<Node>();
        while (true){
            if(i==0){
                questionNode.add(getFirstNode(nodesArray,edgeArray));
            } else {
                questionNode.add(matchAnswertToNextNode(answer,possibleAnswers.get(i-1),nodesArray));
            }

            System.out.println(questionNode.get(i).question);
            
            if(questionNode.get(i).isExitNode==true){
                break;
            }

            possibleAnswers.add(getAnswerEdges(questionNode.get(i),edgeArray));
            System.out.println(possibleAnswers.get(i).get(0).answer+" of "+possibleAnswers.get(i).get(1).answer+"?");
          
            answer = inputScanner();
           
            i++;
            System.out.println(i);
        }
    }
}

class FileReader {

    public Scanner reader;

    public FileReader(String filePath) throws IOException{
          try {
              this.reader = new Scanner(new File(filePath),StandardCharsets.UTF_8);
          } catch (FileNotFoundException e) {
               System.out.println("Could not find file \"" + filePath + "\". ");
               e.printStackTrace();
          }
     }

}

class Node{
    String name = "";
    String question = "";
    boolean isExitNode = false;

    Node(String nodeName,String nodeQuestion,boolean isExit){
        this.name = nodeName;
        this.question = nodeQuestion;
        this.isExitNode = isExit;
    }
}

class Edge{
    String startNode = "";
    String endNode = "";
    String answer = "";

    Edge(String startNodeName,String endNodeName,String nodeQuestion){
        this.startNode = startNodeName;
        this.endNode = endNodeName;
        this.answer = nodeQuestion;
    }
}
