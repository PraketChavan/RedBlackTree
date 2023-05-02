import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("SuspiciousNameCombination")

public class RedBlackTree {
    public TreeNode root;

    public RedBlackTree() {
        this.root = TreeNode.NIL;
    }

    public TreeNode search(int key) {
        TreeNode x = this.root;
        while (x != TreeNode.NIL) {
            if (x.key == key)
                return x;
            else if (x.key > key)
                x = x.right;
            else
                x = x.left;
        }
        return TreeNode.NIL;
    }


    public boolean contains(int key) {
        return !(search(key) == TreeNode.NIL);
    }

    public void insert(int key) {
        TreeNode y = TreeNode.NIL;
        TreeNode x = this.root;
        while (x != TreeNode.NIL) {
            y = x;
            if (x.key == key)
                return; // modify when implementing key value pairs
            else if (key > x.key)
                x = x.right;
            else
                x = x.left;
        }
        TreeNode newNode = new TreeNode(key, TreeNode.NIL, TreeNode.NIL, y, false);
        if (y == TreeNode.NIL)
            this.root = newNode;
        else if (key > y.key)
            y.right = newNode;
        else
            y.left = newNode;

        insertFixUp(newNode);
    }

    private void rotateRight(TreeNode node) {
        TreeNode y = node.left;

        node.left = y.right; // update the left child of the node
        if (node.left != TreeNode.NIL) {
            node.left.parent = node; // update the parent of the child if it exists
        }
        TreeNode parent = node.parent;
        if (parent == TreeNode.NIL) {
            this.root = y;
        } else if (parent.left == node) {
            parent.left = y;
        } else {
            parent.right = y;
        }
        y.parent = parent;
        node.parent = y;
        y.right = node;

    }

    private void rotateLeft(TreeNode node) {
        TreeNode y = node.right; // get the right child of the node to rotate at
        node.right = y.left; // update the right child of node to be the left child of y
        if (y.left !=
                TreeNode.NIL) // If the left child of y is not NIL, then update its parent field
            y.left.parent = node;

        y.parent = node.parent; //link node's parent to y
        if (node.parent ==
                TreeNode.NIL) // If node was originally the root, update the root to be y
            this.root = y;
        else if (node ==
                node.parent.left) // update the parent of node to replace y as a child instead of node
            node.parent.left = y;
        else
            node.parent.right = y;


        y.left = node;  // put the node on y's left
        node.parent = y; // update the parent of node to be y
    }

    private void insertFixUp(TreeNode x) {
        x.colour = TreeNode.RED;
        while (x != TreeNode.NIL && x != this.root && x.parent.colour == TreeNode.RED) {
            if (x.parent == x.parent.parent.left) {
                TreeNode s = x.parent.parent.right;
                // case 1
                if (s.colour == TreeNode.RED) {
                    x.parent.colour = TreeNode.BLACK;
                    s.colour = TreeNode.BLACK;
                    x.parent.parent.colour = TreeNode.RED;
                    x = x.parent.parent;
                } else {
                    if (x.parent.right == x) {
                        //case 3
                        x = x.parent;
                        rotateLeft(x);
                    }
                    //case 2
                    x.parent.colour = TreeNode.BLACK;
                    x.parent.parent.colour = TreeNode.RED;
                    rotateRight(x.parent.parent);
                }
            } else {
                //case 1 but mirrored
                TreeNode s = x.parent.parent.left;
                if (s.colour == TreeNode.RED) {
                    x.parent.colour = TreeNode.BLACK;
                    x.parent.parent.colour = TreeNode.RED;
                    s.colour = TreeNode.BLACK;
                    x = x.parent.parent;
                } else {
                    if (x.parent.left == x) {
                        // case 3 but mirrored
                        x = x.parent;
                        rotateRight(x);
                    }
                    //case 2 but mirrored
                    x.parent.colour = TreeNode.BLACK;
                    x.parent.parent.colour = TreeNode.RED;
                    rotateLeft(x.parent.parent);
                }
            }
        }
        root.colour = TreeNode.BLACK; //reset the root property of the tree
    }

    public void remove(int key) {
        TreeNode x = this.search(key);
        if (x.left != TreeNode.NIL && x.right != TreeNode.NIL) {
            TreeNode s = successor(x);
            x.key = s.key;
            x = s;
        }

        TreeNode replacement = x.left != TreeNode.NIL ? x.left : x.right;

        if (replacement != TreeNode.NIL) {
            replacement.parent = x.parent;
            if (replacement.parent == TreeNode.NIL) {
                this.root = replacement;
            } else {
                if (x.parent.left == x)
                    x.parent.left = replacement;
                else
                    x.parent.right = replacement;
            }

            x.left = x.right = x.parent = TreeNode.NIL;
            if (x.colour == TreeNode.BLACK) {
                removeFixUp(replacement);
            }
        } else {
            if (x.parent == TreeNode.NIL) {
                this.root = TreeNode.NIL;
            } else {
                if (x.colour == TreeNode.BLACK) {
                    removeFixUp(x);
                }
                if (x.parent != TreeNode.NIL) {
                    if (x.parent.right == x) {
                        x.parent.right = TreeNode.NIL;
                    } else {
                        x.parent.left = TreeNode.NIL;
                    }
                    x.parent = TreeNode.NIL;

                }
            }
        }
    }

    private TreeNode successor(TreeNode x) {
        if (x.right == TreeNode.NIL)
            return TreeNode.NIL;
        else {
            x = x.right;
            while (x.left != TreeNode.NIL) {
                x = x.left;
            }
            return x;
        }
    }

    private void removeFixUp(TreeNode x) {
        while(x != root && x.colour == TreeNode.BLACK) {
            if (x.parent.left == x) {
                TreeNode s = x.parent.right;
                //case 2
                if (s.colour == TreeNode.RED) {
                    x.parent.colour = TreeNode.RED;
                    s.colour = TreeNode.BLACK;
                    rotateLeft(x.parent);
                    s = x.parent.right; //update the sibling after rotation
                }
                //The result of case 2 will lead to either case 4 or case 6
                //case 4
                if (s.right.colour == TreeNode.BLACK && s.left.colour == TreeNode.BLACK) {
                    s.colour = TreeNode.RED;
                    x = x.parent;
                } else {
                    //case 5
                    if(x.right.colour == TreeNode.BLACK) {
                        s.colour = TreeNode.RED;
                        s.left.colour = TreeNode.BLACK;
                        rotateRight(s);
                    }
                    //case 6
                    s.colour = x.parent.colour;
                    x.parent.colour = TreeNode.BLACK;
                    s.right.colour = TreeNode.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                //symmetrical cases
                TreeNode s = x.parent.left;
                //case 2
                if (s.colour == TreeNode.RED) {
                    x.parent.colour = TreeNode.RED;
                    s.colour = TreeNode.BLACK;
                    rotateRight(x.parent);
                    s = x.parent.left; //update the sibling after rotation
                }
                //The result of case 2 will lead to either case 4 or case 6
                //case 4
                if (s.left.colour == TreeNode.BLACK && s.right.colour == TreeNode.BLACK) {
                    s.colour = TreeNode.RED;
                    x = x.parent;
                } else {
                    //case 5
                    if(x.left.colour == TreeNode.BLACK) {
                        s.colour = TreeNode.RED;
                        s.right.colour = TreeNode.BLACK;
                        rotateLeft(s);
                    }
                    //case 6
                    s.colour = x.parent.colour;
                    x.parent.colour = TreeNode.BLACK;
                    s.left.colour = TreeNode.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.colour = TreeNode.BLACK;
    }


    public void print() {
        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        System.out.println(sb);
    }

    private void inorder(TreeNode root, StringBuilder sb) {
        if (root == TreeNode.NIL) return;
        inorder(root.left, sb);
        sb.append(root.key).append(" ");
        inorder(root.right, sb);
    }

    public String bst() {
        ArrayList<TreeNode> list = new ArrayList<>();
        Queue<TreeNode> open = new LinkedList<>();
        open.add(root);
        while(!open.isEmpty()){
            TreeNode curr = open.poll();
            list.add(curr);
            if (curr.left != null) open.add(curr.left);
            if (curr.right != null) open.add(curr.right);
        }

        StringBuilder sb = new StringBuilder();
        for(TreeNode node: list){
            sb.append(node.key).append(node.colour ? "B" : "").append(" ");
        }
        return sb.toString();
    }
}
