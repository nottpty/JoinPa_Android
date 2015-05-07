package com.example.taweesoft.joinpa.Library;

import android.util.Log;

import com.example.taweesoft.joinpa.MainActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
                    Log.d("OWNER DATABASE : ", owner.hashCode()+"");
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
    				friendList.add(0,(Friend)getEachUser(friendName));
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
    				Map<User,Integer> invitedList = getMapInvitedList(dataArr[1]);
    				int iconID = Integer.parseInt(dataArr[4]);
    				String[] dateArr = dataArr[7].split("/"); //[10, 03, 2015]
                    String[] timeArr = dataArr[8].split(":"); //[08, 00, 00]
    				Date date = createDate(dateArr,timeArr);
    				Event event = new Event(eventID,owner,invitedList,iconID,topic,note,date);
    				Log.d("PPP : ", event.getTopic());
    				eventList.add(0,event);
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
    
    public static Map<User,Integer> getMapInvitedList(String eventID){
    	HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_joinList WHERE EventID=\'%s\'",eventID));
    	Map<User,Integer> invitedList = new HashMap<User,Integer>();
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
    	Map<User,Integer> invitedListMap = event.getInvitedList();
    	String ownerName = owner.getUsername();
    	String invitedListStr="";
    	for(Map.Entry<User,Integer> each : invitedListMap.entrySet()){
            User friend = each.getKey();
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
                    User owner = allUser.get(ownerName);
                    Map<User,Integer> invitedList = getMapInvitedList(eventID);
                    String[] dateArr = dataArr[7].split("/");
                    String[] timeArr = dataArr[8].split(":");
                    int iconID = Integer.parseInt(dataArr[4]);
                    Date date = createDate(dateArr, timeArr);
                    event = new JoiningEvent(eventID,owner,invitedList,iconID,dataArr[5],dataArr[6],date);
                }
                count++;
            }
        }catch(IOException e){ e.printStackTrace();}
        return event;
    }

    public static List<JoiningEvent> myJoiningEvents(Owner owner,int whichStatus){
        Log.d("OOOOOO : ", whichStatus +"");
        List<JoiningEvent> events = new ArrayList<JoiningEvent>();
        HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_joinList WHERE Username=\'%s\' AND Status=\'%s\'",owner.getUsername(),whichStatus));
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String input;
            int count=0;
            while((input = in.readLine()) != null){
                if(count!=0){
                    String[] dataArr = input.split("\t");
                    Log.d("EEEE : ", whichStatus + " " + Arrays.toString(dataArr));
                    //[, EventID, Username, Status]
                    String eventID = dataArr[1];
                    JoiningEvent joiningEvent = getJoiningEvent(eventID);
                    events.add(0,joiningEvent);
                }
                count++;
            }
        }catch(IOException e){ e.printStackTrace();}
        return events;
    }

    public static void removeEvent(Event event){
        String eventID = event.getEventID();
        HttpURLConnection connect = getConnection(String.format("DELETE FROM tb_event WHERE EventID=\'%s\'", eventID));
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

    public static void addFriend(Friend friend){
        String ownerName = Resources.owner.getUsername();
        String friendName = friend.getUsername();
        int isFriend = 0 ;
        if(!checkIsFriend(friendName)) isFriend = 1;

        HttpURLConnection connect = getConnection(String.format("INSERT INTO tb_friendList VALUES (\'%s\',\'%s\',\'%s\')",ownerName,friendName,isFriend));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNext()) scan.next();
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }

        if(isFriend == 1){
            sendMsg(friend.getNotifyKey(),String.format("%s just add you to friend list",Resources.owner.getUsername()));
        }
    }

    public static void acceptFriend(Friend friend){
        HttpURLConnection connect = getConnection(String.format("UPDATE tb_friendList SET isFriend=\'0\' WHERE OwnerName=\'%s\' AND FriendName=\'%s\'",friend.getUsername(),Resources.owner.getUsername()));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNext()) scan.next();
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }
    }
    public static void joinEvent(JoiningEvent event,int status){
        String name = Resources.owner.getUsername();
        String eventID = event.getEventID();
        HttpURLConnection connect = getConnection(String.format("UPDATE tb_joinList SET Status=\'%s\' WHERE EventID=\'%S\' AND Username=\'%s\'",status,eventID,name));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNext()) scan.next();
            scan.close();
            connect.disconnect();
        }catch(IOException e) { e.printStackTrace(); }
    }

    public static void sendMsg(String key,String msg){
        HttpURLConnection connect = null;
        try{
            String url = "http://www.cmvk-tech.com/joinpa/gcm_engine.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String message = "message=" + msg;
            String regisKey = "registrationIDs=" + key;
            String apiKey = "apiKey=AIzaSyAJfTqAcHM-TlWYFawJXfCmPDR23PB7-Bs";
            String parameter = apiKey+"&"+regisKey+"&"+message;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(parameter);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateNotificationKey(String key,String username){
        HttpURLConnection connect = getConnection(String.format("UPDATE tb_login SET NotiKey=\'%s\' WHERE Username=\'%s\'",key,username));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNext()) scan.next();
            scan.close();
            connect.disconnect();
        }catch(IOException e) { e.printStackTrace(); }
    }

    public static boolean checkExistUsername(String username){
        HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_login WHERE Username=\'%s\'",username));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            scan.nextLine();
            if(scan.hasNextLine()) return true;
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }
        return false;
    }

    public static void addNewUser(String username,String password, String notiKey){
        HttpURLConnection connect = getConnection(String.format("INSERT INTO tb_login VALUES (\'%s\',\'%s\',\'%s\')",username,password,notiKey));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            while(scan.hasNextLine()) scan.nextLine();
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace();}
    }

    public static boolean checkIsFriend(String friendName){
        HttpURLConnection connect = getConnection(String.format("SELECT * FROM tb_friendList WHERE OwnerName=\'%s\' AND FriendName=\'%s\'",friendName,Resources.owner.getUsername()));
        try{
            Scanner scan = new Scanner(connect.getInputStream());
            Log.d("TTTTTTTTT : " , scan.nextLine());
            while(scan.hasNext()) {
                Log.d("NNNNN : " , scan.nextLine());
                return true;
            }
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }
        return false;
    }
    public static List<Friend> getWaitingFriendList(Owner owner){
        HttpURLConnection connect = getConnection(String.format("SELECT OwnerName FROM tb_friendList WHERE FriendName=\'%s\' AND isFriend=\'1\'",owner.getUsername()));
        List<Friend> friendWaitingList = new ArrayList<Friend>();
        try{
            Scanner scan = new Scanner(connect.getInputStream());

            scan.nextLine();
            while(scan.hasNext()){
                String friendName = scan.next();
                friendWaitingList.add(0,(Friend)getEachUser(friendName));
            }
            scan.close();
            connect.disconnect();
        }catch(IOException e){ e.printStackTrace(); }
        return friendWaitingList;
    }
}
