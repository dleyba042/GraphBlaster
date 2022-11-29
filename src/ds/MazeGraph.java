package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MazeGraph
{
    private LinkedList<Integer>[] adjacencyLists;

    public MazeGraph(int rows, int cols)
    {
        adjacencyLists = initAdjacencyLists(rows * cols);
        List<Integer> vertexes = initList(rows,cols);
        DisjointSets sets = new DisjointSets(rows*cols);
        addAllEdges(sets,vertexes,rows,cols,adjacencyLists);
    }

    public static void addAllEdges(DisjointSets sets, List<Integer> vertexes, int rows, int cols,
                                   LinkedList<Integer>[] adjacent)
    {
        List<Integer> randomizer = initRandomizer();

        for(int i = 0; i< vertexes.size() - 1; i++)
        {
            goodUnion(sets,i,randomizer,rows, cols,adjacent);
        }

    }

    public static void addEdgesTogether(int first, int second, LinkedList<Integer>[] adjacent )
    {
        adjacent[first].add(second);
        adjacent[second].add(first);
    }

    /**
     * Helper function to init list of vertexes
     * @param rows
     * @param cols
     * @return the list to use in our graph creation
     */
    public static List<Integer> initList(int rows, int cols)
    {
        List<Integer> lst =  new ArrayList<>();

        for(int i = 0; i<rows*cols; i++)
        {
            lst.add(i);
        }
        return lst;
    }

    /**
     * Helper function to add the appropriate elements to the DS and the graph
     * @param ds disjoint sets
     * @param vertex current vertex
     * @param randomizer way to add them in a random order
     * @param cols columns
     * @param rows rows
     */

    public static void goodUnion(DisjointSets ds, int vertex, List<Integer> randomizer,
                                 int rows, int cols, LinkedList<Integer>[] adjacencyLists)
    {
        Collections.shuffle(randomizer);
        int current = 0;

        while(current < 4)
        {
            switch (randomizer.get(current))
            {
                //check top
                case 0: if(checkVal(vertex - cols,cols*rows) && !ds.sameSet(vertex,vertex - cols))
                {
                    ds.union(vertex,vertex - cols);
                    addEdgesTogether(vertex,vertex - cols,adjacencyLists);
                    return;
                }
                    break;
                //check bottom
                case 1: if(checkVal(vertex + cols, cols * rows) && !ds.sameSet(vertex,vertex + cols))
                {
                    ds.union(vertex,vertex + cols);
                    addEdgesTogether(vertex,vertex + cols,adjacencyLists);
                    return;
                }
                    break;
                //check left
                case 2: if(vertex % cols != 0)
                {
                    if (checkVal(vertex - 1, cols * rows) && !ds.sameSet(vertex, vertex - 1))
                    {
                        ds.union(vertex, vertex - 1);
                        addEdgesTogether(vertex,vertex - 1,adjacencyLists);
                        return;
                    }
                }
                    break;
                //check right
                case 3: if((vertex + 1) % cols != 0)
                {
                    if(checkVal(vertex + 1,cols*rows) && !ds.sameSet(vertex, vertex + 1))
                    {
                        ds.union(vertex, vertex + 1);
                        addEdgesTogether(vertex,vertex + 1,adjacencyLists);
                        return;
                    }
                }
                    break;
            }

            current++;
        }
    }

    /**
     * Helper function for adding to graph and DS
     * @param val proposed value to union with vertex
     * @param limit size limit of graph
     * @return
     */
    public static boolean checkVal(int val,int limit)
    {
        return !(val < 0 || val >= limit);
    }

    public static LinkedList<Integer>[] initAdjacencyLists(int size)
    {
        LinkedList<Integer>[] lsts = new LinkedList[size];

        for(int i = 0; i< lsts.length; i++)
        {
            lsts[i] = new LinkedList<>();
        }
        return lsts;
    }

    public static List<Integer> initRandomizer()
    {
        List<Integer> rand = new ArrayList<>();

        for(int i = 0; i<4; i++)
        {
            rand.add(i);
        }

        return rand;
    }

    public LinkedList<Integer>[] getAdjacencyLists()
    {
        return adjacencyLists;
    }
}
