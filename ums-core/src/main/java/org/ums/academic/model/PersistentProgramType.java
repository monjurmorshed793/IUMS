package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.MutableProgramType;
import org.ums.domain.model.ProgramType;
import org.ums.manager.ContentManager;
import org.ums.util.Constants;

public class PersistentProgramType implements MutableProgramType {
  private static ContentManager<ProgramType, MutableProgramType, Integer> sManager;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Constants.SERVICE_CONTEXT);
    sManager = (ContentManager<ProgramType, MutableProgramType, Integer>)applicationContext.getBean("programTypeManager");
  }

  int mId;
  String mName;

  public PersistentProgramType() {

  }

  public PersistentProgramType(final ProgramType pProgramType) {
    mId = pProgramType.getId();
    mName = pProgramType.getName();
  }

  public int getId() {
    return mId;
  }

  public void setId(final int pId) {
    mId = pId;
  }

  public String getName() {
    return mName;
  }

  public void setName(final String pName) {
    mName = pName;
  }

  public void delete() throws Exception {
    sManager.delete(this);
  }

  public void commit(final boolean pUpdate) throws Exception {
    if (pUpdate) {
      sManager.update(this);
    } else {
      sManager.create(this);
    }
  }

  public MutableProgramType edit() throws Exception {
    return new PersistentProgramType(this);
  }
}