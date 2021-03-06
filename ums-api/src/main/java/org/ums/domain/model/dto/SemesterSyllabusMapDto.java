package org.ums.domain.model.dto;

import org.ums.domain.model.readOnly.Program;
import org.ums.domain.model.readOnly.Semester;

/**
 * Created by User on 1/16/2016.
 */
public interface SemesterSyllabusMapDto {
  Semester getCopySemester();

  Semester getAcademicSemester();

  Program getProgram();
}
