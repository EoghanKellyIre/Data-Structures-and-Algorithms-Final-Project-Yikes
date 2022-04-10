
/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  From earlier assignment Semester 1
 *
 *  @author Eoghán Kelly
 *
 *************************************************************************/
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    /**
     * Private node class.
     */
    private class Node {
        private Key key;           // sorted by key
        private String val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, String val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() { return size() == 0; }

    // return number of key-value pairs in BST
    public int size() { return size(root); }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

	/**
     *  Search BST for given key.
     *  Does there exist a key-value pair with given key?
     *
     *  @param key the search key
     *  @return true if key is found and false otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     *  Search BST for given key.
     *  What is the value associated with given key?
     *
     *  @param key the search key
     *  @return value associated with the given key if found, or null if no such key exists.
     */
    public String get(Key key) { return get(root, key); }

    private String get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     *  Insert key-value pair into BST.
     *  If key already exists, update with new value.
     *
     *  @param key the key to insert
     *  @param val the value associated with key
     */
    public void put(Key key, String val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, String val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    /**
     * Print all keys of the tree in a sequence, in-order.
     */
    public String printKeysInOrder()
    {
    	// returns required value if given empty tree
    	if (isEmpty()) return "()";
    	// calls private function
    	return printKeysInOrder(root);
    }
    
    private String printKeysInOrder(BST<Key, Value>.Node node)
    {
    	// returns required value if given empty node
    	if (node == null)
    	{
    		return "";
    	}
    	else
    	{
    		// Uses recursion to print the nodes to the left, then the node and then the node to the right.
    		StringBuilder s = new StringBuilder();
    		s.append(printKeysInOrder(node.left));
    		System.out.println(node.val.toString());
    		s.append(printKeysInOrder(node.right));	
			return s.toString();
    	}
	}

	/**
     * Deletes a key from a tree (if the key is in the tree).
     * Note that this method works symmetrically from the Hibbard deletion:
     * If the node to be deleted has two child nodes, then it needs to be
     * replaced with its predecessor (not its successor) node.
     *
     * @param key the key to delete
     */
    public void delete(Key key)
    {
    	// returns if given empty tree
    	if (isEmpty())
    	{
    		return;
    	}
    	// deletes tree if size of the tree is one and key is the same as root
    	else if(this.size()==1 & key==root.key)
    	{
    		root = null;
    	}
    	// calls private recursive function
    	else 
    	{
    		delete(root, key);
    	}
    }

	private BST<Key, Value>.Node delete(BST<Key, Value>.Node node, Key key)
	{
		// returns if the node is empty
		if (node == null)
		{
			return null;
		}
		// Compares the key with the current node to find out which side of the tree to follow
		int compareNode = key.compareTo(node.key);
		if (compareNode < 0)
		{
			node.left = delete(node.left, key);
		}
		else if (compareNode > 0)
		{	
			node.right = delete(node.right, key);
		}
		// returns if the node is null
		else
		{ 
			if (node.left == null)
			{
				return node.right;
			}
			if (node.right == null)
			{
				return node.left;
			}
			// when found the node it deletes the node by updating the links between the nodes and using the floor and deleteMax functions
			Node t = node;
			node = floor(t.left, key);
			node.left = deleteMax(t.left);
			node.right = t.right;
		}
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	 
	private Node deleteMax(Node x)
	{
		if (x.right == null)
		{
			return x.left;
		}
		x.right = deleteMax(x.right);
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}
	
	private Node floor(Node x, Key key)
	{
		if (x == null)
		{
			return null;
		}
		Node t = floor(x.right, key);
		if (t != null)
		{
			return t;
		}
		else 
		{
			return x;
		}
	}
}