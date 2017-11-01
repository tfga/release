package com.github.tfga.release.confirm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class RenameDialog extends JDialog
{
    private JTextField filenameTextField;
//    private JTextField nomeAbaTextField;
    private boolean cancelled;

    public RenameDialog(String tagName, String nextVersion)
    {
        setTitle("Release"); // JDialog
        setModal(true);     // JDialog
        
//        setResizable(false);
        setLayout(new BorderLayout());
        Border border = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
        
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha1.add(new JLabel(String.format("Tag: %s", tagName)));
        add(linha1, BorderLayout.NORTH);
        
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        linha2.add(new JLabel("Next version:"));
        linha2.add(filenameTextField = new JTextField(20));
//        linha1.add(new JLabel("-SNAPSHOT"));
        add(linha2, BorderLayout.SOUTH);
        
        
        pack();  // JDialog
        
        setFilename(nextVersion);
        
        // Não funcionou
        KeyAdapter keyAdapter = new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == '\n')
                {
//                    model.setNomeAba(getNomeAba());
//                    model.setNomeClasse(getNomeClasse());
                    
// Tentativa de fazer a árvore atualizar só o nó renomeado (ao invés da árvore inteira)
//                    pai = model.getParent();
//                    treeModel.acionarEvento(path.getParentPath(), new int[] {treeModel.getIndexOfChild(pai, model)},
//                            new Object[]{model});
                    
//                    treeModel.acionarEvento();
                    
//                    //make the node visible by scroll to it
//                    TreeNode[] nodes = treeModel.getPathToRoot(newNode);
//                    TreePath path = new TreePath(nodes);
//                    m_tree.scrollPathToVisible(path);
//                    
//                    //select the newly added node
//                    m_tree.setSelectionPath(path);
                    
                    setVisible(false);
                    
                    //                    String nomeAba = getText();
//                    
//                    // view
////                    abas.setTitleAt(index, nomeAba);
//                    
//                    // model
//                    Object model = getModel();
//                    if (model instanceof ViewerExterno)
//                    {
//                        ((ViewerExterno)model).get(index).setNomeAba(nomeAba);
//                    }
//                    else if (model instanceof PanelsViewer)
//                    {
//                        ((PanelsViewer)model).get(index).setNomeAba(nomeAba);
//                    }
                }
                else if (e.getKeyCode() == 27) // Esc
                {
                    cancelled = true;
                    
                    setVisible(false);
                }
            }
        };
        
        filenameTextField.addKeyListener(keyAdapter);
        
//        filenameTextField.getDocument().addDocumentListener(new DocumentListener()
//        {
//            @Override
//            public void removeUpdate(DocumentEvent e)
//            {
//                onChange();
//            }
//            
//            @Override
//            public void insertUpdate(DocumentEvent e)
//            {
//                onChange();
//            }
//            
//            @Override
//            public void changedUpdate(DocumentEvent e)
//            {
//                onChange();
//            }
//        });
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                cancelled = true;
            }
        });
    }
    
    public void show(int x, int y)
    {
        setLocation(x, y);
        setVisible(true);
    }
    
//    public void set(IAba aba, ModeloArvoreApresentacao treeModel, TreePath path)
//    {
//        model = aba;
//        
//        setNomeAba(aba.getNomeAba());
//        setNomeClasse(aba.getNomeClasse());
//        
//        this.treeModel = treeModel;
//        this.path = path;
//    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void setFilename(String nomeClasse)
    {
        filenameTextField.setText(nomeClasse);
        
//        onChange(); // quando vc seta, isso não é disparado.
    }
    
    public String getFilename()
    {
        return filenameTextField.getText();
    }

    public boolean cancelled()
    {
        return cancelled;
    }
}
