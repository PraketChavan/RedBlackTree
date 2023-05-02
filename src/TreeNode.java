/**
 * Class representing a node in a tree
 */
public class TreeNode {
    public static TreeNode NIL = new TreeNode(null, null, null, null, true);
    public static boolean RED = false;
    public static boolean BLACK = true;
    public Integer key;
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;
    public boolean colour;

    public TreeNode(Integer key, TreeNode left, TreeNode right, TreeNode parent, boolean colour) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TreeNode && this.key == ((TreeNode) obj).key &&
               this.colour == ((TreeNode) obj).colour &&
               this.left == ((TreeNode) obj).left &&
               this.right == ((TreeNode) obj).right &&
               this.parent == ((TreeNode) obj).parent;
    }

}
