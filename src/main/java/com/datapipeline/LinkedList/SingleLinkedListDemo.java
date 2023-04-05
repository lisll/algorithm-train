package com.datapipeline.LinkedList;
/**
 * Company: www.hecom.cn
 *
 * @description: 使用单向链表实现水浒英雄插入
 * @author: ly
 */

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero5 = new HeroNode(5, "意义", "浪里白条");
        HeroNode hero6 = new HeroNode(6, "意义1", "浪里白条1");
        //创建单向链表
        SingleLinkedListHero singleLinkedListHero = new SingleLinkedListHero();
        // 1.将新来的数据直接插入到链表尾部
//        singleLinkedListHero.addByTail(hero1);
//        singleLinkedListHero.addByTail(hero4);
//        singleLinkedListHero.addByTail(hero2);
//        singleLinkedListHero.addByTail(hero3);
//
//        singleLinkedListHero.addByHead(hero5);
//        singleLinkedListHero.addByHead(hero6);
        //2,按照编号顺序插入
        singleLinkedListHero.addByOrder(hero1);
        singleLinkedListHero.addByOrder(hero4);
        singleLinkedListHero.addByOrder(hero3);
        singleLinkedListHero.addByOrder(hero2);
        singleLinkedListHero.list();

        //3,根据编号修改
        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~");
        singleLinkedListHero.update(newHeroNode);
        System.out.println("修改后~~~");
        singleLinkedListHero.list();

        //4，根据编号删除
        int no = 8; //删除一个不存在的节点
        singleLinkedListHero.del(8);
        int no1 = 2;
        singleLinkedListHero.del(no1);
        System.out.println("删除后~~~");
        singleLinkedListHero.list();


        // 求倒数第K个节点


    }
}

//定义 SingleLinkedList 管理我们的英雄
class SingleLinkedListHero{
    // 初始化一个头节点，这个头节点不存放任何数据，只是一个标志位
   private static HeroNode head = new HeroNode(0,"","");

    //1，不考虑顺序，添加英雄到单向链表尾部
    /**
     * 思路分析
     * 1,找到当前这个链表的最后一个节点
     * 2，将最后这个节点的next域指向这个新加入的节点
     */
    public void addByTail(HeroNode newHero){
        // 因为head节点不能动，所以需要一个辅助变量temp
        HeroNode temp = head;
        // 这个while循环的目的就在于找到最后一个节点
        while(true){
            if(temp.next==null){    // 证明此时temp节点就是最后一个节点
                break;
            }
            temp=temp.next; // 如果此时temp节点不是最后一个节点，那么就将temp节点后移
        }
        //当退出 while 循环时，temp 就指向了链表的最后
        //将temp节点的next域指向新的节点
        temp.next=newHero;
    }
    //2,不考虑顺序，添加英雄到单向链表头部
    /**
     * 思路分析：
     *
     */
    public void addByHead(HeroNode newHero){
        if(head.next==null){  // 说明这个链表目前为空
            head = newHero;
        }else{
            newHero.next=head;
            head = newHero;
        }
    }

    /**
     * 第二种方式在添加英雄时，根据排名将英雄插入到指定位置,(如果有这个排名，则添加失败，并给出提示)
     *
     */
    public void addByOrder(HeroNode heroNode) {
        /**
         * 因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
         * 因为单链表，因为我们找的 temp 是位于 添加位置的前一个节点，否则插入不了
         */
       HeroNode temp = head;  // 头节点不动，遍历的时候永远是第一个
        boolean flag = false;
        while(true){
            if(temp.next==null){   //当temp.next==null时，证明这是最后一个节点
                break;
            }
            //不是最后一个节点
            if(temp.next.no>heroNode.no){   //这就是待插入的位置,这个位置的判断是关键
                break;  //位置找到，跳出循环
            }else if(temp.next.no==heroNode.no){    //说明待添加的 heroNode 的编号已然存在
                flag = true;
                break;
            }else{
                temp = temp.next;   //temp后移
            }
        }
        //判断 flag 的值
        if(flag){
            System.out.printf("准备插入的英雄的编号 %d 已经存在了, 不能加入\n", heroNode.no);
        }else{
            // 将待加入节点插入到temp的后面
            heroNode.next = temp.next;    // 将原先的temp域换成现在的新节点的next域
            temp.next=heroNode; //  将当前节点赋值给temp的next域
        }
    }

    /**
     * 改节点的信息, 根据 no 编号来修改，即 no 编号不能改.
     * 根据 newHeroNode 的 no 来修改即可
     */
    public void update(HeroNode newHeroNode) {
            HeroNode temp = head;
            boolean flag = false;
            while(true){
                if(temp.next==null){
                    break;
                }
                if(temp.no==newHeroNode.no){
                    flag = true;
                    break;
                }
                temp=temp.next;
            }
            if(flag){
                temp.name = newHeroNode.name;
                temp.nickName = newHeroNode.nickName;
            }else{
                System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newHeroNode.no);
            }
//            if(head.next==null){
//                System.out.println("链表为空~");
//                return;
//            }
//            HeroNode temp = head.next;
//            boolean flag = false; //表示是否找到该节点
//            while(true){
//                if(temp.next==null){
//                    break;
//                }
//                if(temp.no==newHeroNode.no){
//                    flag = true;
//                    break;
//                }
//                temp = temp.next;
//            }
//        //根据 flag 判断是否找到要修改的节点
//          if(flag) {
//              temp.name = newHeroNode.name;
//              temp.nickName = newHeroNode.nickName;
//          } else {
//              //没有找到
//              System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newHeroNode.no); }
    }

    // 删除节点
    public void del(int no) {
        HeroNode temp = head;
        boolean flag = false;
        while(true){
            if(temp.next==null){
                break;
            }
            if(temp.next.no==no){   //找到的待删除节点的前一个节点 temp
                flag = true;
                break;
            }
            temp = temp.next;   // 后移
        }
        if(flag){
            temp.next=temp.next.next;   //核心代码（将待删除节点的前一个节点的next域往后移动两次）
        }else{
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }

    //求链表中有效节点个数
    public static  int getLength(HeroNode heroNode){
        if(heroNode.next==null){    //空链表
            return 0;
        }
        int length =0;
        HeroNode cur = head.next;   //定义一个辅助的变量, 这里我们没有统计头节点(head表示头节点)
        while(true){
            if(cur.next==null){
                break;
            }
            length++;
            cur = cur.next;
        }
        return length;
    }

    // 查找单链表中倒数第k个节点
    public static HeroNode findLastIndexNode(HeroNode head, int index){
        if(head.next==null){
            return null;    //如果是空链接则返回null
        }
        //第一个遍历得到链表的长度(节点个数)
        int size = getLength(head);
        //对传入的index进行校验
        if(index<0 || index>size){
            System.out.println("传入的index有问题");
            return null;
        }
        HeroNode cur = head.next;   //去掉头节点
        //第二次遍历 size-index 位置，就是我们倒数的第 K 个节点
        for(int i = 0;i<size-index; i++){
            cur = cur.next;
        }
        return cur;
    }

    // 显示链表
    /**
     * 通过找到头节点，进而找到所有的节点
     */
    public void list(){
        // 判断链表是否为空
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }
        //因为头节点，不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
        while(true){
            if(temp==null){
                break;
            }
            //输出节点信息
            System.out.println(temp);
            //将temp后移
            temp = temp.next;
        }
    }
}

//定义 HeroNode ， 每个 HeroNode 对象就是一个节点
class HeroNode{
    public int no;  //英雄编号
    public String name; //英雄真实名字
    public String nickName; //英雄花名
    public HeroNode next; //指向下一个节点
    // 构造器
    public HeroNode(int no,String name,String nickName){
        this.no=no;
        this.name=name;
        this.nickName=nickName;
    }

    //重写toString方法，便于打印查看
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
