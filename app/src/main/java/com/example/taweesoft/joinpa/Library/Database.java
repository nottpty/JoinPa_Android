package com.example.taweesoft.joinpa.Library;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Database {
	private static String urlStr = Resources.URL;
	public static Map<String,Friend> allUser = getAllUser();
	private final static String WAITING = "0";
	
	public static Owner getOwner(String username,String password){
		Owner owner = null;
		HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_login WHERE Username=\'%s\' AND Password=\'%s\'",username, password));
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String input;
			int count=0;
			while((input = in.readLine()) != null){
				if(count!=0){
					String[] information = input.split("\t");
					//[, username, password, notifyKey]
					owner = new Owner(information[1],information[3]);
				}
				count++;
			}
			in.close();
			connect.disconnect();
		}catch(Exception e){ e.printStackTrace(); };
		return owner;
	}
    public static HttpURLConnection getConnection(String command){
        HttpURLConnection connect = null;
        BufferedReader in=null;
          
			try {
				URL url = new URL(urlStr);
				String query = "query=" + command;
	            connect = (HttpURLConnection) url.openConnection();
	            connect.setRequestMethod("POST");
	            connect.setDoInput(true);
	            connect.setDoOutput(true);
	            OutputStream out = connect.getOutputStream();
	            out.write(query.getBytes());
	            out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
        
        return connect;
    }
    
    public static Map<String,Friend> getAllUser(){
    	Map<String,Friend> allUser = new HashMap<String,Friend>();
    	HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_login"));
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		int count=0;
    		String input;
    		while((input = in.readLine())!=null){
    			if(count!=0){
    				String[] eachUser = input.split("\t");
    				//[, Username, Password, NotifyKey]
    				allUser.put(eachUser[1],new Friend(eachUser[1],eachUser[3]));
    			}
    			count++;
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	};
    	return allUser;
    }
    public static List<Friend> getFriendList(Owner owner){
    	String ownerName = owner.getUsername();
    	List<Friend> friendList = new ArrayList<Friend>();
    	HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_friendList WHERE OwnerName=\'%s\'", ownerName));
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		int count=0;
    		String input;
    		while((input = in.readLine())!=null){
    			if(count!=0){
    				String[] eachFriend = input.split("\t");
    				// [, OwnerName, friendName]
    				String friendName = eachFriend[2];
    				friendList.add((Friend)getEachUser(friendName));
    			}
    			count++;
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	};
    	return friendList;
    }
    
    public static User getEachUser(String name){
    	User user = null;
    	HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_login WHERE Username=\'%s\'",name));
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		String input;
    		int count=0;
    		while( (input = in.readLine()) != null ){
    			if(count!=0){
    				String[] information = input.split("\t");
    				//[, Username, Password, NotifyKey]
    				user = new Friend(information[1],information[3]);
    			}
    			count++;
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	};
    	return user;
    }
    
    public static List<Event> getMyEvent(Owner owner){
    	String ownerName = owner.getUsername();
    	List<Event> eventList = new ArrayList<Event>();
    	HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_event WHERE OwnerName=\'%s\'",ownerName));
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		String input;
    		int count=0;
    		while((input = in.readLine()) != null){
    			if(count != 0){
    				String[] dataArr = input.split("\t");
    				//[, EventID, OwnerName, InvitedList, IconID, Topic, Note, Date, Time]
                    String eventID = dataArr[1];
                    String topic = dataArr[5];
                    String note = dataArr[6];
    				Map<Friend,Integer> invitedList = getMapInvitedList(dataArr[1]);
    				int iconID = Integer.parseInt(dataArr[4]);
    				String[] dateArr = dataArr[7].split("/"); //[10, 03, 2015]
                    String[] timeArr = dataArr[8].split(":"); //[08, 00, 00]
    				Date date = createDate(dateArr,timeArr);
    				Event event = new Event(eventID,owner,invitedList,iconID,topic,note,date);
    				
    				eventList.add(event);
    			}
    			count++;
    		}
    	}catch(IOException e){ e.printStackTrace();}
    	return eventList;
    }

    public static Date createDate(String[] dateArr,String[] timeArr){
        Date date = new Date();
        date.setDate(Integer.parseInt(dateArr[0]));
        date.setMonth(Integer.parseInt(dateArr[1]));
        date.setYear(Integer.parseInt(dateArr[2]));
        date.setHours(Integer.parseInt(timeArr[0]));
        date.setMinutes(Integer.parseInt(timeArr[1]));
        return date;
    }
    public static void addJoiningList(String eventID,String username){
    	HttpURLConnection connect = getConnection(String.format("INSERT INTO tb_joinList VALUES (\'%s\',\'%s\',\'%s\')", eventID,username,WAITING));
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		while(in.readLine() != null){}
    		in.close();
    		connect.disconnect();
    	}catch(IOException e){
    		e.printStackTrace();
    	};
    }
    
    public static Map<Friend,Integer> getMapInvitedList(String eventID){
    	HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_joinList WHERE EventID=\'%s\'",eventID));
    	Map<Friend,Integer> invitedList = new HashMap<Friend,Integer>();
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		String input;
    		int count=0;
    		while((input = in.readLine()) != null){
    			if(count != 0){
    				String[] eachUser = input.split("\t");
    				//[, EventID, Username, Status]
    				int status = Integer.parseInt(eachUser[3]);
    				Friend friend = searchFriendInUsers(eachUser[2]);
    				if(friend!=null){
    					invitedList.put(friend, status);
    				}
    			}
    			count++;
    		}
    	}catch(IOException e){ e.printStackTrace();}
    	return invitedList;
    }
    
    public static Event addEvent(Event event){
        Owner owner = event.getOwner();
        Date date = event.getDate();
        int iconID = event.getIconID();
        String eventName = event.getTopic();
        String note = event.getNote();
    	String eventID = event.getEventID();
    	Map<Friend,Integer> invitedListMap = event.getInvitedList();
    	String ownerName = owner.getUsername();
    	String invitedListStr="";
    	for(Map.Entry<Friend,Integer> each : invitedListMap.entrySet()){
            Friend friend = each.getKey();
    		addJoiningList(eventID,friend.getUsername());
    		invitedListStr += ","+friend.getUsername();
    	}
    	String dateStr = String.format("%02d/%02d/%02d", date.getDate(),date.getMonth(),date.getYear());
    	String time = String.format("%02d:%02d", date.getHours(),date.getMinutes());
    	HttpURLConnection connect = getConnection(String.format("INSERT INTO tb_event VALUES (\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')", eventID, ownerName, invitedListStr, iconID, eventName, note, dateStr, time));
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
    		while(in.readLine() != null){}
    		in.close();
    		connect.disconnect();
    		event = new Event(eventID,owner,invitedListMap,iconID,eventName,note,date);
    	}catch(IOException e){
    		e.printStackTrace();
    	};
    	return event;
    }
    public static Friend searchFriendInUsers(String username){
    	Collection<Friend> collection = allUser.values();
    	for(User user : collection) {
    		if(username.equals(user.getUsername()))
    			return new Friend(user.getUsername(),user.getNotifyKey());
    	}
    	return null;
    }

    public static JoiningEvent getJoiningEvent(String eventID){
        JoiningEvent event=null;
        HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_event WHERE EventID=\'%s\'",eventID));
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String input;
            int count=0;
            while((input = in.readLine()) != null){
                if(count!=0){
                    String[] dataArr = input.split("\t");
                    //[, EventID, OwnerName, InvitedList, IconID, Topic, Note, Date, Time]
                    String ownerName = dataArr[2];
                    Map<Friend,Integer> invitedList = getMapInvitedList(eventID);
                    String[] dateArr = dataArr[7].split("/");
                    String[] timeArr = dataArr[8].split(":");
                    int iconID = Integer.parseInt(dataArr[4]);
                    Date date = createDate(dateArr,timeArr);
                    event = new JoiningEvent(eventID,ownerName,invitedList,iconID,dataArr[5],dataArr[6],date);
                }
                count++;
            }
        }catch(IOException e){ e.printStackTrace();}
        return event;
    }
    public static List<JoiningEvent> myJoiningEvents(Owner owner){
        List<JoiningEvent> events = new ArrayList<JoiningEvent>();
        HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_joinList WHERE Username=\'%s\'",owner.getUsername()));
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String input;
            int count=0;
            while((input = in.readLine()) != null){
                if(count!=0){
                    String[] dataArr = input.split("\t");
                    //[, EventID, Username, Status]
                    String eventID = dataArr[1];
                    JoiningEvent joiningEvent = getJoiningEvent(eventID);
                    events.add(joiningEvent);
                }
                count++;
            }
        }catch(IOException e){ e.printStackTrace();}
        return events;
    }

    public static void removeEvent(Event event){
        String eventID = event.getEventID();
        HttpURLConnection connect = getConnection(String.format("DELETE FROM tb_event WHERE EventID=\'%s\'",eventID));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNext()){ scan.next(); }
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }

        connect = getConnection(String.format("DELETE FROM tb_joinList WHERE EventID=\'%s\'", eventID ));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNext()){ scan.nextLine(); }
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }
    }


}
