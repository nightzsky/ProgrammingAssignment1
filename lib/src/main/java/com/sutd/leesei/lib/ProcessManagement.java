/* Programming Assignment 1
* Author : Siow Lee Sei, Ong Jing Xuan
* ID : 1002257, 1002065
* Date : 08/03/2018  */

package com.sutd.leesei.lib;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessManagement {

    //set the working directory
    private static File currentDirectory = new File(System.getProperty("user.dir"));
    //set the instructions file
    private static File instructionSet = new File("graph-file1.txt");
    public static Object lock=new Object();

    public static void main(String[] args) throws InterruptedException {

        //parse the instruction file and construct a data structure, stored inside ProcessGraph class
        ParseFile.generateGraph(new File(currentDirectory.getAbsolutePath() + "/"+instructionSet));

        //Print the graph information
        ProcessGraph.printGraph();

        // Using index of ProcessGraph, loop through each ProcessGraphNode, to check whether it is ready to run
        // check if all the nodes are executed

        // boolean notfinish = true if not all the nodes are executed
        boolean notfinish = true;
        // for counting the number of executed nodes
        int numberOfExecuted = 0;

        while (notfinish){

            //check if all the node is executed, if executed, number of executed node + 1
            for (ProcessGraphNode node: ProcessGraph.nodes){
                if (node.isExecuted()){
                    numberOfExecuted++;
                }
            }

            //check if all the node are executed, if all are executed, quit the while loop
            if (numberOfExecuted == ProcessGraph.nodes.size()){
                notfinish = false;
                break;
            }

            //set the node to runnable
            for (ProcessGraphNode node: ProcessGraph.nodes){
                //if the node has no parents and is not executed, set the node to be runnable
                if (node.getParents().isEmpty()&& !node.isExecuted()){
                    System.out.println("Node " + node.getNodeId() + " is ready to run.");
                    node.setRunnable();
                }

                //if the node has parents and is not executed, then check if all parents have executed, if executed then set it to be runnable
                else if (!node.getParents().isEmpty() && !node.isExecuted()){

                    if (node.allParentsExecuted()){
                        System.out.println("Node " + node.getNodeId() + " is ready to run.");
                        node.setRunnable();
                    }

                    // if the node is not ready to run, print out the hindrance (parent node that hasn't execute), for debugging
                    else {
                        System.out.println("Node " + node.getNodeId() + " cannot run because of the following parents node: ");
                        for (int i = 0 ; i < node.getParents().size(); i ++ ){

                            if (!node.getParents().get(i).isExecuted()){
                                System.out.println("Parent Node " + node.getParents().get(i).getNodeId() + " is not executed.");
                            }
                        }
                    }
                }

            }

            //check if the node is runnable and not executed
            for (ProcessGraphNode node: ProcessGraph.nodes){

                if (node.isRunnable()&&!node.isExecuted()){

                    System.out.println("Process " + node.getNodeId() + " is running.");
                    //create a processbuilder
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.command(node.getCommand().split(" "));

                    //parse the input file
                    if (!node.getInputFile().equals(new File("stdin"))){
                        if (node.getInputFile().exists()){ //check if the input file exists, if not print out the error.
                            pb.redirectInput(node.getInputFile());
                        }
                        else {
                            System.out.println("Error occurred! The input file is invalid or does not exist.");
                        }
                    }

                    //parse the output file
                    if (!node.getOutputFile().equals((new File("stdout")))){
                        if (node.getOutputFile().exists()){
                            pb.redirectOutput(node.getOutputFile());
                        }
                        else { //check if the output file exists, if not print out the error.
                            System.out.println("Error occurred! The output file is invalid or does not exist.");
                        }
                    }

                    try {

                        //start the process
                        Process p = pb.start();

                        p.waitFor(); // wait for the process to finish before executing the following command

                        System.out.println("Process " + node.getNodeId() + " finished running.");
                        node.setExecuted();
                    }catch(IOException io){
                        io.printStackTrace();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

        }

        System.out.println("All process finished successfully");
        System.out.println("Final result:");
        ProcessGraph.printGraph();
    }

}
