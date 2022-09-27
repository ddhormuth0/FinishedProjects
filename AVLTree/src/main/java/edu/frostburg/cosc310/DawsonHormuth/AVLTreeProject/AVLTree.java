package edu.frostburg.cosc310.DawsonHormuth.AVLTreeProject;

/**
 * Implements the AVLTreeProject interface,
 *
 * @author Dawson Hormuth
 */
public class AVLTree {

    //fields
    AVLTreeNode overallRoot;

    /**
     * Constructor for the tree with a specific root.
     *
     * @param root Root you want to be overall root
     */
    public AVLTree(AVLTreeNode root) {
        this.overallRoot = root;
    }

    /**
     * Constructor for the tree with a null root.
     */
    public AVLTree() {
        this.overallRoot = null;
    }

    /**
     * Sets the nodes height of the given node and its children.
     *
     * @param root Root that you want to set height of.
     * @return Integer of height for that node.
     */
    public int setNodeHeight(AVLTreeNode root) {
        //has no children
        if (root.left == null && root.right == null) {
            return 1;
        } else {
            //set height of left and right child
            //in if statements to avoid null child error
            if (root.left != null) {
                root.left.setHeight(setNodeHeight(root.left));

            }

            if (root.right != null) {
                root.right.setHeight(setNodeHeight(root.right));

            }

            if (root.right != null && root.left != null) {
                if (root.left.getHeight() > root.right.getHeight()) {
                    return root.left.getHeight() + 1;

                } else {
                    return root.right.getHeight() + 1;
                }
            } else if (root.left == null) {

                return root.right.getHeight() + 1;

            } else {

            }

            return root.left.getHeight() + 1;

        }

    }

    /**
     * Checks to see if the given node is balanced by comparing its left child
     * tree to its right child.
     *
     * @param root Root you want to see is balanced or not.
     * @return True or false on whether given root is balanced.
     */
    public boolean isBalanced(AVLTreeNode root) {
        return -1 <= getNodeHeight(root.left) - getNodeHeight(root.right) && (1 >= getNodeHeight(root.left) - getNodeHeight(root.right));
    }

    /**
     * Checks all nodes in the tree in a postorder traversal to see if its
     * balanced, once it finds an unbalanced node it will fix it and reset the
     * heights.
     *
     * @param root The root and children that you want to see is balanced or
     * not.
     */
    public void checkBalance(AVLTreeNode root) {

        if (root.left != null) {
            //go left
            checkBalance(root.left);

            //check left child, if it is unbalanced fix it and set left child to fixed tree
            if (!isBalanced(root.left)) {
                //call the method to balance tree
                root.left = balanceTree(root.left);
                setNodeHeight(this.overallRoot);
            }

        }

        if (root.right != null) {
            //go right
            checkBalance(root.right);

            //check right child, if it is unbalanced fix it and set left child to fixed tree
            if (!isBalanced(root.right)) {
                //call method to assign node to the new balanced tree
                root.right = balanceTree(root.right);
                setNodeHeight(this.overallRoot);
            }
        }
    }

    /**
     * Decides what type of rotation to make to balance the tree.
     *
     * @param root The root that you want to balance.
     * @return The root of the new balanced tree.
     */
    public AVLTreeNode balanceTree(AVLTreeNode root) {
        if (getNodeHeight(root.left) > getNodeHeight(root.right)) {
            //for a single right rotation
            if (getNodeHeight(root.left.left) > getNodeHeight(root.left.right)) {
                return rightRotation(root);
                //for a double rotation
            } else {
                root.left = leftRotation(root.left);
                return rightRotation(root);
            }

        } else {
            //for a single left rotation
            if (getNodeHeight(root.right.right) > getNodeHeight(root.right.left)) {
                return leftRotation(root);
                //for a double rotation
            } else {
                root.right = rightRotation(root.right);
                return leftRotation(root);
            }
        }
    }

    /**
     * Rotates the given node right.
     *
     * @param root Root that you want to rotate.
     * @return The root after the right rotation.
     */
    public AVLTreeNode rightRotation(AVLTreeNode root) {
        //places right tree of left node in a placeholder

        AVLTreeNode placeholder = root.left.right;

        //points right childs left child to the root
        root.left.right = root;
        //root now becomes the left child
        root = root.left;
        //replace the placeHolder in the tree
        root.right.left = placeholder;

        return root;

    }

    /**
     * Rotates the given node left.
     *
     * @param root Root that you want to rotate.
     * @return The node after the left rotation.
     */
    public AVLTreeNode leftRotation(AVLTreeNode root) {
        //places left tree of right node in placehold
        AVLTreeNode placeholder = root.right.left;

        //points right childs left child to the root
        root.right.left = root;
        //root now becomes the right child
        root = root.right;
        //replace the placeholder in the tree
        root.left.right = placeholder;

        return root;

    }

    /**
     * Inserts the given name and interest at the place where it should, using a
     * recursive method
     *
     * @param name The name of the person you want to insert.
     * @param interests The interests you want to insert for that person.
     * @param root Root used to recursively traverse the trees.
     * @return Node of the new tree.
     */
    public AVLTreeNode insertAtNull(String name, String[] interests, AVLTreeNode root) {
        if (root == null) {

            return new AVLTreeNode(name, interests);

        } else if (root.getShortName().compareTo(name) < 0) {

            root.right = insertAtNull(name, interests, root.right);

        } else {

            root.left = insertAtNull(name, interests, root.left);

        }

        return root;
    }

    /**
     * Special get height function that will return 0 for null nodes to avoid
     * errors from checking height of nonexistent nodes.
     *
     * @param root Root you want to get height of.
     * @return The height of the given root.
     */
    public int getNodeHeight(AVLTreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Prints out the preorder traversal of the given node and its children.
     *
     * @param node Node of the tree you want to print.
     */
    public void printPreorder(AVLTreeNode node) {
        if (node != null) {
            System.out.print(node.getShortName() + " ");
            printPreorder(node.left);
            printPreorder(node.right);
        }

    }

    /**
     * prints out the postorder traversal of its node and its children.
     *
     * @param node Node of the tree you want to print.
     */
    public void printPostorder(AVLTreeNode node) {
        if (node != null) {
            printPostorder(node.left);
            printPostorder(node.right);
            System.out.print(node.getShortName() + " ");
        }

    }

    /**
     * Prints out the inorder traversal of the given node and its children.
     *
     * @param node Node of the tree you want to print.
     */
    public void printInorder(AVLTreeNode node) {
        if (node != null) {
            printInorder(node.left);
            System.out.print(node.getShortName() + " ");
            printInorder(node.right);
        }

    }

    /**
     * Takes the given name and string array and prints it out.
     *
     * @param name Name of person you want to insert.
     * @param interests The interests of the person you want to insert.
     */
    public void printInterests(String name, String[] interests) {
        System.out.print(name + " likes ");
        for (int i = 0; i < interests.length; i++) {

            if (interests[i] != null) {
                System.out.print(interests[i]);
            }

            if (i < (interests.length - 1)) {
                if (interests[i + 1] != null) {
                    System.out.print(" and ");
                }
            }
        }
        System.out.println(".");
    }

    /**
     * Recursively searches through the tree and finds the name input in.
     *
     * @param name Name you want to search for.
     * @param root Root used to search through recursively.
     * @return True or false based on if the name is in the list.
     */
    public boolean search(String name, AVLTreeNode root) {
        String rootName = root.getShortName();
        if (rootName.equals(name)) {
            printInterests(name, root.getInterests());
            return true;
        } else if (name.compareTo(rootName) > 0 && root.right != null) {
            return search(name, root.right);
        } else if (root.left != null) {
            return search(name, root.left);
        }

        //name not found
        return false;
    }

}
