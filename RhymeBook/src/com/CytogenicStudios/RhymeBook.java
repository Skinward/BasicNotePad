package com.CytogenicStudios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class RhymeBook extends JFrame{

    JFrame frame;

    JPanel panel;

    JTextArea textArea;

    JMenuBar menuBar;
    JMenu menu;
    JMenuItem newFile;
    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem quit;

    JFileChooser fileChooser;

    private boolean isSaved;



    public RhymeBook(){

        isSaved = false;

        fileChooser = new JFileChooser();

        frame = new JFrame("Rhyme Book");
        JFrame.setDefaultLookAndFeelDecorated(true);
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        frame.setIconImage(icon);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(10,10,10,10));
        panel.add(textArea);
        frame.add(panel);


        menuBar = new JMenuBar();
        menu = new JMenu("File");

        newFile = new JMenuItem(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save data first?
                //create checker to look for changes between the last save and current form
                //then set isSaved
                if(!isSaved){
                    int answer = JOptionPane.showConfirmDialog(frame,"Do you want to save your current project?");
                    if(answer == JOptionPane.YES_OPTION){
                        fileChooser.setCurrentDirectory(new java.io.File("."));
                        fileChooser.setDialogTitle("Save");

                        if (fileChooser.showSaveDialog(RhymeBook.this) == JFileChooser.APPROVE_OPTION) {
                            //save the data here
                            //run saved() isSaved = true will become dedundant
                            Charset charset = StandardCharsets.US_ASCII;
                            String s = textArea.getText();
                            Path file = FileSystems.getDefault().getPath(fileChooser.getSelectedFile().toString());
                            try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                                writer.write(s, 0, s.length());
                            } catch (IOException x) {
                                System.err.format("IOException: %s%n", x);
                            }
                            System.out.println("getCurrentDirectory(): "
                                    +  fileChooser.getCurrentDirectory());
                            System.out.println("getSelectedFile() : "
                                    +  fileChooser.getSelectedFile());
                        }
                        else {
                            System.out.println("No Selection ");
                        }

                        isSaved = true;
                        System.out.println("Saved");
                        System.out.println("Save clicked");
                        textArea.setText("");
                    }
                    if(answer == JOptionPane.NO_OPTION){
                        System.out.println("Dont Save Clicked");
                        textArea.setText("");
                    }
                    if(answer == JOptionPane.CANCEL_OPTION){
                        System.out.println("Cancel clicked");
                    }
                }



            }
        });
        openFile = new JMenuItem(new AbstractAction("Open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setDialogTitle("Open");

                if (fileChooser.showOpenDialog(RhymeBook.this) == JFileChooser.APPROVE_OPTION) {
                    Charset charset = StandardCharsets.US_ASCII;
                    Path file = FileSystems.getDefault().getPath(fileChooser.getSelectedFile().toString());
                    try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            textArea.setText(line);
                        }
                    } catch (IOException x) {
                        System.err.format("IOException: %s%n", x);
                    }

                    System.out.println("getCurrentDirectory(): "
                            +  fileChooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "
                            +  fileChooser.getSelectedFile());
                }
                else {
                    System.out.println("No Selection ");
                }
                System.out.println("open");

            }
        });
        saveFile = new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setDialogTitle("Save");

                if (fileChooser.showSaveDialog(RhymeBook.this) == JFileChooser.APPROVE_OPTION) {
                    //save the data here
                    //run saved() isSaved = true will become dedundant
                    Charset charset = StandardCharsets.US_ASCII;
                    String s = textArea.getText();
                    Path file = FileSystems.getDefault().getPath(fileChooser.getSelectedFile().toString());
                    try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                        writer.write(s, 0, s.length());
                    } catch (IOException x) {
                        System.err.format("IOException: %s%n", x);
                    }
                    System.out.println("getCurrentDirectory(): "
                            +  fileChooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "
                            +  fileChooser.getSelectedFile());
                }
                else {
                    System.out.println("No Selection ");
                }

                isSaved = true;
                System.out.println("Saved");
            }
        });
        quit = new JMenuItem(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(newFile);
        menu.add(openFile);
        menu.add(saveFile);
        menu.add(quit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        frame.setMinimumSize(new Dimension(500,600));
        frame.setSize(500,600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }

}
