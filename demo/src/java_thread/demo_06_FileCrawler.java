package java_thread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列实现生产者消费者模式
 * 例子摘自：https://blog.csdn.net/liyuetao680/article/details/47831921
 */
public class demo_06_FileCrawler {
    public static void main(String[] args) throws InterruptedException {
        // 创建搜索条件
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && (pathname.getName().endsWith(".txt") || pathname.getName().endsWith(".log") || pathname.getName().endsWith(".java"));
            }
        };
        new Thread(new FileCrawler(filter, "E:\\workspace\\idea\\demo_workspace","if")).start();
    }

}

// 使用BlockingQueue查找某个文件夹下的所有文本文件中是否包含文字
// BlockingQueue：线程安全的队列类
class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue = new LinkedBlockingQueue<>(10);
    final List<String> result = new ArrayList<>(16);
    private final FileFilter filter;
    private final String root;
    private final String key;
    volatile boolean running = true;
    private Indexer indexer;
    // 搜索规则，路径
    public FileCrawler(FileFilter filter, String root, String key) {
        this.filter = filter;
        this.root = root;
        this.key = key;
        indexer = new Indexer("Indexer");
    }

    @Override
    public void run() {
        File src = new File(root);
        if (src.exists()) {
            try {
                indexer.start();  // 开启检索器
                cralw(src);        // 爬取路径下，符合规则的文件
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            Thread.sleep(1000);
            while (!fileQueue.isEmpty()){
                running = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // 寻找文件，并保存到队列里
    private void cralw(File src) throws InterruptedException {
        File[] files = src.listFiles();
        if (files != null)
            for (File file : files) {
                System.out.println("检索文件："+file.getPath());
                if (file.isDirectory()){
                    cralw(file);
                }else if (filter.accept(file)){
                    fileQueue.put(file);
                    System.out.println("找到符合条件的文件："+file.getPath());
                }
            }
    }

    // 内部类，线程，做检索器
    // 根据队列内的东西执行
    private class Indexer extends Thread {
        public Indexer(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (running || !fileQueue.isEmpty()) {
                try {
                    indexFile(fileQueue.take());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
//            showResult();
        }
        // 遍历文件，是否包含关键字
        private void indexFile(File take) {
            LineNumberReader in = null;
            try {
                System.out.println("开始遍历文件："+take.getPath());
                in = new LineNumberReader(new FileReader(take));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains(key)) {
                        addResult(in, take, line);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 追加结果
        private void addResult(LineNumberReader in, File src, String line) {
            StringBuilder msg = new StringBuilder();
            String path = src.getAbsolutePath();
            msg.append("path : " + path + " ; ");
            msg.append("lineNumber : " + in.getLineNumber() + " ; ");
            msg.append("line : " + line);
//            result.add(msg.toString());
            System.out.println(msg);
        }
    }
//    public void showResult() {
//        for (String str : result) {
//            System.out.println(str);
//        }
//    }
}



