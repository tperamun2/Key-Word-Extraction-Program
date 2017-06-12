import java.util.Iterator;
import java.util.LinkedList;

public class AVLTree implements AVLTreeInterface {

	private AVLnode root; // Empty tree
	private int count = 0; // counts the number of entries not the number of  nodes (since the leaves don't store anything)
	private int height = 0; // height of the tree
	private Comparator comp;

	public AVLTree(Comparator inputComparator) {

		this.comp = inputComparator;
		root = new AVLnode(null, null, null, null);

	}

	@Override
	/**
	 * returns true if the node at position p is a leaf, false otherwise
	 */
	public boolean external(Position p) {
		AVLnode t = (AVLnode) p;
		if (t.left() == null && t.right() == null)
			return true;
		else
			return false;

	}

	// checks the position and returns it as a node (checks if the right or left
	// nodes actually exist)
	private AVLnode check(Position p) throws IllegalArgumentException {

		if (!(p instanceof AVLnode))
			throw new IllegalArgumentException("Null p");

		AVLnode node = (AVLnode) p;

		if (node.left() == null) {
			throw new IllegalArgumentException("p does not exist in the tree");
		}
		if (node.right() == null) {
			throw new IllegalArgumentException("p does not exist in the tree");
		}

		return node;

	}

	// returns the position of the given node
	private Position position(AVLnode node) {
		return node;
	}

	@Override
	// returns the position of the left child of p
	public Position left(Position p) {

		AVLnode node = check(p);
		return position(node.left());
		// return (Position) node.left();

	}

	@Override
	// returns the position of the right child of p
	public Position right(Position p) {

		AVLnode node = check(p);
		return position(node.right());

	}

	@Override
	/**
	 * returns the position of the root
	 */
	public Position giveRoot() {
		return position(root);
	}

	// removes a node that has at least one leaf node
	private DictEntry removeExternal(AVLnode leaf) {
		AVLnode parent, nodeToLink;
		parent = leaf.parent();

		if (parent.left() == leaf)
			nodeToLink = parent.right();
		else
			nodeToLink = parent.left();
		// if parent is the root
		if (parent == root) {
			root = nodeToLink;
			root.setParent(null);
		}
		// 'parent' actually has another parent in this case
		else {
			if (parent.parent().left() == parent)
				parent.parent().setLeft(nodeToLink);

			else
				parent.parent().setRight(nodeToLink);

			nodeToLink.setParent(parent.parent());
		}

		return parent.getEntry();

	}

	// traverses the right subtree of the node to be deleted
	// and gets the smallest element of that right subtree (which is by going
	// all the way left in that right subtree)
	private AVLnode minTraverseLeft(AVLnode node) {

		if (node.left().getEntry() == null)
			return node;
		else
			return minTraverseLeft(node.left());
	}

	// recursive method to delete an entry if the entry exists
	private DictEntry removeEntry(AVLnode node, Object key) throws AVLtreeException {

		if (comp.compare(key, node.getEntry().key()) == 0) {
			// case 1: node has a leaf
			if (node.left().getEntry() == null)
				return removeExternal(node.left());
			else if (node.right().getEntry() == null)
				return removeExternal(node.right());
			// case 2: node doesn't have a leaf

			else if (node.left().getEntry().key() != null && node.right().getEntry().key() != null) {

				AVLnode minNode = minTraverseLeft(node.right());
				node.setEntry(minNode.getEntry());
				return removeExternal(minNode.left());
			}
		} else if (comp.compare(key, node.getEntry().key()) < 0)
			return removeEntry(node.left(), key);
		else if (comp.compare(key, node.getEntry().key()) > 0)
			return removeEntry(node.right(), key);

		return node.getEntry();
	}

	@Override
	// Delete method which checks if the key exists in the tree,if found remove
	// it, else throw an exception
	public DictEntry remove(Object key) throws AVLtreeException {

		if (find(key) == null)
			throw new AVLtreeException("Key does not exist");

		else {
			count--;
			return removeEntry(root, key);
		}
	}

	@Override
	/**
	 * method which returns the dictionary entry corresponding to the input key,
	 * or null if there is no entry with the input key. Calls the private
	 * findKey method which actually finds if the key is there, and returns it
	 * in this method
	 */
	public DictEntry find(Object key) {

		if (root.getEntry() != null) {
			return findKey(root, key);
		}

		return null;
	}

	/**
	 * private method to be used for the public find method
	 * 
	 * @param root
	 * @param node
	 * @return the entry if it contains the node contains the key searched for
	 */
	private DictEntry findKey(AVLnode root, Object key) {

		if (comp.compare(key, root.getEntry().key()) == 0)
			return root.getEntry();

		else if (comp.compare(key, root.getEntry().key()) < 0) {
			if (root.left().getEntry() == null)
				return null;
			else
				return findKey(root.left(), key);
		} else if (comp.compare(key, root.getEntry().key()) > 0) {
			if (root.right().getEntry() == null)
				return null;
			else
				return findKey(root.right(), key);

		}
		return null;
	}

	/**
	 * Private method which Traverses the tree to find a place to insert. Goes
	 * left if the string compariosn is less than 0, right if it is greater than
	 * 0, and throws an Exception if the Key already exists
	 * 
	 * @param node
	 * @param newNode
	 * @return
	 * @throws AVLtreeException
	 */

	private AVLnode traverseToAdd(AVLnode node, AVLnode newNode) throws AVLtreeException {

		if (comp.compare(newNode.getEntry().key(), node.getEntry().key()) == 0) {
			throw new AVLtreeException("Key already exists");
		}
		if (comp.compare(newNode.getEntry().key(), node.getEntry().key()) < 0) {
			if (node.left().getEntry() == null) {
				node.setLeft(newNode);
				newNode.setParent(node); // new
				count++;
			} else
				traverseToAdd(node.left(), newNode);
		} else if (comp.compare(newNode.getEntry().key(), node.getEntry().key()) > 0) {
			if (node.right().getEntry() == null) {
				node.setRight(newNode);
				newNode.setParent(node); // new
				count++;
			} else
				traverseToAdd(node.right(), newNode);

		}

		return newNode;
	}

	@Override
	/**
	 * Inserts a new entry into the AVL tree with the given key and value. Don’t
	 * forget to rebalance using triNode restructuring. Throws AVLtreeException
	 * if an entry wit the input key is already in the tree.
	 */
	public void insertNew(Object key, Object value) throws AVLtreeException {

		DictEntry dEntry = new DictEntry(key, value);
		AVLnode left, right;
		left = new AVLnode(null, null, null, null);
		right = new AVLnode(null, null, null, null);

		AVLnode newNode = new AVLnode(dEntry, null, left, right);
		// newNode.setParent(root);
		left.setParent(newNode);
		right.setParent(newNode);

		if (root.getEntry() == null) {
			root = newNode;
			count++;
			return;
		}
		traverseToAdd(root, newNode);
	}

	/**
	 * returns the child of node with larger height
	 * 
	 * @param node
	 * @return
	 */
	private AVLnode tallerChild(AVLnode node) throws AVLtreeException {

		if (node.left() != null && node.right() != null) {
			if (node.left().getHeight() > node.right().getHeight())
				return node.left();
			else if (node.left().getHeight() < node.right().getHeight())
				return node.right();
			else if (node.left().getHeight() == node.right().getHeight()) {
				if (node.parent().left() == node)
					return node.left();
				else
					return node.right();

			}

		} else if ((node.left() != null || node.right() != null) && !(node.left() != null && node.right() != null)) {
			if (node.left() != null)
				return node.left();
			if (node.right() != null)
				return node.right();

		}

		return null;

	}

	/*
	 * Private method used by the modifyValue method to traverse and modify the
	 * value to a new value if the entry was found
	 */
	private AVLnode modify(Object key, Object valueNew, AVLnode node) {
		if (comp.compare(key, node.getEntry().key()) == 0) {
			node.setEntry(new DictEntry(key, valueNew));
			return node;
		} else if (comp.compare(key, node.getEntry().key()) < 0) {
			return modify(key, valueNew, node.left());
		} else
			return modify(key, valueNew, node.right());
	}

	@Override
	/**
	 * A method which modifies the value of the entry with the input key to the
	 * input valueNew. If the key is not in the dictionary, this method throws
	 * AVLtreeException.
	 */
	public void modifyValue(Object key, Object valueNew) throws AVLtreeException {
		if (find(key) == null)
			throw new AVLtreeException("Entry does not exist");
		else
			modify(key, valueNew, root);

	}

	@Override
	/*
	 * @see AVLTreeInterface#isEmpty()
	 * 
	 * @return boolean returns true if it is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (count == 0)
			return true;
		else
			return false;

	}

	@Override
	/**
	 * height from the root all the way to the deepest leaf node in the tree
	 * 
	 * An empty tree has height -1 A tree with just 1 node (the root) has height
	 * 0
	 */

	public int treeHeight() {
		if (root == null)
			return height - 1;

		if (root.left() == null && root.right() == null)
			return height;
		if (root.left() != null && root.right() != null) {

			if (root.left().getHeight() >= root.right().getHeight()) {
				root.left().getHeight();
				treeHeight();
				height++;

			} else {
				root.right().getHeight();
				treeHeight();
				height++;
			}

		} else if ((root.left() != null || root.right() != null) && !(root.left() != null && root.right() != null)) {
			root.left().getHeight();
			root.right().getHeight();
			height++;
		}

		return height;

	}

	@Override
	/**
	 * returns an iterator for the list
	 */
	public Iterator<DictEntry> inOrder() {
		LinkedList<DictEntry> tempList = new LinkedList<DictEntry>();
		inOrderTraverse(root, tempList);

		return tempList.iterator();

	}

	/**
	 * 
	 * @param node
	 * @param tempList
	 *            private method to traverse the nodes in the tree in order to
	 *            add them to the Linked list
	 */
	private void inOrderTraverse(AVLnode node, LinkedList<DictEntry> tempList) {
		if (node.getEntry() != null) {
			inOrderTraverse(node.left(), tempList);
			tempList.add(node.getEntry());
			inOrderTraverse(node.right(), tempList);

		}
	}

	private void inOrderReverse(AVLnode node, LinkedList<DictEntry> list) {
		if (node.getEntry() != null) {

			inOrderReverse(node.right(), list);
			list.add(node.getEntry());
			inOrderReverse(node.left(), list);

		}
	}

	@Override
	/**
	 * Takes the largest n numbers in the LinkedList and returns its Iterator
	 */
	public Iterator<DictEntry> findnLargestKeys(int n) {
		LinkedList<DictEntry> list = new LinkedList<DictEntry>();
		inOrderReverse(root, list);
		LinkedList<DictEntry> reList = new LinkedList<DictEntry>();

		for (int i = 0; i < n; i++)
			reList.add(list.get(i));

		return reList.listIterator();

	}

	/**
	 * Balances the Tree if insertion/deletion causes the tree to be unbalanced
	 * 
	 * @param x-is
	 *            the taller child of y
	 * @param y-is
	 *            the taller child of z
	 * @param z-unbalanced
	 *            node
	 * @return
	 */
	private AVLnode triNodeRest(AVLnode x, AVLnode y, AVLnode z) {

		AVLnode a = null, b = null, c = null;
		if (z.right() == y && y.left() == x) {
			a = z;
			b = x;
			c = y;
		}
		if (z.right() == y && y.right() == x) {
			a = z;
			b = y;
			c = x;
		}
		if (z.left() == y && y.left() == x) {
			a = x;
			b = y;
			c = z;
		}
		if (z.left() == y && y.right() == x) {
			a = y;
			b = x;
			c = z;
		}

		if (z == root) {
			root = b; // root changes after trinode restructure
			b.setParent(null);
		}
		// reconnect parent of z to the node replacing z
		else {
			if (z.parent().left() == z)
				z.parent().setLeft(b);
			else
				z.parent().setRight(b);
		}
		// left orphan present
		if (b.left() != x && b.left() != y) {
			a.setRight(b.left());
			// a.right()=b;
			// b.parent=a;
		}
		// right orphan present
		if (b.right() != x && b.right() != y) {
			c.setLeft(b.right());
		}

		b.setLeft(a);
		b.setRight(c);

		return b;
	}

	@Override
	/**
	 * returns the number of entries in the tree
	 */
	public int size() {
		return count;
	}

}
