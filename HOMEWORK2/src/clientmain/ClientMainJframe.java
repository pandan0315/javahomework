/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import bank.Bank;
import bank.BankAccount;
import bank.RejectedException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import server.ClientAccount;
import server.Item;
import server.MarketService;


/**
 *
 * @author danpan
 */
public class ClientMainJframe extends javax.swing.JFrame {

    private BankAccount bankAccount;
    private Bank bankObj;
    private MarketService market;
    private ClientAccount client;
    //DefaultListModel wishItemModel=new DefaultListModel();
    //DefaultListModel listModel = new DefaultListModel();
    private ClientInterface clientInterfaceObj = null;
    DefaultListModel itemModel = new DefaultListModel();
    DefaultListModel itemModel1 = new DefaultListModel();
    /**
     * Creates new form ClientMainJframe
     */
    public ClientMainJframe() {

        initComponents();

        // createBankAccountButton.setEnabled(false);
        depositButton.setEnabled(false);
        registerMarketButton.setEnabled(false);
        sellButton.setEnabled(false);
        wishButton.setEnabled(false);
        getAllItemButton.setEnabled(false);
        buyButton.setEnabled(false);
        unregisterMarketButton.setEnabled(false);
        this.getClientWishButton.setEnabled(false);
        this.removeWishButton.setEnabled(false);
        
        
       //this.wishList.setModel(listModel);
        try {
            clientInterfaceObj = new ClientImpl(this);

            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            bankObj = (Bank) Naming.lookup("ICABANK");
            market = (MarketService) Naming.lookup("BlocketServer");
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        accountNameTextField = new javax.swing.JTextField();
        depositTextField = new javax.swing.JTextField();
        depositButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bankText = new javax.swing.JTextArea();
        userNameTextField = new javax.swing.JTextField();
        wishButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        itemNameTextField = new javax.swing.JTextField();
        itemPriceTextField = new javax.swing.JTextField();
        getAllItemButton = new javax.swing.JButton();
        buyButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemsList = new javax.swing.JList();
        sellButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        wishList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        marketText = new javax.swing.JTextArea();
        registerMarketButton = new javax.swing.JButton();
        createBankAccountButton = new javax.swing.JButton();
        unregisterMarketButton = new javax.swing.JButton();
        getClientWishButton = new javax.swing.JButton();
        removeWishButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("AccountName:");

        jLabel2.setText("DepositMoney:");

        depositButton.setText("depositMoney");
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });

        bankText.setColumns(20);
        bankText.setRows(5);
        jScrollPane1.setViewportView(bankText);

        wishButton.setText("wishItem");
        wishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wishButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("UserName:");

        jLabel4.setText("ItemName:");

        jLabel5.setText("ItemPrice:");

        getAllItemButton.setText("All Item to sell:");
        getAllItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getAllItemButtonActionPerformed(evt);
            }
        });

        buyButton.setText("BuyItem");
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(itemsList);

        sellButton.setText("SellItem");
        sellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellButtonActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(wishList);

        marketText.setColumns(20);
        marketText.setRows(5);
        jScrollPane4.setViewportView(marketText);

        registerMarketButton.setText("registerMarketAccount");
        registerMarketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerMarketButtonActionPerformed(evt);
            }
        });

        createBankAccountButton.setText("createBankAccount");
        createBankAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBankAccountButtonActionPerformed(evt);
            }
        });

        unregisterMarketButton.setText("unregisterMarketAccount");
        unregisterMarketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unregisterMarketButtonActionPerformed(evt);
            }
        });

        getClientWishButton.setText("My wish list:");
        getClientWishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getClientWishButtonActionPerformed(evt);
            }
        });

        removeWishButton.setText("RemoveWish");
        removeWishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWishButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(25, 25, 25)
                        .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(registerMarketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(itemNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(sellButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wishButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(unregisterMarketButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(16, 16, 16))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(accountNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(depositTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(depositButton)
                                    .addComponent(createBankAccountButton)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buyButton))
                                    .addComponent(getAllItemButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(getClientWishButton)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removeWishButton)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(createBankAccountButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(depositTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(depositButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(registerMarketButton))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(itemNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(itemPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unregisterMarketButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wishButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(getClientWishButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(getAllItemButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(removeWishButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createBankAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBankAccountButtonActionPerformed

        try {

            this.bankAccount = bankObj.createAccount(accountNameTextField.getText());
            //bankText.append(accountNameTextField.getText() + " is successfully created in ICABANK" + "\n");
            this.accountNameTextField.setEnabled(false);
            this.createBankAccountButton.setEnabled(false);
          // this.getBankAccountButton.setEnabled(false);
            depositButton.setEnabled(true);
            registerMarketButton.setEnabled(true);
            unregisterMarketButton.setEnabled(false);
            

        } catch (RemoteException | RejectedException ex) {
            JOptionPane.showMessageDialog(this, "this bank account name has already been existed", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
       


    }//GEN-LAST:event_createBankAccountButtonActionPerformed

    private void registerMarketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerMarketButtonActionPerformed
        try {
            // TODO add your handling code here:
            this.client = market.register(userNameTextField.getText(), this.bankAccount);

            //this.marketText.append(userNameTextField.getText() + " is successfully registered in the market" + "\n");
            this.registerMarketButton.setEnabled(false);
            //this.userNameTextField.setEnabled(false);
            sellButton.setEnabled(true);
            wishButton.setEnabled(true);
            getAllItemButton.setEnabled(true);
            buyButton.setEnabled(true);
            unregisterMarketButton.setEnabled(true);
            this.getClientWishButton.setEnabled(true);
            this.removeWishButton.setEnabled(true);

            //sent client interface reference to server
            this.market.addClientNotifyObject(clientInterfaceObj, client);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "this bank account name has already been existed", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_registerMarketButtonActionPerformed

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositButtonActionPerformed

        try {
            bankAccount.deposit(Float.valueOf(depositTextField.getText()));
            bankText.append("your account has $" + bankAccount.getBalance() + "\n");

        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_depositButtonActionPerformed

    private void sellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellButtonActionPerformed
        try {
            // TODO add your handling code here:\
            market.sellItem(itemNameTextField.getText(), Float.valueOf(itemPriceTextField.getText()), client);
            this.marketText.append("your item " + itemNameTextField.getText() + " is in the market now" + "\n");
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sellButtonActionPerformed

    private void wishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wishButtonActionPerformed
        try {
            // TODO add your handling code here:

            Item wishedItem=market.wishItem(itemNameTextField.getText(), Float.valueOf(itemPriceTextField.getText()), client);
            this.marketText.append("your wish item is " + itemNameTextField.getText() + " of $" + itemPriceTextField.getText() + "\n");
            //listModel.addElement(itemNameTextField.getText() + " of $" + itemPriceTextField.getText() + "\n");
            //listModel.addElement(wishedItem);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_wishButtonActionPerformed

    private void getAllItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getAllItemButtonActionPerformed
        // TODO add your handling code here:
       itemModel.removeAllElements();
        try {
            for (Item item : market.getAllItem()) {
                itemModel.addElement(item);
            }
            this.itemsList.setModel(itemModel);
        } catch (Exception e) {

        }
    }//GEN-LAST:event_getAllItemButtonActionPerformed

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
        try {
            // TODO add your handling code here:
            Item item = (Item) itemsList.getSelectedValue();
           
          
            boolean isSuccessfull;

            isSuccessfull = market.buyItem(item, client);

            if (!isSuccessfull) {
                JOptionPane.showMessageDialog(this, "You have not enough money to buy it", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.getAllItemButtonActionPerformed(evt);
            
           
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buyButtonActionPerformed

    private void unregisterMarketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unregisterMarketButtonActionPerformed
        try {
            // TODO add your handling code here:
            market.unRegister(userNameTextField.getText());
            registerMarketButton.setEnabled(true);
            sellButton.setEnabled(false);
            wishButton.setEnabled(false);
            getAllItemButton.setEnabled(false);
            buyButton.setEnabled(false);
            this.getClientWishButton.setEnabled(false);
            this.removeWishButton.setEnabled(false);
            this.marketText.append(userNameTextField.getText() + " is successfully unregistered in the market" + "\n");
            itemModel.removeAllElements();
            itemModel1.removeAllElements();
            this.unregisterMarketButton.setEnabled(false);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_unregisterMarketButtonActionPerformed

    private void getClientWishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getClientWishButtonActionPerformed
        itemModel1.removeAllElements();
        
        try {
            for (Item item : market.getAllWishItem()) {
                if(item.getOwner().equals(client.getUserName())){
                itemModel1.addElement(item);}
            }
            this.wishList.setModel(itemModel1);
        } catch (Exception e) {

        }
        
        
    }//GEN-LAST:event_getClientWishButtonActionPerformed

    private void removeWishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWishButtonActionPerformed
        // TODO add your handling code here:
        Item itemWished= (Item)wishList.getSelectedValue();
        
       
        try {
            //boolean isRemove=this.market.updateWishItemList(itemWished);
            //System.out.println(isRemove);
            this.market.updateWishItemList(itemWished);
            this.getClientWishButtonActionPerformed(evt);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_removeWishButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientMainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientMainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientMainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientMainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientMainJframe().setVisible(true);

            }
        });
    }

    public void addMessage(String message) {
        marketText.append(message + "\n");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accountNameTextField;
    private javax.swing.JTextArea bankText;
    private javax.swing.JButton buyButton;
    private javax.swing.JButton createBankAccountButton;
    private javax.swing.JButton depositButton;
    private javax.swing.JTextField depositTextField;
    private javax.swing.JButton getAllItemButton;
    private javax.swing.JButton getClientWishButton;
    private javax.swing.JTextField itemNameTextField;
    private javax.swing.JTextField itemPriceTextField;
    private javax.swing.JList itemsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea marketText;
    private javax.swing.JButton registerMarketButton;
    private javax.swing.JButton removeWishButton;
    private javax.swing.JButton sellButton;
    private javax.swing.JButton unregisterMarketButton;
    private javax.swing.JTextField userNameTextField;
    private javax.swing.JButton wishButton;
    private javax.swing.JList wishList;
    // End of variables declaration//GEN-END:variables
}
