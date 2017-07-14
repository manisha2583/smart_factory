package com.cmpe273.smart_factory;

import java.util.Scanner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WorkstationCli {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
	
			while(true)
		      {
			

		      Scanner in = new Scanner(System.in);
		     
		      String msg = in.nextLine();
		      
		      if (msg.equals("show pl"))
		      {
		    	  //String object_id = "3";
		    	  String resource_id = "1";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/smart_factory/webapi/server/read/"  + resource_id );

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		      }else if(msg.equals("run pl"))
		      {
		    	  String resource_id = "1";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/smart_factory/webapi/server/write/"  + resource_id +"/ACTIVE" );

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}

		     
		      }else if(msg.equals("observe pl"))
		      {
		    	  //observe all four robots
		    	 
		    	  String manufacturing_rate=null;
		    	  String assembly_rate=null;
		    	  String QA_pass_rate=null;
		    	  String QA_defect_rate=null;
		    	  String packaging_rate=null;
		    	  Client client = Client.create();
		    	  WebResource webResource;
		    	  ClientResponse response;
		    	  do
		    	  {
					 webResource = client
						   .resource("http://localhost/smart_factory/webapi/server/observe/manuf_robo/3/2");

						 response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						    manufacturing_rate = response.getEntity(String.class);

							
							System.out.println("Manufacturing Rate:" +  manufacturing_rate );

						}
						Thread.sleep(3000);  // sleep for 3 minutes
						 webResource = client
								   .resource("http://localhost/smart_factory/webapi/server/observe/assembly_robo/4/2");

								 response = webResource.accept("application/json")
						                   .get(ClientResponse.class);

								if (response.getStatus() != 200) {
								   throw new RuntimeException("Failed : HTTP error code : "
									+ response.getStatus());
								}else
								{
								     assembly_rate = response.getEntity(String.class);

									
									System.out.println("Assembly Rate:" + assembly_rate);

								}
								Thread.sleep(3000);  // sleep for 3 minutes
								webResource = client
										   .resource("http://localhost/smart_factory/webapi/server/observe/QA_robo/pass_rate/5/2");

										 response = webResource.accept("application/json")
								                   .get(ClientResponse.class);

										if (response.getStatus() != 200) {
										   throw new RuntimeException("Failed : HTTP error code : "
											+ response.getStatus());
										}else
										{
										    QA_pass_rate = response.getEntity(String.class);

											
											System.out.println("QA Pass Rate:" + QA_pass_rate);

										}
										Thread.sleep(3000);  // sleep for 3 minutes
										webResource = client
												   .resource("http://localhost/smart_factory/webapi/server/observe/QA_robo/defect_rate/5/3");

												 response = webResource.accept("application/json")
										                   .get(ClientResponse.class);

												if (response.getStatus() != 200) {
												   throw new RuntimeException("Failed : HTTP error code : "
													+ response.getStatus());
												}else
												{
												     QA_defect_rate = response.getEntity(String.class);

													
													System.out.println("QA Defect Rate:" + QA_defect_rate);

												}
												Thread.sleep(3000);  // sleep for 3 minutes
												webResource = client
														   .resource("http://localhost/smart_factory/webapi/server/observe/packaging_robo/6/2");

														 response = webResource.accept("application/json")
												                   .get(ClientResponse.class);

														if (response.getStatus() != 200) {
														   throw new RuntimeException("Failed : HTTP error code : "
															+ response.getStatus());
														}else
														{
														     packaging_rate = response.getEntity(String.class);

															
															System.out.println("Packaging Rate:" + packaging_rate);

														}
														Thread.sleep(3000);  // sleep for 3 minutes

		    	  }while(!(manufacturing_rate.equals("0") | assembly_rate.equals("0") | QA_pass_rate.equals("0") | QA_defect_rate.equals("5") | packaging_rate.equals("0")));
		    	  
		    	  //System.out.println(manufacturing_rate + assembly_rate + QA_pass_rate + QA_defect_rate);
		    	  
		    	  System.out.println("Alert:Production line needs technical support");
		    	  System.out.println("Operation Paused");
		    	  //execute pause operation   -- production line is paused
		    	  String resource_id = "1";
		    	  String state ="PAUSED";
		    	  
		    	  webResource = client
						   .resource("http://localhost/smart_factory/webapi/server/execute/" + resource_id + "/" + state);

						 response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						     String reply = response.getEntity(String.class);

							
							System.out.println(reply);

						}

		      
		      
		      }
		      
		    	/*
		      break;
		      case 2: //discover
		      {
		    	  String object_id = "3";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/discover/" + object_id );

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		        }
			    break;
		      case 3: // write
		      {
		    	  String object_id = "3";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/write/" + object_id +"/1/STANDBY");

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		        }

		    	  break;
		      case 4: //write attribute
		      {
		    	  String object_id = "3";
		    	  String resource_id = "1";
		    	  String step = "10";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/write_attr/" + object_id + "/" + resource_id + "/" + step );

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		      }

			      break;
		      case 5: //execute
		      {
		    	  String object_id = "3";
		    	  String resource_id = "1";
		    	  String state = "PASSIVE";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/execute/" + object_id + "/" + resource_id + "/" + state );

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		      }

			      break;
		      case 6: //create
		      {
		    	  String object_id = "3";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/create/" + object_id +"/RWE/1");

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		        }

			      break;
		      case 7: //delete
		      {
		    	  String object_id = "3";
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/delete/" + object_id);

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		        }


			      break;
		      case 8: //observe
		      {
		    	  String object_id = "3";
		    	  String resource_id = "2";
		    	  String msg1 = null;
		    	  Client client = Client.create();
		    	  do
		    	  {
//		    	  while(msg1 == "y")
	//	    	  {
					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/observe/" + object_id + "/" + resource_id);

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
						Thread.sleep(3000);  // sleep for 3 minutes
						System.out.println("Do want to continue to observe(y/n): ");
					       msg1 = in.nextLine();
		    	  }while(msg1.equals("y"));
		      }

              break;
		      case 9: //cancel observation
		      {
		    	  String object_id = "3";
		    	  String resource_id = "2";
		    	  
		    	  Client client = Client.create();

					WebResource webResource = client
						   .resource("http://localhost/bootstrap/webapi/server/cancel/" + object_id + "/" + resource_id);

						ClientResponse response = webResource.accept("application/json")
				                   .get(ClientResponse.class);

						if (response.getStatus() != 200) {
						   throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
						}else
						{
						   String ClientResponse = response.getEntity(String.class);

							
							System.out.println(ClientResponse);

						}
		      }


			      break;
		      case 10: //exit
		    	  System.exit(0);
		    	  //return;
			      break;
			  default:    
		    	  break;
		      }
		      
		      } 
		      */
		      }
		 }catch (Exception e) {

					e.printStackTrace();
				
		 }
		
	}

}
