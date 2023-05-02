public class Main {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(5);
        tree.print();
        tree.insert(8);
        tree.print();
        tree.insert(7);
        tree.print();
        tree.insert(6);
        tree.print();
        tree.insert(4);
        tree.print();
        tree.insert(-1);
        tree.print();
        tree.insert(34);
        tree.print();
        tree.insert(3);
        tree.print();
        tree.remove(8);
        tree.print();
        System.out.println(tree.bst());
    }
}
