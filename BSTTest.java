import java.io.*;
import java.util.ArrayList;


public class BSTTest {
     BufferedReader inputFileReader;
     BST<String> BST = new BST<>();


    public static void main(String [] args) {
        BSTTest test = new BSTTest("datafile.txt");
    }

    /******************************************************************************************
     * this program is main and will call readNextRecord until the file has been read.
     * then it will print the content of the array list and each keywords linked-list***
     * ***************************************************************************************/

    public BSTTest(String filename) {
        try { // in try/catch block to catch FileNotFoundException
            // from java source code of FileReader(String)
            // Java Def: public FileReader(String fileName) throws FileNotFoundException
            inputFileReader = new BufferedReader(new FileReader(filename));



            if (inputFileReader == null) {
                System.out.println("Error: you must open the file first!");
            }
            else {
                while (readNextRecord());
            }



            // print the array to the console
                    BST.inorder();



        }
        catch (IOException e) {
            // e.printStackTrace();
        }
    }

    /*
     * this method will read the next record in the file and add to keyword arrayList
     * if the keyword is not found. it will also add articles to the linked lists contained
     * in the arrayList
     * *
     */
    public boolean readNextRecord() {

        try {
            String data = inputFileReader.readLine();
            if (data == null) return false;
            int titleId = 0 ;
            titleId = Integer.parseInt(data);
            String title = inputFileReader.readLine();
            String author = inputFileReader.readLine();

            int numberOfKeys = Integer.parseInt(inputFileReader.readLine());


            Article a = new Article(titleId, title, author);

            for(int i = 0; i < numberOfKeys; i++){
                String keyWordString = inputFileReader.readLine();
                keyWordString.trim();
                Element keyWordObj = new Element(keyWordString);


                if(!BST.search(keyWordString)){
                   BST.insert(keyWordString);
                   BST.TreeNode treeNode = BST.getTreeNode(keyWordString);
                   Element element = treeNode.getTreeNodeElement();
                   element.addToLinkedList(a);
                }
                else {
                    BST.TreeNode treeNode = BST.getTreeNode(keyWordString);
                    Element element = treeNode.getTreeNodeElement();
                    element.addToLinkedList(a);

                }


            }

            inputFileReader.readLine();

        }
        catch (IOException e) {
            // System.out.printf("%s\n", e);
        }
        return true;
    }



    //This method will look for a keyword and return true if it is found
    public boolean contains(String keyWord, ArrayList<Element> list){

        keyWord.trim();

        if(list.isEmpty()){
            return false;
        }
        for(int i = 0 ; i  < list.size(); i++){

            String keyWordInIndex = list.get(i).getKeyWord();

            if( keyWordInIndex.equals(keyWord)){
                return true;
            }
            else{
                continue;
            }
        }

        return false;
    }







}


/**********************************************************************************************
 * This is the Article class. This class contains the id, title name, and author name of
 * an article found in the data file
 *******************************************************************************************/
class Article {
    private int id;
    private String title;
    private String author;

    public Article(int i, String t, String a) {
        id = i;
        title = t;
        author = a;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s\n", id, title, author);
    }

    public int getId(){
        return id;
    }

    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }
}


/**********************************************************************************************
 * This is the Element class. This class contains the key word and linked list of each
 * key word found in the data file.
 *******************************************************************************************/
class Element  {

    private MyLinkedList<Article> articleLinkedList = new MyLinkedList<>();

    private String keyWord = "";

    Element(){

        keyWord = "";
    }

    Element(String keyWord){
        this.keyWord = keyWord;

    }

    public void addToLinkedList(Article article){

        articleLinkedList.addFirst(article);
    }

    public void printLinkedList(){
       System.out.printf(articleLinkedList.toString());

    }


    public String getKeyWord(){
        return keyWord;
    }

    public MyLinkedList getLinkedList(){
        return articleLinkedList;
    }
}



/****************************************************************************************************
 * Past this point is classes provided from the textbook
 * **************************************************************************************************/
class MyLinkedList<E> extends MyAbstractList<E> {
    private Node<E> head, tail;

    /** Create a default list */
    public MyLinkedList() { }

    /** Create a list from an array of objects */
    public MyLinkedList(E[] objects) {
        super(objects);
    }

    /** Return the head element in the list */
    public E getFirst() {           /* O(1) */
        if (size == 0) {
            return null;
        }
        else {
            return head.element;
        }
    }

    /** Return the last element in the list */
    public E getLast() {            /* O(1) */
        if (size == 0) {
            return null;
        }
        else {
            return tail.element;
        }
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {         /* O(1) */
        Node<E> newNode = new Node<>(e); // Create a new node
        newNode.next = head; // link the new node with the head
        head = newNode; // head points to the new node
        size++; // Increase list size

        if (tail == null) // The new node is the only node in list
            tail = head;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e); // Create a new node for e

        if (tail == null) {
            head = tail = newNode; // The only node in list
        }
        else {
            tail.next = newNode; // Link the new node with the last node
            tail = tail.next; // tail now points to the last node
        }

        size++; // Increase size
    }

    @Override /** Add a new element at the specified index
                * in this list. The index of the head element is 0 */
    public void add(int index, E e) {
    // Implemented in Section 24.4.3.3, so omitted here
        if (index == 0) addFirst(e); // Insert first
        else if (index >= size) addLast(e); // Insert last
        else { // Insert in the middle
            Node < E > current =  head;
            for (int i = 1; i < index; i++)
              current = current.next;
            Node < E > temp = current.next;
            current.next = new Node < E > (e);
            (current.next).next = temp;
            size++;
      }
    }

    /** Remove the head node and
    * return the object that is contained in the removed node. */
    public E removeFirst() {
    // Implemented in Section 24.4.3.4, so omitted here
        if (size == 0) return null; // Nothing to delete
        else {
            Node<E> temp = head; // Keep the first node temporarily
            head = head.next; // Move head to point to next node
            size--; // Reduce size by 1
            if (head == null) tail = null; // List becomes empty
            return temp.element; // Return the deleted element
        }
    }

    /** Remove the last node and
    * return the object that is contained in the removed node. */
    public E removeLast() {
    // Implemented in Section 24.4.3.5, so omitted here
        if (size == 0) return null; // Nothing to remove
        else if (size == 1) { // Only one element in the list
            Node<E> temp = head;
            head = tail = null; // list becomes empty
            size = 0;
            return temp.element;
        }
        else {
            Node<E> current = head;

            for (int i = 0; i < size - 2; i++)
                current = current.next;

            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
    }
    }

    @Override /** Remove the element at the specified position in this
            * list. Return the element that was removed from the list. */
    public E remove(int index) {
    // Implemented earlier in Section 24.4.3.6, so omitted here
        if (index < 0 || index >= size) return null; // Out of range
        else if (index == 0) return removeFirst(); // Remove first
        else if (index == size - 1) return removeLast(); // Remove last
        else {
            Node<E> previous = head;

            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }

            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\n\t- ");

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element );
            current = current.next;

            if(current != null){
                result.append("\t- ");
            }

        }

        return result.toString();
    }




    @Override /** Clear the list */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override /** Return true if this list contains the element e */
    public boolean contains(E e) {
        System.out.println("Implementation left as an exercise");
        return true;
    }

    @Override /** Return the element at the specified index */
    public E get(int index) {
        System.out.println("Implementation left as an exercise");
        return null;
    }

    @Override /** Return the index of the head matching element
            * in this list. Return -1 if no match. */
    public int indexOf(E e) {
        System.out.println("Implementation left as an exercise");
        return 0;
    }

    @Override /** Return the index of the last matching element
    * in this list. Return -1 if no match. */
    public int lastIndexOf(E e) {
        System.out.println("Implementation left as an exercise");
        return 0;
    }

    @Override /** Replace the element at the specified position
        * in this list with the specified element. */
    public E set(int index, E e) {
        System.out.println("Implementation left as an exercise");
        return null;
    }

    @Override /** Override iterator() defined in Iterable */
    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator
        implements java.util.Iterator<E> {
        private Node<E> current = head; // Current index

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            return e;
        }

        @Override
        public void remove() {
            System.out.println("Implementation left as an exercise");
        }
    }

    // This class is only used in LinkedList, so it is private.
    // This class does not need to access any
    // instance members of LinkedList, so it is defined static.
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }
 }

abstract class MyAbstractList<E> implements MyList<E> {
    protected int size = 0; // The size of the list

    /** Create a default list */
    protected MyAbstractList() { }

    /** Create a list from an array of objects */
    protected MyAbstractList(E[] objects) {
        for (int i = 0; i < objects.length; i++)
        add(objects[i]);
    }

    @Override /** Add a new element at the end of this list */
    public void add(E e) {
        add(size, e);
    }

    @Override /** Return true if this list doesn't contain any elements */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override /** Return the number of elements in this list */
    public int size() {
        return size;
    }

    @Override /** Remove the first occurrence of the element e
                * from this list. Shift any subsequent elements to the left.
                * Return true if the element is removed. */
    public boolean remove(E e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        }
        else{
            return false;
        }

    }
}

// LISTING 24.1 MyList.java
interface MyList<E> extends Iterable<E> {
    /** Add a new element at the end of this list */
    public void add(E e);

    /** Add a new element at the specified index in this list */
    public void add(int index, E e);

    /** Clear the list */
    public void clear();

    /** Return true if this list contains the element */
    public boolean contains(E e);

    /** Return the element from this list at the specified index */
    public E get(int index);

    /** Return the index of the first matching element in this list.
      * Return -1 if no match. */
    public int indexOf(E e);

    /** Return true if this list doesn't contain any elements */
    public boolean isEmpty();

    /** Return the index of the last matching element in this list
      * Return -1 if no match. */
    public int lastIndexOf(E e);

    /** Remove the first occurrence of the element e from this list.
      * Shift any subsequent elements to the left.
      * Return true if the element is removed. */

    public boolean remove(E e);

     /** Remove the element at the specified position in this list.
      * Shift any subsequent elements to the left.
      * Return the element that was removed from the list. */
    public E remove(int index);

    /** Replace the element at the specified position in this list
     * with the specified element and return the old element. */
    public Object set(int index, E e);

    /** Return the number of elements in this list */
    public int size();
}

/*****************************************************************************************************
 * from this point on all classes and methods are associated with binary search tree
 * ***************************************************************************************************
 *
 */

class TreeNode<E> {
    protected E element;
    protected TreeNode<E> left;
    protected TreeNode<E> right;
    Element treeNodeElement;

    public TreeNode(E e) {
        element = e;
        String keyWord = (String) e;
        treeNodeElement = new Element(keyWord);
    }

    public Element getTreeNodeElement(){
        return treeNodeElement;
    }

}


    interface Tree<E> extends Iterable<E> {
        /**
         * Return true if the element is in the tree
         */
        public boolean search(E e);

        /**
         * Insert element e into the binary search tree.
         * Return true if the element is inserted successfully.
         */
        public boolean insert(E e);

        /**
         * Delete the specified element from the tree.
         * Return true if the element is deleted successfully.
         */
        public boolean delete(E e);

        /**
         * Inorder traversal from the root
         */
        public void inorder();

        /**
         * Postorder traversal from the root
         */
        public void postorder();

        /**
         * Preorder traversal from the root
         */
        public void preorder();

        /**
         * Get the number of nodes in the tree
         */
        public int getSize();

        /**
         * Return true if the tree is empty
         */
        public boolean isEmpty();
    }


    abstract class AbstractTree<E>
            implements Tree<E> {
        @Override
        /** Inorder traversal from the root*/
        public void inorder() {
        }

        @Override
        /** Postorder traversal from the root */
        public void postorder() {
        }

        @Override
        /** Preorder traversal from the root */
        public void preorder() {
        }

        @Override
        /** Return true if the tree is empty */
        public boolean isEmpty() {
            return getSize() == 0;
        }
    }



    class BST<E extends Comparable<E>>
            extends AbstractTree<E> {
        protected TreeNode<E> root;
        protected int size = 0;

        /**
         * Create a default binary search tree
         */
        public BST() {
        }

        /**
         * Create a binary search tree from an array of objects
         */
        public BST(E[] objects) {
            for (int i = 0; i < objects.length; i++)
                insert(objects[i]);
        }

        @Override
        /** Return true if the element is in the tree */
        public boolean search(E e) {
            TreeNode<E> current = root; // Start from the root

            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    current = current.right;
                } else // element matches current.element
                    return true; // Element is found
            }

            return false;

        }

        TreeNode getTreeNode(E e){

        TreeNode<E> current = root; // Start from the root

            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    current = current.right;
                } else // element matches current.element
                    return current; // Element is found
            }

           return null;

        }



        @Override
        /** Insert element e into the binary search tree.
         * Return true if the element is inserted successfully. */
        public boolean insert(E e) {
            if (root == null)
                root = createNewNode(e); // Create a new root
            else {
                // Locate the parent node
                TreeNode<E> parent = null;
                TreeNode<E> current = root;
                while (current != null)
                    if (e.compareTo(current.element) < 0) {
                        parent = current;
                        current = current.left;
                    } else if (e.compareTo(current.element) > 0) {
                        parent = current;
                        current = current.right;
                    } else
                        return false; // Duplicate node not inserted

                // Create the new node and attach it to the parent node
                if (e.compareTo(parent.element) < 0)
                    parent.left = createNewNode(e);
                else
                    parent.right = createNewNode(e);
            }

            size++;
            return true; // Element inserted successfully
        }

        protected TreeNode<E> createNewNode(E e) {
            return new TreeNode<>(e);
        }

        @Override
        /** Inorder traversal from the root */
        public void inorder() {
            inorder(root);
        }

        /**
         * Inorder traversal from a subtree
         */
        protected void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            System.out.println();
            System.out.print(root.element);
            Element element = root.getTreeNodeElement();
            element.printLinkedList();
            inorder(root.right);
        }

        @Override
        /** Postorder traversal from the root */
        public void postorder() {
            postorder(root);
        }

        /**
         * Postorder traversal from a subtree
         */
        protected void preorder(TreeNode<E> root) {
            if (root == null) return;
            postorder(root.left);
            System.out.println();
            System.out.print(root.element);
            Element element = root.getTreeNodeElement();
            element.printLinkedList();
            postorder(root.right);
        }

        @Override
        /** Preorder traversal from the root */
        public void preorder() {
            preorder(root);
        }

        /**
         * Preorder traversal from a subtree
         */
        protected void postorder(TreeNode<E> root) {
            if (root == null) return;
            System.out.println();
            System.out.print(root.element);
            Element element = root.getTreeNodeElement();
            element.printLinkedList();
            preorder(root.left);
            preorder(root.right);
        }

        /**
         * This inner class is static, because it does not access
         * any instance members defined in its outer class
         */
        public static class TreeNode<E extends Comparable<E>> {
            protected E element;
            protected TreeNode<E> left;
            protected TreeNode<E> right;
            Element treeNodeElement;
            public TreeNode (E e) {
                element = e;
                String keyWord = (String) e;
                treeNodeElement = new Element(keyWord);
            }
            public Element getTreeNodeElement(){
                return treeNodeElement;
            }

        }

        @Override
        /** Get the number of nodes in the tree */
        public int getSize() {
            return size;
        }

        /**
         * Returns the root of the tree
         */
        public TreeNode<E> getRoot() {
            return root;
        }

        /**
         * Returns a path from the root leading to the specified element
         */
        public java.util.ArrayList<TreeNode<E>> path(E e) {
            java.util.ArrayList<TreeNode<E>> list =
                    new java.util.ArrayList<>();
            TreeNode<E> current = root; // Start from the root

            while (current != null) {
                list.add(current); // Add the node to the list
                if (e.compareTo(current.element) < 0) {
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    current = current.right;
                } else
                    break;
            }

            return list; // Return an array list of nodes
        }

        @Override
        /** Delete an element from the binary search tree.
         * Return true if the element is deleted successfully.
         * Return false if the element is not in the tree. */
        public boolean delete(E e) {
            // Locate the node to be deleted and also locate its parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                } else
                    break; // Element is in the tree pointed at by current
            }

            if (current == null)
                return false; // Element is not in the tree
            // Case 1: current has no left child
            if (current.left == null) {
                // Connect the parent with the right child of the current node
                if (parent == null) {
                    root = current.right;
                } else {
                    if (e.compareTo(parent.element) < 0)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
                }
            } else {
                // Case 2: The current node has a left child.
                // Locate the rightmost node in the left subtree of
                // the current node and also its parent.
                TreeNode<E> parentOfRightMost = current;
                TreeNode<E> rightMost = current.left;

                while (rightMost.right != null) {
                    parentOfRightMost = rightMost;
                    rightMost = rightMost.right; // Keep going to the right
                }

                // Replace the element in current by the element in rightMost
                current.element = rightMost.element;

                // Eliminate rightmost node
                if (parentOfRightMost.right == rightMost)
                    parentOfRightMost.right = rightMost.left;
                else
                    // Special case: parentOfRightMost == current
                    parentOfRightMost.left = rightMost.left;
            }

            size--;
            return true; // Element deleted successfully
        }

        @Override
        /** Obtain an iterator. Use inorder. */
        public java.util.Iterator<E> iterator() {
            return new InorderIterator();
        }

        // Inner class InorderIterator
        private class InorderIterator implements java.util.Iterator<E> {
            // Store the elements in a list
            private java.util.ArrayList<E> list = new java.util.ArrayList<>();
            private int current = 0; // Point to the current element in list

            public InorderIterator() {
                inorder(); // Traverse binary tree and store elements in list
            }

            /**
             * Inorder traversal from the root
             */
            private void inorder() {
                inorder(root);
            }

            /**
             * Inorder traversal from a subtree
             */
            private void inorder(TreeNode<E> root) {
                if (root == null) return;
                inorder(root.left);
                list.add(root.element);
                inorder(root.right);
            }

            @Override
            /** More elements for traversing? */
            public boolean hasNext() {
                if (current < list.size())
                    return true;

                return false;
            }

            @Override
            /** Get the current element and move to the next */
            public E next() {
                return list.get(current++);
            }

            @Override
            /** Remove the current element */
            public void remove() {
                delete(list.get(current)); // Delete the current element
                list.clear(); // Clear the list
                inorder(); // Rebuild the list
            }
        }

        /**
         * Remove all elements from the tree
         */
        public void clear() {
            root = null;
            size = 0;
        }
    }

