package merkle.implementation;

import merkle.Configuration;
import merkle.IMerkleTree;

import java.io.*;

import static merkle.Configuration.blockSize;
import static merkle.Configuration.hashFunction;

/**
 * TASK 1
 *
 * @author Iain Woodburn
 * @pso 17
 * @date 21-Oct-16
 */
public class MerkleTree extends IMerkleTree {

    /**
     * Given an <i>inputFile</i> this function builds a Merkle Tree and return the <i>masterHash</i>
     * <i>this.tree</i> is the array representation of the tree which you need to create
     * You can use <i>Configuration.hashFunction</i>
     * The basic code to read a file block wise is provided. You can choose to use it.
     * The tree should be 1-indexed
     */
    @Override
    public String build(File inputFile) throws Exception {
        int blocks = (int) Math.ceil((double) inputFile.length() / Configuration.blockSize);

        tree = new Node[2 * blocks];//2 * number of leaves - 1 is the total number of nodes + 1 gives room for dummy so: 2 * blocks
        tree[0] = new Node("dummy", 0);//Zeroth dummy node

        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(inputFile))) {
            byte[] byteArray = new byte[blockSize];
            int readStatus;
            int position = 0;

            while ((readStatus = reader.read(byteArray)) != -1) {
                String block = padBytes(byteArray, readStatus);

                //Total nodes is 2 * leaves - 1
                //Therefore, first leaf appears at 2 * leaves - 1 - leaves
                //It is incremented by position to fill all of the leaves
                int locOfFirstLeaf = 2 * blocks/* - 1*/ - blocks;

                //Inserts the the leaves into the trees
                tree[locOfFirstLeaf + position] = new Node(Configuration.hashFunction.hashBlock(block), locOfFirstLeaf + position);
                position++;
            }

            //After all of the leaves are inserted, work upwords
            //by concatenate hashing the leaves below them
            for(int i = (tree.length - 1) - blocks; i >= 1 ; i--){
                //ConcatenateHash both children and put it in the empty node at tree[i]
                tree[i] = new Node(Configuration.hashFunction.concatenateHash(tree[i * 2], tree[i * 2 + 1]), i);
            } //end for

        }

        //masterHash is the root of the Merkle Tree
        String masterHash = tree[1].getHash();
        return masterHash;
    }
}
