package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.readOnly.Department;
import org.ums.domain.model.readOnly.Teacher;
import org.ums.manager.ContentManager;
import org.ums.manager.TeacherManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class TeacherResourceHelper extends ResourceHelper<Teacher, MutableTeacher, String> {
  @Autowired
  TeacherManager mManager;

  @Autowired
  @Qualifier("departmentManager")
  ContentManager<Department, MutableDepartment, String> mDepartmentManager;

  @Autowired
  private List<Builder<Teacher, MutableTeacher>> mBuilders;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected TeacherManager getContentManager() {
    return mManager;
  }

  @Override
  protected List<Builder<Teacher, MutableTeacher>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(Teacher pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getByDepartment(final String pDepartmentId, final UriInfo pUriInfo) throws Exception {
    Department department = mDepartmentManager.get(pDepartmentId);
    List<Teacher> teachers = getContentManager().getByDepartment(department);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Teacher teacher : teachers) {
      children.add(toJson(teacher, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }
}
