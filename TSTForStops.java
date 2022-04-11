import java.util.ArrayList;

//Implemented using Sedgewick and Waynes version found here https://algs4.cs.princeton.edu/52trie/TST.java.html
public class TSTForStops
{
	//Instance Variables
    public static ArrayList<Integer> matches = new ArrayList<>();
    GenericForTSTNode root;

    //Constructor Declaration of Class
    protected TSTForStops()
    {
        root = null;
    }

    // The get function which tries to find from the TST. It calls recursively to search
    public int get(String key)
    {
        if (key.isEmpty())
        {
            return -1; //Error returns -1
        }
        key = key.toUpperCase(); //All stops are in upper case
        return get(root, key);
    }

    public int get(GenericForTSTNode node, String key)
    {
        char c = key.charAt(0);
        if (node == null)
        {   
        	//Error, did not find =-1
            return -1;
        }
        else if (key.length() > 1)
        {
            if (c == node.c)
            {   
            	//This checks further if there is more
                return get(node.mid, key.substring(1));
            }
            else if (c > node.c)
            {   
            	//Checks right
                return get(node.right, key);
            }
            else
            {   //Checks left
                return get(node.left, key);
            }
        }
        else
        {   
        	//No more nodes after
            if (c == node.c)
            {   
            	//Checking if there is one result
                if (node.value != null)
                {   
                    return node.value;
                }
            	//More than one result, called similar
                else
                {   
                    Similar(node, "");
                    return 0;
                }
            }
            else if (c > node.c)
            {
                if (node.value == null)
                {   
                    return get(node.right, key);
                }
                //Didn't find. Error =-1
                return -1; 
            }
            else
            {
                if (node.value == null)
                {   
                    return get(node.left, key);
                }
                //Didn't find. Error =-1
                return -1;
            }
        }
    }

    //Checks for multiple results
    private void Similar(GenericForTSTNode node, String string)
    {
        if (node != null)
        {
            Similar(node.left, string);
            string = string + node.c;
            //Adding the value to the ArrayList for the number of the line
            if (node.value != null)
            {   
                matches.add(node.value);
            }
            Similar(node.mid, string);
            string = string.substring(0, string.length() - 1);
            Similar(node.right, string);
        }
    }

    // Function to add to TST. Recursively calls
    public void put(String key, int value)
    {
        if (key.isEmpty())
        {
            System.out.println("No input");
            return;
        }
        key = key.toUpperCase();
        root = put(root, key, value);
    }

    public GenericForTSTNode put(GenericForTSTNode node, String key, int value)
    {
        char c = key.charAt(0);
        if (key.length() > 1)
        {
            if (node == null)
            {   
                node = new GenericForTSTNode(c, null);
                node.mid = put(node.mid, key.substring(1), value);
                return node;
            }
            else if (c == node.c)
            {   
                node.mid = put(node.mid, key.substring(1), value);
                return node;
            }
            else if (c < node.c)
            {   
                node.left = put(node.left, key, value);
                return node;
            }
            else
            {
                node.right = put(node.right, key, value);
                return node;
            }
        }
        else
        {
            if (node == null)
            {
                return new GenericForTSTNode(c, value);
            }
            else
            {   
                return node;
            }
        }
    }
}