package cn.xinill;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {
    int i;
    List<Integer> integers;

    public static TreeNode createAllTree(List<Integer> bytes){
        TreeUtils utils =  new TreeUtils();
        utils.i = 0;
        utils.integers = bytes;
        return utils.createAllTree();
    }

    private TreeNode createAllTree(){
        int c = integers.get(i);
        ++i;
        TreeNode root = new TreeNode();
        if(c == Integer.MAX_VALUE){ //非叶子结点
            root.b = 0;
            root.left = createAllTree();
            root.right = createAllTree();
        }else{
            root.b = (byte)c;
        }
        return root;
    }

    public static List<Integer> getTreeString(TreeNode root){
        List<Integer> list = new ArrayList<>();

        if(root.left == root.right){
            list.add((int)root.b);
        }else{
            list.add(Integer.MAX_VALUE); //Integer.MAX_VALUE 表示
            list.addAll(TreeUtils.getTreeString(root.left));
            list.addAll(TreeUtils.getTreeString(root.right));
        }
        return list;
    }

    public static void print(TreeNode node){
        if(node == null){
            System.out.print(" null");
            return;
        }
        System.out.print(" "+ node.b);

        print(node.left);
        print(node.right);
    }
}
