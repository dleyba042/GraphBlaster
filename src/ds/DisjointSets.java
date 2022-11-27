package ds;

public class DisjointSets
{
    private int[] disjointSets;

    public DisjointSets(int numSets)
    {
        this.disjointSets = new int[numSets];

        for(int i = 0; i<disjointSets.length; i++)
        {
            disjointSets[i] = -1;
        }
    }

    public int find(int other)
    {
        if(disjointSets[other] == -1)
        {
            return other;
        }

        else
        {
            return find(disjointSets[other]);
        }
    }

    public boolean union(int first, int second)
    {

        if(disjointSets[first] != -1)
        {
            return false;
        }

        disjointSets[first] = second;
        return true;
    }

    public boolean sameSet(int first, int second)
    {
        return find(first) == find(second);
    }

}
