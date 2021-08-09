package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

class DerevoTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame frame = new DerevoFrameEdit();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
class DerevoFrameEdit extends JFrame {
    public DerevoFrameEdit() {
        setTitle("Мир");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        TreeNode root = makeSampleDerevo();
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
        tree.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        makeButtons();
    }
    public TreeNode makeSampleDerevo() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Мир");
        DefaultMutableTreeNode country = new DefaultMutableTreeNode("Россия");
        root.add(country);
        DefaultMutableTreeNode city = new DefaultMutableTreeNode("Москва");
        country.add(city);
        country = new DefaultMutableTreeNode("Молдова");
        root.add(country);
        city = new DefaultMutableTreeNode("Кишинев");
        country.add(city);
        country = new DefaultMutableTreeNode("Англия");
        root.add(country);
        city = new DefaultMutableTreeNode("Лондод");
        country.add(city);
        return root;
    }
    public void makeButtons() {
    } {
        JPanel panel = new JPanel();
        JButton addSiblingButton = new JButton("Добавить страну");
        addSiblingButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
                if(selectedNode == null) return;
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
                        selectedNode.getParent();
                if(parent == null) return;

                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Новая страна");
                int selectedIndex = parent.getIndex(selectedNode);
                model.insertNodeInto(newNode, parent, selectedIndex + 1);

                TreeNode[] nodes = model.getPathToRoot(newNode);
                TreePath path = new TreePath(nodes);
                tree.scrollPathToVisible(path);
            }
        });
        panel.add(addSiblingButton);

        JButton addChildButton = new JButton("Добавить наследника");
        addChildButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();

                if(selectedNode == null) return;

                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Новый город");
                model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());

                TreeNode[] nodes = model.getPathToRoot(newNode);
                TreePath path = new TreePath(nodes);
                tree.scrollPathToVisible(path);
            }
        });
        panel.add(addChildButton);
        JButton deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();

                if(selectedNode != null && selectedNode.getParent() != null)
                    model.removeNodeFromParent(selectedNode); } });
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);
    }
    private DefaultTreeModel model;
    private JTree tree;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;
}
