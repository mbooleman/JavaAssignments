package nl.sogyo.javaopdrachten.SudokuSolver;

import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import java.math.BigDecimal;
import org.ojalgo.optimisation.Expression;
import com.google.common.collect.ImmutableList;
import org.ojalgo.optimisation.Variable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuSolver{
    
    String sudokuInput;
    ArrayList<ArrayList<Character>> initialStateVisualisation;
    ArrayList<ArrayList<Character>> solvedStateVisualisation;
    ArrayList<ArrayList<Integer>> initialState;
    ArrayList<ArrayList<Integer>> solvedStateObject;
    boolean isValid;
    List<Variable> allVariables;


    public SudokuSolver(String input){
        sudokuInput = input;
    }

    public ArrayList<ArrayList<Character>> inputToVisualisation(ArrayList<ArrayList<Integer>> inputState){
        ArrayList<ArrayList<Character>> sudokuState = new ArrayList<ArrayList<Character>>();
        int gridWidth = 19;
        
        for(int j =0; j < (sudokuInput.length()/9)*2; j+=2){
            sudokuState.add(new ArrayList<Character>());
            for(int i = 0; i < gridWidth+1;i++){
                sudokuState.get(j).add('-');
            }
            sudokuState.add(new ArrayList<Character>());
            for(int k = 0;k < (gridWidth-1)/2;k++){
                sudokuState.get(j+1).add('|');
                if(inputState.get(j/2).get(k)==0){
                    sudokuState.get(j+1).add(' ');
                } else {
                    sudokuState.get(j+1).add(Character.forDigit(inputState.get(j/2).get(k),10));
                }
                if(k==8){
                sudokuState.get(j+1).add('|');
                }
            }
        }
        return sudokuState;       
    }

    public void inputToState(){
        ArrayList<ArrayList<Integer>> sudokuState = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < sudokuInput.length()/9;i++){
            sudokuState.add(new ArrayList<Integer>());
            for(int j = 0; j < sudokuInput.length()/9;j++){
                sudokuState.get(i).add(Integer.parseInt(sudokuInput.substring(i*9+j,i*9+j+1)));
            }
        }
    initialState = sudokuState;
    }

    public void printState( ArrayList<ArrayList<Character>> arrayPrint){
        
        for (ArrayList<Character> traverseRow : arrayPrint) {
            for (char ind : traverseRow) {
                System.out.print(ind);
            }
        System.out.println();
        }    
        System.out.println();
    }

    public List<Variable> makeViarables(){
        ImmutableList.Builder<Variable> result=ImmutableList.builder();
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                for(int k = 0; k<9;k++){
                    if(initialState.get(i).get(j)==(k+1)){
                        result.add(new Variable("x_"+(i+1)+"_"+(j+1)+"_"+(k+1)).weight(0).lower(1).upper(1));
                    } else {
                    result.add(new Variable("x_"+(i+1)+"_"+(j+1)+"_"+(k+1)).weight(0).lower(0).upper(1));
                    }
                }
            }
        }
        
       return result.build();
    }

    public  void solveModel(){
        ExpressionsBasedModel model = new ExpressionsBasedModel();
        
        List<Variable> v = makeViarables();
        
        for(Variable variables : v){
            model.addVariable(variables);
        }

        for (Variable var : model.getVariables()) {
            var.setInteger(true);
        }

        for(int i = 0; i < 9;i++){
            for(int k = 0; k < 9;k++){
                rowConstraint(model, v, i, k);
            }
        }

        for(int j = 0; j < 9;j++){
            for(int k = 0; k < 9;k++){
                columnConstraint(model, v, j, k);
            }
        }
        for(int n = 0; n <3; n++ ){
            for(int m = 0; m<3;m++){
                for(int k=0; k < 9; k++){
                    boxConstraint(model, v, n, m, k);
                }
            }
        }
        for(int i=0; i < 9; i++){
            
            for(int j = 0; j < 9;j++){
                valueConstraint(model, v, i, j);
            }
        }

    ArrayList<ArrayList<Integer>> solvedState = new ArrayList<ArrayList<Integer>>();
        
    Optimisation.Result result = model.maximise();
            
    optimisationToArray(solvedState, result);
    checkSolutionForZeroes(solvedState);
    solvedStateObject = solvedState;

    }

    private void checkSolutionForZeroes(ArrayList<ArrayList<Integer>> solvedState) {
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                if(solvedState.get(i).get(j)==0){
                    System.out.println("Could not find a unique solution, please check your input.");
                    System.exit(0);
                }
            }
        } 
    }

    private void optimisationToArray(ArrayList<ArrayList<Integer>> solvedState, Optimisation.Result result) {
        for(int i = 0; i < 9;i++){
            solvedState.add(new ArrayList<Integer>());
            for(int j = 0; j < 9;j++){
                solvedState.get(i).add(0);
                for(int k = 0; k<9;k++){
                    Long index = Long.valueOf(i*9*9+j*9+k);
                                       
                    if(result.get(index).equals(BigDecimal.valueOf(1))){
                        solvedState.get(i).set(j,k+1);
                    }
                }
            }
        }
    }

    private void rowConstraint(ExpressionsBasedModel model, List<Variable> v, int i, int k) {
        Expression expression = model.addExpression("Row_constraint_row_"+(i+1)+"_value_"+(k+1)).lower(1).upper(1);
        for(int j=0; j < 9; j++){
            expression.set(v.get(k+i*9*9+j*9), 1);
        }
    }

    private void columnConstraint(ExpressionsBasedModel model, List<Variable> v, int j, int k) {
        Expression expression = model.addExpression("Column_constraint_column_"+(j+1)+"_value_"+(k+1)).lower(1).upper(1);
        for(int i=0; i < 9; i++){
            expression.set(v.get(k+i*9*9+j*9), 1);
        }
    }

    private void boxConstraint(ExpressionsBasedModel model, List<Variable> v, int n, int m, int k) {
        Expression expression = model.addExpression("Box_constraint_box_("+(n+1)+","+(m+1)+")_value_"+(k+1)).lower(1).upper(1);
        for(int i = (0+n*3); i < (3+n*3);i++){
            for(int j = (0+m*3); j < (3+m*3);j++){
                expression.set(v.get(k+i*9*9+j*9), 1);
            }
        }
    }

    private void valueConstraint(ExpressionsBasedModel model, List<Variable> v, int i, int j) {
        Expression expression = model.addExpression("cell_constraint_cell_()"+(i+1)+","+(j+1)+")").lower(1).upper(1);
        for(int k = 0; k < 9;k++){
            expression.set(v.get(k+i*9*9+j*9), 1);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter your sudoku as a string with 0 for empty cells");
        SudokuSolver sudoku = new SudokuSolver(sc.nextLine());
        //System.out.println(sudoku.sudokuInput.length());
        long startTime = System.nanoTime();
        sudoku.inputToState();
        sudoku.isValid = sudoku.checkValidity();
        
        if(!sudoku.isValid){
            System.out.println("Your input is not valid because it can't be converted to a valid sudoku grid, please try again. \n Valid means it doesn't conflict with standard sudoku rules and that your input has enough digits to allow for a unique solution.");
            System.exit(0);
        }    
        
        sudoku.initialStateVisualisation = sudoku.inputToVisualisation(sudoku.initialState);
        System.out.println("Initial State:");
        sudoku.printState(sudoku.initialStateVisualisation);
        
        sudoku.solveModel();
        
        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000;
        
        sudoku.solvedStateVisualisation = sudoku.inputToVisualisation(sudoku.solvedStateObject);
        System.out.println("Solved State:");
        sudoku.printState(sudoku.solvedStateVisualisation);
        System.out.println("Solved in: "+totalTime+" milliseconds.");
        /*
        000820090500000000308040007100000040006402503000090010093004000004035200000700900 assignment question

        028000000140006000000700040070804060000000201005000034000310059000050000609007000 //2nd working example

        987604321000000000000000000000000000000500000000000000000000000000000000123406789 trial error - gives empty solves

        000000000000000000000000000000000000000000000000000000000000000000000000000000001 infeasible errors
        */
        sc.close();
    }

    private boolean checkValidity() {    
        boolean returnValue = true;
        if(sudokuInput.length() != 81){
            
            return returnValue = false;
        }
        boolean enoughDigits = checkDigits(initialState);
        //System.out.println(enoughDigits);
        boolean rowValid = checkRows(initialState);
        //System.out.println(rowValid);
        boolean columnValid = checkColumns(initialState);
        //System.out.println(columnValid);
        boolean boxValid = checkBoxes(initialState);
        //System.out.println(boxValid);
        boolean valueValid = checkValues(initialState);
        //System.out.println(valueValid);
        if (!rowValid || !columnValid || !boxValid || !valueValid || !enoughDigits){
            returnValue = false;
        }
        return returnValue;
    }

    private boolean checkDigits(ArrayList<ArrayList<Integer>> array) {
        boolean enoughDigits = true;
        int counter = 0;
        for(int i = 0; i <9;i++){
            for(int j = 0; j < 9; j++){
                if(array.get(i).get(j)!=0){
                    counter++;
                }
            }
        }
        if(counter<17){
            enoughDigits = false;
        }
        return enoughDigits;
    }

    private boolean checkValues(ArrayList<ArrayList<Integer>> array) {
        boolean valueValid = true;
        for(int k = 1;k<10;k++){
            int counter = 0;
            for(int i = 0; i <9;i++){
                for(int j = 0; j < 9; j++){
                    if(array.get(i).get(j)==k){
                        counter++;
                    }
                }
            }
            if(counter > 9){
                return valueValid = false;
            }
        }
        return valueValid;
    }

    private boolean checkBoxes(ArrayList<ArrayList<Integer>> array) {
        boolean boxValid = true;
        for(int n = 0; n < 3;n++){
            for(int m = 0; m < 3;m++){
                for(int k = 1; k < 10 ; k++){
                    int counter = 0;
                    for(int i = 0+3*n;i<3+n*3;i++){
                        for(int j = 0+3*m; j<3+m*3 ;j++){
                            if(array.get(i).get(j)==k){
                                counter++;
                                if(counter>=2){
                                    return boxValid = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return boxValid;
    }

    private boolean checkColumns(ArrayList<ArrayList<Integer>> array) {
        boolean columnsValid = true;
        for(int j = 0; j < 9;j++){
            for(int i = 0; i < 9;i++){
                for(int n =i+1; n<9;n++){
                    if(array.get(i).get(j)==0){
                        continue;
                    } else if(array.get(i).get(j)==array.get(n).get(j)){
                        columnsValid = false;
                    }
                }
            }
        }
        return columnsValid;
    }

    private boolean checkRows(ArrayList<ArrayList<Integer>> array) {
        boolean rowsValid = true;
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                for(int n =j+1; n<9;n++){
                    if(array.get(i).get(j)==0){
                        continue;
                    }         
                    else if(array.get(i).get(j)==array.get(i).get(n)){
                        rowsValid = false;
                    }
                }
            }
        }
    return rowsValid;
    }

    
}