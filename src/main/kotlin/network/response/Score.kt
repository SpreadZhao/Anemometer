package network.response

import com.google.gson.annotations.SerializedName


// DM: 代码
data class CourseScore(
    @SerializedName("BY1") val by1: Any?,
    @SerializedName("BY2") val by2: Any?,
    @SerializedName("BY3") val by3: Any?,
    @SerializedName("BY4") val by4: Any?,
    @SerializedName("BY5") val by5: Any?,
    @SerializedName("BY6") val by6: Any?,
    @SerializedName("BY7") val by7: Any?,
    @SerializedName("BY8") val by8: Any?,
    @SerializedName("BY9") val by9: Any?,
    @SerializedName("BY10") val by10: Any?,
    @SerializedName("XDFSDM") val studyMethodCode: String?,                         // 修读方式代号
    @SerializedName("XDFSDM_DISPLAY") val studyMethodName: String?,                 // 修读方式
    @SerializedName("KSSJ") val examTime: String?,                                  // 考试时间
    @SerializedName("SJKSRQ") val realExamDate: String?,                            // 实际考试日期
    @SerializedName("KKDWDM") val lectureDepartmentCode: String?,                   // 开课单位代号
    @SerializedName("KKDWDM_DISPLAY") val lectureDepartmentName: String?,           // 开课单位名字
    @SerializedName("SFYX") val isValid: String?,                                   // 是否有效
    @SerializedName("SFYX_DISPLAY") val isValidName: String?,                       // 是否有效, 就是汉字"是"或者"否"
    @SerializedName("KSLXDM") val examTypeCode: String?,                            // 考试类型代号
    @SerializedName("KSLXDM_DISPLAY") val examTypeName: String?,                    // 考试类型
    @SerializedName("XNXQDM") val schoolYearAndSemester: String?,                   // 学年学期代号
    @SerializedName("XNXQDM_DISPLAY") val schoolYearAndSemesterName: String?,       // 学年学期
    @SerializedName("SHOWMAXCJ") val showMaxScore: String?,                         // 满分是多少（我猜的）
    @SerializedName("SHOWMAXCJ_DISPLAY") val showMaxScoreName: String?,             // 满分是多少
    @SerializedName("KCH") val lectureNumber: String?,                              // 课程号
    @SerializedName("XSKCH") val stuLectureNumber: String?,                         // 学生课程号
    @SerializedName("SFJG") val passCode: String?,                                  // 是否及格代号
    @SerializedName("SFJG_DISPLAY") val pass: String?,                              // 是否及格
    @SerializedName("KCM") val lectureName: String?,                                // 课程名
    @SerializedName("XSKCM") val stuLectureName: String?,                           // 学生课程名
    @SerializedName("XGXKLBDM") val publicLectureTypeCode: String?,                 // 校公选课类别代号
    @SerializedName("XGXKLBDM_DISPLAY") val publicLectureTypeName: String?,         // 校公选课类别
    @SerializedName("XFJD") val gpa: Double?,                                       // 绩点
    @SerializedName("XS") val classHour: Double?,                                   // 学时
    @SerializedName("KCXZDM") val lecturePropertyCode: String?,                     // 课程性质代号
    @SerializedName("KCXZDM_DISPLAY") val lecturePropertyName: String?,             // 课程性质
    @SerializedName("WID") val wid: String?,                                        // 不懂
    @SerializedName("TSYYDM") val tsyydm: String?,                                  // 特殊原因代号
    @SerializedName("TSYYDM_DISPLAY") val tsyydmDisplay: String?,                   // 特殊原因
    @SerializedName("ZCJ") val totalScore: Double?,                                 // 总成绩
    @SerializedName("SFZX") val isMajorCode: String?,                               // 是否主修代号
    @SerializedName("SFZX_DISPLAY") val isMajorName: String?,                       // 是否主修
    @SerializedName("XH") val stuId: String?,                                       // 学号
    @SerializedName("ORDERFILTER") val orderFilter: Any?,                           // 不懂，应该是用其它方式查的时候返回的排序过滤
    @SerializedName("CXCKDM") val takeOrRetakeCode: String?,                        // 初修还是重考代号
    @SerializedName("CXCKDM_DISPLAY") val takeOrRetake: String?,                    // 初修还是重考
    @SerializedName("XF") val credit: Double?,                                      // 学分
    @SerializedName("JXBID") val classId: String?,                                  // 教学班id
    @SerializedName("KCLBDM") val lectureTypeCode: String?,                         // 课程类别代号
    @SerializedName("KCLBDM_DISPLAY") val lectureTypeName: String?,                 // 课程类别
    @SerializedName("KXH") val kxh: String?,                                        // 课序号0
    @SerializedName("DJCJLXDM_DISPLAY") val scoreLevelTypeName: String?,            // 等级成绩类型（百分制，等级制）
    @SerializedName("HASFC") val hasfc: Any?,                                       // 不懂
    @SerializedName("RZLBDM") val rzlbdm: Any?,                                     // 不懂
    @SerializedName("SFPJ") val sfpj: Any?,                                         // 不懂，是否pj?
    @SerializedName("DJCJLXDM") val scoreLevelCode: String?,                        // 等级成绩代码
    @SerializedName("DJCJMC") val scoreLevel: String?                               // 等级成绩（优秀，通过）
)

data class ExtParams(
    @SerializedName("logId")
    val logId: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("totalPage")
    val totalPage: Int,
    @SerializedName("msg")
    val msg: String
)