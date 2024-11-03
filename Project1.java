/*
Name: Viviana Luna
Course: CNT 4714 - Fall 2024
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday September 8, 2024
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Project1 extends JFrame{
    // Creating shopping cart
    private static String[] cart = new String[80];
    private static int cartSpot = 0;

    public static int itemNum = 1;
    public static int itemNum1 = 0;
    public static double taxRate = 0.06;


    // transaction.csv details
    public static void transactionFile(String filename, String info){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(info);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Creating the main frame
        JFrame frame = new JFrame("Nile.Com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel label1 = new JLabel("Enter item ID for Item #" + itemNum);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 0;
        gbc.weightx = 0;

        frame.add(label1,gbc);

        // Creating text area 1
        JTextArea textArea1 = new JTextArea(5, 40);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
//        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        frame.add(scrollPane1, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(scrollPane1, gbc);

        JLabel label2 = new JLabel("Enter quantity for Item #" + itemNum);
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weighty = 0;
        gbc.weightx = 0;
        frame.add(label2,gbc);

        // Creating text area 2
        JTextArea textArea2 = new JTextArea(5, 40);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        frame.add(scrollPane2, gbc);

        JLabel label3 = new JLabel("Details for Item #" + itemNum);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = 0;
        gbc.weightx=0;
        frame.add(label3,gbc);

        // Creating text area 3
        JTextArea textArea3 = new JTextArea(5, 40);
        JScrollPane scrollPane3 = new JScrollPane(textArea3);
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        frame.add(scrollPane3, gbc);

        JLabel label4 = new JLabel("Current Subtotal for " + itemNum1 +" item(s): ");
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.weighty = 0;
        gbc.weightx = 0;
        frame.add(label4,gbc);

        // Creating text area 4
        JTextArea textArea4 = new JTextArea(5, 40);
        JScrollPane scrollPane4 = new JScrollPane(textArea4);
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        frame.add(scrollPane4, gbc);

        gbc.weighty = 0;

        // Making a label for the shopping cart
        JLabel labelCart = new JLabel("Your Shopping Cart Currently Contains " + itemNum1 + " Item(s)");
        gbc.gridy = 4;
        gbc.gridx = 0;
        frame.add(labelCart,gbc);

        // Making a shopping cart area
        JTextArea textArea5 = new JTextArea(5, 40);
        JScrollPane scrollPane5 = new JScrollPane(textArea5);
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx =1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(scrollPane5, gbc);


        // changing the format of the buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridy = 8;
        gbc.gridx = 0;

        // Creating a button to get the text from the text areas
        JButton searchButton = new JButton("Search for Item #" + itemNum);
//        gbc.gridy = 8;
        searchButton.setBackground(Color.pink);
        frame.add(searchButton, gbc);

        JButton viewButton = new JButton("View Cart");
        gbc.gridy = 8;
        gbc.gridx = 1;
        viewButton.setEnabled(false);
        viewButton.setBackground(Color.pink);
        frame.add(viewButton, gbc);

        JButton cartButton = new JButton("Empty Cart");
        gbc.gridy = 9;
        gbc.gridx = 0;
        cartButton.setBackground(Color.pink);
        frame.add(cartButton, gbc);

        JButton addButton = new JButton("Add Item #" + itemNum + " To Cart");
        gbc.gridy = 9;
        gbc.gridx = 1;
        addButton.setBackground(Color.pink);
        addButton.setEnabled(false);
        frame.add(addButton, gbc);

        JButton checkButton = new JButton("Check Out");
        gbc.gridy = 10;
        gbc.gridx = 0;
        checkButton.setEnabled(false);
        checkButton.setBackground(Color.pink);
        frame.add(checkButton, gbc);

        JButton exitButton = new JButton("Exit");
        gbc.gridy = 10;
        gbc.gridx = 1;
        exitButton.setBackground(Color.pink);
        frame.add(exitButton, gbc);

        // Adding action listener for the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //here, we're going to save user input items to a string array
//                String item = itemNum + ". Item ID: " + textArea1.getText().trim() + " Quantity: " +textArea2.getText().trim() + " Item Description: " +textArea3.getText().trim();
                try {
                    String itemID = textArea1.getText().trim();
                    String itemQuanS = textArea2.getText().trim();
                    if (itemQuanS.isEmpty() || !itemQuanS.matches("\\d+")) {
                        // exception handling to ensure user enters a quantity
                        JOptionPane.showMessageDialog(frame, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                    // convert itemquans to an int
                    int itemQuantity = Integer.parseInt(itemQuanS);
                    List<String[]> itemInfo = readFile("inventory.csv");

                    // searching in csv file
                    String itemDetails = "";
                    float price = 0.0f;
                    boolean itemFound = false;
                    for (String[] item : itemInfo) {
                        if (item[0].equals(itemID)) {
                            itemDetails = item[1];
                            price = Float.parseFloat(item[4]);
                            int stock = Integer.parseInt(item[3].trim());
                            // Here's the uhh quantity error frames
                            if (itemQuantity > stock) {
                                JOptionPane.showMessageDialog(frame, "Error: Not enough stock for Item ID " + itemID + ". Only " + stock + " on hand. Please reduce the quantity", "Nile dot com: Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            // Updating the stoooock
                            item[3] = String.valueOf(stock - itemQuantity);
                            itemFound = true;
                            break;
                        }
                    }

                    if (!itemFound) {
                        JOptionPane.showMessageDialog(frame, "Error: Item ID " + itemID + " not found in file.", "Nile dot com: Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Calling on writeFIle to update the stock
                    writeFile("inventory.csv", itemInfo);

                    // Date and time for all items added
                    LocalDateTime time = LocalDateTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String finalFormat = time.format(format);

                    // discount ahhh
                    int discount = 0;
                    if (itemQuantity >= 15) {
                        discount = 20;
                    }
                    else if (itemQuantity >= 10) {
                        discount = 15;
                    }
                    else if (itemQuantity >= 5) {
                        discount = 10;
                    }
                    float totalPrice = price * itemQuantity;
                    float discountAmount = totalPrice * discount / 100.0f;
                    float finalPrice = totalPrice - discountAmount;

                    // user input item
                    String userItem = String.format("Transaction Date and Time: %s | %d. Item ID: %s | Description: %s | Price per item: $%.2f | Quantity: %d | Discount: %d%% | Total: $%.2f", finalFormat, itemNum, itemID, itemDetails, price, itemQuantity, discount, finalPrice);
                    // adding to cart array
                    if (cartSpot < 5) {
                        cart[cartSpot] = userItem;
                        cartSpot++;
                        // increasing item numbers
                        itemNum++;
                        itemNum1++;
                        System.out.println("Added " + userItem + " to cart");
                    } else {
                        JOptionPane.showMessageDialog(frame,"Error: Cart is full");
                    }

                    // Updating the text areas
                    label1.setText("Enter item ID for Item #" + itemNum);
                    label2.setText("Enter quantity for Item #" + itemNum);
                    label3.setText("Details for Item #" + itemNum);
                    label4.setText("Current Subtotal for " + itemNum1 + " item(s):");
                    labelCart.setText("Your Shopping Cart Currently Contains " + itemNum1 + " Item(s)");

                    // Updating shopping cart area with info from cart
                    StringBuilder shoppingCart = new StringBuilder();
                    float total = 0.0f;
                    for (int i = 0; i < cartSpot; i++) {
//                    shoppingCart.append(cart[i]);
//                    System.out.println("\n");
                        shoppingCart.append(cart[i]).append("\n");
                        String[] parts = cart[i].split("Total: \\$");
//                        total += Float.parseFloat(parts[1]);
                        total += Float.parseFloat(parts[1].split(" ")[0]);
                    }
                    shoppingCart.append(String.format("\nTotal: $%.2f", total));
                    textArea5.setText(shoppingCart.toString());

                    // Updating buttons
                    searchButton.setText("Search for Item #" + itemNum);
                    addButton.setText("Add Item #" + itemNum + " To Cart");

                    // Updating subtotal section
                    textArea4.setText("$"+total);

                    frame.revalidate();
                    frame.repaint();
                }
                // Testing: error handling .3. hehe just for cleanliness for now
                catch (Exception x) {
                    JOptionPane.showMessageDialog(frame, "Error");
                    x.printStackTrace();
                }
            }
        });

        // Creating the search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemID = textArea1.getText().trim();
//                int itemQuantity = Integer.parseInt(textArea2.getText().trim());

                List<String[]> itemData = readFile("inventory.csv");
                boolean itemFound = false;
                boolean inStock = false;

                // searching for item and updating details box
                for(String[] item : itemData){
                    if(item[0].equals(itemID)){
//                        textArea3.setText(item[0]+item[1]+" "+item[3]+" $"+item[4]);
                        int stock = Integer.parseInt(item[3].trim());
                        itemFound=true;
                        if(stock>0) {
                            textArea3.setText(item[0]+item[1]+" "+item[3]+" $"+item[4]);
                            addButton.setEnabled(true);
                            checkButton.setEnabled(true);
                            viewButton.setEnabled(true);
                            inStock = true;
                            break;
                        }
                    }
                }

                if(!itemFound){
                    // Here's where the item not found frame
                    // error 1 will refer to "item not in file" error
                    JOptionPane.showMessageDialog(frame, "Error: Item ID " + itemID + " not found in file.", "Nile dot com: Error", JOptionPane.ERROR_MESSAGE);
                    addButton.setEnabled(false);
                    checkButton.setEnabled(false);
                    viewButton.setEnabled(false);
                }
                else if(!inStock){
                    JOptionPane.showMessageDialog(frame, "Sorry... that item is out of stock, please try another item", "Nile dot com: Error", JOptionPane.ERROR_MESSAGE);
                    // Clearing the text field
                    textArea1.setText("");
                }
            }
        });

        // Doing the checkout button now ahh
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder cartInfo = new StringBuilder();
                StringBuilder messageInfo = new StringBuilder();
                float total1 = 0.0f;
                float totalDiscount = 0.0f;
                String dateTime = "";
                for(int i =0; i<cartSpot; i++){
                    cartInfo.append(cart[i]).append("\n");
//                    messageInfo.append(cart[i]).append("\n");
                    String[] parts = cart[i].split("Total: \\$");
                    total1 += Float.parseFloat(parts[1].split(" ")[0]);

                    // Calculating the discount
                    String[] discountParts = cart[i].split("Discount: ");
                    if (discountParts.length > 1) {
                        String discount = discountParts[1].split("%")[0];
                        int discountPercentage = Integer.parseInt(discount);
                        float itemTotal = Float.parseFloat(parts[1].split(" ")[0]);
                        totalDiscount += itemTotal * discountPercentage / 100.0f;
                    }

                    // getting the dateTime
                    String[] itemParts = cart[i].split("Date and Time: ");
                    if (itemParts.length > 1) {
                        String itemDateTime = itemParts[1].split(" ")[0];
                        dateTime = itemDateTime;
                    }
                }

//                transactionFile("transaction.csv", cartInfo.toString()

                // the time thingy
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:MM:SS");
                String finalFormat = time.format(format);
//                cartInfo.append("Date and Time: ").append(finalFormat);

                // Taxes and totals
                float taxRate = 0.06f;
                float taxTotal = total1 * taxRate;
                float totalDue = total1 - totalDiscount + taxTotal;

                // writing the info to the message display
                messageInfo.append(String.format("\nOrder Date: %s", finalFormat));
                messageInfo.append("\nCart Items:\n").append(cartInfo).append("\n");
                messageInfo.append(String.format("\nNumber of line items: %d", itemNum1));
                messageInfo.append(String.format("\nOrder Subtotal: $%.2f", total1));
                messageInfo.append(String.format("\nTax (6%%): $%.2f", taxTotal));
                messageInfo.append(String.format("\nORDER TOTAL: $%.2f", totalDue));
                messageInfo.append(String.format("\nThanks for shopping at Nile Dot Com!"));
                // Invoice details here
                //String message = String.format("Checkout successful! \n\nNumber of line items: %d \nSubtotal: $%.2f\nTax (6%%): $%.2f\nTotal: $%.2f\n\nThanks for shopping at Nile Dot Com!", itemNum1, total1, taxTotal, totalDue);

                JOptionPane.showMessageDialog(frame, messageInfo.toString(), "Nile Dot Com: FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE);
                // Invoice frame and write to csv
//                JOptionPane.showMessageDialog(frame, cartInfo.toString(), "Nile Dot Com: FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE);
                transactionFile("transaction.csv", cartInfo.toString());

                // Cleaning up!! Aka starting a new order
                cartSpot = 0;
                itemNum = 1;
                itemNum1 = 0;
                textArea1.setText("");
                textArea2.setText("");
                textArea3.setText("");
                textArea4.setText("");
                textArea5.setText(""); // Clear the cart display
                label1.setText("Enter item ID for Item #"+itemNum);
                label2.setText("Enter quantity for Item #"+itemNum);
                label3.setText("Details for Item #"+itemNum);
                label4.setText("Current Subtotal for "+itemNum1+ " item(s):");
                labelCart.setText("Your Shopping Cart Currently Contains " + itemNum1 + " Item(s)");
                searchButton.setText("Search for Item #"+itemNum);
                addButton.setText("Add Item #"+itemNum +" To Cart");
                // Gonna put the user order receipt here:
                // Nvm lol
//                cartInfo.append("\nOrder subtotal: $").append(String.format("%.2f", total1));
//                cartInfo.append("\nTax rate: 6%");
            }
        });


        // Creating the view cart action
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewCart = new JFrame("Nile Dot Com: Shopping Cart");
                viewCart.setSize(700,400);
                viewCart.setLayout(new BorderLayout());

                // Creating a text area for the shopping cart
                JTextArea cartArea = new JTextArea();
                cartArea.setEditable(false);
                JScrollPane cartScroll = new JScrollPane(cartArea);
                viewCart.add(cartScroll, BorderLayout.CENTER);

                StringBuilder cartItems = new StringBuilder();
                for(int i=0; i<cartSpot; i++){
                    cartItems.append(cart[i]).append("\n");

                }
                cartArea.setText(cartItems.toString());

                viewCart.setVisible(true);

            }
        });

        // Emptying cart button action
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cartSpot = 0;
                itemNum = 1;
                itemNum1 = 0;
                textArea1.setText("");
                textArea2.setText("");
                textArea3.setText("");
                textArea4.setText("");
                textArea5.setText("");

                // Clearing the labels too
                label1.setText("Enter item ID for Item #"+itemNum);
                label2.setText("Enter quantity for Item #"+itemNum);
                label3.setText("Details for Item #"+itemNum);
                label4.setText("Current Subtotal for "+itemNum1+ " item(s):");
                labelCart.setText("Your Shopping Cart Currently Contains " + itemNum1 + " Item(s)");

                // oops wair the buttons too
                searchButton.setText("Search for Item #"+itemNum);
                addButton.setText("Search for Item #"+itemNum);

                // i need to check to see if i have to clear the transaction.csv as well
            }
        });

        // Exiting lol
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Making the GUI visible
        frame.setVisible(true);
    }

    // readFIle method to read inventory csv
    public static List<String[]> readFile(String file){
        List<String[]> inventory = new ArrayList<>();

        try(BufferedReader bread = new BufferedReader(new FileReader(file))){
            String content;
            while((content = bread.readLine()) != null){
                String[] data = content.split(",");
                inventory.add(data);

            }

        }
        catch (IOException e){
            e.printStackTrace();
        }

        return inventory;
    }

    // this method is for updating the stock based on user input for quan
    public static void writeFile(String file, List<String[]> inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] item : inventory) {
                writer.write(String.join(",", item));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
