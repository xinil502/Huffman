package cn.xinill;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class IUI {
    public void ui(){
        JFrame frame = new JFrame("哈夫曼编码压缩解压界面");
        frame.setSize(600, 200); //设置界面宽，高。
        frame.setLocation(600, 400); //设置界面在屏幕中的位置
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);//设置布局为null。

        File inputPath = getInputPath();
        File outputPath = getOutputPath();

        JButton compress = new JButton("压缩");
        compress.setBounds(50, 50, 70, 30);
        compress.addActionListener(e -> {
            FileIO f = new FileIO();
            f.compress(inputPath,outputPath);
            JOptionPane.showMessageDialog(null, "压缩成功！");
        });
        frame.add(compress);


        JButton decompression = new JButton("解压");
        decompression.setBounds(300, 50, 70, 30);
        decompression.addActionListener(e -> {
            FileIO f = new FileIO();
            f.decompression(inputPath,outputPath);
            JOptionPane.showMessageDialog(null, "解压成功！");
        });
        frame.add(decompression);
        // 设置界面可见
        frame.setVisible(true);
    }


    File getInputPath(){
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView(); //这里重要的一句
        System.out.println(fsv.getHomeDirectory());     //得到桌面路径
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("请选择要压缩或解压的文件路径");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int result = fileChooser.showOpenDialog(fileChooser);
        if (JFileChooser.APPROVE_OPTION == result) {
            path=fileChooser.getSelectedFile().getPath();
        }
        return new File(path);
    }

    File getOutputPath(){
//        //动态获取保存文件路径
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        fileChooser.showSaveDialog(null);
//        String path = fileChooser.getSelectedFile().getPath();
//        return new File(path+name);
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView(); //这里重要的一句
        System.out.println(fsv.getHomeDirectory());     //得到桌面路径
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("请选择文件的保存路径");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(fileChooser);
        if (JFileChooser.APPROVE_OPTION == result) {
            path=fileChooser.getSelectedFile().getPath();
        }
        return new File(path);
    }
}
/**
 JProgressBar pb = new JProgressBar();

 //进度条最大100
 pb.setMaximum(100);
 //当前进度是50
 pb.setValue(50);
 //显示当前进度
 pb.setStringPainted(true);

 f.add(pb);



 */