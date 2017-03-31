package merkle.implementation;

import merkle.IMerkleTree;
import merkle.IServer;

import java.util.LinkedList;
import java.util.List;


/**
 * TASK 2
 * TODO: IMPLEMENT generateResponse
 *
 * @author Iain Woodburn
 * @pso 17
 * @date 25-Oct-2016
 */
public class Server extends IServer {

    /**
     * Given a node to verify identified by <i>blockToTest</i>
     * which corresponds to the node received by calling <i>merkleTree.getNode(blockToTest)</i>
     * this function generates the path siblings which are required for verification
     * The returned list should contains Nodes in order from node to the root, i.e. bottom up
     */
    public List<IMerkleTree.Node> generateResponse(int blockToTest) {
        List<IMerkleTree.Node> verificationList = new LinkedList<>();

        //List which contains the path of nodes from blockToTest to the root of the tree
        List<IMerkleTree.Node> path = new LinkedList<>();

        IMerkleTree.Node node = merkleTree.getNode(blockToTest);

        //Automatically adds the first node
        verificationList.add(node);
        path.add(node);

        //While the index of the node is not outside of the tree, but still includes the root
        while(node.getIndex() != 0){

          //Finds the parent of the node and then changes the node to that parent
          path.add(merkleTree.getNode(node.getIndex() / 2));
          //New node has the hash of its parent and the location of its parent
          node = new IMerkleTree.Node(merkleTree.getNode(node.getIndex() / 2).getHash(), node.getIndex() / 2);

        }

        //Check every node in the path from the given block to the root
        //if the children of those nodes exist and are not in the path
        //then add it to the verificationList
        for(int i = 1; i < path.size(); i++){

          //If path contains the left child, do not add it to the list
          if(merkleTree.getNode( path.get(i).getIndex() * 2 ) != null && !path.contains(merkleTree.getNode( path.get(i).getIndex() * 2 ))){
            verificationList.add(merkleTree.getNode(path.get(i).getIndex() * 2));
          }
          //If path contains the right child, do not add it to the list
          if(merkleTree.getNode( path.get(i).getIndex() * 2 + 1) != null && !path.contains(merkleTree.getNode( path.get(i).getIndex() * 2 + 1))){
            verificationList.add(merkleTree.getNode(path.get(i).getIndex() * 2 + 1));
          }

        }

        return verificationList;
    }
}
