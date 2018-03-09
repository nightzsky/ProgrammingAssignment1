# Programming Assignment 1
* Author : Siow Lee Sei, Ong Jing Xuan
* ID : 1002257, 1002065
* Date : 08/03/2018

# The Purpose of Our Program
The program uses UNIX system calls. From the list of processes given to the program, it will allow processes to be executed without any of them being dependent on one another, as seen in a directed acyclic graph of user programs in parallel. As the output is verbose, the user will be able to observe what is happening when trying to run the processes.


# How to Compile Our Program
1. Save all the files into one folder of your choice (including test cases and codes).
2. cd (change directory) in terminal to the folder which files are saved.
3. Type javac ProcessManagement.java in terminal to compile the code.
4. Type java ProcessManagement in terminal to run the code.
5. The output will be seen on the console.


# What Exactly Our Program Does
ProcessManagement.java receives a file as the input. The file contains commands which are parsed into an object known as ProcessGraph.This would allow the file to be executed as a directed acyclic graph. As the nodes in the graph are iterated through, nodes in the graph without dependecies are executed. During this, the program will consistently check if there are nodes which are not executed yet runnable. If there is, it will execute them. As each node has specified input and output, allowing for redirection if required and ensuring that the output will only be inherited from the parent process.
