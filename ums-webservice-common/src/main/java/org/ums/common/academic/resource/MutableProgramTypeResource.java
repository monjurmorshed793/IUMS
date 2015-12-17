package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.domain.model.MutableProgramType;
import org.ums.domain.model.ProgramType;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


public class MutableProgramTypeResource extends Resource {

  @Autowired
  ResourceHelper<ProgramType, MutableProgramType, Integer> mResourceHelper;

  @POST
  public Response createProgramType(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateProgramType(final @PathParam("object-id") int pObjectId, final JsonObject pJsonObject) throws Exception {
    ProgramType programType = mResourceHelper.load(pObjectId);
    if (programType != null) {
      mResourceHelper.put(programType, pJsonObject);
    }
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteProgramType(final @PathParam("object-id") int pObjectId) throws Exception {
    ProgramType programType = mResourceHelper.load(pObjectId);
    if (programType != null) {
      mResourceHelper.delete(programType.edit());
    }
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }
}