package com.lsm1998.im.ui;

import com.lsm1998.im.domain.Friends;
import com.lsm1998.im.domain.Group;
import com.lsm1998.im.domain.User;
import com.lsm1998.im.utils.ContextAwareUtil;
import com.lsm1998.im.service.HttpService;
import com.lsm1998.im.socket.TcpClient;
import com.lsm1998.im.ui.component.DataModel;
import com.lsm1998.im.ui.component.HeadImg;
import com.lsm1998.im.utils.GlobalUser;
import com.lsm1998.im.utils.GlobalUtil;
import com.lsm1998.im.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
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
    public static final String GLOBAL_KEY = "MainUI";

    private final HttpService httpService;
    private final TcpClient client;
    // 当前用户
    private User myInfo;
    private User friendInfo;
    // 我的信息
    private JLabel myInfoLabel;

    private JTabbedPane tabbedPane;

    private final List<Group> groupList;
    private List<Friends> friendsList;

    private JList<Object>[] jLists;

    private JPopupMenu popupMenu;

    public final Map<String, ChatUI> chatWin = new HashMap<>();

    {
        groupList = new ArrayList<>(5);
        groupList.add(Group.builder().id(1L).groupName("好友").build());
        groupList.add(Group.builder().id(2L).groupName("关注").build());
        groupList.add(Group.builder().id(3L).groupName("黑名单").build());
        groupList.add(Group.builder().id(4L).groupName("特别关心").build());
        groupList.add(Group.builder().id(5L).groupName("群聊").build());
    }

    public MainUI()
    {
        this.myInfo = GlobalUser.getUser();
        this.httpService = ContextAwareUtil.getBean(HttpService.class);
        this.client = ContextAwareUtil.getBean(TcpClient.class);
        if (this.myInfo == null)
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

    @PostConstruct
    public void initMainUI()
    {
        log.info("initMainUI...");
        GlobalUtil.set(GLOBAL_KEY, this);
    }

    @PreDestroy
    public void destroyMainUI()
    {
        log.info("destroyMainUI...");
        GlobalUtil.remove(GLOBAL_KEY);
    }

    private void initLayout()
    {
        // 背景图片
        JLabel backgroundLabel = new JLabel(ImageUtil.getImageIconByPath("/static/images/MainBg.jpg"));
        backgroundLabel.setOpaque(false);
        backgroundLabel.setLayout(new BorderLayout());
        this.add(backgroundLabel);
        UIManager.put("TabbedPane.contentOpaque", false);
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setOpaque(false);
        listPaneInit();
        backgroundLabel.add(tabbedPane);

        // 设置个人信息
        JPanel panelN = new JPanel(new GridLayout(1, 2));
        this.myInfoLabel = new JLabel(myInfo.getNickname() + "[" + myInfo.getUsername() + "]", ImageUtil.getImageIconByUrl(myInfo.getHeadImg(), 75, 75), JLabel.LEFT);
        panelN.setLayout(new GridLayout(2, 2));
        panelN.add(myInfoLabel);
        myInfoLabel.addMouseListener(this);
        JPanel panelS = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelS.setOpaque(false);
        panelN.add(panelS);
        panelN.setOpaque(false);
        backgroundLabel.add(panelN, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        backgroundLabel.add(panel, BorderLayout.SOUTH);
        this.createMenu();
        this.setSize(300, 700);
    }

    private void listPaneInit()
    {
        this.friendsList = this.httpService.findGroupList();
        Map<Long, List<User>> friendsMap = new HashMap<>(5);
        this.friendsList.forEach(e -> friendsMap.put(e.getGroupId(), e.getUserList()));
        this.jLists = new JList[groupList.size()];
        for (int i = 0; i < groupList.size(); i++)
        {
            List<User> temp = friendsMap.get(groupList.get(i).getId());
            if (temp == null)
            {
                tabbedPane.addTab(groupList.get(i).getGroupName(), new JList<>(new String[]{}));
                continue;
            }
            String[] nameArray = new String[temp.size()];
            for (int j = 0; j < temp.size(); j++)
            {
                nameArray[j] = temp.get(j).getNickname();
            }
            jLists[i] = new JList<>(nameArray);
            jLists[i].setModel(new DataModel(temp));
            jLists[i].setCellRenderer(new HeadImg(temp));
            jLists[i].setOpaque(false);
            jLists[i].addMouseListener(this);
            tabbedPane.addTab(groupList.get(i).getGroupName(), jLists[i]);
        }
    }

    private void createMenu()
    {
        popupMenu = new JPopupMenu();
        int size = groupList.size();
        JMenuItem[] menuItems = new JMenuItem[size + 2];
        for (int i = 0; i < menuItems.length - 2; i++)
        {
            String groupName = groupList.get(i).getGroupName();
            menuItems[i] = new JMenuItem("移到" + groupName + "组");
            popupMenu.add(menuItems[i]);
            // menuItems[i].addActionListener(e -> changGroup(groupName));
        }
        size = menuItems.length;
        menuItems[size - 1] = new JMenuItem("查看好友信息");
        popupMenu.add(menuItems[size - 1]);
        menuItems[size - 2] = new JMenuItem("删除好友");
        popupMenu.add(menuItems[size - 2]);
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

        for (int i = 0; i < jLists.length; i++)
        {
            if (e.getSource() == jLists[i])
            {
                if (jLists[i].getSelectedIndex() >= 0)
                {
                    friendInfo = this.friendsList.get(i).getUserList().get(jLists[i].getSelectedIndex());
                }
                if (e.getButton() == 3)
                {
                    if (jLists[i].getSelectedIndex() >= 0)
                    {
                        popupMenu.show(jLists[i], e.getX(), e.getY());
                    }
                } else if (e.getClickCount() == 2)
                {
                    if (jLists[i].getSelectedIndex() >= 0)
                    {
                        openChat();
                    }
                }
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

    public ChatUI openChat()
    {
        ChatUI chat = chatWin.get(friendInfo.getUsername());
        if (chat == null)
        {
            chat = new ChatUI(myInfo, friendInfo, client);
            chatWin.put(friendInfo.getUsername(), chat);
        }
        chat.setVisible(true);
        return chat;
    }

    public void appendView(String title, String body)
    {
        try
        {
            ChatUI chat = openChat();
            StyledDocument doc = new HTMLDocument();
            chat.appendView(title, doc);
        } catch (BadLocationException e)
        {
            e.printStackTrace();
        }
    }
}
