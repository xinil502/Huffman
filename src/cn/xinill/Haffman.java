package cn.xinill;

import java.util.*;

public class Haffman {

    private Map<Byte, String> haffmanMap = new HashMap<>();

    private TreeNode haffmanTree = null;

    public void setHaffmanTree(TreeNode haffmanTree) {
        this.haffmanTree = haffmanTree;
    }

    public TreeNode getHaffmanTree() {
        return haffmanTree;
    }


    public Map<Byte, String> getHaffmanMap() {
        return haffmanMap;
    }

    Map<Byte, Integer> countByte(Map<Byte, Integer> countMap, List<Byte> bytes){
        for(byte b: bytes){
            countMap.put(b, countMap.getOrDefault(b, 0)+1);
        }
        return countMap;
    }

    void createHaffman(Map<Byte, Integer> countMap){
        //创建所有结点
        List<TreeNode> list = new ArrayList<>();
        for(Iterator<Byte> ite = countMap.keySet().iterator(); ite.hasNext();){
            TreeNode t = new TreeNode();
            t.b = ite.next();
            t.val = countMap.get(t.b);
            list.add(t);
        }

        //创建哈夫曼树
        int[][] minIndex = new int[2][2];
        while (list.size() != 1) {
            for(int i=0; i<2; ++i){ //初始化记录数组
                for(int j=0; j<2; ++j){
                    minIndex[i][j] = Integer.MAX_VALUE;
                }
            }
            for(int i=0; i<list.size(); ++i){ //记录最小的两个结点
                if(list.get(i).val < minIndex[0][0]){
                    minIndex[0][0] = list.get(i).val;
                    minIndex[0][1] = i;
                }else if(list.get(i).val < minIndex[1][0]){
                    minIndex[1][0] = list.get(i).val;
                    minIndex[1][1] = i;
                }
            }

            //合并最小结点
            TreeNode node1, node2;
            if(minIndex[0][1] > minIndex[1][1]){  //先删除索引值大的结点
                 node1 = list.remove(minIndex[0][1]);
                 node2 = list.remove(minIndex[1][1]);
            }else{
                node1 = list.remove(minIndex[1][1]);
                node2 = list.remove(minIndex[0][1]);
            }
            TreeNode root = new TreeNode();
            root.val = node1.val+node2.val;
            if(node1.val < node2.val){ //把权值小的放在左边。
                root.left = node1;
                root.right = node2;
            }else{
                root.left = node2;
                root.right = node1;
            }
            list.add(root);
        }
        haffmanTree = list.get(0);
        createHaffmanCode(haffmanTree, "");
    }


    private void createHaffmanCode(TreeNode root, String str){
        if(root.left != root.right){
            createHaffmanCode(root.left, str+"0");
            createHaffmanCode(root.right, str+"1");
        }else {
            haffmanMap.put(root.b, str);
        }
    }

    String compress(List<Byte> bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes){
            sb.append(haffmanMap.get(b));
        }
        return sb.toString();
    }

    List<Byte> decompression(String s){
        List<Byte> list = new ArrayList<>();
        TreeNode root = haffmanTree;
        for(char c:s.toCharArray()){
            if(c == '0'){
                root = root.left;
            }else{
                root = root.right;
            }
            if(root.left == root.right){
                list.add(root.b);
                root = haffmanTree;
            }
        }
        return list;
    }
}
