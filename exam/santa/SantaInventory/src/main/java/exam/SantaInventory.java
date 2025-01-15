package exam;


/**
 * Santa needs to calculate the median price of gifts he will deliver this year.
 * The gift prices are stored in a unique data structure known as the 'magical Christmas search tree'.
 *
 * Each node in this tree represents a gift price (as the key) and the quantity of gifts at that price (as the value).
 * The goal is to implement two methods:
 * - put (to add gift prices to the tree) and
 * - median (to find the median price of the gifts).
 *
 * For example, consider the following magical Christmas search tree:
 *
 *                               [150, 4]
 *                                /     \
 *                               /       \
 *                              /         \
 *                             /           \
 *                        [100, 10]       [300, 2]
 *                                         /   \
 *                                        /     \
 *                                       /       \
 *                                      /         \
 *                                   [200, 8]     [500, 1]
 *
 * This tree represents a total of 25 gifts. The median price is the 13th price in the sorted list of gift prices.
 * In this example, the sorted list of prices is:
 * 100 (10 times), 150 (4 times), 200 (8 times), 300 (2 times), 500 (once). The 13th price in this list is 150.
 * Thus, the median price of the gifts is 150.
 *
 * Note: It's assumed that the total number of gifts is always an odd number.
 *
 * Hint: you may need to add a size attribute to the Node class to keep track of the total number of gifts in the subtree.
 */

public class SantaInventory {

    private Node root; // root of BST
    private int nElements = 0;

    private class Node {
        private int toyPrice; // Price of the toy
        private int count; // Number of time a toy with price `toyPrice` has been added in the tree
        private Node left, right; // left and right subtrees

        public Node(int toyPrice, int count){
            this.toyPrice = toyPrice;
            this.count = count;
        }
    }

    /**
     * Inserts a new toy price into the magical Christmas search tree or updates the count of an existing toy price.
     * This method is part of the implementation of the magical Christmas search tree where each node
     * represents a unique toy price and the number of toys available at that price.
     *
     * If the tree already contains the toy price, the method updates the count of toys at that price.
     * If the toy price does not exist in the tree, a new node with the toy price and count is created.
     *
     * @param toyPrice The price of the toy to be added or updated in the tree.
     * @param count    The number of toys added to the magical tree. If the toy price already exists,
     *                 this count is added to the existing count.
     */
    private void put(Node root, int toyPrice, int count){
        if(root.toyPrice == toyPrice){
            root.count += count;
        } else if(toyPrice < root.toyPrice){
            if(root.left == null){
                root.left = new Node(toyPrice, count);
            } else {
                this.put(root.left, toyPrice, count);
            }
        } else {
            if(root.right == null){
                root.right = new Node(toyPrice, count);
            } else {
                this.put(root.right, toyPrice, count);
            }
        }
    }

    public void put(int toyPrice, int count){
        this.nElements += count;
        if(this.root == null) this.root = new Node(toyPrice, count);
        else this.put(this.root, toyPrice, count);
    }

    /**
     * Calculates the median price of the toys in the magical Christmas search tree.
     *
     * The median is determined by the size of the tree. If the tree is empty, it throws an IllegalArgumentException.
     *
     * Note: The method assumes that the total number of toys (the sum of counts of all prices) is odd.
     * The median is the price at the middle position when all toy prices are listed in sorted order.
     *
     * @return The median price of the toys.
     * @throws IllegalArgumentException if the tree is empty.
     */
    private int n;

    private int median(Node root){
        //System.out.println("===========");
        //System.out.println(root.toyPrice);
        //System.out.println(this.nElements / 2);

        //System.out.format("=> %d\n", this.n);

        if(root.left != null){
            int left = this.median(root.left);

            if(left != -1) return left;
        }

        //System.out.format("=2> %d and %d and %d\n", this.n, this.nElements / 2, root.count);

        if(this.n <= this.nElements / 2 && this.nElements / 2 < this.n + root.count){
            return root.toyPrice;
        }
        this.n += root.count;

        //System.out.format("=3> %d\n", this.n);

        if(root.right != null) return this.median(root.right);
        else return -1;
    }

    public int median(){
        if(this.root == null) throw new IllegalArgumentException();

        this.n = 0;
        return this.median(this.root);
    }

    private void print(Node root){
        if(root == null) return;

        this.print(root.left);
        System.out.format("[(%d: %d)]", root.toyPrice, root.count);
        this.print(root.right);
    }

    public void print(){
        this.print(this.root);
        System.out.println();
    }

    public static void main(String[] args) {
        SantaInventory inv = new SantaInventory();

        inv.put(150, 4);
        inv.put(100, 10);
        inv.put(300, 2);
        inv.put(200, 8);
        inv.put(500, 1);

        inv.print();

        System.out.println(inv.median());
    }
}