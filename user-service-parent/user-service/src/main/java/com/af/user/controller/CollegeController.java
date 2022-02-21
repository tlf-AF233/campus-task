package com.af.user.controller;

import com.af.common.model.ResponseBean;
import com.af.user.api.entity.College;
import com.af.user.api.vo.CollegeMajorVo;
import com.af.user.api.vo.CollegeTeacherVo;
import com.af.user.api.vo.CollegeVo;
import com.af.user.service.CollegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/17 17:44
 */
@Api(tags = "学院信息接口")
@RestController
@RequestMapping("/v1/college")
@AllArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

//    @ApiOperation("查询所有学院信息")
//    @GetMapping("/list")
//    public ResponseBean<List<CollegeVo>> selectAllColleges() {
//        return new ResponseBean<>(collegeService.findAllCollege());
//    }

    @ApiOperation("添加学院")
    @PostMapping("/add")
    public ResponseBean<Boolean> addCollege(@RequestBody College college) {
        return new ResponseBean<>(collegeService.insert(college));
    }

    @ApiOperation("查询所有学院信息")
    @GetMapping("/list")
    public ResponseBean<List<CollegeMajorVo>> findCollegeMajorVoList() {
        return new ResponseBean<>(collegeService.findCollegeMajorVoList());
    }

    @ApiOperation("查询学院下老师信息")
    @GetMapping("/findCollegeAndTeacher")
    public ResponseBean<List<CollegeTeacherVo>> findCollegeTeacherVoList() {
        return new ResponseBean<>(collegeService.findCollegeTeacherVoList());
    }
}
