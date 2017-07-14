package com.cmpe273.smart_factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/server")
public class RobotDMIR {

	@GET
	@Path("/create/{object_id}/{ACL}/{access_ctl_owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleCreateRequest(@PathParam("object_id") String object_id,@PathParam("ACL") String ACL,@PathParam("access_ctl_owner") String access_ctl_owner) throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      preparedStatement = connect
	          .prepareStatement("insert into  ddevice_objects(object_id,ACL,access_control_onwer) values (?, ?, ? )");
	     
	  
      //int status = 0;   
	      preparedStatement.setString(1, object_id);
	      preparedStatement.setString(2,ACL);
	      preparedStatement.setString(3, access_ctl_owner);
	      
	       preparedStatement.executeUpdate();
	      
	      preparedStatement = connect
		          .prepareStatement("select * from ddevice_objects where object_id=?");  
	      preparedStatement.setString(1, object_id);
	      ResultSet rs = preparedStatement.executeQuery();  
	      
	      //System.out.println("Saving bootsrap information...");
	      
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("object_id"));
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("ACL"));
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("access_control_onwer"));
	      }
	      
	      preparedStatement.close();
	      connect.close();
	      
	      String reply="Object" + object_id + "Created" ;
	      
	      return reply;
	      
	  
	}
	
	@GET
	@Path("/delete/{object_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleDeleteRequest(@PathParam("object_id") String object_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");
     
      preparedStatement = connect
	          .prepareStatement("delete from  ddevice_objects where object_id=?");
	  
	      //int status = 0;  
      
	      preparedStatement.setString(1, object_id);
	      	      
	      preparedStatement.executeUpdate();
	      
	      preparedStatement.close();
	      connect.close();
	      
	      String reply="Object" + object_id + "deleted" ;
	      
	      return reply;
	      
	  
	}
	

	@GET
	@Path("/read/{resource_id}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleReadRequest(@PathParam("resource_id") String resource_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      preparedStatement = connect
	          .prepareStatement("select resource,value from resource_values where resource_id =?");
	  
	           
	      //preparedStatement.setString(1, object_id);
	      preparedStatement.setString(1, resource_id);
	      	      
         ResultSet rs = preparedStatement.executeQuery();  
	      
	      System.out.println("Reading value for...resource" + resource_id);
	      System.out.println("Value:");
	      
	      JSONArray jsonarray = new JSONArray();
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("resource"));
	    	 // String robot =  rs.getString("resource");
	    	  JSONObject robot = new JSONObject();
	    	  robot.put("Robot", rs.getString("resource"));
	    	  System.out.println(rs.getString("value"));
	    	  JSONObject value = new JSONObject();
	    	  value.put("state",rs.getString("value"));
	    	  jsonarray.add(robot);
	    	  jsonarray.add(value);
	    	}
	      
	      preparedStatement.close();
	      connect.close();
	      	      
	      //String reply = robot + ": " + value  ;
	      
	      return jsonarray.toString();
	   
	}

	
	@GET
	@Path("/write/{resource_id}/{new_value}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleWriteRequest(@PathParam("resource_id") String resource_id,@PathParam("new_value") String new_value)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      // DB connection setup 
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where resource_id = ?");
	  
          preparedStatement.setString(1,new_value);     
	     // preparedStatement.setString(2, object_id);
	      preparedStatement.setString(2, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	      
	      System.out.println("Updated new value for resource" + resource_id);
	      System.out.println("Value:");
	     
	      preparedStatement = connect
		          .prepareStatement("select resource,value from resource_values where  resource_id = ?");
		  
		           
		      //preparedStatement.setString(1, object_id);
		      preparedStatement.setString(1, resource_id);
		      	      
	         ResultSet rs = preparedStatement.executeQuery();  
		  
	      
	         JSONArray jsonarray = new JSONArray();
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("resource"));
		    	 // String robot =  rs.getString("resource");
		    	  JSONObject robot = new JSONObject();
		    	  robot.put("Robot", rs.getString("resource"));
		    	  System.out.println(rs.getString("value"));
		    	  JSONObject value = new JSONObject();
		    	  value.put("state",rs.getString("value"));
		    	  jsonarray.add(robot);
		    	  jsonarray.add(value);
	     	}
	      
	      preparedStatement.close();
	      connect.close();
	      	      
	      return jsonarray.toString();
	  
	  }


	@GET
	@Path("/discover/{object_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleDiscoverRequest(@PathParam("object_id") String object_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      // DB connection setup 
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      preparedStatement = connect
	          .prepareStatement("select * from  device_resources where object_id='3'");
	  
	      //int status = 0;  
      
	      //preparedStatement.setString(1, object_id);
	      	      
	      ResultSet rs = preparedStatement.executeQuery();  
		       
	      String reply_appl_link = null;
	      //String [] pmin = null,pmax = null,arr_object_id = null,arr_resource_id=null;
	      //int i=0;
	      
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("object_id"));
	//    	  arr_object_id[i] = rs.getString("object_id");
	    	  System.out.println(" ");
	     	  System.out.println(rs.getString("resource_id"));
	  //   	 arr_resource_id[i] = rs.getString("resource_id");
	    	  System.out.println(" ");
	    //	  System.out.println(" ");
	     	  System.out.println(rs.getString("attr_maxp"));
	     //	  if(rs.getString("attr_maxp") != null) 
	     	//	  pmax[i] = rs.getString("attr_maxp");
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("attr_minp"));
	    	  //    pmin[i] = rs.getString("attr_minp");
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("attr_gt"));
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("attr_lt"));
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("atrr_step"));
	    	  System.out.println(" ");
	    	  System.out.println(rs.getString("attr_cancel"));
	    	//  i++;
	     	}
	      preparedStatement.close();
	      connect.close();
	      
	      reply_appl_link="<Object-id:3><resource_id:1>;" + "pmax=10,pmin=3,<Object-id:3><resource_id:2>;" + "pmax=10,pmin=3";
	      
	      return reply_appl_link;
	      
	  
	}
	

	@GET
	@Path("/write_attr/{object_id}/{resource_id}/{new_value}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleWriteAttrbuteRequest(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id,@PathParam("new_value") String new_value)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      preparedStatement = connect
	          .prepareStatement("update device_resources set atrr_step=? where object_id=? and resource_id = ?");
	  
          preparedStatement.setString(1,new_value);     
	      preparedStatement.setString(2, object_id);
	      preparedStatement.setString(3, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	      
	      System.out.println("Updated atrribute for resource" + resource_id);
	      System.out.println("Step:");
	     
	      preparedStatement = connect
		          .prepareStatement("select atrr_step from device_resources where object_id=? and resource_id = ?");
		  
		           
		      preparedStatement.setString(1, object_id);
		      preparedStatement.setString(2, resource_id);
		      	      
	         ResultSet rs = preparedStatement.executeQuery();  
		  
	      
	      
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("atrr_step"));
	     	}
	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply = "Success: Atrribute Changed"  ;
	      
	      return reply;
	  
	  }

	
	@GET
	@Path("/execute/{resource_id}/{new_value}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleExecuteRequest(@PathParam("resource_id") String resource_id,@PathParam("new_value") String new_value)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where resource_id = ?");
	  
          preparedStatement.setString(1,new_value);     
	      
	      preparedStatement.setString(2, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	      
	      System.out.println("Executing operation on resource" + resource_id);
	      System.out.println("State changed:");
	     	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply = "Execute operation completed Successfully"  ;
	      
	      return reply;
	  
	  }

	@GET
	@Path("/observe/manuf_robo/{object_id}/{resource_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleObserveRequestManufRobo(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      Random r = new Random();
      int Low = 0;
      int High = 10;
      int Result = r.nextInt(High-Low) + Low;
      String new_scan_rate_value = Integer.toString(Result);
      
      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where object_id=? and resource_id = ?");
	  
          preparedStatement.setString(1,new_scan_rate_value);     
	      preparedStatement.setString(2, object_id);
	      preparedStatement.setString(3, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	 
      
      preparedStatement = connect
	          .prepareStatement("select value from resource_values where object_id=? and resource_id = ?");
	  
	           
	      preparedStatement.setString(1, object_id);
	      preparedStatement.setString(2, resource_id);
	      	      
         ResultSet rs = preparedStatement.executeQuery();  
	      
	      System.out.println("Reading value for...resource" + resource_id);
	      System.out.println("Value:");
	      
	      String value = null;
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("value"));
	    	  value =  rs.getString("value");
	    	}
	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply = value  ;
	      
	      return reply;
	      
	  
	}

	@GET
	@Path("/observe/assembly_robo/{object_id}/{resource_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleObserveRequestAssemblyRobo(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      Random r = new Random();
      int Low = 0;
      int High = 10;
      int Result = r.nextInt(High-Low) + Low;
      String new_scan_rate_value = Integer.toString(Result);
      
      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where object_id=? and resource_id = ?");
	  
          preparedStatement.setString(1,new_scan_rate_value);     
	      preparedStatement.setString(2, object_id);
	      preparedStatement.setString(3, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	 
      
      preparedStatement = connect
	          .prepareStatement("select value from resource_values where object_id=? and resource_id = ?");
	  
	           
	      preparedStatement.setString(1, object_id);
	      preparedStatement.setString(2, resource_id);
	      	      
         ResultSet rs = preparedStatement.executeQuery();  
	      
	      System.out.println("Reading value for...resource" + resource_id);
	      System.out.println("Value:");
	      
	      String value = null;
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("value"));
	    	  value =  rs.getString("value");
	    	}
	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply =  value  ;
	      
	      return reply;
	      
	  
	}

	@GET
	@Path("/observe/QA_robo/pass_rate/{object_id}/{resource_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleObserveRequestQaRoboPassRate(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      Random r = new Random();
      int Low = 0;
      int High = 10;
      int Result = r.nextInt(High-Low) + Low;
      String new_scan_rate_value = Integer.toString(Result);
      
      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where object_id=? and resource_id = ?");
	  
          preparedStatement.setString(1,new_scan_rate_value);     
	      preparedStatement.setString(2, object_id);
	      preparedStatement.setString(3, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	 
      
      preparedStatement = connect
	          .prepareStatement("select value from resource_values where object_id=? and resource_id = ?");
	  
	           
	      preparedStatement.setString(1, object_id);
	      preparedStatement.setString(2, resource_id);
	      	      
         ResultSet rs = preparedStatement.executeQuery();  
	      
	      System.out.println("Reading value for...resource" + resource_id);
	      System.out.println("Value:");
	      
	      String value = null;
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("value"));
	    	  value =  rs.getString("value");
	    	}
	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply =  value  ;
	      
	      return reply;
	      
	  
	}

	@GET
	@Path("/observe/QA_robo/defect_rate/{object_id}/{resource_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleObserveRequestQARoboDefectRate(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      Random r = new Random();
      int Low = 0;
      int High = 5;
      int Result = r.nextInt(High-Low) + Low;
      String new_scan_rate_value = Integer.toString(Result);
      
      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where object_id=? and resource_id = ?");
	  
          preparedStatement.setString(1,new_scan_rate_value);     
	      preparedStatement.setString(2, object_id);
	      preparedStatement.setString(3, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	 
      
      preparedStatement = connect
	          .prepareStatement("select value from resource_values where object_id=? and resource_id = ?");
	  
	           
	      preparedStatement.setString(1, object_id);
	      preparedStatement.setString(2, resource_id);
	      	      
         ResultSet rs = preparedStatement.executeQuery();  
	      
	      System.out.println("Reading value for...resource" + resource_id);
	      System.out.println("Value:");
	      
	      String value = null;
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("value"));
	    	  value =  rs.getString("value");
	    	}
	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply =  value  ;
	      
	      return reply;
	      
	  
	}

	@GET
	@Path("/observe/packaging_robo/{object_id}/{resource_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleObserveRequestPackagingRobo(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id)throws SQLException{
		
	Connection connect = null;
	PreparedStatement preparedStatement = null;
      // DB connection setup 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/lwm2mclient?" + "user=root&password=12345");

      Random r = new Random();
      int Low = 0;
      int High = 10;
      int Result = r.nextInt(High-Low) + Low;
      String new_scan_rate_value = Integer.toString(Result);
      
      preparedStatement = connect
	          .prepareStatement("update resource_values set value=? where object_id=? and resource_id = ?");
	  
          preparedStatement.setString(1,new_scan_rate_value);     
	      preparedStatement.setString(2, object_id);
	      preparedStatement.setString(3, resource_id);
	      	      
          preparedStatement.executeUpdate();  
	 
      
      preparedStatement = connect
	          .prepareStatement("select value from resource_values where object_id=? and resource_id = ?");
	  
	           
	      preparedStatement.setString(1, object_id);
	      preparedStatement.setString(2, resource_id);
	      	      
         ResultSet rs = preparedStatement.executeQuery();  
	      
	      System.out.println("Reading value for...resource" + resource_id);
	      System.out.println("Value:");
	      
	      String value = null;
	      while(rs.next())
	      {
	    	  System.out.println(rs.getString("value"));
	    	  value =  rs.getString("value");
	    	}
	      
	      preparedStatement.close();
	      connect.close();
	      
	      
	      String reply =  value  ;
	      
	      return reply;
	      
	  
	}

	
	@GET
	@Path("/cancel/{object_id}/{resource_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String HandleCancelObservervationRequest(@PathParam("object_id") String object_id,@PathParam("resource_id") String resource_id)throws SQLException{
		
	  
	      
	      String reply = "Observation cancelled for object:" + object_id + " resouce:" + resource_id  ;
	      
	      return reply;
	      
	  
	}



}
