package com.example.taweesoft.joinpa.Library;

public class Friend extends User{
	public Friend(String username,String notifyKey){
		super(username,notifyKey);
	}

    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj.getClass() != Friend.class) return false;
        Friend friend = (Friend)obj;
        return super.getUsername().equals(friend.getUsername());
    }
}
