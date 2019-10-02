// VASU PATEL cs435 4090 mp

import java.util.LinkedList;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.*;
import java.lang.*;
class PageRank4090
{
     static class Graph
    {
        private LinkedList<Integer> Ranklist[];
        LinkedList<Integer> innerlist;

        //Constructor for initializing variables
        Graph() { }

        Graph(int vertexcount)
        {
            int vertices = vertexcount;
            //Linkedlist of each iteration output
            Ranklist = new LinkedList[vertexcount];

            for (int i = 0; i<vertexcount; i++)
            {
                Ranklist[i] = new LinkedList();
            }
        }

        void addEdge(int start, int end)
        {
            Ranklist[start].add(end);
        }


        ArrayList<Integer> getEdges(int node)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i = 0; i< Ranklist.length; i++)
            {
                innerlist = Ranklist[i];
                if(innerlist.contains(node))
                {
                    list.add(i);
                }
            }
            return list;
        }


        int getIndex(int node)
        {
            return Ranklist[node].size();
        }
    }
    public static float PageRank(int vertex, int vertices, Graph pagerank, double[] arr)
    {
        ArrayList<Integer> inEdges = pagerank.getEdges(vertex);

        double dfactor = 0.85;
        int v = vertices;
        float summation = 0;
        for(int i = 0; i<inEdges.size(); i++)
        {
            double denom = pagerank.getIndex(Integer.parseInt(inEdges.get(i).toString()));
            double PageRankT = arr[Integer.parseInt(inEdges.get(i).toString())];
            summation += dfactor*(PageRankT/denom);
        }
        summation += (1.0-dfactor)/v;

        return summation;
    }

    public static void main(String args[]) throws IOException
    {
        if (args.length < 3 || args.length > 3)
        {
            System.out.println("Invalid amount of arguments.");
            System.exit(0);
        }
        else
        {

            //Three args used here
            int iterations = Integer.parseInt(args[0]);
            int initval = Integer.parseInt(args[1]);
            Scanner file = new Scanner(new FileInputStream(args[2]));

            //Scan file each line and get number of vertices
            String[] lines = new String[0];
            String[] line1 = file.nextLine().split(" ");
            int vertices = Integer.parseInt(line1[0]);
            double[] ranking = new double[vertices];


            Graph newpr = new Graph(vertices);
            DecimalFormat decobj = new DecimalFormat("#.000000");
            double errorate = 0.0001;

            while ((file.hasNextLine()))
            {
                String line = file.nextLine();
                lines = line.split(" ");
                newpr.addEdge(Integer.parseInt(lines[0]), Integer.parseInt(lines[1]));
            }
            //Looking at arg 1 to determine base case value
            switch (initval)
            {
                case 0:
                    for (int i = 0; i < ranking.length; i++)
                        ranking[i] = 0;
                    break;
                case 1:
                    for (int i = 0; i < ranking.length; i++)
                        ranking[i] = 1.0;
                    break;
                case -1:
                    for (int i = 0; i < ranking.length; i++)
                        ranking[i] = 1.0 / vertices;
                    break;
                case -2:
                    for (int i = 0; i < ranking.length; i++)
                        ranking[i] = 1.0 / Math.sqrt(vertices);
                    break;
                default:
                    System.out.println("Please use a valid base case.");
                    System.exit(0);
            }

            //first iteration
            if (iterations == 0)
            {
                int count = 1;
                boolean b = false;
                System.out.print("Base\t:\t0 :");
                for (int i = 0; i < vertices; i++)
                {
                    System.out.print("P[ " + i + "] = " + decobj.format((float) 1 / vertices) + " ");
                }
                System.out.println();
                for (int j = 1; b!=true; j++)
                {
                    System.out.print("Iter\t:\t" + count + " :");
                    double[] value = new double[vertices];
                    for (int k = 0; k < vertices; k++)
                    {
                        value[k] = PageRank(k, vertices, newpr, ranking);
                        System.out.print("P[ " + k + "] = " + decobj.format(value[k]) + " ");
                    }
                    System.out.println();
                    for (int i = 0; i < ranking.length; i++)
                    {
                        if (Math.abs(value[i] - ranking[i]) > errorate) break;
                        if (i == ranking.length - 1)
                        {
                            if (Math.abs(value[i] - ranking[i]) <= errorate)
                            {
                                b = true;
                            }
                        }
                    }
                    count++;
                    ranking = value;
                }
            }
            //Everything after first iteration
            else if (iterations > 0)
            {
                System.out.print("Base\t:\t0 :");
                for (int i = 0; i < vertices; i++)
                {
                    System.out.print("P[ " +i+ "] = " + decobj.format((float) 1 / vertices) + " ");
                }
                System.out.println();
                for (int j = 1; j < iterations + 1; j++)
                {
                    double[] value = new double[vertices];
                    System.out.print("Iter\t:\t" + j + " :");
                    for (int k = 0; k < vertices; k++)
                    {
                        value[k] = PageRank(k, vertices, newpr, ranking);
                        System.out.print("P[ " + k + "] = " + decobj.format(value[k]) + " ");
                    }
                    System.out.print("\n");
                    ranking = value;
                }
            }
        }

    }
}
