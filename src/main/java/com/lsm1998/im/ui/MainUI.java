package com.lsm1998.im.ui;

import com.lsm1998.im.domain.Friends;
import com.lsm1998.im.domain.Group;
import com.lsm1998.im.domain.User;
import com.lsm1998.im.listener.ContextAwareUtil;
import com.lsm1998.im.service.HttpService;
import com.lsm1998.im.utils.GlobalUser;
import com.lsm1998.im.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

@Component
@Scope(value = "prototype")
@Slf4j
public class MainUI extends BaseUI implements MouseListener
{
    private HttpService httpService;
    // 当前用户
    private User user;
    // 背景图片
    private JLabel backgroundLabel;
    // 我的信息
    private JLabel myInfoLabel;

    private JTabbedPane tabbedPane;

    private List<Group> groupList;
    private List<Friends> friendsList;

    {
        groupList = new ArrayList<>(5);
        groupList.add(Group.builder().id(1L).groupName("好友").build());
        groupList.add(Group.builder().id(2L).groupName("关注").build());
        groupList.add(Group.builder().id(3L).groupName("群聊").build());
    }

    public MainUI()
    {
        this.user = GlobalUser.getUser();
        this.httpService = ContextAwareUtil.getBean(HttpService.class);
        if (this.user == null)
        {
            log.error("用户未登录");
            this.jumpPage(this, LoginUI.class);
        } else
        {
            setIconImage(ImageUtil.getImageByPath("/static/images/im.png"));
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setResizable(false);
            this.initLayout();
        }
    }

    private void initLayout()
    {
        this.backgroundLabel = new JLabel(ImageUtil.getImageIconByPath("/static/images/MainBg.jpg"));
        this.backgroundLabel.setOpaque(false);
        this.backgroundLabel.setLayout(new BorderLayout());
        this.add(backgroundLabel);
        UIManager.put("TabbedPane.contentOpaque", false);
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setOpaque(false);

        this.friendsList = this.httpService.findGroupList();
        for (int i = 0; i < groupList.size(); i++)
        {
//            List<Friends> l = friendsMapper.getFriendsByMyAccAndGroupName((int) myInfo.getAccNumber(), groupList.get(i).getGroupName());
//            String[] strs = new String[l.size()];
//            for (int j = 0; j < l.size(); j++)
//            {
//                strs[j] = userMapper.getUserByAccNumber(l.get(j).getFriendsId()).getNickName();
//                System.out.println("好友昵称=" + strs[j]);
//            }
//            jLists[i] = new JList(strs);
//            List<User> temp = userMapper.getFriendByAccAndGroupName(this.myInfo.getAccNumber(), groupList.get(i).getGroupName());
//            friendsList[i] = temp;
//            jLists[i].setModel(new DataModel(temp));
//            jLists[i].setCellRenderer(new MyHeadImg(temp));
//            jLists[i].setOpaque(false);
//            jLists[i].addMouseListener(this);
//            tabbedPane.addTab(groupList.get(i).getGroupName(), jLists[i]);
        }

        // 设置个人信息
        JPanel panelN = new JPanel(new GridLayout(1, 2));
        this.myInfoLabel = new JLabel(user.getNickname() + "[" + user.getUsername() + "]", ImageUtil.getImageIconByUrl(user.getHeadImg(), 75, 75), JLabel.LEFT);
        panelN.setLayout(new GridLayout(2, 2));
        panelN.add(myInfoLabel);
        myInfoLabel.addMouseListener(this);
        JPanel panelS = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelS.setOpaque(false);
        panelN.add(panelS);
        panelN.setOpaque(false);
        this.backgroundLabel.add(panelN, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        this.backgroundLabel.add(panel, BorderLayout.SOUTH);
        this.setSize(300, 700);
    }

    @Override
    public String title()
    {
        return "随便聊";
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource() == this.myInfoLabel)
        {
            if (e.getClickCount() == 2)
            {
                // 编辑资料
                this.newPage(MyInfoUI.class);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
