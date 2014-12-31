package com.rav.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class Example extends Object implements Cloneable
{
    private Example example;
    
    private static String REGEX = "^\\w+:";
    private static String INPUT =
        "aabfoo: aabfooabfoob";
    private static String REPLACE = "-";
    private int[] arr;

    /**
     * @param args
     * @throws CloneNotSupportedException 
     */
    public static void main(String[] args) 
    {
        Example e = new Example();
        try 
        {
            ExamSearchInputs p = new ExamSearchInputs("name:A'kasdjfkl jack jill* id:24? asdkfjasdf alksdjfka   jjkjk343434 k:adsfasdf iname:IXE ixe,m,,dd**d");
            /*e.addNumbersToAnArray(100);
            e.missingNumberInAnArray(100);*/
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void addNumbersToAnArray(int arrayLen)
    {
        arr = new int[arrayLen];
        for(int i=0;i<arrayLen;i++)
        {
            if(i!=45)
            arr[i]=i;
        }
    }
    
    private int missingNumberInAnArray(int nNumbers)
    {
        int indx = 0;
        int sum = 0;

        if(arr!=null)
        {
            for(int i=0;i<nNumbers;i++)
            {
                if(arr[i]==0)
                {
                    indx = i;
                }
                else
                {
                    sum+=i+2;
                }
            }
        }
        System.out.println("Missing index: "+indx);
        return indx;
    }
    
    static class ExamSearchInputs
    {

        private Set<String> searchTokenDelimiter = new HashSet<String>();
        private Set<String> simpleSearchValues = new HashSet<String>();
        private Set<String> tokenSearchValues = new HashSet<String>();

        public ExamSearchInputs(String userInput)
        {
            extractSearchTokens(userInput);
            buildTokenSearchQueryResult(userInput);
        }

        //Extract search token's like id, name, mrn etc.,
        private void extractSearchTokens(String rawQueryInput)
        {
            //Create pattern that use to replace/split    
            Pattern searchTokenPattern = Pattern.compile("^\\w+:");
            Pattern removeQuotesPattern = Pattern.compile("\"");

            // get the matcher object
            Matcher matcher = removeQuotesPattern.matcher(rawQueryInput);
            String[] splitedQuery = matcher.replaceAll("").split(" ");
            for (String q : splitedQuery)
            {
                //Extract search tokens(name:, iname: id, mrn: etc.,)
                Matcher m = searchTokenPattern.matcher(q);
                if (m.find())
                {
                    searchTokenDelimiter.add(m.group(0));
                }
            }
        }

        private void buildTokenSearchQueryResult(String userInput)
        {
            List<String> searchTokenAndValues = breakDownToTokenAndValues(userInput);

            for (String stv : searchTokenAndValues)
            {
                //Before add to token search list, ensure the value is formatted
                narrowDownQueryToken(stv);
            }
            
            System.out.println("\nSimple values :");
            for (String ss : simpleSearchValues)
                System.out.println(ss);
            System.out.println("\nToken values :");
            for (String ss : getTokenSearchValues())
                System.out.println(ss);
        }

        private List<String> breakDownToTokenAndValues(String userInput)
        {
            List<String> result = new ArrayList<String>();
            for (String s : searchTokenDelimiter)
            {
                userInput = userInput.replaceAll("\\b" + s, "&" + s).trim();
            }

            //Break down token and value
            String[] splitedSearchTokenValues = userInput.replaceFirst("^" + "&", "").split("&");

            //Remove empty string
            for (String s : splitedSearchTokenValues)
            {
                if (!"".equals(s))
                {
                    result.add(s.toLowerCase());
                }
            }
            return result;
        }

        private void narrowDownQueryToken(String searchTokenValue)
        {
            int vCount = 0;
            String formattedTokenValue = "";
            searchTokenValue = searchTokenValue.replaceAll("[^\\w%:*?-]+", " ").trim();
            String[] splitedValues = searchTokenValue.replaceFirst("^" + " ", "").split(" |,|'");
            //Format patient name values lastname firstname middlename and other token values
            if (!searchTokenDelimiter.isEmpty())
            {
                vCount = 0;
                for (String s : splitedValues)
                {
                    vCount++;
                    if (searchTokenValue.startsWith("name:") && vCount <= 3)
                    {
                        formattedTokenValue = formattedTokenValue + " " + s;
                    }
                    else if (searchTokenValue.contains(":") && vCount <= 1) //get
                    {
                        formattedTokenValue = formattedTokenValue + " " + s;
                    }
                    else
                    {
                        simpleSearchValues.add(s.replaceFirst("^" + " ", ""));
                    }
                }
                tokenSearchValues.add(formattedTokenValue.replaceFirst("^" + " ", ""));
            }
            else
            {
                for (String s : splitedValues)
                {
                    simpleSearchValues.add(s.replaceFirst("^" + " ", ""));
                }
            }
        }

        public Set<String> getSimpleSearchValues()
        {
            return simpleSearchValues;
        }

        public Set<String> getTokenSearchValues()
        {
            return tokenSearchValues;
        }

    
    }
    

  static  class C implements Cloneable {
        static {
            System.out.println("Hello.....");
        }
    }
    
    public void run()
    {
        example = new Example();
        for(int i=1;i<=5;i++){
            new Thread("Thread : "+i){
                public void run() {
                    int i =0;
                    while (i < 5)
                    {
                        try
                        {
                            this.sleep(1000);
                        System.out.println(this.getName());
                        i++;
                        }

                        catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
    
    public void finalize()
    {
        if(example!=null)
        {
            example=null;
        }
    }
    
    @Override
    protected Example clone() {
    
        try
        {
            return (Example) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

class B {
    int x =10;
    void add(int x)
    {
        System.out.println("Inside B: " + x);
    }
}

class A extends B {
    int x = 20;
    void add(int x)
    {
        System.out.println("Inside A: " + x);
    }
}


