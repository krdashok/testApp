package com.rav.core;

public class Example1 extends Example
{

    private Example example;
    /**
     * @param args
     * @throws CloneNotSupportedException 
     */
    public static void main(String[] args) 
    {
        Example e = new Example();
        try 
        {
            Example e1 = (Example) e.clone();
            e1.run();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }

}

