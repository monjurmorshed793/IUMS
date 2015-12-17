package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/academic/semester")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterResource extends MutableSemesterResource {
  @Autowired
  @Qualifier("semesterManager")
  ContentManager<Semester, MutableSemester, Integer> mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    List<Semester> semesters = mManager.getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (Semester semester : semesters) {
      children.add(mResourceHelper.toJson(semester, mUriInfo));
    }
    object.add("entries", children);
    return object.build();
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public JsonObject get(final @PathParam("object-id") String pObjectId) throws Exception {
    Semester semester = mManager.get(Integer.parseInt(pObjectId));
    return mResourceHelper.toJson(semester, mUriInfo);
  }
}
