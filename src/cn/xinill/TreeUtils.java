package cn.xinill;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {
    int i;
    List<Byte> bytes;

    public static TreeNode createAllTree(List<Byte> bytes){
        TreeUtils utils =  new TreeUtils();
        utils.i = 0;
        utils.bytes = bytes;
        return utils.createAllTree();
    }

    private TreeNode createAllTree(){
        byte c = bytes.get(i);
        ++i;
        TreeNode root = new TreeNode();
        root.b = c;
        if(c == (byte)0){
            root.left = createAllTree();
            root.right = createAllTree();
        }
        return root;
    }

    public static List<Byte> getTreeString(TreeNode root){
        List<Byte> list = new ArrayList<>();
        list.add(root.b);
        if(root.left != root.right){
            list.addAll(TreeUtils.getTreeString(root.left));
            list.addAll(TreeUtils.getTreeString(root.right));
        }
        return list;
    }
}
