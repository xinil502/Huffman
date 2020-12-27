package cn.xinill;

import java.io.*;
import java.util.*;

public class FileIO {
    void compressFolder(File from, File to){
        try {
            File[] files = from.listFiles();
            for(File temp: files){
                if(temp.isDirectory()){
                    compressFolder(temp, new File(to.getAbsolutePath() + File.separator + temp.getName()));
                }else{
                    compressFile(temp, new File(to.getAbsolutePath() + File.separator + temp.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean compress(File from, File to){
        try {
            if(from.isDirectory()){
                compressFolder(from, to);
            }else{
                compressFile(from, new File(to.getAbsolutePath() + File.separator + from.getName().split("\\.")[0] + ".code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean compressFile(File from, File to){
        if(!to.exists()){  //创建文件
            try {
                to.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream bis = null;
        DataOutputStream dos = null;
        List<Byte> list = new ArrayList<>();
        try {
            bis = new BufferedInputStream(new FileInputStream(from));
            dos = new DataOutputStream(new FileOutputStream(to));
            byte[] bytes = new byte[8];
            int len;
            while((len = bis.read(bytes)) != -1){ //读取
                for(int i=0; i<len; ++i){
                    list.add(bytes[i]);
                }
            }
            Haffman haffman = new Haffman();
            haffman.createHaffman(haffman.countByte(new HashMap<>(), list)); //哈夫曼编码完毕

            //获取压缩后的字符串
            String s = haffman.compress(list);
            System.out.println(s);

            //写入哈夫曼树
            TreeNode haffmanTree = haffman.getHaffmanTree();
            List<Byte> b = TreeUtils.getTreeString(haffmanTree);
            len = 0;
            for(Iterator<Byte> ite = b.iterator(); ite.hasNext(); ){
                bytes[len] = ite.next();
                ++len;
                if(len == 8){
                    dos.write(bytes);
                    len = 0;
                }
            }
            dos.write(bytes, 0, len);

            //将压缩后的字符串使用byte数组存储， 并写入文件
            int i=0, j=0;
            Arrays.fill(bytes, (byte)0);
            for(char c:s.toCharArray()){
                if(c == '1'){
                    if(j == 0) bytes[i] |= 0x1;
                    if(j == 1) bytes[i] |= 0x2;
                    if(j == 2) bytes[i] |= 0x4;
                    if(j == 3) bytes[i] |= 0x8;
                    if(j == 4) bytes[i] |= 0x10;
                    if(j == 5) bytes[i] |= 0x20;
                    if(j == 6) bytes[i] |= 0x40;
                    if(j == 7) bytes[i] |= 0x80;
                }
                ++j;
                if(j == 8){
                    ++i;
                    j = 0;
                    if(i == 8){
                        i = 0;
                        dos.write(bytes);
                        Arrays.fill(bytes, (byte)0);
                    }
                }
            }
            dos.write(bytes,0,i+1);
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(bis!=null){
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dos!=null){
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }



    public boolean decompression(File from, File to){
        try {
            if(from.isDirectory()){
                decompressionFolder(from, to);
            }else{
                decompressionFile(from, new File(to.getAbsolutePath() + File.separator + from.getName().split("\\.")[0] + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void decompressionFolder(File from, File to){

    }

    void decompressionFile(File from, File to){
        if(!to.exists()){  //创建文件
            try {
                to.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataInputStream dis = null;
        OutputStream os = null;
        List<Byte> list = new ArrayList<>();
        try {
            dis = new DataInputStream(new FileInputStream(from));
            os = new BufferedOutputStream(new FileOutputStream(to));
            int countNull = 0, countChar = 0;
            while(true){ //读取到数组中
                byte b = dis.readByte();
                list.add(b);
                if(b == (byte)0){
                    ++countNull;
                }else{
                    ++countChar;
                }
                if(countChar > countNull){
                    break;
                }
            }
            Haffman haffman = new Haffman();
            haffman.setHaffmanTree(TreeUtils.createAllTree(list));//获取哈夫曼树


            StringBuilder sb = new StringBuilder();
            try {
                while(true){
                    byte b = dis.readByte();
                    sb.append(((b & 0x1) == 0x1)? '1' : '0');
                    sb.append(((b & 0x2) == 0x2)? '1' : '0');
                    sb.append(((b & 0x4) == 0x4)? '1' : '0');
                    sb.append(((b & 0x8) == 0x8)? '1' : '0');
                    sb.append(((b & 0x10)== 0x10)? '1' : '0');
                    sb.append(((b & 0x20)== 0x20)? '1' : '0');
                    sb.append(((b & 0x40)== 0x40)? '1' : '0');
                    sb.append(((b & 0x80)== 0x80)? '1' : '0');
                }
            }catch(Exception e){}
            System.out.println(sb.toString());
            list = haffman.decompression(sb.toString()); //解压

            //写入文件
            byte[] bytes = new byte[8];
            int len = 0;
            for(Iterator<Byte> ite = list.iterator(); ite.hasNext(); ){
                bytes[len] = ite.next();
                ++len;
                if(len == 8){
                    os.write(bytes);
                    len = 0;
                }
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(dis!=null){
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
