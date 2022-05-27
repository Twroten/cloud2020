package com.wroten.demo.linkedlist;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode node3 = new HeroNode(3, "吴用", "智多星");
        HeroNode node4 = new HeroNode(4, "公孙胜", "入云龙");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        /**
         * 无序新增链表
         */
        /*singleLinkedList.add(node1);
        singleLinkedList.add(node2);
        singleLinkedList.add(node3);
        singleLinkedList.add(node4);*/

        /**
         * 有序新增列表
         */
        singleLinkedList.addByOrder(node3);
        singleLinkedList.addByOrder(node1);
        singleLinkedList.addByOrder(node4);
        singleLinkedList.addByOrder(node2);

        //链表长度
        System.out.println("singleLinkedList.size() = " + singleLinkedList.size());
        // 修改卢俊义前
        singleLinkedList.list();
        // 修改卢俊义
        HeroNode lukaNode = new HeroNode(2, "Luka", "河北玉麒麟");
        singleLinkedList.update(lukaNode);
        // 修改卢俊义后
        singleLinkedList.list();
        // 新增关胜
        HeroNode guanNode = new HeroNode(6, "关胜", "大刀");
        singleLinkedList.addByOrder(guanNode);
        // 新增关胜后
        singleLinkedList.list();
        //删除关胜
        singleLinkedList.delete(7);
        // 删除关胜后
        singleLinkedList.list();
        //删除吴用
        singleLinkedList.delete(3);
        // 删除后
        singleLinkedList.list();
    }
}

class SingleLinkedList {
    private final HeroNode head = new HeroNode(0, "", "");

    // 无序新增节点
    public void add(HeroNode node) {
        HeroNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    // 有序新增节点
    public void addByOrder(HeroNode node) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = node;
                break;
            }
            if (node.no > temp.no && node.no < temp.next.no) {
                node.next = temp.next;
                temp.next = node;
                break;
            } else {
                temp = temp.next;
            }
        }
    }

    // 删除节点
    public void delete(int no) {
        HeroNode temp = head;
        boolean flag = false;
        while (temp.next != null) {
            if (no == temp.next.no) {
                temp.next = temp.next.next;
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (!flag) {
            System.out.printf("未找到第【%s】位HeroNode\n", no);
        }
    }

    // 修改节点
    public void update(HeroNode node) {
        HeroNode temp = head;
        while (temp.next != null) {
            if (node.no == temp.next.no) {
                node.next = temp.next.next;
                temp.next = node;
                break;
            }
            temp = temp.next;
        }
    }

    // 链表长度
    public int size() {
        HeroNode temp = head;
        int size = 0;
        while (temp.next != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    // 显示链表【遍历】
    public void list() {
        HeroNode temp = head;
        if (temp.next == null) {
            System.out.println("空链表");
            return;
        }
        while (temp.next != null) {
            temp = temp.next;
            System.out.println(temp);
        }
    }

}

// 定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    // 构造器
    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}

