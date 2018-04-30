/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author harlan.howe
 */
public class BST {

    private TreeNode root;
    
    public BST()
    {
        ArrayList<String> dictionary = new ArrayList<String>();
        File inputFile = new File("word_list_moby_crossword.flat.txt");
        try
        {
            Scanner input = new Scanner(inputFile);
            int i = 0;
            while (input.hasNext())
            {
                dictionary.add(input.next());
                i++;
                if (i%1000 == 0)
                {
                    System.out.println("Loaded "+i+" words.");
                }
            }
            input.close();
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println("File not found.");
        }
        
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("How many words should I add to the tree? ");
        int numWords = keyboard.nextInt();
        for (int i=0; i<numWords; i++)
        {
            int which = (int)(Math.random()*dictionary.size());
            System.out.println(dictionary.get(which));
            add(dictionary.get(which));
        }
    
        System.out.println("------------------\nTree:\n"+this.toString());        
        System.out.println("------------------\nReverse order of words:\n"+this.toStringReverse());
        System.out.println("------------------\nTotal number of characters: "+this.findCharCount(0, root));
        System.out.println("------------------\n"+"Tree Depth: "+findDepth(0,root));  
        System.out.println("------------------\nWhat word for search?");
        Scanner searchKeyboard = new Scanner(System.in);
        String searchWord = searchKeyboard.nextLine();
        boolean isHere = findWord(root,searchWord);
        System.out.println("Is the word in this tree: "+isHere);
    }
    
    public String toString()
    {
        return subString("",root);
    }
    
    public String toStringReverse(){
    	return subStringReverse("",root);
    }
        
    public String subString(String prefix, TreeNode subroot)
    {
        if (subroot == null)
            return "";
        else 
        {
            String result = "";
            result+= subString(prefix+"\t",subroot.getLeft());
            result+= prefix+subroot.getValue()+"\n";
            result+= subString(prefix+"\t",subroot.getRight());
            return result; 
        }
    }
    public void add(String s)
    {
        if (root == null)
            root = new TreeNode(s);
        else
            addToSubTree(s,root);
    }
    
    public void addToSubTree(String s, TreeNode subroot)
    {
        int rel = s.compareTo(subroot.getValue());
        if (rel<0)
        {
            if (subroot.getLeft() == null)
                subroot.setLeft(new TreeNode(s));
            else
                addToSubTree(s,subroot.getLeft());
        }
        else
        {
            if (subroot.getRight() == null)
                subroot.setRight(new TreeNode(s));
            else
                addToSubTree(s,subroot.getRight());
        }
    }
    
    public String subStringReverse(String prefix, TreeNode subroot)
    {
        if (subroot == null)
            return "";
        else 
        {
            String result = "";
            result+= subStringReverse(prefix,subroot.getRight());
            result+= prefix+subroot.getValue()+"\n";
            result+= subStringReverse(prefix,subroot.getLeft());
            return result; 
        }
    }
    
    public int findCharCount(int prefix, TreeNode subroot){
    	if (subroot == null)
            return 0;
        else 
        {
            int result = 0;
            result += findCharCount(prefix,subroot.getRight());
            result += prefix+subroot.getValue().length();
            result += findCharCount(prefix,subroot.getLeft());
            return result;
        }
    }
    
    public boolean findWord(TreeNode subroot, String word){
    	if (subroot==null){
    		return false;
    	}
    	if (subroot.getValue().equals(word))
            return true;
        else 
        {
            boolean result;
            if (findWord(subroot.getRight(),word)){
            	result = true;
            }
            else if(findWord(subroot.getLeft(),word)){
            	result = true;
            }
            else{
            	result = false;
            }
            
            return result;
        }
    }
    
    public int findDepth(int currentDepth,TreeNode subroot){
        if(subroot==null)
        	return currentDepth;
        else{
        	currentDepth+=1;
        	int leftDepth = findDepth(currentDepth,subroot.getLeft());
        	int rightDepth = findDepth(currentDepth,subroot.getRight());
        	if (leftDepth>currentDepth||rightDepth>currentDepth){
        		if (leftDepth>rightDepth)
        			return leftDepth;
        		else
        			return rightDepth;
        	}
        	return currentDepth;
        }
    }
}
