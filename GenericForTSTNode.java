public class GenericForTSTNode
//Implemented using Sedgewick and Waynes version found here https://algs4.cs.princeton.edu/52trie/TST.java.html
{
   //Instance Variables
   char c;
   Integer value;
   GenericForTSTNode left, mid, right;

   //Constructor
   public GenericForTSTNode(char c, Integer value)
   {
     this.value = value;
     this.c = c;
     left = null;
     mid = null;
     right = null;
   }
}