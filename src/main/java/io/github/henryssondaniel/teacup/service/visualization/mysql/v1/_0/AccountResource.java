package io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0;

import javax.management.Notification;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("{a:v1/account|v1.0/account|account}")
public class AccountResource {
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("logIn")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response logIn(Notification notification) {
    return Response.ok().entity(notification).build();
  }

  @GET
  @Path("ping")
  public static Response ping() {
    return Response.ok().entity("Service online").build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("recover")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response recover(Notification notification) {
    return Response.status(201).entity(notification).build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("signUp")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response signUp(Notification notification) {
    return Response.status(201).entity(notification).build();
  }
}
