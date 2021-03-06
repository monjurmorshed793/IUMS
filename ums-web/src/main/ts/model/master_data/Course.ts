module ums {
  export interface Course {
    courseId: string;
    courseNumber: string;
    creditHour:number;
    viewOrder:number;
    courseTitle: string;
    courseTypeId: string;
    courseTypeName: string;
    courseCategoryId: string;
    courseCategoryName: string;
    academicYearId: string;
    academicYearName: string;
    academicSemesterId: string;
    academicSemesterName: string;
    offerByDeptId: string;
    offerByDeptName: string;
    offerToDeptId: string;
    offerToDeptName: string;
    programId: string;
    programName: string;
    syllabusId: string;
    syllabusName: string;
    optionalGroupId: string;
    optionalGroupName: string;
  }
}