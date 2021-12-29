package chatclientserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class signin_handler {
    final private static String FilePath = System.getProperty("user.dir") + "\\accounts.txt";
    private final HashMap<String, String> accounts;
    private final File file;
    private BufferedWriter bf;
    private BufferedReader br;
    private String userName;
    
    public signin_handler() {
        accounts = new HashMap<>();
        file = new File(FilePath);
        get_accounts();
    }
    
    private void get_accounts(){
        try{
            br = new BufferedReader(new FileReader(file));
            String line;
  
            // read file line by line
            while ((line = br.readLine()) != null) {
  
                // split the line by :
                String[] parts = line.split(":");
  
                // first part is name, second is number
                String name = parts[0].trim();
                String number = parts[1].trim();
  
                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !number.equals(""))
                    this.accounts.put(name, number);
            }
            br.close();
        }catch (IOException ex) {
            System.out.println("there is something wrong with accounts file on get accounts!");
        }
        
    }
    
    public boolean add_user (String username, String password){
        if (!accounts.containsKey(username)){
            this.accounts.put(username, password);
            System.out.println(accounts);
            try {
                bf = new BufferedWriter( new FileWriter(file, true));
                // put key and value separated by a colon
                bf.write(username + ":"+ password);
                // new line
                bf.newLine();
                bf.flush();
                bf.close();
                this.userName = username;
                return true;
            } catch (IOException ex) {
                System.out.println("there is something wrong with accounts file on add user!");
            }
        }else{
            return false;
        }
        return false;
    }
    
    public boolean signin(String username, String password){
        if (this.accounts.get(username) == null){
            return false;
        }else{
            if(this.accounts.get(username).equals(password)){
                this.userName = username;
                return true;
            }
            return false;
        }
    }
}
