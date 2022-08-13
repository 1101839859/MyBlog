/**

 * RO Utility

 * Mainly used for:

 * 1.Double Open client

 * 2.Open Unlimited View

 *  这是个样本程序，是我针对游戏修改写的。主要作用是将游戏文件用16进制打开，然后

 * 修改相关的部分，然后保存。

 *

 * @author Ciro Deng(cdtdx@sohu.com)

 * @version 1.0

 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static java.lang.System.exit;

/**

 * RO Utility Mainly used for: 1.Double Open client 2.Open Unlimited View

 *

 * @author Ciro Deng(cdtdx@sohu.com)(16进制作者)

 * @version 1.0

 *

 */

public class MainTool {

    /**

     * 将文件读取为16进制String

     * Read original File and transfer it into Hex String

     *

     * @return

     * @throws IOException

     */

    public String readOriginal2Hex(String filename) throws IOException {

        FileInputStream fin = new FileInputStream(filename);

        StringWriter sw = new StringWriter();

        int len = 1;

        byte[] temp = new byte[len];

        /*16进制转化模块*/

        while ((fin.read(temp, 0, len)) != -1) {

            if (temp[0] > 0xf) {

                sw.write(Integer.toHexString(temp[0]));

            } else if (temp[0] >= 0x0) {//对于只有1位的16进制数前边补“0”

                sw.write("0" + Integer.toHexString(temp[0]));

            } else { //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数

                sw.write(Integer.toHexString(temp[0]).substring(6));

            }

        }

        return sw.toString();

    }

    /**

     * 将替换后的16进制字符串写回文件

     * write replaced original String to file

     *

     * @param replaced

     * @throws NumberFormatException

     * @throws IOException

     */

    public void writeNew2Binary(String replaced,String path) throws NumberFormatException,

            IOException {

        File saveFile = new File(path);//File saveFile = new File("/a/b/c/","a.txt");
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }
        if(!saveFile.exists()) {
            saveFile.createNewFile();
        }

        FileOutputStream fout = new FileOutputStream(saveFile);

        for (int i = 0; i < replaced.length(); i = i + 2) {

            fout.write(Integer.parseInt(replaced.substring(i, i + 2), 16));

        }
        fout.close();

    }
    public void patch(String oldfile, MainTool tool) throws IOException {

            //That's the key!
            String mystr = "47d9579f3197";
            // Read files
            String gamesave = tool.readOriginal2Hex(oldfile);
            String lv40save = tool.readOriginal2Hex("attributes_base.xml");
            String steamidcheck = gamesave.split(mystr)[0];
            String lv40need = lv40save.split(mystr)[1];
            String mynewstr = steamidcheck + mystr + lv40need;
            tool.writeNew2Binary(mynewstr,oldfile);
    }
    public static Color getRandomColor()
    {//100~255
        Random random = new Random();
        random.setSeed(System.nanoTime());
       int red = random.nextInt(155)+100;
       int blue = random.nextInt(155)+100;
       int green = random.nextInt(155)+100;
        return new Color(red,blue,green);
    }

    public static void main(String[] args) throws IOException {
        //检查上次使用痕迹
        String lastloc = "";
        final File last_loc = new File("last_location.txt");
        final File randomc = new File("bypinenut.txt");
        if (last_loc.exists()) {
            lastloc = Files.readString(Paths.get("last_location.txt"));
            System.out.println("Successfully read");
        }
        //检查lang文件是否存在
        File file = new File("lang_name.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null,"NO LANG_NAME FILE！/没有检测到语言文件!","FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            exit(0);
        }
        //否则读取文件
        String langname = Files.readString(Paths.get("lang_name.txt"));
        //再判断这个文件存在不
        file = new File("lang\\" + langname + ".json");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null,"NO LANG FILE！/没有检测到语言文件!","FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            exit(0);
        }
        //都存在，开读！
        String lang = Files.readString(Paths.get("lang\\" + langname + ".json"));
        Config config = JSON.parseObject(lang,Config.class);
        //设置窗口
        JFrame window1 = new JFrame();  //创建一个窗口对象
        MainTool tool = new MainTool();
        window1.setTitle(config.getTitle());
        window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window1.setBounds(0,0,960,800); //设置窗口的位置大小
       // Container con = window1.getContentPane();   //创建一个Container类型变量存放窗口的内容面板
        JTextArea instruction = new JTextArea();
        instruction.setText(config.getTopInstruction());
        instruction.setLineWrap(true);
        instruction.setEditable(false);
        JLabel notice = new JLabel(config.getAttributeLabel());
        notice.setFont(new Font("微软雅黑", Font.BOLD, 25));
        JPanel jPanel=new JPanel();
        JTextArea oldfiletext = new JTextArea(config.getDefaultloc());
        oldfiletext.setLineWrap(true);
        jPanel.setLayout(new GridLayout(7,0,5,5));
        JButton oldfile=new JButton(config.getSelectsavebutton());    //创建按钮
        //匿名函数处理
        String finalLastloc = lastloc;

        oldfile.addActionListener(e -> {
            //首先呼出JPanel
            JFileChooser temp = new JFileChooser();
            if(!"".equals(finalLastloc))
            {
                temp.setCurrentDirectory(new File(finalLastloc));
            }
            temp.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int option = temp.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File files = temp.getSelectedFile();
                String fileloc = files.getAbsolutePath();
                try {
                    FileWriter fw = new FileWriter(last_loc,false);
                    fw.write(files.getParent());
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                oldfiletext.setText(fileloc);
            }
        });

        JButton btn2=new JButton(config.getStartbutton());
        btn2.addActionListener(e -> {
            try {

                if(!oldfiletext.getText().contains("attributes.xml"))
                {
                   int result =  JOptionPane.showConfirmDialog(null,config.getWarning_notice(),"☎☏✄☪WARNING☯✡※〓", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                   if(result==0)
                   {
                       tool.patch(oldfiletext.getText(),tool);
                       JOptionPane.showMessageDialog(null,config.getSuccessNotice(),"好耶！/Yeah!!!",JOptionPane.PLAIN_MESSAGE);
                   }
                }
                else
                {
                    tool.patch(oldfiletext.getText(),tool);
                    JOptionPane.showMessageDialog(null,config.getSuccessNotice(),"好耶！/Yeah!!!",JOptionPane.PLAIN_MESSAGE);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,config.getFailedAttribute(),"坏耶！/NOOOOOO!!!",JOptionPane.PLAIN_MESSAGE);
            }
        });
        JTextArea about = new JTextArea();
        about.setLineWrap(true);
        about.setEditable(false);
        about.setBackground(getRandomColor());
        about.setText("该程序由B站Pinenutn制作。存档来源：discord:Archetype_4#3490 原创方法！严禁倒卖，商用！是的，我不擅长绘制Swing，所以有人乐意写界面也挺好的，只要写明方法来源即可。\n[SaveFile from discord:Archetype_4#3490] \n By Pinenut : I know that the GUI for this software isn't the best.You have my full permission to make a better UI for it.   Commercial use is prohibited.If you do, please give me credit as the original author.\nI will be continously working to make this save file converter the best it can be.");
        if(!randomc.exists())
        {
            jPanel.setBackground(getRandomColor());
            instruction.setBackground(getRandomColor());
            oldfile.setBackground(getRandomColor());
            notice.setBackground(getRandomColor());
            oldfiletext.setBackground(getRandomColor());
            btn2.setBackground(getRandomColor());
            about.setBackground(getRandomColor());
            window1.setBackground(getRandomColor());
            window1.setTitle(window1.getTitle() + " - Without bypinenut.txt - Enjoy!/哈哈哈哈哈玩的开心~");
        }
        JTextArea github = new JTextArea();
        github.setBackground(getRandomColor());
        github.setEditable(false);
        github.setText("Github地址/Website:https://github.com/PaienNate/EvolveLegacySaveConvertor 该版本语言作者(Lang file's author):" + config.getAuthor());
        //介绍页面
        jPanel.add(instruction);
        //旧文件
        jPanel.add(oldfile);    //面板中添加按钮
        //旧文件的路径
        jPanel.add(notice);
        jPanel.add(oldfiletext);
        //一键修改按钮
        jPanel.add(btn2);
        //关于
        jPanel.add(about);
        jPanel.add(github);
        window1.add(jPanel);    //添加面板到容器
        window1.setVisible(true);   //窗口是否可见
    }

}