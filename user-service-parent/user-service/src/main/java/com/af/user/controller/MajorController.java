package com.af.user.controller;

import com.af.common.model.ResponseBean;
import com.af.user.api.entity.Major;
import com.af.user.api.vo.MajorVo;
import com.af.user.service.MajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/18 1:30
 */
@Api(tags = "专业信息接口")
@RequestMapping("/v1/major")
@RestController
@AllArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @ApiOperation("查询学院下所有专业")
    @GetMapping("/list")
    public ResponseBean<List<MajorVo>> findMajorListByCollege(
            @RequestParam(value = "collegeId", required = false) String collegeId,
            @RequestParam(value = "majorId", required = false) String majorId,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "classNo", required = false) Integer classNo) {
        Major major = new Major();
        major.setCollegeId(collegeId);
        major.setMajorId(majorId);
        major.setGrade(grade);
        major.setClassNo(classNo);
        return new ResponseBean<>(majorService.findMajorList(major));
    }
}
