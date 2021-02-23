package cs146F20.Boddukuri.project4;

/**
 * This class serves as the implementation of a Red Black Tree.
 * @author Rashmi Boddukuri
 * @version 10/6/2020
 */
public class RedBlackTree<Key extends Comparable<Key>> 
{
	private static RedBlackTree.Node<String> root; //instance variable pointing to the root Node
	
	/**
	 * This is the Node class that contains all Node properties.
	 */
	public static class Node<Key extends Comparable<Key>> 
	{
		Key key;
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		boolean isRed;
		int color; //0 is red, 1 is black

		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
		}

		public int compareTo(Node<Key> n) { // this < that <0
			return key.compareTo(n.key); // this > that >0
		}

		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null)
				return true;
			if (this.equals(root))
				return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Determines whether input node is a leaf.
	 * @param Node n the input node
	 * @return true if leaf, false if not
	 */
	public boolean isLeaf(RedBlackTree.Node<String> n) {
		if (n.equals(root) && n.leftChild == null && n.rightChild == null)
			return true;
		if (n.equals(root))
			return false;
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;
	}

	/**
	 * This is the visitor interface that calls the visit method.
	 */
	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n the visited node
		 */
		void visit(Node<Key> n);
	}

	/**
	 * Visits the node by printing its key.
	 * @param Node n the input node
	 */
	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	/**
	 * Visits the root. Then calls the below printTree method for subsequent nodes.
	 */
	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);
	}
	
	/**
	 * Prints the leaves of the input node in a recursive manner.
	 * @param Node node the input node
	 */
	public void printTree(RedBlackTree.Node<String> node) {
		System.out.print(node.key);
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}

	
	/**
	 * Places a new node in the RB tree and colors it red.
	 * @param String data the input string
	 */
	public void addNode(String data) 
	{ 
		// this < that <0. this > that >0
		Node<String> nodeToAdd = new Node<String>(data);
		Node<String> current = root;
		Node<String> previous = null;
		
		while(current != null) //happens if the current null exists
		{
			previous = current; //sets current to previous to find the next node
			if(nodeToAdd.compareTo(current) < 0)
			{
				current = current.leftChild;
			}
			else
			{
				current = current.rightChild;
			}
		}
		nodeToAdd.parent = previous; //the parent of the nodeToAdd is set as previous
		if(previous == null) //when there is no previous node it means that we add to root
		{
			root = nodeToAdd;
		}
		else if(nodeToAdd.compareTo(previous) < 0)
		{
			previous.leftChild = nodeToAdd;
		}
		else
		{
			previous.rightChild = nodeToAdd;
		}
		nodeToAdd.rightChild = null; //the children of the node added are both null
		nodeToAdd.leftChild = null;
		nodeToAdd.color = 0; //node added is colored red
		fixTree(nodeToAdd); //the red black tree is fixed to satisfy the rbt requirements
	}

	/**
	 * Inserts a string by calling addNode method.
	 * @param String data the input string
	 */
	public void insert(String data) {
		addNode(data);
	}
	
	/**
	 * Searches for a given string in the RBT tree.
	 * @param String k the input string
	 * @return Node current the node searched for
	 */
	public RedBlackTree.Node<String> lookup(String k){ 
		Node<String> current = root;
		
		if(current == null)
		{
			return current; //basically returns null because when root doesn't exist the tree doesn't exist as well
		}
		else
		{
			while(current != null && current.key.compareTo(k) != 0) //as long as there is a node that exists and while it doesn't equal input string
			{
				if(k.compareTo(current.key) > 0)
				{
					current = current.rightChild;
				}
				else
				{
					current = current.leftChild;
				}
			}
			return current;
		}
	}

	
	/**
	 * Returns sibling node of the parameter If the sibling does not exist, then return null. 
	 * @param Node n the input node
	 * @return nParent.rightChild if sibling is right child, nParent.leftChild if sibling is left child, null if sibling doesn't exist.
	 */
	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) 
	{
		Node<String> nParent = n.parent;
		if(nParent != null)
		{
			if(isLeftChild(nParent, n))
			{
				return nParent.rightChild;
			}
			return nParent.leftChild;
		}
		return null;
	}


	/**
	 * Returns aunt of the parameter or the sibling of the parent node. If the aunt node does not exist, then return null. 
	 * @param Node n the input node
	 * @return grandparent.rightChild if input is left child, grandparent.leftChild if input is right child, null otherwise.
	 */
	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		Node<String> grandparent = getGrandparent(n);
		if(grandparent != null)
		{
			if(isLeftChild(grandparent, n.parent))
			{
				return grandparent.rightChild;
			}
			return grandparent.leftChild;
		}
		return null;
	}

	/**
	 * Returns the parent of your parent node, if it doesn’t exist return null. 
	 * @param Node n the input node
	 * @return n.parent.parent if there exists a grandparent, null otherwise.
	 */
	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		if(n.parent != null && n.parent.parent != null)
		{
			return n.parent.parent;
		}
		return null;
	}

	/**
	 * Rotates left around the node parameter.
	 * @param Node n the input node
	 */
	public void rotateLeft(RedBlackTree.Node<String> n) 
	{
		Node<String> nRightChild = n.rightChild; //setting the right child of n
		n.rightChild = nRightChild.leftChild; //making the nRightChild's left subtree into n's right subtree
		if(nRightChild.leftChild != null)
		{
			nRightChild.leftChild.parent = n;
		}
		nRightChild.parent = n.parent; //linking parent of n to nRightChild
		if(n.parent == null)
		{
			root = nRightChild;
		}
		else if(n == n.parent.leftChild)
		{
			n.parent.leftChild = nRightChild;
		}
		else
		{
			n.parent.rightChild = nRightChild;
		}
		nRightChild.leftChild = n; //putting n on nRightChild's left
		n.parent = nRightChild;
	}

	
	/**
	 * Rotates right around the node parameter.
	 * @param Node n the input node
	 */
	public void rotateRight(RedBlackTree.Node<String> n) 
	{
		Node<String> nLeftChild = n.leftChild; //setting the left child of n
		n.leftChild = nLeftChild.rightChild; //making the nLeftChild's right subtree into n's left subtree
		if(nLeftChild.rightChild != null)
		{
			nLeftChild.rightChild.parent = n;
		}
		nLeftChild.parent = n.parent; //linking parent of n to nLeftChild
		if(n.parent == null)
		{
			root = nLeftChild;
		}
		else if(n == n.parent.rightChild)
		{
			n.parent.rightChild = nLeftChild;
		}
		else
		{
			n.parent.leftChild = nLeftChild;
		}
		nLeftChild.rightChild = n; //putting n on nleftChild's right
		n.parent = nLeftChild;
	}

	/**
	 * Recursively traverses the tree to make it a Red Black tree.
	 * @param Node current the input node
	 */
	public void fixTree(RedBlackTree.Node<String> current) {
		if(current == root)
		{
			current.color = 1; //current is the root node, then change it to black and exit
		}
		else if(current.color == 0 && current.parent.color == 0) //if current and parent of current are red go through more cases, otherwise exit
		{
			//case 1: aunt node is empty or black, then there are four sub cases to consider.
			if(getAunt(current) == null || getAunt(current).color == 1)
			{
				if(getGrandparent(current).leftChild == current.parent && current == current.parent.rightChild) //when parent is grandparent's left child and current is parent's right child
				{
					//Rotate parent left, then recursively fix tree starting from the original parent.
					rotateLeft(current.parent);
					current = current.parent;
					fixTree(current);
				}
				else if(getGrandparent(current).rightChild == current.parent && current == current.parent.leftChild) //when parent is grandparent's right child and current is parent's left child
				{
					//Rotate parent right, then recursively fix tree starting from the original parent. 
					rotateRight(current.parent);
					current = current.parent;
					fixTree(current);
				}
				else if(getGrandparent(current).leftChild == current.parent && current == current.parent.leftChild) //when parent is grandparent's left child and current is parent's left child
				{
					//Make parent black, make grandparent red, rotate grandparent to the right and exit.
					current.parent.color = 1;
					getGrandparent(current).color = 0;
					rotateRight(getGrandparent(current));
				}
				else if(getGrandparent(current).rightChild == current.parent && current == current.parent.rightChild) //when parent is grandparent's right child and current is parent's right child
				{
					//Make parent black, make grandparent red, rotate grandparent to the left and exit.
					current.parent.color = 1;
					getGrandparent(current).color = 0;
					rotateLeft(getGrandparent(current));
				}
			}
			else //case 2: if aunt is red, make parent and aunt black, make the grandparent red and recursively fix tree starting with the grandparent.
			{
				current.parent.color = 1;
				getAunt(current).color = 1;
				getGrandparent(current).color = 0;
				current = getGrandparent(current);
				fixTree(current);
			}
			
		}
	}

	/**
	 * Checks whether a node is empty.
	 * @param Node n the input node
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty(RedBlackTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks to see whether the input child node is a child to the input parent node.
	 * @param Node parent the input parent node
	 * @param Node child the input child node
	 * @return true if left child, false if not/
	 */
	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child) {
		if (child.compareTo(parent) < 0) {// child is less than parent
			return true;
		}
		return false;
	}

	/**
	 * Calls the preOrderVisit method below.
	 * @param Visitor v input visitor interface
	 */
	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}
	
	/**
	 * Visits each node in the red black tree recursively accrding to preorder.
	 * @param Node n the input node
	 * @param Visitor v input visitor interface
	 */
	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
}
