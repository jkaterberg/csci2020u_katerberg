package server;

public class Message {
    private String message;
    private String username;

    //constructor
    public Message(String username, String message){
        this.message = message;
        this.username = username;
    }

    public String toString(){
        return this.username + ": " + this.message;
    }

   //setters
   public void setMessage(String newMessage){
        this.message = newMessage;
   }
   public void setUsername(String newUser){
        this.username = newUser;
   }

   //getters
    public String getMessage(){
        return this.message;
    }
    public String getUsername(){
        return this.username;
    }
}
