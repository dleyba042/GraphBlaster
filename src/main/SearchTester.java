package main;

import ds.MazeGraph;

import java.util.LinkedList;
import java.util.List;

public class SearchTester
{

    public static void main(String[] args)
    {
        MazeGraph graph = new MazeGraph(6,6);

        List<Integer> lst = graph.dfs();
        LinkedList<Integer>[] adjacency = graph.getAdjacencyLists();

        System.out.println("graph");
        for(int i = 0; i< adjacency.length; i++)
        {
            System.out.println(i + ": " + adjacency[i]);
        }
        System.out.println("list");
        lst.forEach(System.out::println);


    }
}
